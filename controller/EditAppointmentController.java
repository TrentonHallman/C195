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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** Controls Edit Appointment Functionality
 *
 * @author Trenton Hallman
 */
public class EditAppointmentController implements Initializable {
    public ComboBox<String> EditContactCombo;
    public TextField EditLocation;
    public TextField EditDescription;
    public TextField EditType;
    public TextField EditTitle;
    public TextField EditCustomerID;
    public TextField EditUserID;
    public TextField EditID;
    public Button EditBack;
    public Button EditSave;
    public Button EditClear;
    public DatePicker EditDate;
    public ComboBox StartTime;
    public ComboBox EndTime;

    /** Back button functionality
     *
     * @param actionEvent
     * @throws IOException
     */
    public void OnEditBack(ActionEvent actionEvent) throws IOException {
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
    public void OnEditSave(ActionEvent actionEvent) {
        DateTimeFormatter LTformatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalTime SelectedStartTime = LocalTime.parse((CharSequence) StartTime.getSelectionModel().getSelectedItem(), LTformatter);
            LocalTime SelectedEndTime = LocalTime.parse((CharSequence) EndTime.getSelectionModel().getSelectedItem(), LTformatter);

            LocalDate SelectedDate = EditDate.getValue();

            int AppointmentID = Integer.parseInt(EditID.getText());
            String AppointmentTitle = EditTitle.getText();
            String AppointmentDescription = EditDescription.getText();
            String AppointmentLocation = EditLocation.getText();
            String AppointmentContact = EditContactCombo.getValue();
            String AppointmentType = EditType.getText();
            int CustomerID = Integer.parseInt(EditCustomerID.getText());
            int UserID = Integer.parseInt(EditUserID.getText());

            LocalDateTime AppointmentStartLocalDT = LocalDateTime.of(SelectedDate, SelectedStartTime);
            LocalDateTime AppointmentEndLocalDT = LocalDateTime.of(SelectedDate, SelectedEndTime);

            ObservableList<Appointment> AllAppointmentsExcept = DBAppointments.getAllAppointmentsExcept(AppointmentID);

            if (AppointmentStartLocalDT.isAfter(AppointmentEndLocalDT)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setContentText("Selected appointment start time must be a time that is before the selected appointment end time.");
                alert.showAndWait();
                return;
            }

            for (Appointment OverlapAppointment : AllAppointmentsExcept) {

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
            }

            int RowsAffected = AppointmentQuery.update(AppointmentID, AppointmentTitle, AppointmentDescription, AppointmentLocation, AppointmentType, AppointmentStartLocalDT, AppointmentEndLocalDT, CustomerID, UserID, DBAppointments.getContactID(AppointmentContact));

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
    public void OnEditClear() {
        EditTitle.clear();
        EditDescription.clear();
        EditLocation.clear();
        EditContactCombo.setValue(null);
        EditType.clear();
        EditDate.setValue(null);
        StartTime.setValue(null);
        EndTime.setValue(null);
        EditCustomerID.clear();
        EditUserID.clear();
    }

    /** Creates Appointment Transfer Object */
    private static Appointment AppointmentTransfer = null;

    /** Captures data transferred from Appointment Controller
     *
     * @param transfer
     */
    public static void AppointmentTransfer(Appointment transfer){
        AppointmentTransfer = transfer;
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
        Appointment AppointmentMod = AppointmentController.GetSelectedAppointment(AppointmentTransfer);

        EditID.setText(String.valueOf(AppointmentMod.getAppointmentID()));
        EditTitle.setText(String.valueOf(AppointmentMod.getAppointmentTitle()));
        EditDescription.setText(String.valueOf(AppointmentMod.getAppointmentDescription()));
        EditLocation.setText(String.valueOf(AppointmentMod.getAppointmentLocation()));
        EditType.setText(String.valueOf(AppointmentMod.getAppointmentType()));
        EditCustomerID.setText(String.valueOf(AppointmentMod.getCustomerID()));
        EditUserID.setText(String.valueOf(AppointmentMod.getUserID()));

        EditContactCombo.setItems(DBContacts.getAllContactNames());
        EditContactCombo.setValue(AppointmentMod.getAppointmentContact());

        StartTime.setValue(String.valueOf(AppointmentMod.getAppointmentStart().toLocalTime()));
        EndTime.setValue(String.valueOf(AppointmentMod.getAppointmentEnd().toLocalTime()));

        EditDate.setValue(AppointmentMod.getAppointmentStart().toLocalDate());

        //LAMBDA #2
        EditDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate EditDate, boolean empty) {
                super.updateItem(EditDate, empty);
                setDisable(empty || EditDate.isBefore(LocalDate.now()));
            }
        });
    }

    /** Code that runs on the selection of a Start Time from the Start Time Combo Box */
    public void OnStartTime() {
        LocalTime SelectedTime = LocalTime.parse((CharSequence) StartTime.getValue());

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

    /** Code that runs on the selection of a Date from the Date Combo Box */
    public void OnEditDate() {
        ObservableList<String> PossibleTimes = FXCollections.observableArrayList();
        LocalDate SelectedDate = EditDate.getValue();

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
