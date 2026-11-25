import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class RecursiveListerFrame extends JFrame {

    private JTextArea fileDisplayArea;
    private JButton startButton;
    private JButton quitButton;

    public RecursiveListerFrame() {
        setTitle("Recursive File Lister");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Set a clean, modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 1. Title JLabel
        JLabel titleLabel = new JLabel("Recursive Directory File Lister", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 2. JTextArea with JScrollPane
        fileDisplayArea = new JTextArea();
        fileDisplayArea.setEditable(false);
        fileDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        fileDisplayArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(fileDisplayArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 3. Control Panel with Start and Quit Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        startButton = new JButton("Select Directory");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        startButton.setPreferredSize(new Dimension(160, 35));
        startButton.addActionListener(e -> chooseAndListDirectory());

        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        quitButton.setPreferredSize(new Dimension(120, 35));
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void chooseAndListDirectory() {
        JFileChooser chooser = new JFileChooser();
        // Set default directory to the user's current project directory
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        // Restrict selection to directories only
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Choose a Directory to Scan");

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = chooser.getSelectedFile();
            fileDisplayArea.setText(""); // Clear previous output
            fileDisplayArea.append("Listing files in: " + selectedDir.getAbsolutePath() + "\n");
            fileDisplayArea.append("====================================================================\n\n");

            // Start the recursive search
            listFilesRecursive(selectedDir, 0);

            // Scroll back to the top after loading
            fileDisplayArea.setCaretPosition(0);
        }
    }

    /**
     * Recursively traverses directories and appends file paths to the JTextArea.
     *
     * @param currentFile The current File or Directory being inspected.
     * @param depth       Indentation level to visually show folder hierarchy.
     */
    private void listFilesRecursive(File currentFile, int depth) {
        String indent = "    ".repeat(depth);

        if (currentFile.isDirectory()) {
            fileDisplayArea.append(indent + "[DIR] " + currentFile.getName() + "\n");
            File[] files = currentFile.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Recursive call on sub-folders and files
                    listFilesRecursive(file, depth + 1);
                }
            }
        } else {
            // Terminal condition: it is a file, so display it
            fileDisplayArea.append(indent + currentFile.getName() + "\n");
        }
    }
}