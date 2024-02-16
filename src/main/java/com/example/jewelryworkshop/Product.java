package com.example.jewelryworkshop;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Product {
    Integer productId;
    String productName;
    Double productSum;
    Integer productCount;

    public Product(){}

    public Product(Integer productId, String productName, Double productSum, Integer productCount){
        this.productId = productId;
        this.productName = productName;
        this.productSum = productSum;
        this.productCount = productCount;
    }

    public static ArrayList<Product> fillCatalog(){
        ArrayList<Product> product = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            product = mapper.readValue(new File("Product.json"), mapper.getTypeFactory().constructCollectionType(List.class, Product.class));
        } catch (IOException error) {
            error.printStackTrace();
        }
        return product;
    }


    public static void editProduct(ObservableList<Product> products){
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File("Product.json"), products);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void setProductName(String productName) {this.productName = productName;}
    public String getProductName() {return productName;}

    public void setProductId(Integer productId) {this.productId = productId;}
    public Integer getProductId() {return productId;}

    public void setProductCount(Integer productCount) {this.productCount = productCount;}
    public Integer getProductCount() {return productCount;}

    public void setProductSum(Double productSum) {this.productSum = productSum;}
    public Double getProductSum() {return productSum;}
}
