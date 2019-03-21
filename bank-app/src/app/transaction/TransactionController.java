package app.transaction;


import app.Main;
import app.account.AccountController;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import app.db.DB;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TransactionController {
    @FXML
    TextField receiverInput;
    @FXML
    TextField messageInput;
    @FXML
    TextField amountInput;
    @FXML
    TextField senderInput;



    @FXML
    private void initialize(){
        System.out.println("initialize transaction");
    }

    public void goToHomeController(){
        AccountController accountController = new AccountController();
        accountController.goToHomeController();
    }


    @FXML
    void moveMoney(){
        Platform.runLater(()->{
        String message = messageInput.getText();
        long sender = Integer.parseInt(senderInput.getText());
        long receiver = Integer.parseInt(receiverInput.getText());
        float amount = Integer.parseInt(amountInput.getText());
            System.out.println(message + ", " +  sender + " ," +  receiver + ", " + amount);
        DB.moveMoney(message, sender, receiver, amount);
        });
    }





    public void setTransaction() {
//        Transaction transaction = new Transaction();
//        message.setText(transaction.getMessage());
//        amount.setText(transaction.amountToString());
        // etc
        // etc
    }
}
