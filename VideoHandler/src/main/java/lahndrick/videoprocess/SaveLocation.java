package lahndrick.videoprocess;

import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JOptionPane;

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
        JOptionPane.showMessageDialog(null, "Select save location");
        int res = jfc.showSaveDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }

        JOptionPane.showMessageDialog(null, "No save location selected.");
        return null;
    }
}
