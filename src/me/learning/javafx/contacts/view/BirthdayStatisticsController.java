package me.learning.javafx.contacts.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import me.learning.javafx.contacts.model.Person;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

/**
 *
 * Created by giovanni on 2/25/16.
 */
public class BirthdayStatisticsController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(months);

        xAxis.setCategories(monthNames);
    }

    public void setPersonData(List<Person> persons) {
        int[] counterMonth = new int[12];
        for(Person person: persons) {
            int monthIndex = person.getBirthday().getMonthValue() - 1;
            counterMonth[monthIndex]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series
        for (int i = 0; i < 12; i++) {
            series.getData().add(
                    new XYChart.Data<>(monthNames.get(i), counterMonth[i])
            );
        }

        barChart.getData().add(series);
    }
}
