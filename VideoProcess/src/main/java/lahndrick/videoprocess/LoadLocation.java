package lahndrick.videoprocess;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Lahndrick Hendricks
 */
public class LoadLocation {
    private JFileChooser jfc;

    public LoadLocation() {
        jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public String selectVideo() {
        JOptionPane.showMessageDialog(null, "Select video to split");
        int res = jfc.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile().toString();
        }

        JOptionPane.showMessageDialog(null, "No video selected.");
        return null;
    }
}
