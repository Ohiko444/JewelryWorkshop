package com.example.jewelryworkshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Purchases, String> surname;

    @FXML
    private TableColumn<Purchases, String> name;

    @FXML
    private Tab catalogTab;

    @FXML
    private TableView<Purchases> catalogTable;

    @FXML
    private TableView<Product> tableZak;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private TableColumn<Product, String> kolZak;

    @FXML
    private TableColumn<Product, String> nameZak;

    @FXML
    private TableColumn<Purchases, String> id;

    @FXML
    private Label numberZakLable;

    @FXML
    private TableColumn<Product, String> priceZak;

    @FXML
    private Button saveButton;

    @FXML
    private Label sumZak;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab zakaz;

    @FXML
    private TextField search;

    @FXML
    private Hyperlink hyper;

    private final ObservableList<Purchases> spisok = FXCollections.observableArrayList();

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("catalog.fxml"));
        catalogTab.setContent(loader.load());

        list.add("В обработке");
        list.add("Ожидает оплаты");
        list.add("Оплачено");
        list.add("Доставлено");

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

    @FXML
    private void searchBut(ActionEvent event){
        showZakDetails(Purchases.search(search.getText()));
    }

    @FXML
    private void exitBut(ActionEvent event){
        saveButton.getScene().getWindow().hide();
    }

    @FXML
    private void selectCatalogTab(ActionEvent event){
        tabPane.getSelectionModel().select(catalogTab);
    }

    @FXML
    private void selectZakazTab(ActionEvent event){
        tabPane.getSelectionModel().select(zakaz);
    }

    @FXML
    private void selectCatalogNewTab(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("catalog.fxml"));
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

    @FXML
    private void selectProgramTab(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
        alert.setTitle("О программе");
        alert.setHeaderText("Программа Учебная.");
        alert.setContentText("Версия 1.0.");
        alert.showAndWait();
    }

    @FXML
    private void showStatistic() throws IOException{
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Product> fillProducts = new ArrayList<>();
        try {
            fillProducts = mapper.readValue(new File("orderProduct.json"), mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        } catch (IOException error) {
            error.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("showDiagram.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Статистика продаж");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
        stage.initOwner(null);
        stage.setScene(scene);

        ShowDiagramController controller = fxmlLoader.getController();
        controller.setProductData(fillProducts);

        stage.show();

    }

}
