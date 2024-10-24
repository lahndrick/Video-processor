package lahndrick.videoprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Lahndrick Hendricks
 */
public class SaveFile {

    public SaveFile() {

    }

    public void saveImages(String directoryPath, List<BufferedImage> images) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return;
            }
        }

        for (int count = 0; count < images.size(); count++) {
            BufferedImage img = images.get(count);
            File outputFile = new File(dir, "image_" + (count + 1) + ".png");
            try {
                ImageIO.write(img, "png", outputFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveVideo(String directoryPath, File video) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File destinationFile = new File(dir, video.getName());
        try {
            Files.copy(video.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
