/**
 * model holds the data and core functionality of PicForge
 * 
 * data:
 *    path to current picture being changed
 * 
 * functionality:
 *    functions that change pictures with native algos
 */

import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Model {

      private static BufferedImage bufferedImage;
      private static String        filePath;
      private static ImageIcon     imageIcon;

      Model() {
            System.out.println("model constructor");
      }

      public BufferedImage getBufferedImage() {
            return bufferedImage;
      }

      public String getFilePath() {
            return filePath;
      }

      public ImageIcon getImageIcon() {
            return imageIcon;
      }

}