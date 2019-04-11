package extra_class_bookingsystem;
import extra_class_bookingsystem.Database.Database_helper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private Database_helper databaseHandler;
    @Override
    public void start(Stage primaryStage) throws Exception {
//        databaseHandler = Database_helper.getInstance();
        /*Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxmls"));
        primaryStage.setTitle("bookIT-extra class booking system");
        primaryStage.setScene(new Scene(root));
      primaryStage.setResizable(false);*/
        boolean internet_available = check_internet();
        System.out.println(internet_available);
        if (internet_available == true) {
            Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/login.fxml"));
            primaryStage.setTitle("bookIT-extra class booking system");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NO INTERNET CONNECTIVITY");
            alert.setHeaderText(" YOU ARE NOT CONNECTED TO THE INTERNET");
            alert.setContentText(" PLEASE CONNECT TO INTERNET FOR BOOKING A ROOM ");
            alert.showAndWait();
        }
    }

public boolean check_internet() {
    Socket sock = new Socket();
    InetSocketAddress addr = new InetSocketAddress("www.google.com",80);

    try
    {
        sock.connect(addr,3000);
        return true;
    }
    catch (Exception e)
    {

        return false;
    }
    finally
    {
        try
        {
            sock.close();
        }
        catch (Exception e)
        {

        }
    }
}

    public static void main(String args[]) {

        launch(args);
    }
}
