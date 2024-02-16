package com.example.jewelryworkshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private Button registration;

    @FXML
    private Button enter;

    @FXML
    private TextField login_id;

    @FXML
    private PasswordField password_id;

    @FXML
    private Button cancel;


    @FXML
    void initialize() {



        enter.setOnAction(actionEvent -> {
            User user = new User();

            if (Objects.equals(user.Check(login_id.getText(), password_id.getText()), "Пароль и логин найдены")){
                if (Objects.equals(user.getType(), "admin")) {
                    enter.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("menuAdmin.fxml"));
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
                } else {
                    enter.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("menu.fxml"));
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
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неудачный вход");
                alert.setContentText(user.Check(login_id.getText(), password_id.getText()));
                alert.showAndWait();
            }
        });

        cancel.setOnAction(actionEvent -> {
            login_id.clear();
            password_id.clear();
        });

        registration.setOnAction(event -> {
            registration.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("registration.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Ювелирная мастерская");
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }

}
