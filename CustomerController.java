package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import helper.AppointmentQuery;
import helper.CustomerQuery;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controls Customer Controller Screen operations
 *
 * @author Trenton Hallman
 */
public class CustomerController implements Initializable {
    public TableView<Customer> CustomerTable;
    public TableColumn<Object, Object> CustomerIdCol;
    public TableColumn<Object, Object> CustomerNameCol;
    public TableColumn<Object, Object> CustomerAddressCol;
    public TableColumn<Object, Object> CustomerPostalCol;
    public TableColumn<Object, Object> CustomerPhoneCol;
    public TableColumn<Object, Object> CustomerStateCol;
    public Button EditCustomer;
    public Button AddCustomer;
    public Button DeleteCustomer;
    static Customer CustomerMod;
    public Button CustomerBackBtn;

    /** Gets the selected customer to be transferred to the Edit Customer Screen
     *
     * @return CustomerMod
     * @param CustomerTransfer
     */
    public static Customer GetSelectedCustomer(Customer CustomerTransfer) {
        return CustomerMod;
    }

    /** Code that runs on initialization of the screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customer> AllCustomers = DBCustomers.getAllCustomers();

        CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        CustomerAddressCol.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        CustomerPostalCol.setCellValueFactory(new PropertyValueFactory<>("CustomerPostal"));
        CustomerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
        CustomerStateCol.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));

        CustomerTable.setItems(AllCustomers);
    }

    /** Loads Edit Customer screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnEditCustomer(ActionEvent actionEvent) throws IOException {
        CustomerMod = CustomerTable.getSelectionModel().getSelectedItem();
        EditCustomerController.CustomerTransfer(CustomerMod);
        if (CustomerMod != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/EditCustomerScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            stage.show();
        } else if (CustomerMod == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Must select customer");
            alert.showAndWait();
        }
    }

    /** Loads Add Customer screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /** Deletes Customer upon pushing button
     *
     * @throws SQLException
     */
    public void OnDeleteCustomer() throws SQLException {
        CustomerMod = CustomerTable.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> CustomerAppointments = DBAppointments.getCustomerAppointments(CustomerMod.getCustomerID());
        if (CustomerAppointments.size() > 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Appointment Conflict");
            alert.setContentText("This Customer has appointments scheduled. \nAre you sure you want to delete this customer and their appointments?");
            Optional<ButtonType> confirm = alert.showAndWait();

            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                for (Appointment appointment: CustomerAppointments) {

                    int AppointmentRowsAffected = AppointmentQuery.delete(appointment.getAppointmentID());

                    if (AppointmentRowsAffected > 0) {
                        System.out.println("Success");
                        ObservableList<Customer> RefreshCustomers = DBCustomers.getAllCustomers();
                        CustomerTable.setItems(RefreshCustomers);
                    } else {
                        System.out.println("Failed");
                    }
                }
                int RowsAffected = CustomerQuery.delete(CustomerMod.getCustomerID());

                if (RowsAffected > 0) {
                    System.out.println("Success");
                    ObservableList<Customer> RefreshCustomers = DBCustomers.getAllCustomers();
                    CustomerTable.setItems(RefreshCustomers);
                } else {
                    System.out.println("Failed");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Are you sure you want to delete this customer?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                int RowsAffected = CustomerQuery.delete(CustomerMod.getCustomerID());
                if (RowsAffected > 0){
                    System.out.println("Success");
                    ObservableList<Customer> RefreshCustomers = DBCustomers.getAllCustomers();
                    CustomerTable.setItems(RefreshCustomers);
                } else {
                    System.out.println("Failed");
                }
            }
        }
    }

    /** Back button functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnCustomerBackBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
