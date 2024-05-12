package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
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
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** Controls Add Appointment Functionality
 *
 * @author Trenton Hallman
 */
public class AddAppointmentController implements Initializable {
    public ComboBox<String> AddContactCombo;
    public TextField AddLocation;
    public TextField AddDescription;
    public TextField AddType;
    public TextField AddTitle;
    public TextField AddCustomerID;
    public TextField AddUserID;
    public TextField AddID;
    public Button AddBack;
    public Button AddSave;
    public Button AddClear;
    public ComboBox<String> EndTime;
    public ComboBox<String> StartTime;
    public DatePicker AddDate;

    /** Back button functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    /** Save button functionality
     *
     * @param actionEvent
     */
    public void OnAddSave(ActionEvent actionEvent) {
        try {
            ObservableList<Appointment> AllAppointments = DBAppointments.getAllAppointments();

            LocalTime SelectedStartTime = LocalTime.parse(StartTime.getValue());
            LocalTime SelectedEndTime = LocalTime.parse(EndTime.getValue());

            LocalDate SelectedDate = AddDate.getValue();

            int AppointmentID = (int) (Math.random() * 100);
            String AppointmentTitle = AddTitle.getText();
            String AppointmentDescription = AddDescription.getText();
            String AppointmentLocation = AddLocation.getText();
            String AppointmentContact = AddContactCombo.getValue();
            String AppointmentType = AddType.getText();
            int CustomerID = Integer.parseInt(AddCustomerID.getText());
            int UserID = Integer.parseInt(AddUserID.getText());

            LocalDateTime AppointmentStartLocalDT = LocalDateTime.of(SelectedDate, SelectedStartTime);
            LocalDateTime AppointmentEndLocalDT = LocalDateTime.of(SelectedDate, SelectedEndTime);

            if (AppointmentStartLocalDT.isAfter(AppointmentEndLocalDT)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Selected appointment start time must be a time that is before the selected appointment end time.");
                alert.showAndWait();
                return;
            }

            for (Appointment OverlapAppointment : AllAppointments) {
                LocalDateTime CheckStart = OverlapAppointment.getAppointmentStart();
                LocalDateTime CheckEnd = OverlapAppointment.getAppointmentEnd();

                if ((AppointmentStartLocalDT.isBefore(CheckStart) && AppointmentEndLocalDT.isAfter(CheckEnd))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlap!");
                    alert.setContentText("This customer already has an appointment during this time slot.");
                    alert.showAndWait();
                    return;
                }
                if ((AppointmentStartLocalDT.isAfter(CheckStart) && AppointmentStartLocalDT.isBefore(CheckEnd))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);

                    alert.setTitle("Appointment Overlap!");
                    alert.setContentText("Appointment Start is already scheduled during a different time slot");
                    alert.showAndWait();
                    return;
                }
                if ((AppointmentEndLocalDT.isAfter(CheckStart) && AppointmentEndLocalDT.isBefore(CheckEnd))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlap!");
                    alert.setContentText("Appointment End is already scheduled during a different time slot");
                    alert.showAndWait();
                    return;
                }
                if ((AppointmentEndLocalDT.isEqual(CheckStart) || AppointmentEndLocalDT.isEqual(CheckEnd))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlap!");
                    alert.setContentText("Appointment End is already scheduled during a different time slot");
                    alert.showAndWait();
                    return;
                }
                if ((AppointmentStartLocalDT.isEqual(CheckStart) || AppointmentStartLocalDT.isEqual(CheckEnd))) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Appointment Overlap!");
                    alert.setContentText("Appointment End is already scheduled during a different time slot");
                    alert.showAndWait();
                    return;
                }
            }

            int RowsAffected = AppointmentQuery.insert(AppointmentID, AppointmentTitle, AppointmentDescription, AppointmentLocation, AppointmentType, AppointmentStartLocalDT, AppointmentEndLocalDT, CustomerID, UserID, DBAppointments.getContactID(AppointmentContact));

            if (RowsAffected > 0) {
                System.out.println("Success");
                Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Appointment Screen");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Failed");
            }

        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /** Clear button functionality */
    public void OnAddClear() {
        AddTitle.clear();
        AddDescription.clear();
        AddLocation.clear();
        AddContactCombo.setValue(null);
        AddType.clear();
        AddDate.setValue(null);
        StartTime.setValue(null);
        EndTime.setValue(null);
        AddCustomerID.clear();
        AddUserID.clear();
    }

    /** Code that runs on the selection of a Start Time from the Start Time Combo Box */
    public void OnStartTime() {
        LocalTime SelectedTime = LocalTime.parse(StartTime.getValue());

        ObservableList<String> PossibleEndTimes = FXCollections.observableArrayList();

        LocalTime OfficeClose = LocalTime.of(22, 0);

        LocalDate Today = LocalDate.now();
        LocalDateTime OfficeCloseDT = LocalDateTime.of(Today, OfficeClose);
        ZonedDateTime OfficeCloseZDT = ZonedDateTime.of(OfficeCloseDT, ZoneId.of("America/New_York"));
        ZonedDateTime OfficeCloseEST = OfficeCloseZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime LastAppointmentTime = OfficeCloseEST.toLocalDateTime().toLocalTime();

        if (LastAppointmentTime.equals(SelectedTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Scheduling Issue!");
            alert.setContentText("Cannot choose closing time for Appointment Start!");
            alert.showAndWait();
        } else {
            while (!PossibleEndTimes.contains(String.valueOf(LastAppointmentTime))) {
                SelectedTime = SelectedTime.plusMinutes(15);
                PossibleEndTimes.add(String.valueOf(SelectedTime));
                EndTime.setItems(PossibleEndTimes);
            }
        }
    }

    /** Code that runs on initialization of the screen
     * Includes LAMBDA #2
     * Justification - Easy way to remove dates that have already passed from the date picker
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddContactCombo.setItems(DBContacts.getAllContactNames());

        //LAMBDA #2

        AddDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate AddDate, boolean NoData) {
                super.updateItem(AddDate, NoData);
                setDisable(
                        NoData || AddDate.isBefore(LocalDate.now()));
            }
        });
    }

    /** Code that runs on the selection of a Date from the Date Combo Box */
    public void OnAddDate() {
        ObservableList<String> PossibleTimes = FXCollections.observableArrayList();
        LocalDate SelectedDate = AddDate.getValue();

        LocalTime OfficeOpen = LocalTime.of(8, 0);
        LocalTime OfficeClose = LocalTime.of(22,0);

        LocalDate Today = LocalDate.now();

        LocalDateTime OfficeOpenDT = LocalDateTime.of(Today,OfficeOpen);
        LocalDateTime OfficeCloseDT = LocalDateTime.of(Today,OfficeClose);

        ZonedDateTime OfficeOpenZDT = ZonedDateTime.of(OfficeOpenDT, ZoneId.of("America/New_York"));
        ZonedDateTime OfficeCloseZDT = ZonedDateTime.of(OfficeCloseDT, ZoneId.of("America/New_York"));

        ZonedDateTime OfficeOpenEST = OfficeOpenZDT.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime OfficeCloseEST = OfficeCloseZDT.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime FirstAppointmentTime = OfficeOpenEST.toLocalDateTime().toLocalTime();
        LocalTime LastAppointmentTime = OfficeCloseEST.toLocalDateTime().toLocalTime();

        if (!PossibleTimes.contains(String.valueOf(FirstAppointmentTime))){
            if (SelectedDate.isEqual(Today)){
                while (FirstAppointmentTime.isBefore(LocalTime.now()) && !PossibleTimes.contains(String.valueOf(LastAppointmentTime))){
                    FirstAppointmentTime = FirstAppointmentTime.plusMinutes(15);
                }
                while (FirstAppointmentTime.isAfter(LocalTime.now()) && !PossibleTimes.contains(String.valueOf(LastAppointmentTime))) {
                    PossibleTimes.add(String.valueOf(FirstAppointmentTime));
                    FirstAppointmentTime = FirstAppointmentTime.plusMinutes(15);
                }
            }else if (!SelectedDate.isEqual(Today)){
                while (!PossibleTimes.contains(String.valueOf(LastAppointmentTime))){
                    PossibleTimes.add(String.valueOf(FirstAppointmentTime));
                    FirstAppointmentTime = FirstAppointmentTime.plusMinutes(15);
                }
            }
        }
        StartTime.setItems(PossibleTimes);
    }
}
