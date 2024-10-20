package lahndrick.videoprocess;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author Lahndrick Hendricks
 */
public class VideoProcess {

    public static void main(String[] args) {
        System.out.println("test test test");

        ImageSplit images = new ImageSplit("C:\\Users\\Lahndrick Hendricks\\Desktop\\testvideo.mp4");

        List<BufferedImage> extractedImages = images.getAllImages();
        System.out.println("Number of images extracted: " + extractedImages.size());
    }
}
