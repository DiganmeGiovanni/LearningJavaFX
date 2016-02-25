package me.learning.javafx.contacts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.learning.javafx.contacts.model.Person;
import me.learning.javafx.contacts.view.PersonOverviewController;

import java.io.IOException;

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
        this.primaryStage.getIcons().add(new Image("file:resources/img/ic_contacts_flat.png"));

        initRootLayout();
        showPersonOverview();
    }

    /**
     * Initialize the root layour
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
