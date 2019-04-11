package extra_class_bookingsystem.Controllers;
import extra_class_bookingsystem.Alert_maker.Alert_handler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;

public class dashboard  implements Initializable{
    @FXML
    private AnchorPane pane;

    @FXML
    private VBox sidepane;

    @FXML
    private Label welcome_label;

    @FXML
    private Button user_name;

    @FXML
    private Button home_button;

    @FXML
    private Button info_button;

    @FXML
    private Button password_button;

    @FXML
    private Button contact_button;

    @FXML
    private Pane title_pane;



    @FXML
    public Button add_button;

    @FXML
    private Button delete_button;

    @FXML
    private Button modify_button;

    @FXML
    void add_func1(MouseEvent event) throws Exception {
        Stage primaryStage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/ADD_BUTTON_WINDOW.fxml"));
        primaryStage.setTitle(" ");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
}
 public void clear_selection()
{
    tableData.getSelectionModel().clearSelection();
}


    @FXML
public Label USER_LABEL;
    @FXML
    private  TableView< extra_class_bookingsystem.Controllers.ModelTable> tableData;

    @FXML
    private TableColumn< extra_class_bookingsystem.Controllers.ModelTable, String> roomname;

    @FXML
    private TableColumn< extra_class_bookingsystem.Controllers.ModelTable,Date> book_date;

    @FXML
    private TableColumn< extra_class_bookingsystem.Controllers.ModelTable, String> timeslot;
    @FXML
    private Button sign_out;
    @FXML
    private TableColumn< extra_class_bookingsystem.Controllers.ModelTable, String> name;
    static ObservableList< extra_class_bookingsystem.Controllers.ModelTable> obList= FXCollections.observableArrayList();
    public static HashMap<Integer, String> time_slot_map_inverse = new HashMap<>();
    @Override
   public void initialize(URL location, ResourceBundle resources) {
        obList.clear();
        tableData.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        int c = 8;
        for (int i = 0; i < DATE_TIME_SELECT.list.size(); i++) {
            time_slot_map_inverse.put(c, DATE_TIME_SELECT.list.get(i));
            c = c + 1;
        }
        System.out.println(time_slot_map_inverse);
         USER_LABEL.setText(LoginController.fname);
        roomname.setCellValueFactory(new PropertyValueFactory<extra_class_bookingsystem.Controllers.ModelTable, String>("roomname"));
        book_date.setCellValueFactory(new PropertyValueFactory<extra_class_bookingsystem.Controllers.ModelTable, Date>("book_date"));
        timeslot.setCellValueFactory(new PropertyValueFactory<extra_class_bookingsystem.Controllers.ModelTable, String>("timeslot"));
        name.setCellValueFactory(new PropertyValueFactory<extra_class_bookingsystem.Controllers.ModelTable, String>("name"));
        System.out.println("start");
        try {
            System.out.println("222222");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("1");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");
            System.out.println("2");
            ResultSet rs = con.createStatement().executeQuery("select * from booked where lower(name)='" + LoginController.uname + "';");
            System.out.println(rs.toString());

            System.out.println("3");

            while (rs.next()) {
                System.out.println("4");
                obList.add(new extra_class_bookingsystem.Controllers.ModelTable(rs.getString("Roomname"), rs.getDate("book_date"), rs.getString("timeslot"), rs.getString("description")));
            }
            System.out.println("5");
            tableData.setItems(obList);

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "err");
        }

    }
public void home_window_open() throws Exception
{
    Stage primaryStage=new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/home_window.fxml"));
    primaryStage.setTitle(" home");
    primaryStage.initModality(Modality.APPLICATION_MODAL);
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
}
    public void refresh_userLabel()
    {
        USER_LABEL.setText(LoginController.fname);
    }
    public void handle_edit_info_button()
    {
        Stage primaryStage = new Stage();
        primaryStage.setOnCloseRequest(e->
        {
            e.consume();
            System.out.println("closiunfjf");
            refresh_userLabel();
            primaryStage.close();
        });
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/edit_info_window.fxml"));
            primaryStage.setTitle("bookIT-extra class booking system");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.initModality(Modality.APPLICATION_MODAL);


        } catch (Exception e) {
            System.out.println(e.getCause());
        }
            USER_LABEL.setText(LoginController.fname);
    }
public void contactus_window_open() throws Exception
{
       Stage primaryStage= new Stage();
 try {

    Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/contact_window.fxml"));
    primaryStage.setTitle("contact us");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
    primaryStage.initModality(Modality.APPLICATION_MODAL);


} catch (Exception e) {
    System.out.println(e.getCause());
}

}

        public void sign_out_function() throws Exception {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SIGN OUT ");
            alert.setContentText("You are about to sign out");
            alert.setHeaderText("Are you sure you want to proceed ?");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                    Stage current = (Stage) USER_LABEL.getScene().getWindow();
                    LoginController.uname = null;
                    LoginController.password = null;
                    current.close();
                    System.out.println("done");
                    Stage primaryStage = new Stage();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/login.fxml"));
                        primaryStage.setTitle("bookIT-extra class booking system");
                        primaryStage.setScene(new Scene(root));
                        primaryStage.setResizable(false);
                        primaryStage.show();
                    } catch (Exception e) {
                        System.out.println(e.getCause());
                    }
                }
            });
        }
public void delete_row_from_table() {
    extra_class_bookingsystem.Controllers.ModelTable row_to_delete = tableData.getSelectionModel().getSelectedItem();
    if(row_to_delete==null)
        Alert_handler.showErrorMessage("Now booking selected"," Please  select a entry from table","");
    if (row_to_delete != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE SELECTED BOOKING");
        alert.setHeaderText("Are you sure you want to delete selected entry from booked table?");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {


                try {

                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");
                    Statement stmt = con.createStatement();
                    PreparedStatement preparedStmt = con.prepareStatement("delete from booked where book_date=? and roomname=? and timeslot=? and name =?");
                    preparedStmt.setDate(1, row_to_delete.getBook_date());
                    preparedStmt.setString(2, row_to_delete.Roomname);
                    preparedStmt.setString(3, row_to_delete.getTimeslot());
                    preparedStmt.setString(4, LoginController.uname);
                    int p=preparedStmt.executeUpdate();
                    if(p==1)
                    {
                        Alert_handler.showConformationMessage("Deletion successful","The entry was succesfully deleted ","");
                    }

                    con.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }
                tableData.getItems().removeAll(row_to_delete);
            }
        });
    }
}
    public void change_password_window_open() throws Exception
    {
        Stage primaryStage= new Stage();
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/change_password_window.fxml"));
            primaryStage.setTitle("change password");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.initModality(Modality.APPLICATION_MODAL);


        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }

}







