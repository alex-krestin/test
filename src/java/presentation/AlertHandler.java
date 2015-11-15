package presentation;

import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;


public class AlertHandler  {

    public static void showAlert(Alert.AlertType alertType, String title, String headerText, String contextText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String contextText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }

    public static Boolean showAlertConfirmationDialog(String title, String question,
                                                      String contextTest, String confirmBtn) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(question);
        alert.setContentText(contextTest);
        alert.initStyle(StageStyle.UTILITY);

        ButtonType confirmButton = new ButtonType(confirmBtn, ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annulla", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == confirmButton;
    }

    public static void showException(String contextText, Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);

        // Get exception Stacktrace
        String exceptionText = getStringFromStackTrace(ex);

        //TODO Change label
        Label label = new Label("Informazione:");

        // Create expandable exception
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable exception into the dialog pane
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    private static String getStringFromStackTrace(Throwable ex)
    {
        if (ex==null)
        {
            return "";
        }
        StringWriter str = new StringWriter();
        PrintWriter writer = new PrintWriter(str);
        try
        {
            ex.printStackTrace(writer);
            return str.getBuffer().toString();
        }
        finally
        {
            try
            {
                str.close();
                writer.close();
            }
            catch (IOException e)
            {
                //ignore
            }
        }
    }
}
