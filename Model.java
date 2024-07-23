/**
 * model holds the data and core functionality of PicForge
 * 
 * data:
 *    path to current picture being changed
 * 
 * functionality:
 *    functions that change pictures with native algos
 */

import java.io.File;

public class Model {

      private static java.io.File filePath;

      Model() {
            System.out.println("model constructor");
      }

      public java.io.File getFilePath() {
            return filePath;
      }
}