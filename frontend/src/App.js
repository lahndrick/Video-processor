import React, { useState } from "react";
import "./App.css";

function App() {
  const [uploadProgress, setUploadProgress] = useState(0);
  const [uploading, setUploading] = useState(false);
  const [videoFile, setVideoFile] = useState(null);

  const handleFileChange = (event) => {
    setVideoFile(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!videoFile) return;

    setUploading(true);
    const formData = new FormData();
    formData.append("video", videoFile);

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/video/process", true);

    // Track upload progress
    xhr.upload.onprogress = (event) => {
      if (event.lengthComputable) {
        const percent = Math.round((event.loaded / event.total) * 100);
        setUploadProgress(percent);
      }
    };

    xhr.onload = () => {
      if (xhr.status === 200) {
        // Create a Blob from the response
        const blob = xhr.response;
        const downloadLink = document.createElement("a");
        const url = window.URL.createObjectURL(blob);
        downloadLink.href = url;
        downloadLink.download = "processed_images.zip"; // Set the default name for the zip file
        downloadLink.click();
        window.URL.revokeObjectURL(url); // Release the object URL
        alert("Upload successful! Zip file will be downloaded.");
      } else {
        alert("Upload failed!");
      }
      setUploading(false);
    };

    xhr.onerror = () => {
      console.error("Error during file upload.");
      alert("An error occurred during the upload.");
      setUploading(false);
    };

    // Set responseType to 'blob' to handle the binary data
    xhr.responseType = "blob";

    xhr.send(formData);
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
