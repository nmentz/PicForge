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
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view  = view;
        
        // give fileExplorer button functionality
        view.addFileExplorerListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pull view's componenents onto stack for manipulation
                JFrame       frame       = view.getFrame();
                JFileChooser fileChooser = view.getFileChooser();
                JLabel       label       = view.getLabel();
                      
                // pull model's data onto stack for manipulation
                String        filePath      = model.getFilePath();
                BufferedImage bufferedImage = model.getBufferedImage();
                ImageIcon     imageIcon     = model.getImageIcon();
                  
                // this method only checks if the user chose a file
                int returnValue = fileChooser.showOpenDialog(frame);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    File file = new File(filePath);

                    // does the file exist?
                    if (!file.exists()) {
                        new ExceptionGUI("File Does Not Exist!");
                        return;
                    }

                    // ImageIO.read() is a checked exception
                    try {
                        bufferedImage = ImageIO.read(file);
                    } catch (Exception ioe) {
                        new ExceptionGUI(ioe);
                        return;
                    }
                    
                    // is this actually a picture file?
                    if (bufferedImage == null) {
                        new ExceptionGUI("Image Is Null");
                        return;
                    }
                    
                    // all checks are passed, model.imageIcon is stored
                    imageIcon = new ImageIcon(bufferedImage);
                    
                    displayImage(imageIcon, label, frame);

/*
        the issue is that not all images are the same size,
        so the image needs to have scale functionality. 

        if dimensions are larger than x || y, zoom out z times
*/

                } else {
                    // else is hit when the user cancels image selection
                    return;
                }
            }
        });


/*
        // actions menu to run jni image processing
        view.addActionsSubMenuListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });


        // allow mouse to click and drag image to move
        view.addClickDragImageListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });

        // use scroll wheel to zoom
        view.addScrollWheelZoomListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

        });
*/

    }

    private static void displayImage(ImageIcon imageIcon, JLabel label, JFrame frame) {

        // attach image to label
        label.setIcon(imageIcon);

        // center lable and add to frame
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
    }

}