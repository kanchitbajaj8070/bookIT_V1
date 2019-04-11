package extra_class_bookingsystem.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class home_controller implements Initializable {

    @FXML
    private Label Name_label;

    @FXML
    private Label uname_label;

    @FXML
    private Label type_label;

    @FXML
    private Label email_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");

            ResultSet rs = con.createStatement().executeQuery("select * from employees where lower(username)='" + LoginController.uname + "';");
            System.out.println(rs.toString());


            while (rs.next()) {
                String ty=null;
               Name_label.setText(rs.getString("name"));
               uname_label.setText(rs.getString("username"));
               if(rs.getInt("type")==1)
                   ty="Teacher";
                type_label.setText(ty);
                email_label.setText(rs.getString("email_id"));

            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        }
    }

