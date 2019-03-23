package app.home;

import app.Entities.Account;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.List;

public class HomeController {
    public Label name_label;

    List<Account> userAccounts = null;

    @FXML
    VBox accountsBox;
    @FXML
    Button addAccountButton;
    @FXML
    TextField accountName;
    @FXML
    ComboBox accountType;
    @FXML
    ComboBox accountBoxDelete;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;
    @FXML
    ComboBox accountBoxChange;
    @FXML
    TextField accountNameChange;
    @FXML
    ComboBox pickAccount;
    @FXML
    ComboBox toType;

    private Object Account;

    public

    @FXML
    void initialize() {
        name_label.setText("Välkommen " + LoginController.getUser().getFirst_name());
//        name_label.setAlignment(Pos.TOP_LEFT);
        generateAccounts();
        System.out.println("initialize home");
        accountType.getItems().addAll("Salary", "Savings", "Creditcard");
        generateComboBox();
        accountBoxDelete.setValue("Ta bort konto");
        accountBoxChange.setValue("Konton");
        toType.setValue("Ändra till typ");
        pickAccount.setValue("Konton");
        toType.getItems().addAll("Salary", "Savings", "Creditcard");
    }

    @FXML
    void generateAccounts() {
        accountsBox.getChildren().clear();
//        accountsBox.toFront();
        userAccounts = (List<Account>) DB.getAccounts(LoginController.getUser().getSocial_number());
        userAccounts.forEach(account -> {
            Account = account;
            Button accountBtn = new Button("" + account.getAccountName());
            accountBtn.setMinSize(200, 40);
            accountsBox.getChildren().add(accountBtn);
            accountBtn.setOnAction(event -> {
                try {
                    goToAccount(account.getNumber());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }


    @FXML
    void addAccount() {
        DB.addAccount(accountName.getText(), accountType.getValue().toString(), LoginController.getUser().getSocial_number());
        generateAccounts();
    }

    @FXML
    void deleteAccount() {
        if (!accountBoxDelete.getValue().toString().equals("Ta bort konto")) {
            warningLabel2.setVisible(false);
            System.out.println("Ta bort konto");
            String number = accountBoxDelete.getValue().toString();
            number = number.substring(number.length() - 8);
            long numberr = Long.parseLong(number);
            Account account = DB.getAccount(numberr);
            if (account.getBalance() == 0) {
                System.out.println("numberr = " + numberr);
                DB.deleteAccount(numberr);
                generateAccounts();
                warningLabel1.setVisible(false);
            } else {
                warningLabel1.setVisible(true);
            }
        } else {
            warningLabel2.setVisible(true);
        }
    }

    @FXML
    void changeAccountName() {
        if (!accountBoxChange.getValue().toString().equals("Konton")) {
            String number = accountBoxChange.getValue().toString();
            number = number.substring(number.length() - 8);
            long numberr = Long.parseLong(number);
            if (!accountNameChange.getText().equals("")) {
                System.out.println("NU kan man byta");
                DB.changeAccountName(numberr, accountNameChange.getText());
                generateAccounts();
                generateComboBox();
            } else {
                System.out.println("Du måste fylla i ett namn");
            }
        }else {
            System.out.println("Välj ett konto du vill uppdatera");
        }
    }
    @FXML
    void changeAccountType() {
        System.out.println(pickAccount.getValue().toString());
        System.out.println(toType.getValue().toString());
        if (!pickAccount.getValue().toString().equals("Konton")){
            String numberr = pickAccount.getValue().toString();
            System.out.println(numberr);
            if (!toType.getValue().toString().equals("Ändra till typ")){
            numberr = numberr.substring(numberr.length() - 8);
            long number = Long.parseLong(numberr);
                System.out.println("nu kan man byta" + number);
                DB.changeType(toType.getValue().toString(), number);
                generateComboBox();
            } else {
                System.out.println("du måste fylla i andra lådan");
            }
        } else {
            System.out.println("du måste fylla i första lådan");
        }

    }

    @FXML
    void generateComboBox() {
        accountBoxChange.getItems().clear();
        accountBoxDelete.getItems().clear();
        pickAccount.getItems().clear();
        userAccounts = (List<Account>) DB.getAccounts(LoginController.getUser().getSocial_number());
        userAccounts.forEach(account -> {
            Account = account;
            accountBoxDelete.getItems().addAll(account.getAccountName());
            System.out.println(account.getAccountName());
        });

        userAccounts.forEach(account -> {
            Account = account;
            accountBoxChange.getItems().addAll(account.getAccountName());
            System.out.println(account.getName());
        });

        userAccounts.forEach(account -> {
            Account = account;
            pickAccount.getItems().addAll(account.getAccountNameAndType());
        });
    }


    @FXML
    void goToAccount(Long number) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/account/account.fxml"));
        Parent fxmlInstance = loader.load();
        Scene scene = new Scene(fxmlInstance, 800, 600);
        AccountController controller = loader.getController();
        controller.setAccount(number);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
}
