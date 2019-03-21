package app.transaction;

import app.Entities.Account;
import app.account.AccountController;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import app.db.DB;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    @FXML
    ComboBox accountsBox;

    private Object Account;
    Account thisAccount;
    List<Account> userAccounts = null;

    @FXML
    private void initialize(){
        System.out.println("initialize transaction");
        Platform.runLater(()-> senderInput.setText(thisAccount.numbertoString()));
        Platform.runLater(()-> generateComboBox());
    }

    public void goToHomeController(){
        AccountController accountController = new AccountController();
        accountController.goToHomeController();
    }

    @FXML
    void generateComboBox() {
        userAccounts = (List<Account>)DB.getAccounts(LoginController.getUser().getSocial_number());
        System.out.println(userAccounts);
        userAccounts.forEach(account -> {
            Account = account;
            if (account.getNumber() != thisAccount.getNumber()) {
            accountsBox.getItems().addAll(account.getAccountName());
            } else {
                System.out.println("Det fanns i listan");
            }
        });
    }

    @FXML
    void setReceiverWithComboBox() {
        String number = accountsBox.getValue().toString();
        receiverInput.setText(number.substring(number.length() - 8));
    }


    @FXML
    void moveMoney(){
        String message = messageInput.getText();
        long sender = Integer.parseInt(senderInput.getText());
        long receiver = Integer.parseInt(receiverInput.getText());
        float amount = Integer.parseInt(amountInput.getText());
        DB.moveMoney(message, sender, receiver, amount);
    }

    public void setThisAccount(long number) {
        thisAccount = DB.getAccount(number);
    }
}
