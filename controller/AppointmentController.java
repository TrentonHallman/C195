package controller;

import DBAccess.DBAppointments;
import helper.AppointmentQuery;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controls Appointment Controller Screen operations
 *
 * @author Trenton Hallman
 */
public class AppointmentController implements Initializable {
    public TableView<Appointment> AppointmentTable;
    public TableColumn<Object, Object> AppointmentID;
    public TableColumn<Object, Object> AppointmentTitle;
    public TableColumn<Object, Object> AppointmentDescription;
    public TableColumn<Object, Object> AppointmentLocation;
    public TableColumn<Object, Object> AppointmentContact;
    public TableColumn<Object, Object> AppointmentType;
    public TableColumn<Object, Object> AppointmentStart;
    public TableColumn<Object, Object> AppointmentEnd;
    public TableColumn<Object, Object> AppointmentCustID;
    public TableColumn<Object, Object> AppointmentUserID;
    public Button DeleteAppointment;
    public Button EditAppointment;
    public Button AddAppointment;
    public Button BackAppointment;
    public ComboBox<String> TimelineCombo;
    static Appointment AppointmentMod;

    /** Gets the selected appointment to be transferred to the Edit Appointment Screen
     *
     * @return AppointmentMod
     * @param AppointmentTransfer
     */
    public static Appointment GetSelectedAppointment(Appointment AppointmentTransfer) {
        return AppointmentMod;
    }

    /** Deletes appointment upon pressing button
     *
     * @throws SQLException
     */
    public void OnDeleteAppointment() throws SQLException {
        AppointmentMod = AppointmentTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Appointment Conflict");
        alert.setContentText("Are you sure you want to delete this appointment?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            int RowsAffected = AppointmentQuery.delete(AppointmentMod.getAppointmentID());

            if (RowsAffected > 0) {
                System.out.println("Success");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input Error");
                alert.setContentText("Appointment with ID: " + AppointmentMod.getAppointmentID() + " and Type: " + AppointmentMod.getAppointmentType()+ " was deleted.");
                alert.showAndWait();
                ObservableList<Appointment> RefreshAppointments = DBAppointments.getAllAppointments();
                AppointmentTable.setItems(RefreshAppointments);
            } else {
                System.out.println("Failed");
            }
        }
    }

    /** Loads Edit Appointment screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnEditAppointment(ActionEvent actionEvent) throws IOException {
        AppointmentMod = AppointmentTable.getSelectionModel().getSelectedItem();
        EditAppointmentController.AppointmentTransfer(AppointmentMod);
        if (AppointmentMod != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/EditAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Edit Appointment");
            stage.setScene(scene);
            stage.show();
        } else if (AppointmentMod == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Must select appointment");
            alert.showAndWait();
        }
    }

    /** Loads Add Appointment screen upon pushing button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /** Back button functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnBackAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main menu");
        stage.setScene(scene);
        stage.show();
    }

    /** Code that runs on the selection of a timeline sorting option from the Timeline Combo Box*/
    public void OnTimelineCombo() {
        String SelectedTimeline = TimelineCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> WithinAll = DBAppointments.getAllAppointments();
        ObservableList<Appointment> WithinMonth = DBAppointments.getAppointmentsWithinMonth();
        ObservableList<Appointment> WithinWeek = DBAppointments.getAppointmentsWithinWeek();

        switch (SelectedTimeline) {
            case "All" -> AppointmentTable.setItems(WithinAll);
            case "Month" -> AppointmentTable.setItems(WithinMonth);
            case "Week" -> AppointmentTable.setItems(WithinWeek);
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

        ObservableList<String> TimelineChoices = FXCollections.observableArrayList("Week", "Month", "All");

        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        AppointmentTitle.setCellValueFactory(new PropertyValueFactory<>("AppointmentTitle"));
        AppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));
        AppointmentLocation.setCellValueFactory(new PropertyValueFactory<>("AppointmentLocation"));
        AppointmentContact.setCellValueFactory(new PropertyValueFactory<>("AppointmentContact"));
        AppointmentType.setCellValueFactory(new PropertyValueFactory<>("AppointmentType"));
        AppointmentStart.setCellValueFactory(new PropertyValueFactory<>("AppointmentStart"));
        AppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("AppointmentEnd"));
        AppointmentCustID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        AppointmentUserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));

        AppointmentTable.setItems(AllAppointments);

        TimelineCombo.setItems(TimelineChoices);
        TimelineCombo.setValue("All");
    }
}
