package me.learning.javafx.contacts.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import me.learning.javafx.contacts.MainApp;

import java.io.File;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed
 *
 * Created by giovanni on 2/25/16.
 */
public class RootLayoutController {

    private MainApp mainApp;

    /**
     * Creates an empty contacts file
     */
    @FXML
    private void handleNew() {
        mainApp.getPersons().clear();
        mainApp.savePersonsFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set filter for extensions
        FileChooser.ExtensionFilter filter = new FileChooser
                .ExtensionFilter("XML Files(.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            mainApp.loadsPersonsFromXMLFile(file);
        }
    }

    /**
     * Saves the currently open file. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File personsFile = mainApp.getPersonsFilePath();
        if (personsFile != null) {
            mainApp.savePersonsToXMLFile(personsFile);
        }
        else {

        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    public void handleSaveAs() {
        FileChooser chooser = new FileChooser();

        // Filter only xml files
        FileChooser.ExtensionFilter filter =
                new FileChooser.ExtensionFilter("XML Files(.xml)", "*.xml");

        chooser.getExtensionFilters().add(filter);

        File targetFile = chooser.showSaveDialog(mainApp.getPrimaryStage());
        if (!targetFile.getPath().endsWith(".xml")) {
            targetFile = new File(targetFile.getPath() + ".xml");
        }

        mainApp.savePersonsToXMLFile(targetFile);
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Contacts App");
        alert.setHeaderText("About");
        alert.setContentText("Author: Giovanni Aguirre\nWebsite: http://lineaporlinea.wordpress.com");

        alert.showAndWait();
    }

    @FXML
    private void handleShowStatistics() {
        mainApp.showBirthdayStatistics();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
