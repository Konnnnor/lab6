package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    public TextField t_name;
    public TextField t_hour;
    public TextField t_min;
    public TextField t_time;
    public Button b_ok;
    public Button b_cancel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        b_ok.setOnMouseClicked(p -> {
            press_ok();
        });

        b_cancel.setOnMouseClicked( p -> pressCancel());
    }

    void press_ok(){
        if(t_hour.getText()!=null&&t_min.getText()!=null&&t_time.getText()!=null){
            int hour = Integer.parseInt(t_hour.getText());
            int min = Integer.parseInt(t_min.getText());
            int time = Integer.parseInt(t_time.getText());
            String name = t_name.getText();

            Main._29_11.insertEnd(hour, min, time, name);

            DialogNewSess.window.close();
        }
    }

    public void pressCancel() {
        DialogNewSess.window.close();
    }
}
