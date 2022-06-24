package sample;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sample.items.Hall;
import sample.items.Place;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    public GridPane gridp_places;
    public GridPane gridp_buttons;

    public ChoiceBox sessions;

    public Button b_sess;
    public ToggleButton b_del;
    public ToggleButton b_add;
    public Button b_ok;

    public Label l_counter;
    public Label l_current;

    public TableView table_vis;

    public TextField t_in_n;
    public TextField t_in_p;

    public TableColumn<Place, Integer> col_place;
    public TableColumn<Place, String > col_name;

    public ArrayList<String> sess = new ArrayList<>(Arrays.asList(Main._29_11.get_String_Sess()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sess.add("Add new session");

        t_in_p.setVisible(false);

        t_in_n.setVisible(false);
        b_ok.setVisible(false);

        l_counter.setText("Visitors: " + Hall.getCounter());

        sessions.getItems().addAll(sess);
        sessions.setValue(sess.get(0));

        get_vis();

        col_place.setCellValueFactory(new PropertyValueFactory<>("numb"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        sessions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(sessions.getValue()!=null&&sessions.getValue().toString().equals("Add new session")){
                    try {
                        DialogNewSess.display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    table_vis.getItems().clear();
                    gridp_places.getChildren().clear();

                    table_vis.getItems().clear();

                    l_counter.setText("Visitors: " + Hall.getCounter());
                    l_current.setText("Current: ");
                    sess = new ArrayList<>(Arrays.asList(Main._29_11.get_String_Sess()));
                    sess.add("Add new session");
                    sessions.getItems().clear();
                    sessions.getItems().addAll(sess);

                    sessions.setValue(sess.get(0));

                    get_vis();

                }
                table_vis.getItems().clear();
                get_vis();

            }
        });


        b_sess.setOnMouseClicked(event -> {
            Main._29_11.delete(sessions.getValue().toString());
            table_vis.getItems().clear();
            gridp_places.getChildren().clear();

            table_vis.getItems().clear();

            l_counter.setText("Visitors: " + Hall.getCounter());
            l_current.setText("Current: ");
            sess = new ArrayList<>(Arrays.asList(Main._29_11.get_String_Sess()));
            sess.add("Add new session");
            sessions.getItems().clear();
            sessions.getItems().addAll(sess);

            System.out.println(sess.get(0));
            sessions.setValue(sess.get(0));

            get_vis();


        });

        b_add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                t_in_p.setVisible(!t_in_p.isVisible());
                t_in_n.setVisible(!t_in_n.isVisible());
                b_ok.setVisible(!b_ok.isVisible());
            }
        });

        b_ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                add_me();
            }
        });


    }


    private void add_me() {

        int a = Integer.parseInt(t_in_p.getText());
        System.out.println(t_in_p.getText());
        String name = t_in_n.getText();
        if(a>0&&a<=64&&name!=""){
            Main._29_11.get(sessions.getValue().toString()).hall_set.add_in(a, name);
            Main._29_11.get(sessions.getValue().toString()).hall_set.gen_table();

            l_counter.setText("Visitors: " + Hall.getCounter());

            table_vis.getItems().clear();

            Main._29_11.get(sessions.getValue().toString()).hall_set.gen_table();

            table_vis.setItems(Main._29_11.get(sessions.getValue().toString()).hall_set.plas);

            l_current.setText("Current: " + sessions.getValue().toString());

            build_grid();

            t_in_n.clear();
            t_in_p.clear();
        }

    }


    private void get_vis() {
        try{
            Main._29_11.get(sessions.getValue().toString()).hall_set.gen_table();

            table_vis.setItems(Main._29_11.get(sessions.getValue().toString()).hall_set.plas);

            l_current.setText("Current: " + sessions.getValue().toString());

            build_grid();
        } catch (Exception e){
            System.out.println("wwops");
        }

    }

    private void build_grid(){

        Hall tmp = Main._29_11.get(sessions.getValue().toString()).hall_set;

        DropShadow ds = new DropShadow( 20, Color.rgb(44, 176, 238) );
        for(int a=0, c=0; a<=(Main.PLACE_NUMB-1)/8; a++){
            for(int b=0; b<=(Main.PLACE_NUMB-1)/8; b++,  c++){
                ImageView tmps = new ImageView(new Image(tmp.getIt(c).getstat()?"sample/images/free.png":"sample/images/full.png"));
                tmps.setId("" + (c+1));
                tmps.setOnMouseEntered(event -> {
                    tmps.setEffect( ds );
                });

                tmps.setOnMouseExited(event -> {
                    tmps.setEffect( null );
                });
                tmps.setOnMouseClicked(event -> {
                    if(b_add.isSelected()){
                        t_in_p.setText(tmps.getId());
                        t_in_n.requestFocus();
                    }
                    if(b_del.isSelected()){
                        Main._29_11.get(sessions.getValue().toString()).hall_set.dell_in(Integer.parseInt(tmps.getId()));

                        l_counter.setText("Visitors: " + Hall.getCounter());

                        table_vis.getItems().clear();

                        Main._29_11.get(sessions.getValue().toString()).hall_set.gen_table();

                        table_vis.setItems(Main._29_11.get(sessions.getValue().toString()).hall_set.plas);

                        l_current.setText("Current: " + sessions.getValue().toString());

                        build_grid();
                    }
                });

                Label text = new Label("" + (c+1));
                text.setId("" + (c+1));
                text.setFont(new Font("Brush Script MT Italic", 20));
                text.setPrefWidth(55);
                text.setAlignment(Pos.CENTER);

                text.setOnMouseEntered(event -> {
                    tmps.setEffect( ds );
                });

                text.setOnMouseExited(event -> {
                    tmps.setEffect( null );
                });

                text.setOnMouseClicked(event -> {
                    if(b_add.isSelected()){
                        t_in_p.setText(text.getId());
                        t_in_n.requestFocus();
                    }
                    if(b_del.isSelected()){
                        Main._29_11.get(sessions.getValue().toString()).hall_set.dell_in(Integer.parseInt(text.getId()));

                        l_counter.setText("Visitors: " + Hall.getCounter());

                        table_vis.getItems().clear();

                        Main._29_11.get(sessions.getValue().toString()).hall_set.gen_table();


                        table_vis.setItems(Main._29_11.get(sessions.getValue().toString()).hall_set.plas);



                        l_current.setText("Current: " + sessions.getValue().toString());

                        build_grid();
                    }
                });

                gridp_places.add(tmps, b, a);
                gridp_places.add(text, b, a);

            }
        }
    }


}
