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

    }

    private static void displayImage(ImageIcon imageIcon, JLabel label, JFrame frame) {

        // attach image to label
        label.setIcon(imageIcon);

        // center lable and add to frame
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);

        //if image is bigger than 700x900, make window size of image, otherwise, make it 700x900
        if (imageIcon.getIconWidth() > frame.getWidth() || imageIcon.getIconHeight() > frame.getHeight()) {
            frame.pack();
        } else {
            frame.setSize(900, 700);
        }
    }

}