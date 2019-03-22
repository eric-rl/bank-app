package app.account;

import app.Entities.Account;
import app.Entities.Transaction;
import app.Main;
import app.db.DB;
import app.transaction.TransactionController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AccountController {
    Account account;
    private Object Transaction;

    @FXML
    Button cardPayment;
    @FXML
    Button salaryIncome;
    @FXML
    VBox transactionBox;
    @FXML
    Button pay;
    List<Transaction> transactions;

    @FXML
    private void initialize() {
        System.out.println("initialize account");
        Platform.runLater(() -> transactions = (List<Transaction>) DB.getTransactions(account.getNumber()));
        Platform.runLater(() -> generateTransactions());
        Platform.runLater(() -> generateCardPaymentButton());
    }


    public void generateMore() {
        transactions = (List<Transaction>) DB.getTransactions(account.getNumber(), 0, Integer.MAX_VALUE);
        generateTransactions();
    }

    public void cardPayment() {
        DB.moveMoney("Creditcard", account.getNumber(), 99999999, 200);
    }

    public void setSalaryIncome() {
        System.out.println("Klickad");
        DB.insertAccountIntoSalary(account.getNumber());
    }

    void generateTransactions() {
        transactionBox.getChildren().clear();
        transactions.forEach(transaction -> {
            Transaction = transaction;
            Label transactionLabel = new Label("" + Transaction.toString());
            transactionLabel.setMinSize(500, 40);
            transactionBox.getChildren().add(transactionLabel);
        });
    }

    public void goToHomeController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/home/home.fxml"));
            Parent fxmlInstance = loader.load();
            Scene scene = new Scene(fxmlInstance);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void generateCardPaymentButton() {
        if (account.getType().equals("Creditcard")) {
            cardPayment.setVisible(true);
        } else if (account.getType().equals("Salary")) {
            salaryIncome.setVisible(true);
        } else {
            cardPayment.setVisible(false);
            salaryIncome.setVisible(false);
        }
    }


    public void goToTransactionController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/transaction/transaction.fxml"));
            Parent fxmlInstance = loader.load();
            Scene scene = new Scene(fxmlInstance, 800, 600);
            TransactionController transactionController = loader.getController();
            transactionController.setThisAccount(account.getNumber());
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccount(long number) {
        account = DB.getAccount(number);
    }
}

