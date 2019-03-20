package app.home;

import app.Entities.Account;
import app.Entities.User;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.IOException;


public class HomeController {
    public Label name_label;
//    Map<Integer, Account> accounts = new HashMap<>();
    java.util.List<Object> userAccounts = null;
    @FXML
    Pane pane;
    @FXML
    VBox accountsBox;

    @FXML
    void initialize(){
        name_label.setText("Välkommen " + LoginController.getUser().getFirst_name());
        userAccounts = DB.getAccounts(LoginController.getUser().getSocial_number());
        System.out.println(userAccounts);

        userAccounts.forEach(account -> {
            try {
                Account temp = (Account) account;
                generateAccounts(temp.getAccountName());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
//        System.out.println(userAccounts.get(0).getClass());



//        DB.getMatchingUser("940330-5312", "hejhej123");
//        System.out.println(DB.getMatchingUser("940330-5312", "hejhej123"));
//        System.out.println("Accounts" + DB.getAccounts( "940330-5312"));
//        System.out.println(DB.getTransaction(4));
    }

    @FXML
    void generateAccounts(Object account) throws IOException {
        Button accountBtn = new Button("" + account);
        accountBtn.setMinSize(500, 40);
        accountsBox.getChildren().add(accountBtn);
    }


    @FXML
    void goToAccount() throws IOException {

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        Scene scene = new Scene( fxmlInstance, 800, 600 );

        // Make sure that you display "the correct account" based on which one you clicked on
            AccountController controller = loader.getController();
//            controller.setAccount(accountFromDB);

        // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        Main.stage.setScene(scene);
        Main.stage.show();

    }
}
