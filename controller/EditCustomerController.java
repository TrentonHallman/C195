package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** Controls Edit Customer Functionality
 *
 * @author Trenton Hallman
 */
public class EditCustomerController implements Initializable {

    public ComboBox<String> EditCountryCombo;
    public ComboBox<String> EditDivisionCombo;
    public TextField EditPhone;
    public TextField EditPostal;
    public TextField EditAddress;
    public TextField EditName;
    public TextField EditID;
    public Button EditSaveBtn;
    public Button EditBackBtn;
    public Button EditClearBtn;

    /** Code that runs on the selection of a country from the Country Combo Box */
    public void OnEditCountryCombo() {
        String SelectedCountry = EditCountryCombo.getSelectionModel().getSelectedItem();

        ObservableList<String> DivisionUS = DBDivisions.getUSDivisionNames();
        ObservableList<String> DivisionUK = DBDivisions.getUKDivisionNames();
        ObservableList<String> DivisionCanada = DBDivisions.getCanadaDivisionNames();

        switch (SelectedCountry) {
            case "U.S" -> EditDivisionCombo.setItems(DivisionUS);
            case "UK" -> EditDivisionCombo.setItems(DivisionUK);
            case "Canada" -> EditDivisionCombo.setItems(DivisionCanada);
        }
    }

    /** Save button functionality
     *
     * @param actionEvent
     */
    public void OnEditSaveBtn(ActionEvent actionEvent) {
        try {
            int CustomerID = Integer.parseInt(EditID.getText());
            String CustomerName = EditName.getText();
            String CustomerAddress = EditAddress.getText();
            String CustomerPostal = EditPostal.getText();
            String CustomerPhone = EditPhone.getText();
            String DivisionName = EditDivisionCombo.getValue();

            int RowsAffected = CustomerQuery.update(CustomerID, CustomerName, CustomerAddress, CustomerPostal, CustomerPhone, DBCustomers.getCustomerDivisionID(DivisionName));

            if (RowsAffected > 0){
                System.out.println("Success");
                Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Customer Menu");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Failed");
            }

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Input Error");
            alert.showAndWait();
        }
    }

    /** Back button functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnEditBackBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);
        stage.show();
    }

    /** Clear button functionality
     *
     * @param actionEvent
     */
    public void OnEditClearBtn(ActionEvent actionEvent) {
        EditName.clear();
        EditAddress.clear();
        EditPostal.clear();
        EditPhone.clear();
        EditDivisionCombo.setValue(null);
        EditCountryCombo.setValue(null);
    }

    /** Creates Customer Transfer Object */
    private static Customer CustomerTransfer = null;

    /** Captures data transferred from Customer Controller
     *
     * @param transfer
     */
    public static void CustomerTransfer(Customer transfer){
        CustomerTransfer = transfer;
    }

    /** Code that runs on initialization of the screen
     * Includes LAMBDA #1
     * Justification - Populates Country combo box in a clean way
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer CustomerMod = CustomerController.GetSelectedCustomer(CustomerTransfer);
        ObservableList<Country> AllCountries = DBCountries.getAllCountries();
        ObservableList<String> CountryNames = FXCollections.observableArrayList();

        int CustDivisionID = CustomerMod.getDivisionID();

        EditID.setText(String.valueOf(CustomerMod.getCustomerID()));
        EditName.setText(String.valueOf(CustomerMod.getCustomerName()));
        EditAddress.setText(String.valueOf(CustomerMod.getCustomerAddress()));
        EditPostal.setText(String.valueOf(CustomerMod.getCustomerPostal()));
        EditPhone.setText(String.valueOf(CustomerMod.getCustomerPhone()));

        try {
            EditCountryCombo.setValue(DBCountries.getCountryName(DBCustomers.getCustomerCountryID(CustDivisionID)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            EditDivisionCombo.setValue(DBDivisions.getSpecificDivisionName(CustDivisionID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            EditDivisionCombo.setItems(DBDivisions.getDivisions(DBCountries.getSpecificCountryID(EditCountryCombo.getValue())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //LAMBDA #1
        AllCountries.forEach(country -> CountryNames.add(country.getCountryName()));

        EditCountryCombo.setItems(CountryNames);
    }
}
