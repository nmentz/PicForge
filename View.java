/**
 * all gui objects are stored in view
 */

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class View {

    // ui components
    private static JFrame frame;
    private static JLabel label;
    private static JButton fileExplorer;
    private static JButton actionsSubMenu;
    private static JFileChooser fileChooser;

    View() {
        // create componenets
        frame = new JFrame("PicForge");
        frame.setSize(900, 700); 
        frame.setLayout(null); // Using no layout managers
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        label = new JLabel();

        fileExplorer = new JButton("Open");
        fileExplorer.setBounds(0, 0, 125, 30);

        actionsSubMenu = new JButton("Actions");
        actionsSubMenu.setBounds(125, 0, 125, 30);

        fileChooser = new JFileChooser();

        // add components
        frame.add(label);
        frame.add(fileExplorer);
        frame.add(actionsSubMenu);
    }

    // return private vars
    public JFrame getFrame() {
        return frame;
    }

    public JLabel getLabel() {
        return label;
    }

    public JButton getFileExplorer() {
        return fileExplorer;
    }

    public JButton getActionsSubMenu() {
        return actionsSubMenu;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    // add event listeners to buttons
    public void addFileExplorerListener(ActionListener listener) {
        fileExplorer.addActionListener(listener);
    }

    public void addActionsSubMenuLister(ActionListener listener) {
        actionsSubMenu.addActionListener(listener);
    }
}
