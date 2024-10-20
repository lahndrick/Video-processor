/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lahndrick.videoprocess;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

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
            grabber.start(); // Start the grabber

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
}
