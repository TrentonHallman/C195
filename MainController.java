package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/** Controls Main Screen Functionality
 *
 * @author Trenton Hallman
 */
public class MainController{
    public Button AppointmentsBtn;
    public Button CustomersBtn;
    public Button ReportsBtn;

    /** Loads Customer screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnCustomersBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /** Loads Appointment screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnAppointmentsBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointment Screen");
        stage.setScene(scene);
        stage.show();
    }

    /** Loads Reports screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnReportsBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports Screen");
        stage.setScene(scene);
        stage.show();
    }
}
