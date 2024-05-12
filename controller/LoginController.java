package controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** Controls Login Screen Functionality
 *
 * @author Trenton Hallman
 */
public class LoginController implements Initializable {
    public TextField LogInUsername;
    public TextField LogInPassword;
    public Button LogInSubmit;
    public Label Location;
    public Label LoginLabel;
    public Label LocationData;

    /** Code that runs on initialization of the screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        LocationData.setText(String.valueOf(zone));

        ResourceBundle rb = ResourceBundle.getBundle("helper/login", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            LogInUsername.setPromptText(rb.getString("username"));
            LogInPassword.setPromptText(rb.getString("password"));
            LogInSubmit.setText(rb.getString("login"));
            LoginLabel.setText(rb.getString("login"));
            Location.setText(rb.getString("location"));
        }
    }

    /** LogIn button functionality
     * Checks Password and Username and loads Main Screen if correct info is input
     *
     * @param actionEvent
     */
    public void OnLogInSubmit(ActionEvent actionEvent) {
        ResourceBundle rb = ResourceBundle.getBundle("helper/login", Locale.getDefault());
        try {
            String UN = LogInUsername.getText();
            String P = LogInPassword.getText();
            int ID = DBUsers.UserCheck(UN, P);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            BufferedWriter  LoginActivity = new BufferedWriter (fileWriter);

            boolean LoginSuccessful = ID > 0;

            ZoneId UserZone = ZoneId.systemDefault();
            LocalDateTime LoginTime = LocalDateTime.now();
            ZonedDateTime ZonedLoginTime = ZonedDateTime.of(LoginTime.toLocalDate(),LoginTime.toLocalTime(),UserZone);
            ZonedDateTime ZonedLoginTimeUTC = ZonedLoginTime.withZoneSameInstant(ZoneId.of("UTC"));

            LoginActivity.append("USER '"+ UN + "' ATTEMPTED TO LOGIN | TIME OF ATTEMPT: " + ZonedLoginTimeUTC + " | ATTEMPT SUCCESSFUL: " + LoginSuccessful);

            LoginActivity.newLine();
            LoginActivity.flush();
            LoginActivity.close();
            System.out.println("Login Activity Recorded");

            if (ID > 0) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();

                ObservableList<Appointment> AllAppointments = DBAppointments.getAllAppointments();

                LocalDateTime MinutesAfterNow15 = LocalDateTime.now().plusMinutes(15);
                LocalDateTime MinutesBeforeNow15 = LocalDateTime.now().minusMinutes(15);
                boolean AppointmentIn15 = false;

                for(Appointment appointment : AllAppointments) {
                    int AppointmentID = appointment.getAppointmentID();
                    LocalDateTime AppointmentStartTime = appointment.getAppointmentStart();

                        if ((AppointmentStartTime.isBefore(MinutesAfterNow15) || AppointmentStartTime.isEqual(MinutesAfterNow15)) && (AppointmentStartTime.isAfter(MinutesBeforeNow15) || AppointmentStartTime.isEqual(MinutesBeforeNow15))) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Upcoming Appointment");
                            alert.setContentText("Appointment ID: " + AppointmentID + "\nStarting Time: " + AppointmentStartTime);
                            alert.showAndWait();
                            AppointmentIn15 = true;
                        }
                }
                if (!AppointmentIn15){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Upcoming Appointments");
                    alert.setContentText("There is no appointments scheduled to begin within the next 15 minutes");
                    alert.showAndWait();
                }

            } else if (ID < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("errormessage"));
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errormessage"));
            alert.showAndWait();
        }
    }
}