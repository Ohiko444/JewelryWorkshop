package com.example.jewelryworkshop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("authorization.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        
        stage.setTitle("Ювелирная мастерская");
        stage.setScene(scene);
        stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}