package com.example.jewelryworkshop;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancel;

    @FXML
    private Button ok;

    @FXML
    private TextField productCountField;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productSumField;

    private Stage dialogStage;
    private Product product;
    public boolean okClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    void setProduct(Product product){
        this.product = product;

        if (product.getProductId() != null) {
            productIdField.setText(Integer.toString(product.getProductId()));
            productNameField.setText(product.getProductName());
            productSumField.setText(Double.toString(product.getProductSum()));
            productCountField.setText(Integer.toString(product.getProductCount()));
        }
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if(productIdField.getText() == null || productIdField.getText().length() == 0){
            errorMessage += "Нет доступного артикула\n";
        }
        if(productNameField.getText() == null || productNameField.getText().length() == 0){
            errorMessage += "Нет доступного наименования товара\n";
        }
        if(productSumField.getText() == null || productSumField.getText().length() == 0){
            errorMessage += "Нет доступного суммы\n";
        }
        if(productCountField.getText() == null || productCountField.getText().length() == 0){
            errorMessage += "Нет доступного количества\n";
        }
        if(errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("C:\\Users\\Daria\\OneDrive\\Рабочий стол\\УЧЁБА\\проект по ПЧМИ\\JewelryWorkshop\\src\\image\\icon.png"));
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Внесите корректную информацию");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            product.setProductId(Integer.parseInt(productIdField.getText()));
            product.setProductName(productNameField.getText());
            product.setProductSum(Double.parseDouble(productSumField.getText()));
            product.setProductCount(Integer.parseInt(productCountField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
}
