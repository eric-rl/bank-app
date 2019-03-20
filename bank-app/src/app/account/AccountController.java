package app.account;


import app.Entities.Account;
import app.Entities.Transaction;
import app.db.DB;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AccountController {
    Account account;
    private Object Transaction;

    @FXML
    VBox transactionBox;

    @FXML
    private void initialize(){
        System.out.println("initialize account");
        Platform.runLater(() -> generateTransactions());
    }

    void generateTransactions(){
        List<Transaction> transactions = (List<Transaction>) DB.getTransactions(account.getNumber());
        transactions.forEach(transaction -> {
            Transaction = transaction;
            Label transactionLabel = new Label("" + Transaction.toString());
            transactionLabel.setMinSize(500, 40);
            transactionBox.getChildren().add(transactionLabel);
        });
//        displayTransaction(transactions);
        System.out.println("In generateTransaction" + DB.getTransactions(account.getNumber()));
    }


    void displayTransaction(List<Transaction> transactions){
        // For every transaction, do the following:

        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/transaction/transaction.fxml" ) );
            Parent fxmlInstance = loader.load();
            Scene scene = new Scene( fxmlInstance );

//            TransactionController controller = loader.getController();
//            controller.setTransaction(transaction);

            transactionBox.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccount(long number) {
        account = DB.getAccount(number);
    }

    @FXML void clickLoadTransactions(Event e) { generateTransactions(); }
}
