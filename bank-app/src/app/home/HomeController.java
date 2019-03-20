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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;


public class HomeController {
    public Label name_label;

    List<Account> userAccounts = null;

    @FXML
    VBox accountsBox;
    private Object Account;

    @FXML
    void initialize(){
        name_label.setText("Välkommen " + LoginController.getUser().getFirst_name());
        generateAccounts();
        System.out.println("initialize home");




//        DB.getMatchingUser("940330-5312", "hejhej123");
//        System.out.println(DB.getMatchingUser("940330-5312", "hejhej123"));
//        System.out.println("Accounts" + DB.getAccounts( "940330-5312"));
//        System.out.println(DB.getTransaction(4));
    }

    @FXML
    void generateAccounts() {

        userAccounts = (List<Account>)DB.getAccounts(LoginController.getUser().getSocial_number());
        userAccounts.forEach(account -> {
            Account = account;
            Button accountBtn = new Button("" +account.getAccountName());
            accountBtn.setMinSize(500, 40);
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
    void goToAccount(Long number) throws IOException {

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        Scene scene = new Scene( fxmlInstance, 800, 600 );

        AccountController controller = loader.getController();


        // Make sure that you display "the correct account" based on which one you clicked on
            controller.setAccount(number);

        // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        Main.stage.setScene(scene);
        Main.stage.show();

    }


}
