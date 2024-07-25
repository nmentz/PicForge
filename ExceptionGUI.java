import java.io.IOException;
import javax.swing.JOptionPane;

public class ExceptionGUI extends Exception {

    ExceptionGUI(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    ExceptionGUI(Exception e) {

        StackTraceElement[] stackTrace = e.getStackTrace();
        StackTraceElement   element;

        StringBuilder buildErrorMessage = new StringBuilder();

        if (stackTrace.length > 0) {
            element = stackTrace[0];
            buildErrorMessage.append("Exception Thrown At");
            buildErrorMessage.append("\n");
            buildErrorMessage.append("\n\tFile:   ").append(element.getFileName());
            buildErrorMessage.append("\n\tClass:  ").append(element.getClassName());
            buildErrorMessage.append("\n\tMethod: ").append(element.getMethodName());
            buildErrorMessage.append("\n\tLine:   ").append(element.getLineNumber());
        } else {
            buildErrorMessage.append("Exception Thrown, Unable To Determine Stack Trace");
        }

        String error = buildErrorMessage.toString();

        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

    }

}