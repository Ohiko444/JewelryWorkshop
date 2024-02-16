package com.example.jewelryworkshop;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RepairController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enter;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private ComboBox<String> type;

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        list.add("Кольцо");
        list.add("Цепочка");
        list.add("Браслет");
        list.add("Подвеска");
        list.add("Серьги");

        type.getItems().addAll(list);

        enter.setOnAction(event -> {
            ArrayList<Product> fillProducts = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                fillProducts = mapper.readValue(new File("orderProduct.json"), mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            } catch (IOException error) {
                error.printStackTrace();
            }
            ArrayList<Purchases> zak = new ArrayList<>();
            try {
                zak = mapper.readValue(new File("orders.json"), mapper.getTypeFactory().constructCollectionType(List.class, Purchases.class));
            } catch (IOException error) {
                error.printStackTrace();
            }

            if (type.getValue() != null && surname != null && name != null) {
                fillProducts.add(new Product(fillProducts.size() + 1, type.getValue(), 1000.0, 1));
                try {
                    mapper.writeValue(new File("orderProduct.json"), fillProducts);
                } catch (IOException error) {
                    error.printStackTrace();
                }

                boolean perem = false;
                for (int i = 0; i < zak.size(); i++) {
                    if (surname.getText().equals(zak.get(i).surname) && name.getText().equals(zak.get(i).name)) {
                        perem = true;
                        List<Integer> prod = new ArrayList<>(Arrays.asList(zak.get(i).products));
                        Integer a = fillProducts.size();
                        prod.add(a);

                        zak.get(i).products = prod.toArray(new Integer[0]);
                    }
                }
                Integer[] mas = {fillProducts.size()};
                if (!perem) {
                    zak.add(new Purchases(Integer.toString(zak.size() + 1), surname.getText(), name.getText(), "В обработке", mas));
                }

                try {
                    mapper.writeValue(new File("orders.json"), zak);
                } catch (IOException error) {
                    error.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
                alert.setTitle("Ювелирная мастерская");
                alert.setHeaderText("Заявка на ремонт");
                alert.setContentText("Успешно");
                alert.showAndWait();
            }

        });
    }

}
