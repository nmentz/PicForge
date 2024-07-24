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
        this.view = view;
        
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
                  
                // this method does not return until the dialog is closed
                int returnValue = fileChooser.showOpenDialog(frame);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {

                    // this is starting to look like spaghetti
                    // after the if else, try splitting into functions:
                    //      spawnImage(...)
                    //      scaleImage(...)
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    File file = new File(filePath);

                    try {
                        bufferedImage = ImageIO.read(file);
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                    
                    imageIcon = new ImageIcon(bufferedImage);
                    
                    label.setIcon(imageIcon);

                    frame.setLayout(new BorderLayout());
                    frame.add(label, BorderLayout.CENTER);

                } else {
                    // create error class
                    System.out.println("No file chosen");
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


}