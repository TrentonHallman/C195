package main;

import helper.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/** Code that first runs upon launching the program
 *
 * @author Trenton Hallman
 */
public class Main extends Application {

    /** Loads LogIn Screen */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInScreen.fxml"));
        if(Locale.getDefault().getLanguage().equals("fr")){
            stage.setTitle("Ecran de Connexion");
        } else {
            stage.setTitle("Login Screen");
        }
        stage.setScene(new Scene(root,400,400));
        stage.show();
    }

    /** Launches connection with SQL Database */
    public static void main(String[] args){
        DBConnection.openConnection();

        launch(args);

        DBConnection.closeConnection();
    }
}
