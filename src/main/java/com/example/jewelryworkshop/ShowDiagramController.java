package com.example.jewelryworkshop;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class ShowDiagramController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<Product, String> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> productNames = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        String[] products = {"Кольцо", "Цепочка", "Браслет", "Подвеска", "Серьги"};

        productNames.addAll(Arrays.asList(products));
        xAxis.setCategories(productNames);
    }

    public void setProductData(List<Product> orders){
        int[] productCounter = new int[productNames.size()];
        for (Product order: orders){
            for (int i = 0; i < productNames.size(); i++) {
                if (order.productName.equals(productNames.get(i))){
                    productCounter[i] += order.productCount;
                }
            }
        }

        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < productCounter.length; i++){
            series.getData().add(new XYChart.Data<>(productNames.get(i), productCounter[i]));
        }

        barChart.getData().add(series);
    }

}
