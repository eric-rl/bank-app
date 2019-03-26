package app.home;

import app.Entities.Account;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomeController {

    List<Account> userAccounts = null;

    @FXML
    Label name_label;
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
    @FXML
    Label changeTypeWarning;
    @FXML
    Label changeNameWarning;
    @FXML
    Label addAccountWarning;



    private Object Account;

    public

    @FXML
    void initialize() {
        name_label.setText("Välkommen " + LoginController.getUser().getFirst_name());
        generateAccounts();
        System.out.println("initialize home");
        accountType.getItems().addAll("Salary", "Savings", "Creditcard");
        generateComboBox();
        accountBoxDelete.setValue("Ta bort konto");
        accountBoxChange.setValue("Konton");
        toType.setValue("Ändra till typ");
        pickAccount.setValue("Konton");
        toType.getItems().addAll("Salary", "Savings", "Creditcard");
        accountType.setValue("Konto typ");

    }

    @FXML
    void generateAccounts() {
        accountsBox.getChildren().clear();
        userAccounts = (List<Account>) DB.getAccounts(LoginController.getUser().getSocial_number());
        userAccounts.forEach(account -> {
            Account = account;
            Button accountBtn = new Button("" + account.getAccountName());
            accountBtn.setMinSize(300, 40);
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
        if (!accountType.getValue().toString().equals("Konto typ") && !accountName.getText().equals("")) {
            addAccountWarning.setVisible(false);
            DB.addAccount(accountName.getText(), accountType.getValue().toString(), LoginController.getUser().getSocial_number());
            generateAccounts();
            succsess();

        } else {
            addAccountWarning.setVisible(true);
        }
    }

    @FXML
    public void succsess() {
        Stage successWindow = new Stage();
        successWindow.initModality(Modality.APPLICATION_MODAL);
        successWindow.setTitle("Välj användarnamn");
        successWindow.setMinWidth(300);
        successWindow.setMinHeight(200);
        VBox layout = new VBox(10);
        Label sccsessLabel = new Label("Din handling har blivit utförd!");
        Button okButton = new Button("Ok");
        layout.getChildren().addAll(
                sccsessLabel,
                okButton
        );
        layout.setAlignment(Pos.CENTER);
        successWindow.setResizable(false);
        okButton.setOnAction(e -> successWindow.close());

        Scene scene = new Scene(layout, 200, 200);
        successWindow.setScene(scene);
        successWindow.showAndWait();

    }

    @FXML
    void deleteAccount() {
        if (!accountBoxDelete.getValue().toString().equals("Ta bort konto")) {
            warningLabel2.setVisible(false);
            String number = accountBoxDelete.getValue().toString();
            number = number.substring(number.length() - 9);
            long numberr = Long.parseLong(number);
            Account account = DB.getAccount(numberr);
            System.out.println("klara");
            if (account.getBalance() == 0) {
                System.out.println("numberr = " + numberr);
                DB.deleteAccount(numberr);
                generateAccounts();
                warningLabel1.setVisible(false);
                succsess();
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
            changeNameWarning.setVisible(false);
            String number = accountBoxChange.getValue().toString();
            number = number.substring(number.length() - 9);
            long numberr = Long.parseLong(number);
            if (!accountNameChange.getText().equals("")) {
                System.out.println("NU kan man byta");
                DB.changeAccountName(numberr, accountNameChange.getText());
                generateAccounts();
                generateComboBox();
                succsess();
            } else {
                changeNameWarning.setVisible(true);
            }
        } else {
            changeNameWarning.setVisible(true);
        }
    }

    @FXML
    void changeAccountType() {
        if (!pickAccount.getValue().toString().equals("Konton")) {
            changeTypeWarning.setVisible(false);
            String numberr = pickAccount.getValue().toString();
            if (!toType.getValue().toString().equals("Ändra till typ")) {
                changeTypeWarning.setVisible(false);
                numberr = numberr.substring(numberr.length() - 9);
                long number = Long.parseLong(numberr);
                DB.changeType(toType.getValue().toString(), number);
                generateComboBox();
                succsess();
            } else {
                changeTypeWarning.setVisible(true);
            }
        } else {
            changeTypeWarning.setVisible(true);
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
        });

        userAccounts.forEach(account -> {
            Account = account;
            accountBoxChange.getItems().addAll(account.getAccountName());
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
