package com.example.jewelryworkshop;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button basketButton;

    @FXML
    private Button catalogButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {

        exitButton.setOnAction(event -> {
            exitButton.getScene().getWindow().hide();
        });

        catalogButton.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("catalogUser.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("Ювелирная мастерская");
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            stage.setScene(scene);
            stage.show();
        });

        orderButton.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("requestUser.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("Ювелирная мастерская");
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            stage.setScene(scene);
            stage.show();
        });

        basketButton.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("basket.fxml"));
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            stage.setTitle("Ювелирная мастерская");
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            stage.setScene(scene);
            stage.show();
        });

    }

}
