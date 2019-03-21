package app.transaction;


import app.Entities.Account;
import app.Entities.Transaction;
import app.Main;
import app.account.AccountController;
import app.home.HomeController;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import app.db.DB;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class TransactionController {
    @FXML
    TextField receiverInput;
    @FXML
    TextField messageInput;
    @FXML
    TextField amountInput;
    @FXML
    TextField senderInput;

    Account account;



    @FXML
    private void initialize(){
        System.out.println("initialize transaction");
        Platform.runLater(()->senderInput.setText(account.numbertoString()));
    }

    public void goToHomeController(){
        AccountController accountController = new AccountController();
        accountController.goToHomeController();
    }


    @FXML
    void moveMoney(){
        String message = messageInput.getText();
        long sender = Integer.parseInt(senderInput.getText());
        long receiver = Integer.parseInt(receiverInput.getText());
        float amount = Integer.parseInt(amountInput.getText());
        DB.moveMoney(message, sender, receiver, amount);
    }





    public void setTransaction() {
//        Transaction transaction = new Transaction();
//        message.setText(transaction.getMessage());
//        amount.setText(transaction.amountToString());
        // etc
        // etc
    }

    public void setAccount(long number) {
        account = DB.getAccount(number);
    }
}
