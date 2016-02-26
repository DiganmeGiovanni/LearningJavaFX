package me.learning.javafx.contacts.model;

import com.dooapp.fxform.FXForm;
import javafx.beans.property.*;
import javafx.scene.control.Label;
import me.learning.javafx.contacts.util.LocalDateAdapter;
import me.learning.javafx.contacts.util.Utilities;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * Created by giovanni on 2/24/16.
 */
public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * Default constructor.
     */
    public Person() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(1999, 2, 21));
    }

    /**
     * Set custom labels texts for each form input
     * @param fxForm
     */
    public void customizeLabels(FXForm fxForm) {

        Label labelFirstName = (Label) fxForm.lookup("#firstName-form-label");
        labelFirstName.textProperty().unbind();
        labelFirstName.setText("First Name");

        Label labelLastName = (Label) fxForm.lookup("#lastName-form-label");
        labelLastName.textProperty().unbind();
        labelLastName.setText("Last Name");

        Label labelStreet = (Label) fxForm.lookup("#street-form-label");
        labelStreet.textProperty().unbind();
        labelStreet.setText("Street");

        Label labelPostalCode = (Label) fxForm.lookup("#postalCode-form-label");
        labelPostalCode.textProperty().unbind();
        labelPostalCode.setText("Postal Code");

        Label labelCity = (Label) fxForm.lookup("#city-form-label");
        labelCity.textProperty().unbind();
        labelCity.setText("City");

        Label labelBirthday = (Label) fxForm.lookup("#birthday-form-label");
        labelBirthday.textProperty().unbind();
        labelBirthday.setText("Birthday");
    }

    /**
     * Check model validation status through
     * hibernate validator
     * @return
     */
    public boolean isValid() {
        Validator validator = Utilities.validatorFactory.getValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(this);

        return constraintViolations.size() == 0;
    }

    @NotNull(message = "First name can not be empty")
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    @NotNull(message = "Please enter the last name")
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
}
