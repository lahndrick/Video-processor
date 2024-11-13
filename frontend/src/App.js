import React, { useState } from "react";
import "./App.css";

function App() {
  const [uploadProgress, setUploadProgress] = useState(0);
  const [uploading, setUploading] = useState(false);
  const [videoFile, setVideoFile] = useState(null);

  const handleFileChange = (event) => {
    setVideoFile(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!videoFile) return;

    setUploading(true);
    const formData = new FormData();
    formData.append("video", videoFile);

    try {
      const response = await fetch("http://localhost:8080/video/process", {
        method: "POST",
        body: formData,
        headers: {
          // Add any headers if needed
        },
        onUploadProgress: (event) => {
          if (event.lengthComputable) {
            const percent = Math.round((event.loaded / event.total) * 100);
            setUploadProgress(percent);
          }
        },
      });

      if (response.ok) {
        alert("Upload successful!");
      } else {
        alert("Upload failed!");
      }
    } catch (error) {
      console.error("Error during file upload:", error);
      alert("An error occurred during the upload.");
    } finally {
      setUploading(false);
    }
  };

  return (
    <div className="App">
      <h1>Upload Video for Processing</h1>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={handleFileChange} accept="video/*" />
        <br />
        <button type="submit" disabled={uploading}>
          {uploading ? "Uploading..." : "Upload Video"}
        </button>
      </form>
      
      {uploading && (
        <div className="progress-bar-container">
          <div
            className="progress-bar"
            style={{ width: `${uploadProgress}%` }}
          />
          <p>{uploadProgress}%</p>
        </div>
      )}
    </div>
  );
}

export default App;
