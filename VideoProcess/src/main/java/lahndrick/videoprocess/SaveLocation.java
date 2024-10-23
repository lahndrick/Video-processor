package lahndrick.videoprocess;

import javax.swing.JFileChooser;
import java.io.File;

/**
 *
 * @author Lahndrick Hendricks
 */
public class SaveLocation {
    private JFileChooser jfc;
    
    public SaveLocation() {
        jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
    
    public File selectDirectory() {
        int res = jfc.showOpenDialog(null);
        
        if (res == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }
        
        return null;
    }
}
