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
    String path;
    List<BufferedImage> images;

    public ImageSplit(String path) {
        this.path = path;
        this.images = new ArrayList<>();
    }

    public void splitImages() {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path)) {
            grabber.start();

            Java2DFrameConverter converter;
            Frame frame;
            int frameNumber = 0;

            while ((frame = grabber.grabImage()) != null) {
                converter = new Java2DFrameConverter();
                BufferedImage img = converter.convert(frame);
                images.add(img);
                frameNumber++;
            }

            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BufferedImage> getAllImages() {
        return images;
    }
}
