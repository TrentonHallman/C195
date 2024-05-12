package controller;

import DBAccess.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.MonthType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/** Controls Reports Screen Functionality
 *
 * @author Trenton Hallman
 */
public class ReportsController implements Initializable {
    public TableColumn<Object, Object> IDCol;
    public TableColumn<Object, Object> TitleCol;
    public TableColumn<Object, Object> TypeCol;
    public TableColumn<Object, Object> DescriptionCol;
    public TableColumn StartCol;
    public TableColumn EndCol;
    public TableColumn CustomerIDCol;
    public ComboBox<String> SelectContact;
    public TableView<Appointment> AppointmentsTable;
    public ComboBox<String> SelectCustomer;
    public Button BackBtn;
    public TableView<MonthType> MonthTypeTable;
    public TableColumn MonthTypeCol;
    public TableColumn MonthNumberCol;
    public TableView CustomerAppointmentsTable;
    public TableColumn CustomerAppointmentIDCol;
    public TableColumn CustomerTitleCol;
    public TableColumn CustomerIDColumn;
    public TableColumn MonthCol;

    /**
     * Code that runs on the selection of a Customer from the Customer Combo Box
     */
    public void OnSelectCustomer() {
        String SelectedCustomer = SelectCustomer.getValue();
        CustomerAppointmentsTable.setItems(DBAppointments.getAppointmentsByCustomerID(DBCustomers.getCustomerID(SelectedCustomer)));
    }

    /**
     * Code that runs on the selection of a Contact from the Contact Combo Box
     *
     * @throws SQLException
     */
    public void OnSelectContact() throws SQLException {
        String SelectedContact = SelectContact.getValue();
        ObservableList<Appointment> AllAppointments = DBAppointments.getAllAppointments();
        if (!Objects.equals(SelectedContact, "All")) {
            AppointmentsTable.setItems(DBAppointments.getAppointmentsByContactID(DBAppointments.getContactID(SelectedContact)));
        } else {
            AppointmentsTable.setItems(AllAppointments);
        }

    }

    /** Code that runs on initialization of the screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> AllAppointments = DBAppointments.getAllAppointments();
        ObservableList<MonthType> AllMonthTypes = DBAppointments.getAppointmentsMonthType();

        ObservableList<String> AllContacts = DBContacts.getAllContactNames();
        AllContacts.add("All");
        
        ObservableList<String> AllMonths = FXCollections.observableArrayList();
        AllMonths.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        ObservableList<String> AllCustomers = DBCustomers.getAllCustomerNames();

        IDCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        TitleCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentTitle"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));
        TypeCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentType"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentStart"));
        EndCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentEnd"));
        CustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));

        MonthCol.setCellValueFactory(new PropertyValueFactory<>("Month"));
        MonthTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        MonthNumberCol.setCellValueFactory(new PropertyValueFactory<>("TypeSize"));

        CustomerAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        CustomerTitleCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentTitle"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));

        MonthTypeTable.setItems(AllMonthTypes);
        AppointmentsTable.setItems(AllAppointments);
        SelectContact.setItems(AllContacts);
        SelectCustomer.setItems(AllCustomers);

        SelectContact.setValue("All");
    }

    /** Back button functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnBackBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
