package com.example.jewelryworkshop;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BasketController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Purchases> catalogTable;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private Hyperlink hyper;

    @FXML
    private TableColumn<Purchases, String> id;

    @FXML
    private TableColumn<Product, String> kolZak;

    @FXML
    private TableColumn<Purchases, String> name;

    @FXML
    private TableColumn<Product, String> nameZak;

    @FXML
    private Label numberZakLable;

    @FXML
    private TableColumn<Product, String> priceZak;

    @FXML
    private Button saveButton;

    @FXML
    private TextField search;

    @FXML
    private Button searchButton;

    @FXML
    private Label sumZak;

    @FXML
    private TableColumn<Purchases, String> surname;

    @FXML
    private TableView<Product> tableZak;

    private final ObservableList<Purchases> spisok = FXCollections.observableArrayList();

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private void searchBut(ActionEvent event){
        showZakDetails(Purchases.search(search.getText()));
    }

    @FXML
    void initialize() {

        list.add("Оплачено");
        list.add("В обработке");

        comboBox1.setDisable(true);

        comboBox1.getItems().addAll(list);

        ArrayList<Purchases> spisok1 = Purchases.fillPurchases();

        spisok.addAll(spisok1);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        catalogTable.setItems(spisok);

        catalogTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showZakDetails(newValue));
    }


    private void showZakDetails(Purchases purchases){
        ObjectMapper mapper = new ObjectMapper();
        if (purchases != null){
            double sum = 0;
            numberZakLable.setText(purchases.getId().toString());
            comboBox1.setValue(purchases.getStatus());
            ObservableList<Product> products = FXCollections.observableArrayList();

            ArrayList<Product> fillProducts = new ArrayList<>();
            try {
                fillProducts = mapper.readValue(new File("orderProduct.json"), mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            } catch (IOException error) {
                error.printStackTrace();
            }

            for (int choice: purchases.getProducts()){
                for (Product product: fillProducts) {
                    if (choice == product.getProductId()){

                        products.add(product);
                        sum += product.productSum * product.productCount;
                    }
                }
            }
            nameZak.setCellValueFactory(new PropertyValueFactory<>("productName"));
            kolZak.setCellValueFactory(new PropertyValueFactory<>("productCount"));
            priceZak.setCellValueFactory(new PropertyValueFactory<>("productSum"));
            tableZak.setItems(products);
            sumZak.setText(Double.toString(sum));
            hyper.setOnAction(actionEvent -> {
                comboBox1.setDisable(false);
            });
            saveButton.setOnAction(event -> {
                purchases.setStatus(comboBox1.getValue());
                comboBox1.setDisable(true);
                int ind = catalogTable.getSelectionModel().getSelectedIndex();
                spisok.set(ind, purchases);
                try{
                    mapper.writeValue(new File("orders.json"), spisok);
                } catch (IOException error){
                    error.printStackTrace();
                }
            });
        } else{
            numberZakLable.setText("");
            sumZak.setText("");
        }
    }

}

