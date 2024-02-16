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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CatalogUserController {

    private final ObservableList<Product> productData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField surname;

    @FXML
    private TextField name;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Product> catalogTable;

    @FXML
    private TextField count;

    @FXML
    private TableColumn<Product, String> productCount;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private Label productNameLable;

    @FXML
    private TableColumn<Product, String> productSum;

    @FXML
    private Label productSumLable;

    @FXML
    private Button removeButton;

    private Product danProduct;

    @FXML
    void initialize() {
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Product> list = Product.fillCatalog();
        productData.addAll(list);

        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productSum.setCellValueFactory(new PropertyValueFactory<>("productSum"));
        productCount.setCellValueFactory(new PropertyValueFactory<>("productCount"));

        catalogTable.setItems(productData);

        showProductDetails(null);

        catalogTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));

        addButton.setOnAction(event -> {
            ArrayList<Product> fillProducts = new ArrayList<>();
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

            if (productNameLable != null && productSumLable != null && count != null && surname != null && name != null) {
                fillProducts.add(new Product(fillProducts.size() + 1, danProduct.productName, danProduct.productSum, Integer.parseInt(count.getText())));
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
                alert.setHeaderText("Добавление продукта");
                alert.setContentText("Успешно");
                alert.showAndWait();
            }

        });
    }


    private void showProductDetails(Product product){
        if (product != null){
            productNameLable.setText(product.getProductName());
            productSumLable.setText(Double.toString(product.getProductSum()));
            danProduct = product;
        } else {
            productNameLable.setText("");
            productSumLable.setText("");
        }
    }

}

