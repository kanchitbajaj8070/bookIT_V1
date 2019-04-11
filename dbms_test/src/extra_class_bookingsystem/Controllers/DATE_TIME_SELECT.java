package extra_class_bookingsystem.Controllers;
import java.net.URL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import extra_class_bookingsystem.Alert_maker.Alert_handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DATE_TIME_SELECT implements Initializable {
   public static int time_slot_val=-1;
   public static String date_slot_val;
   public static LocalDate  full_date_slot_val;

    public DATE_TIME_SELECT() {
    }
   public static ObservableList<String> room_list;

    @FXML
    private AnchorPane PANE_2;

    @FXML
    private DatePicker DATE_PICKER;

    @FXML
    private Label DATE_LABEL;

    @FXML
    private Label TIME_LABEL;


    @FXML
    private Button available_room_button;

    @FXML
    void available_room_show(MouseEvent event) throws Exception {
 button_functionality();
    }
    public void button_functionality() throws Exception
    {
        LocalDate date = DATE_PICKER.getValue();
        System.out.println(date);
        String time_selection = null;
        time_selection = TIME_SLOT_BOX.getValue();
        System.out.println(time_selection);
        if (time_selection == null&& date_slot_val!=null) {
            Alert_handler.showErrorMessage("ERROR-NO TIME SLOT SELECTED"," Please select some timeslot ","Select time slot in which you want to conduct the class ");
        }

        else if (date == null&&time_selection!=null) {
            Alert_handler.showErrorMessage("ERROR-NO DATE SELECTED","Select some date from calendar","Please select some date to conduct extra class");
        }

        else if (date != null && time_selection != null) {
            int map_time_slot = time_slot_map.get(time_selection);
            time_slot_val = map_time_slot;
            System.out.println(map_time_slot);
            full_date_slot_val = date;
            date_slot_val = date.getDayOfWeek().toString().toLowerCase().substring(0, 3);
            ArrayList<String> room_available = new ArrayList<>();
            try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select roomname from main where isavailable = 1 and timeslot="+ time_slot_val + " and lower(day)='" + date_slot_val + "' and roomname not in(select Roomname from booked where book_date = '"+full_date_slot_val+"' and timeslot = '"+time_selection+"');");

                System.out.println(rs);
                while (rs.next())
                    room_available.add(rs.getString(1));
                System.out.println(room_available);

            } catch (Exception e) {
                System.out.println("CAUGHT EXCEPTION");
            }
            room_list = FXCollections.observableArrayList(room_available);
            if (room_list.size() == 0) {
                Alert_handler.showErrorMessage("NO ROOMS AVAILABLE "," No Rooms are available for your \n date and time combination","Please select some other date or time slot");

            } else {
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/extra_class_bookingsystem/fxmls/available_room.fxml"));
                primaryStage.setTitle("SELECT ROOM");
                primaryStage.initModality(Modality.APPLICATION_MODAL);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
                primaryStage.setResizable(false);

                Stage stage = (Stage) DATE_LABEL.getScene().getWindow();
                stage.close();


            }

        }
        else
        {
            Alert_handler.showErrorMessage("ERROR-NO DATE OR TIME SELECTED","No Date and Time selected"," Please select both date and time");
        }
    }
    public void available_room_show_keyEvent(KeyEvent key_event) throws Exception
    {
        if (key_event.getCode() == KeyCode.ENTER) {
            System.out.println(key_event.getCode());
         button_functionality();
    }}
    @FXML
    private ComboBox<String> TIME_SLOT_BOX;
    static ObservableList<String>  list = FXCollections.observableArrayList("8AM-9AM", "9AM -10AM", "10AM-11AM", "11AM-12PM", "12PM-1PM ", "1PM-2 PM ", "2PM-3PM", "3PM-4PM", "4PM-5PM", "5PM-6PM", "6PM-7PM");

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        TIME_SLOT_BOX.setItems(list);
        int c=8;
        for (int i = 0; i <list.size() ; i++) {
            time_slot_map.put(list.get(i),c);
            c=c+1;
        }
    }

    public static HashMap<String, Integer> time_slot_map = new HashMap<>();


}
