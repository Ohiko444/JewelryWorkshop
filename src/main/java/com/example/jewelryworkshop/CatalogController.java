package com.example.jewelryworkshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class CatalogController {

    private final ObservableList<Product> productData = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> catalogTable;

    @FXML
    private TableColumn<Product, String> productCount;

    @FXML
    private Label productCountLable;

    @FXML
    private TableColumn<Product, String> productId;

    @FXML
    private Label productIdLable;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private Label productNameLable;

    @FXML
    private TableColumn<Product, String> productSum;

    @FXML
    private Label productSumLable;

    @FXML
    void initialize() {

        ArrayList<Product> list = Product.fillCatalog();
        productData.addAll(list);

        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productSum.setCellValueFactory(new PropertyValueFactory<>("productSum"));
        productCount.setCellValueFactory(new PropertyValueFactory<>("productCount"));

        catalogTable.setItems(productData);

        showProductDetails(null);

        catalogTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    @FXML
    private void deleteProduct(){
        int selectedIndex = catalogTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            catalogTable.getItems().remove(selectedIndex);

            Product.editProduct(catalogTable.getItems());

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            alert.initOwner(null);
            alert.setTitle("Не выделено");
            alert.setHeaderText("Не выбран товар");
            alert.setContentText("Выберите товар в таблице");

            alert.showAndWait();
        }
    }

    @FXML
    private void newProduct(){
        Product tempProduct = new Product();

        boolean okClicked = showProduct(tempProduct);

        if (okClicked){
            productData.add(tempProduct);

            Product.editProduct(productData);
        }
    }

    @FXML
    private void editProduct(){
        Product product = catalogTable.getSelectionModel().getSelectedItem();
        if(product != null){
            boolean okClicked = showProduct(product);

            if(okClicked){
                showProductDetails(product);

                int ind = catalogTable.getSelectionModel().getSelectedIndex();
                productData.set(ind, product);
                Product.editProduct(productData);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            alert.initOwner(null);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Нет выбраного продукта");
            alert.setContentText("Выберите продукт в таблице");

            alert.showAndWait();
        }
    }

    public boolean showProduct(Product product){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EditSceneController.class.getResource("edit.fxml"));            Scene scene;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Ювелирная мастерская");
        stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));

        // Передаём адресата в контроллер.
        EditSceneController controller = loader.getController();
        controller.setDialogStage(stage);
        controller.setProduct(product);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        stage.showAndWait();

        return controller.isOkClicked();
    }

    private void showProductDetails(Product product){
        if (product != null){
            productIdLable.setText(Integer.toString(product.getProductId()));
            productNameLable.setText(product.getProductName());
            productSumLable.setText(Double.toString(product.getProductSum()));
            productCountLable.setText(Integer.toString(product.getProductCount()));
        } else {
            productIdLable.setText("");
            productNameLable.setText("");
            productCountLable.setText("");
            productSumLable.setText("");
        }
    }

}

