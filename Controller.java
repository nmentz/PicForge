/**
 * 
 * controller is an intermediary between view and model
 * 
 * 
 * controller will have the events
 */

import java.awt.event.*;
import java.awt.*;
import java.io.File;

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
                  java.io.File file        = model.getFilePath();
                    
                  // Show open dialog; this method does not return until the dialog is closed
                  int returnValue = fileChooser.showOpenDialog(frame);
                  
                  if (returnValue == JFileChooser.APPROVE_OPTION) {
                        // The user selected a file
                        file = fileChooser.getSelectedFile();

                        // Clear existing components in the center of BorderLayout then center
                        frame.add(label, BorderLayout.CENTER);

                        displayImage(file, frame, label);

                        //centerImage(file, frame, label);

                        System.out.println("Selected file: " + file.getAbsolutePath());
                  } else {
                        System.out.println("No file chosen");
                  }
            }
        });



        // add event listener to clig and drag image around, also make it so you can scroll and zoom in
    }



/*
    private void updateModel() {
        model.setText(view.getTextFieldText());
    }

    private void updateView() {
        view.setLabelText(model.getText());
    }
*/


    private static void displayImage(java.io.File file, JFrame frame, JLabel label) {
        // Load image from file and display in imageLabel
        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
        Image image = icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_SMOOTH);

        int pictureWidth   = icon.getIconWidth();
        int pictureHeight  = icon.getIconHeight();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        System.out.println("pictureWidth = " + pictureWidth + "\n pictureHeight = " + pictureHeight + "\nframeWidth = " + frameWidth + "\nframeHeight = " + frameHeight);

        Point printCoords = centerImage(pictureWidth, pictureHeight, frameWidth, frameHeight);

        System.out.println("print coordsx = " + printCoords.x + "\nprintCoords.y = " + printCoords.y);

        // Update imageLabel (since imageLabel is static, we can reference it here)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                label.setIcon(new ImageIcon(image));
                label.setBounds(printCoords.x, printCoords.y, icon.getIconWidth(), icon.getIconHeight());
            }
        });

    }


    //static Point centerImage(int pictureWidth, int pictureHeight, int frameWidth, int frameHeight) {
    static Point centerImage(int frameWidth, int frameHeight, int pictureWidth, int pictureHeight) {
        double scale = Math.min((double) frameWidth / pictureWidth, (double) frameHeight / pictureHeight);

        int newWidth = (int) (pictureWidth * scale * -1);
        int newHeight = (int) (pictureHeight * scale * -1);

        double x = (frameWidth - newWidth) / 2;
        double y = (frameHeight - newHeight) / 2;

        return new Point((int)x, (int)y);
    }



}