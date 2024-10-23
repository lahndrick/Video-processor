package lahndrick.videoprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 *
 * @author Lahndrick Hendricks
 */
public class VideoProcess {

    public static void main(String[] args) {
        LoadLocation loadLoc = new LoadLocation();
        String video = loadLoc.selectVideo();
        
        ImageSplit images = new ImageSplit(video);

        List<BufferedImage> extractedImages = images.getAllImages();
        System.out.println("Number of images extracted: " + extractedImages.size());
        
        SaveLocation sl = new SaveLocation();
        File selectedDir = sl.selectDirectory();
        
        if(selectedDir != null) {
            images.saveImages(selectedDir.getAbsolutePath());
            System.out.println("Images saved to: " + selectedDir.getAbsolutePath());
        } else {
            System.out.println("No directory selected.");
        }
    }
}
