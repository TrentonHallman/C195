package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBDivisions;
import helper.CustomerQuery;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controls Add Customer Functionality
 *
 * @author Trenton Hallman
 */

public class AddCustomerController implements Initializable {
    public ComboBox<String> AddCountryCombo;
    public ComboBox<String> AddDivisionCombo;
    public TextField AddPhone;
    public TextField AddPostal;
    public TextField AddAddress;
    public TextField AddName;
    public TextField AddID;
    public Button AddSaveBtn;
    public Button AddBackBtn;
    public Button AddClearBtn;

    /** Code that runs on the selection of a country from the Country Combo Box */
    public void OnAddCountryCombo() {
        String SelectedCountry = AddCountryCombo.getSelectionModel().getSelectedItem();

        ObservableList<String> DivisionUS = DBDivisions.getUSDivisionNames();
        ObservableList<String> DivisionUK = DBDivisions.getUKDivisionNames();
        ObservableList<String> DivisionCanada = DBDivisions.getCanadaDivisionNames();

        switch (SelectedCountry) {
            case "U.S" -> AddDivisionCombo.setItems(DivisionUS);
            case "UK" -> AddDivisionCombo.setItems(DivisionUK);
            case "Canada" -> AddDivisionCombo.setItems(DivisionCanada);
        }
    }

    /** Save button functionality
     *
     * @param actionEvent
     */
    public void OnAddSaveBtn(ActionEvent actionEvent) {
        try {
            int CustomerID = (int)(Math.random() *100);
            String CustomerName = AddName.getText();
            String CustomerAddress = AddAddress.getText();
            String CustomerPostal = AddPostal.getText();
            String CustomerPhone = AddPhone.getText();
            String DivisionName = AddDivisionCombo.getValue();

            int RowsAffected = CustomerQuery.insert(CustomerID, CustomerName, CustomerAddress, CustomerPostal, CustomerPhone, DBCustomers.getCustomerDivisionID(DivisionName));

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
    public void OnAddBackBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);
        stage.show();
    }

    /** Clear button functionality */
    public void OnAddClearBtn() {
        AddName.clear();
        AddAddress.clear();
        AddPostal.clear();
        AddPhone.clear();
        AddDivisionCombo.setValue(null);
        AddCountryCombo.setValue(null);
    }

    /** Code that runs on initialization of the screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> CountryNames = DBCountries.getCountryNames();
        AddCountryCombo.setItems(CountryNames);
    }
}
