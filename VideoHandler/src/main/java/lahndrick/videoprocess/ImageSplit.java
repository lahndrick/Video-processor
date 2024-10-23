package lahndrick.videoprocess;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Lahndrick Hendricks
 */
public class ImageSplit {
    
    List<BufferedImage> images;
    
    public ImageSplit(String path) {
        this.images = new ArrayList<>();
        splitImages(path);
    }
    
    private void splitImages(String path) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path)) {
            grabber.start();

            Java2DFrameConverter converter = new Java2DFrameConverter();
            Frame frame;
            int frameNumber = 0;

            while ((frame = grabber.grabImage()) != null) {
                BufferedImage img = converter.convert(frame);
                images.add(img);
                frameNumber++;
            }
            
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<BufferedImage> getAllImages () {
        return images;
    }
    
        public void saveImages(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        int count = 1;
        for (BufferedImage img : images) {
            File outputFile = new File(dir, "image_" + count++ + ".png");
            try {
                ImageIO.write(img, "png", outputFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
