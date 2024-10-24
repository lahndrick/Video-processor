package lahndrick.videoprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 *
 * @author Lahndrick Hendricks
 */
public class Main {

    public static void main(String[] args) {
        //loading location
        LoadLocation loadLoc = new LoadLocation();
        String loadDir = loadLoc.selectVideo();

        ImageSplit images = new ImageSplit(loadDir);
        images.splitImages();
        List<BufferedImage> extractedImages = images.getAllImages();

        //saving location
        SaveLocation sl = new SaveLocation();
        File saveDir = sl.selectDirectory();
        SaveFile sf = new SaveFile();

        sf.saveImages(saveDir.toString(), extractedImages);
    }
}
