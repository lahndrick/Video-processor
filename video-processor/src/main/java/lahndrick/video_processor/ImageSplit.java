package lahndrick.video_processor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ImageSplit {

    private static final String TEMP_DIR = "C:/video-uploads/";

    public byte[] splitVideoToZip(MultipartFile videoFile) throws IOException, InterruptedException {
        File videoTempFile = new File(TEMP_DIR + videoFile.getOriginalFilename());
        videoFile.transferTo(videoTempFile);

        //ByteArrayOutputStream to hold the zip file data in memory
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

        //ffmpeg command to extract frames
        String command = String.format(
                "ffmpeg -i \"%s\" -vf fps=1 -f image2pipe -vcodec mjpeg -",
                videoTempFile.getAbsolutePath()
        );

        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        int imageIndex = 0;

        // read images from the video stream and write them to the ZIP
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            String imageFileName = String.format("frame_%04d.jpg", imageIndex++);
            ZipEntry zipEntry = new ZipEntry(imageFileName);
            zipOutputStream.putNextEntry(zipEntry);

            zipOutputStream.write(buffer, 0, bytesRead);

            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
        inputStream.close();

        videoTempFile.delete();

        return byteArrayOutputStream.toByteArray();
    }
}
