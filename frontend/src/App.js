import React, { useState } from "react";
import "./App.css";

function App() {
  const [uploadProgress, setUploadProgress] = useState(0);
  const [uploading, setUploading] = useState(false);
  const [videoFile, setVideoFile] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");

  const maxFileSize = 10 * 1024 * 1024; // 10 MB

  const handleFileChange = (event) => {
    const file = event.target.files ? event.target.files[0] : null;

    // Ensure file exists before setting it
    if (file) {
      setVideoFile(file);

      if (file.size > maxFileSize) {
        setErrorMessage(`File too large! Maximum allowed size is ${maxFileSize / 1024 / 1024} MB.`);
      } else {
        setErrorMessage("");
      }
    } else {
      setVideoFile(null);
      setErrorMessage("");
    }
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
        const blob = xhr.response;
        const downloadLink = document.createElement("a");
        const url = window.URL.createObjectURL(blob);
        downloadLink.href = url;
        downloadLink.download = "processed_images.zip"; // Set the default name for the zip file
        downloadLink.click();
        window.URL.revokeObjectURL(url); // Release the object URL
        alert("Upload successful! Zip file will be downloaded.");
      } else if (xhr.status === 417) {
        alert(xhr.responseText);
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

    xhr.responseType = "blob";

    xhr.send(formData);
  };

  return (
    <div className="App">
      <h1>Upload Video for Processing</h1>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={handleFileChange} accept="video/*" />
        <br />
        {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
        <button type="submit" disabled={uploading || !videoFile || errorMessage}>
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
