package app.transaction;

import app.Entities.Account;
import app.account.AccountController;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import app.db.DB;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionController {
    @FXML
    TextField receiverInput;
    @FXML
    TextField messageInput;
    @FXML
    TextField amountInput;
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
    TextField ammountS;
    @FXML
    TextField messageS;
    @FXML
    Label succsessOne;
    @FXML
    Label warningOne;
    @FXML
    Label thisAccountLabel;
    @FXML
    Label scheduleWarning;
    @FXML
    Label ocrLabel;
    @FXML
    Label messageLabel;
    @FXML
    CheckBox ocrORmessageButton;


    private Object Account;
    Account thisAccount;
    List<Account> userAccounts = null;

    @FXML
    private void initialize() {
        System.out.println("initialize transaction");

        Platform.runLater(() -> generateComboBox());
        intervallS.getItems().addAll("ONCE", "MINUTE", "HOUR", "MONTH", "YEAR");
        Platform.runLater(() -> thisAccountLabel.setText("Överförningar från " + thisAccount.getAccountName()));
        Platform.runLater(() -> thisAccountLabel.setAlignment(Pos.TOP_CENTER));



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
    void ocrORmessage() {
        if (ocrORmessageButton.isSelected()){
        messageLabel.setVisible(false);
        ocrLabel.setVisible(true);
        } else {
            messageLabel.setVisible(true);
            ocrLabel.setVisible(false);
        }
    }

    @FXML
    void setReceiverWithComboBox() {
        String number = accountsBox.getValue().toString();
        receiverInput.setText(number.substring(number.length() - 8));
    }

    @FXML
    void scheduledMoveMoney() {
        if (intervallS.getValue() == null ||
                toAccountS.getText().equals("") ||
                ammountS.getText().equals("") ||
                messageS.getText().equals("") ||
                startDateS.getValue().equals(null) ||
                endDateS.getValue().equals(null) ||
                !startDateS.getValue().isBefore(endDateS.getValue())) {
            scheduleWarning.setVisible(true);
        } else {
            scheduleWarning.setVisible(false);
        String name = thisAccount.getName() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("mmssms"));

        Timestamp startDate = Timestamp.valueOf(startDateS.getValue().atTime(LocalTime.now()));
        Timestamp endDate = Timestamp.valueOf(endDateS.getValue().atTime(LocalTime.now()));

        long receiver = Integer.parseInt(toAccountS.getText());
        long sender = thisAccount.getNumber();
        float amount = Integer.parseInt(ammountS.getText());
        String message = messageS.getText();

        String dateBuilder = "EVERY 1 " + intervallS.getValue() + " STARTS " + "'"+startDate.toString().substring(0,19)+"'" + " ENDS " + "'"+endDate.toString().substring(0,19)+"'";
        System.out.println(dateBuilder);
        DB.addToScheduled(name, dateBuilder, message, sender, receiver, amount);
        }
    }


    @FXML
    void moveMoney() {
        if (messageInput.getText().equals("") ||
                receiverInput.getText().equals("") ||
                amountInput.getText().equals("")) {
            warningOne.setVisible(true);
        } else {
            warningOne.setVisible(false);
            String message = messageInput.getText();
            long sender = thisAccount.getNumber();
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
