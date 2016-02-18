package main.java.me.learning.javafx.shoppinglist;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * Created by giovanni on 2/17/16.
 */
public class ShoppingListController extends Application implements Initializable {

    @FXML
    private Label labelAmountApples;

    @FXML
    private Label labelAmountOranges;

    @FXML
    private Label labelAmountPotatoes;

    @FXML
    private Label labelAmountTotal;

    @FXML
    private PieChart shoppingPieChart;

    private IntegerProperty amountPotatoes = new SimpleIntegerProperty();
    private IntegerProperty amountOranges  = new SimpleIntegerProperty();
    private IntegerProperty amountApples   = new SimpleIntegerProperty();
    private IntegerProperty amountTotal    = new SimpleIntegerProperty();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent rootParent = FXMLLoader.load(getClass().getResource("/main/resources/views/shoppingList.fxml"));
        Scene scene = new Scene(rootParent, 500, 550);

        primaryStage.setTitle("Shopping list");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Configure labels to update when properties change
        labelAmountPotatoes.textProperty().bind(amountPotatoes.asString());
        labelAmountOranges.textProperty().bind(amountOranges.asString());
        labelAmountApples.textProperty().bind(amountApples.asString());

        // Doing math inline with properties
        amountTotal.bind(amountPotatoes.add(amountOranges).add(amountApples));
        labelAmountTotal.textProperty().bind(amountTotal.asString());

        // Initialize data for pie chart
        PieChart.Data dataPotatoes = new PieChart.Data("Potatoes", 0);
        PieChart.Data dataOranges  = new PieChart.Data("Oranges", 0);
        PieChart.Data dataApples   = new PieChart.Data("Apples", 0);

        // Set data to pie chart
        ObservableList<PieChart.Data> pieChartDataList =
                FXCollections.observableArrayList(dataPotatoes, dataOranges, dataApples);
        shoppingPieChart.setData(pieChartDataList);

        // Configure pie chart data to update when properties change
        dataPotatoes.pieValueProperty().bind(amountPotatoes);
        dataOranges.pieValueProperty().bind(amountOranges);
        dataApples.pieValueProperty().bind(amountApples);
    }

    public void addPotato() {
        amountPotatoes.set(amountPotatoes.get() + 1);
    }

    public void subtractPotato() {
        if (amountPotatoes.greaterThan(0).get()) {
            amountPotatoes.set(amountPotatoes.get() - 1);
        }
    }

    public void addOrange() {
        amountOranges.set(amountOranges.get() + 1);
    }

    public void subtractOrange() {
        if (amountOranges.greaterThan(0).get()) {
            amountOranges.set(amountOranges.get() - 1);
        }
    }

    public void addApple() {
        amountApples.set(amountApples.get() + 1);
    }

    public void subtractApple() {
        if (amountApples.greaterThan(0).get()) {
            amountApples.set(amountApples.get() - 1);
        }
    }


    /*************************************************************************/

    public static void main(String[] args) {
        launch(args);
    }

}
