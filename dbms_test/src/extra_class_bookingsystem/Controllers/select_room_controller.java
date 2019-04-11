package extra_class_bookingsystem.Controllers;
        import java.net.URL;

        import extra_class_bookingsystem.Mail;
        import javafx.scene.input.KeyEvent;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.input.KeyCode;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;


        import java.sql.*;

        import java.text.SimpleDateFormat;
        import java.util.ResourceBundle;

public class select_room_controller implements Initializable {
    public select_room_controller() {
    }

    public static String room_selected_by_user;
    @FXML
    private AnchorPane pane3;

    @FXML
    private ComboBox<String> select_room_button;

    @FXML
    private Label available_room_label;
    @FXML
    private Label description_label;

    @FXML

    private TextArea description_text_area;
    @FXML
    private Button select_room_ok_button;

    public int get_time() {
        return DATE_TIME_SELECT.time_slot_val;
    }

    public String get_date() {
        return DATE_TIME_SELECT.date_slot_val;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(get_time() + " " + get_date());

        select_room_button.setItems(DATE_TIME_SELECT.room_list);

    }
    public void on_enter_pressed(KeyEvent key_event) {
        if (key_event.getCode() == KeyCode.ENTER) {
            System.out.println(key_event.getCode());
            room_and_description_selected();
        }

    }

    public void room_and_description_selected() {
        room_selected_by_user = select_room_button.getValue();
        if (room_selected_by_user == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("select room");
            alert.setHeaderText("Select some room");
            alert.setContentText("Please select room from available rooms");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            return;
        }
//            ObservableList<CharSequence>
        String desc_list = description_text_area.getText();
        if (desc_list.length() > 50) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Description Length Exceeded");
            alert.setHeaderText("Type the description of booking less than 50 characters");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            return;
        }

        System.out.println(room_selected_by_user);
        System.out.println(desc_list);
        System.out.println(DATE_TIME_SELECT.full_date_slot_val);
        System.out.println(DATE_TIME_SELECT.time_slot_val);
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://remotemysql.com:3306/0htZliliVa", "0htZliliVa", "VoFrbMvpC9");
            PreparedStatement preparedStmt = con.prepareStatement("insert into booked values(?,?,?,?,?)");
            preparedStmt.setString(1, room_selected_by_user);
            preparedStmt.setDate(2, java.sql.Date.valueOf(DATE_TIME_SELECT.full_date_slot_val));
            preparedStmt.setString(3, extra_class_bookingsystem.Controllers.dashboard.time_slot_map_inverse.get(DATE_TIME_SELECT.time_slot_val));
            preparedStmt.setString(4, LoginController.uname);
            preparedStmt.setString(5, desc_list.substring(1, desc_list.length() - 1));
            preparedStmt.execute();

            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        Stage stage = (Stage) description_label.getScene().getWindow();
        stage.close();
        extra_class_bookingsystem.Controllers.dashboard.obList.add(new ModelTable(room_selected_by_user, java.sql.Date.valueOf(DATE_TIME_SELECT.full_date_slot_val), dashboard.time_slot_map_inverse.get(DATE_TIME_SELECT.time_slot_val), desc_list));
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");
        String msg="The room "+room_selected_by_user+ " was succesfully booked for "+ simpleDateFormat.format(java.sql.Date.valueOf(DATE_TIME_SELECT.full_date_slot_val))+" and the time slot is "+dashboard.time_slot_map_inverse.get(DATE_TIME_SELECT.time_slot_val)+".";
        Mail.send("kanchitbajaj8070@gmail.com","hhtpzdbpwrtmxwsz",LoginController.email,"Room booking succesful on bookIT ",msg);

    }

}


