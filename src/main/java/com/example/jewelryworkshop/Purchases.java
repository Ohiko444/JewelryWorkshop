package com.example.jewelryworkshop;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
public class Purchases {

    String id;
    String surname;
    String name;
    String status;
    public Integer[] products;

    public Purchases(){}

    public Purchases(String id, String surname, String name, String status , Integer[] products){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.status = status;
        this.products = products;
    }

    public static ArrayList<Purchases> fillPurchases(){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Purchases> purchases = new ArrayList<>();

        try {
            purchases = mapper.readValue(new File("orders.json"), mapper.getTypeFactory().constructCollectionType(List.class, Purchases.class));
        } catch (IOException error){
            error.printStackTrace();
        }

        return purchases;
    }

    public static Purchases search(String number){
        ArrayList<Purchases> purchases = fillPurchases();

        for (Purchases purchase: purchases){
            if (purchase.id.equals(number)){
                return purchase;
            }
        }
        return null;
    }

    public Integer[] getProducts() {return products;}
    public void setProducts(Integer[] products) {this.products = products;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}
