package com.example.jewelryworkshop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Name;

    @FXML
    private TextField Surname;

    @FXML
    private Button regist;

    @FXML
    private TextField login_id;

    @FXML
    private PasswordField password_id;

    @FXML
    private Button cancel;

    @FXML
    private Button enter;

    @FXML
    void initialize() {

        cancel.setOnAction(actionEvent -> {
            Surname.clear();
            Name.clear();
            login_id.clear();
            password_id.clear();
        });

        enter.setOnAction(actionEvent -> {
            regist.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("authorization.fxml"));
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


        regist.setOnAction(actionEvent -> {
            User user = new User();
            String otv = user.addUsers(Name.getText(), Surname.getText(), login_id.getText(), password_id.getText());

            if (!Objects.equals(otv, "Удачно")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неудачная регистрация");
                alert.setContentText(otv);
                alert.showAndWait();
            }
            else {
                regist.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("authorization.fxml"));
                Scene scene;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = new Stage();
                stage.setTitle("Ювелирная мастерская");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

}