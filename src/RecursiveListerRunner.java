import javax.swing.SwingUtilities;

public class RecursiveListerRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecursiveListerFrame frame = new RecursiveListerFrame();
            frame.setVisible(true);
        });
    }
}