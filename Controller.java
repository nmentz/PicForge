/**
 * 
 * controller is an intermediary between view and model
 * 
 * 
 */

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

// include swing types so methods can be called
import javax.swing.*;

public class Controller {
    private static Model model;
    private static View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
        
        // give fileExplorer button functionality
        view.addFileExplorerListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                      
                // this method only checks if the user chose a file
                int returnValue = view.getFileChooser().showOpenDialog(view.getFrame());
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    String filePath = view.getFileChooser().getSelectedFile().getAbsolutePath();
                    model.setFilePath(filePath);

                    // set File
                    model.setFile(new File(model.getFilePath()));

                    // does the file exist?
                    if (!model.getFile().exists()) {
                        new ExceptionGUI("File Does Not Exist!");
                        return;
                    }

                    // ImageIO.read() is a checked exception
                    try {
                        model.setBufferedImage(ImageIO.read(model.getFile()));
                    } catch (Exception ioe) {
                        new ExceptionGUI(ioe);
                        return;
                    }
                    
                    // is this actually a picture file?
                    if (model.getBufferedImage() == null) {
                        new ExceptionGUI("Image Is Null");
                        return;
                    }
                    
                    // all checks are passed, model.imageIcon is stored
                    model.setImageIcon(new ImageIcon(model.getBufferedImage()));

                    displayImage(model.getImageIcon(), view.getLabel(), view.getFrame());

                } else {
                    // else is hit when the user cancels image selection
                    return;
                }
            }
        });

        view.getLabel().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                int notches = e.getWheelRotation();

                if (notches != 0) {
                    zoomImage(notches, model.getImageIcon().getIconWidth(), model.getImageIcon().getIconHeight());
                }
            }
        });

    }

    private static void zoomImage(int notches, int originalWidth, int originalHeight) {
        // multiply direction by 10
        int newWidth = originalWidth;
        int newHeight = originalHeight;
        
        if (notches > 0) {
            newWidth  *= 0.9;
            newHeight *= 0.9;
        } else {
            newWidth  *= 1.1;
            newHeight *= 1.1;
        }

        // store buffered imaged
        BufferedImage resizedImage = resizeImage(model.getBufferedImage(), newWidth, newHeight);
        model.setBufferedImage(resizedImage);

        // store image icon
        ImageIcon imageIcon = new ImageIcon(resizedImage);
        model.setImageIcon(imageIcon);

        // display image again
        displayImage(model.getImageIcon(), view.getLabel(), view.getFrame());
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    private static void displayImage(ImageIcon imageIcon, JLabel label, JFrame frame) {

        // attach image to label
        label.setIcon(imageIcon);

        // center lable and add to frame
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
    }

}