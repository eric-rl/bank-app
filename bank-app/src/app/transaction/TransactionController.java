package app.transaction;

import app.Entities.Account;
import app.account.AccountController;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import app.db.DB;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalTime;
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
    @FXML
    DatePicker startDateS;
    @FXML
    DatePicker endDateS;
    @FXML
    ChoiceBox intervallS;
    @FXML
    TextField toAccountS;
    @FXML
    TextField senderS;
    @FXML
    TextField ammountS;
    @FXML
    TextField messageS;
    @FXML
    Label succsessOne;
    @FXML
    Label warningOne;


    private Object Account;
    Account thisAccount;
    List<Account> userAccounts = null;

    @FXML
    private void initialize() {
        System.out.println("initialize transaction");
        Platform.runLater(() -> senderInput.setText(thisAccount.numbertoString()));
        Platform.runLater(() -> generateComboBox());
        intervallS.getItems().addAll("ONCE", "MINUTE", "HOUR", "MONTH", "YEAR");
    }

    public void goToHomeController() {
        AccountController accountController = new AccountController();
        accountController.goToHomeController();
    }

    @FXML
    void generateComboBox() {
        userAccounts = (List<Account>) DB.getAccounts(LoginController.getUser().getSocial_number());
        userAccounts.forEach(account -> {
            Account = account;
            if (account.getNumber() != thisAccount.getNumber()) {
                accountsBox.getItems().addAll(account.getAccountName());
            }
        });


    }

    @FXML
    void setReceiverWithComboBox() {
        String number = accountsBox.getValue().toString();
        receiverInput.setText(number.substring(number.length() - 8));
    }

    @FXML
    void scheduledMoveMoney() {
        System.out.println(intervallS.getValue());

        Timestamp startDate = Timestamp.valueOf(startDateS.getValue().atTime(LocalTime.now()));
        Timestamp endDate = Timestamp.valueOf(endDateS.getValue().atTime(LocalTime.now()));
        long receiver = Integer.parseInt(toAccountS.getText());
        long sender = Integer.parseInt(senderS.getText());
        float amount = Integer.parseInt(ammountS.getText());
        String message = messageS.getText();
        DB.addToScheduled(startDate, message, sender, receiver, amount);
    }


    @FXML
    void moveMoney() {
        if (messageInput.getText().equals("") ||
                receiverInput.getText().equals("") ||
                senderInput.getText().equals("") ||
                amountInput.getText().equals("")) {
            warningOne.setVisible(true);
        } else {
            warningOne.setVisible(false);
            String message = messageInput.getText();
            long sender = Integer.parseInt(senderInput.getText());
            long receiver = Integer.parseInt(receiverInput.getText());
            float amount = Integer.parseInt(amountInput.getText());
            DB.moveMoney(message, sender, receiver, amount);
            succsessOne.setVisible(true);
        }
    }

    public void setThisAccount(long number) {
        thisAccount = DB.getAccount(number);
    }
}
