package me.learning.javafx.contacts.view;

import com.dooapp.fxform.FXForm;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import me.learning.javafx.contacts.MainApp;
import me.learning.javafx.contacts.model.Person;
import me.learning.javafx.contacts.util.Utilities;

import java.util.Optional;

/**
 * Created by giovanni on 2/24/16.
 */
public class PersonOverviewController {

    private MainApp mainApp;

    ///////////////////////////////////////////////////////////////////////////
    // FXML Nodes

    @FXML
    private TableView<Person> tablePersons;
    @FXML
    private TableColumn<Person, String> tableColFirstName;
    @FXML
    private TableColumn<Person, String> tableColLastName;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelBirthday;
    @FXML
    private Label labelLastName;
    @FXML
    private Label labelStreet;
    @FXML
    private Label labelCity;
    @FXML
    private Label labelPostalCode;

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Called when user clicks on the delete button
     */
    @FXML
    private void handleDelete() {
        int selectedIndex = tablePersons.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tablePersons.getItems().remove(selectedIndex);
        }
        else {
            Alert nothingSelectedAlert = new Alert(Alert.AlertType.ERROR);
            nothingSelectedAlert.setTitle("No selection");
            nothingSelectedAlert.setHeaderText("No person selected");
            nothingSelectedAlert.setContentText("You must select a person from the table to delete");

            nothingSelectedAlert.showAndWait();
        }
    }

    /**
     * Called when user clicks on the edit button
     */
    @FXML
    private void handleEdit() {

    }

    /**
     * Called when user clicks on the new button
     */
    @FXML
    private void handleNew() {
        // Constructs form
        Person person = new Person();
        FXForm personForm = new FXForm(person);
        person.customizeLabels(personForm);

        // Constructs dialog for the form
        Dialog dialog = new Dialog();
        dialog.setTitle("Add Person");
        dialog.setHeaderText("Creates a new Person");

        // Set the button types
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Enable ok buttons only when person is valid
        ObjectProperty<Person> objectProperty = new SimpleObjectProperty<>(person);
        objectProperty.addListener((observable, oldValue, newValue) -> {
            System.out.println("Object changing");
            okButton.setDisable(!newValue.isValid());
        });


        // Constructs form container
        HBox formContainer = new HBox(10);
        formContainer.setPadding(new Insets(25, 75, 25, 75));
        formContainer.getChildren().add(personForm);

        // Set content to dialog
        dialog.getDialogPane().setContent(formContainer);

        // Configure the results converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return true;
            }

            return false;
        });

        // Display and wait for result
        Optional result = dialog.showAndWait();
        result.ifPresent(accepted -> {
            if (accepted.equals(true)) {
                tablePersons.getItems().add(person);
            }
        });
    }

    /**
     * This method is automatically called after fxml file
     * has been loaded
     */
    @FXML
    public void initialize() {

        // Configure table columns binding to Person properties
        tableColFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        tableColLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Show cleared data by default
        this.showPersonDetails(null);

        // Display the selected person details
        tablePersons.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showPersonDetails(newValue);
        });

        tablePersons.setEditable(true);
        tableColFirstName.setEditable(true);
    }

    /**
     * Fills all the labels to show details about the person.
     * If specified person is null, all labels are cleared
     *
     * @param person The person to show | null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            labelFirstName.setText(person.getFirstName());
            labelLastName.setText(person.getLastName());
            labelStreet.setText(person.getStreet());
            labelPostalCode.setText(Integer.toString(person.getPostalCode()));
            labelCity.setText(person.getCity());
            labelBirthday.setText(Utilities.formatDate(person.getBirthday()));
        }
        else {
            labelFirstName.setText("");
            labelLastName.setText("");
            labelStreet.setText("");
            labelPostalCode.setText("");
            labelCity.setText("");
            labelBirthday.setText("");
        }
    }


    /*************************************************************************/
    /** SETTERS AND GETTERS **/

    /**
     * This must be called by parent/creator to give a reference
     * to main app
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Set items to table persons
        tablePersons.setItems(mainApp.getPersons());
    }
}
