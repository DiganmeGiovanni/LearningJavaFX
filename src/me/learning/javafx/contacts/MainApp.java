package me.learning.javafx.contacts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.learning.javafx.contacts.model.Person;
import me.learning.javafx.contacts.util.PersonListWrapper;
import me.learning.javafx.contacts.view.BirthdayStatisticsController;
import me.learning.javafx.contacts.view.PersonOverviewController;
import me.learning.javafx.contacts.view.RootLayoutController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by giovanni on 2/24/16.
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Person> persons = FXCollections.observableArrayList();

    public MainApp() {

        // Add some sample data
        persons.add(new Person("Hans", "Muster"));
        persons.add(new Person("Ruth", "Mueller"));
        persons.add(new Person("Heinz", "Kurz"));
        persons.add(new Person("Cornelia", "Meier"));
        persons.add(new Person("Werner", "Meyer"));
        persons.add(new Person("Lydia", "Kunz"));
        persons.add(new Person("Anna", "Best"));
        persons.add(new Person("Stefan", "Meier"));
        persons.add(new Person("Martin", "Mueller"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Contacts App");
        this.primaryStage.getIcons().add(new Image("file:resources/img/ic_flat_contacts.png"));

        initRootLayout();
        showPersonOverview();

        this.loadsPersonsFromXMLFile(this.getPersonsFilePath());
    }

    /**
     * Initialize the root layout
     */
    private void initRootLayout() {
        try {
            // Load rootLayout from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.setMainApp(this);

            primaryStage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout
     */
    private void showPersonOverview() {
        try {
            // Load person overview layour
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Put person overview inside root layout
            rootLayout.setCenter(personOverview);

            // Pass a reference to this to the controller
            PersonOverviewController personOverviewController = loader.getController();
            personOverviewController.setMainApp(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the persons file preference, the preference
     * if read from OS registry.
     * @return
     */
    public File getPersonsFilePath() {
        Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
        String filePath = preferences.get("filePath", null);
        return (filePath != null) ? new File(filePath) : null;
    }

    /**
     * Sets the file path to the currently loaded xml file.
     * The path is persisted to the OS registry
     */
    public void savePersonsFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Updates the title of stage
            primaryStage.setTitle("Contacts App - " + file.getName());
        }
        else {
            prefs.remove("filePath");

            // Update the title of stage
            primaryStage.setTitle("Contacts App");
        }
    }

    public void loadsPersonsFromXMLFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
            persons.clear();
            persons.addAll(wrapper.getPersons());

            // Save current file path to preferences
            this.savePersonsFilePath(file);
        }
        catch (JAXBException e) {
            Alert fileError = new Alert(Alert.AlertType.ERROR);
            fileError.setTitle("File error");
            fileError.setHeaderText("An error occurs while processing file");
            fileError.setContentText("Can not load data from: " + file.getAbsolutePath());

            fileError.show();
        }
    }

    public void savePersonsToXMLFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();

            // Wrap out persons list
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(persons);

            // Marshalling persons and saving to file
            m.marshal(wrapper, file);

            // Saving the file path to registry
            this.savePersonsFilePath(file);
        }
        catch (JAXBException e) {
            Alert fileError = new Alert(Alert.AlertType.ERROR);
            fileError.setTitle("File error");
            fileError.setHeaderText("An error occurs while processing file");
            fileError.setContentText("Can not save data from: " + file.getAbsolutePath());

            fileError.show();
        }
    }

    public void showBirthdayStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/BirthdayStatistics.fxml"));
            AnchorPane anchorPane =loader.load();

            Stage statisticsStage = new Stage();
            statisticsStage.setTitle("Birthday Statistics");
            statisticsStage.initModality(Modality.WINDOW_MODAL);
            statisticsStage.initOwner(primaryStage);

            Scene scene = new Scene(anchorPane);
            statisticsStage.setScene(scene);

            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(persons);

            statisticsStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*************************************************************************/
    /** SETTERS AND GETTERS **/

    public ObservableList<Person> getPersons() {
        return persons;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }


    /*************************************************************************/
    /** Static main method **/

    public static void main(String[] args) {
        launch(args);
    }
}
