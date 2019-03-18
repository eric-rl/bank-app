package app.home;

import app.Entities.Account;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.db.Database;
import app.transaction.TransactionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeController {
    Map<Integer, Account> accounts = new HashMap<>();

    @FXML
    void initialize(){
        DB.getAccount("940330-5312");
        System.out.println(DB.getAccount("940330-5312"));
        DB.getMatchingUser("940330-5312", "hejhej123");
        System.out.println(DB.getMatchingUser("940330-5312", "hejhej123"));

    }

    @FXML
    void goToAccount() throws IOException {

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        Scene scene = new Scene( fxmlInstance, 800, 600 );

        // Make sure that you display "the correct account" based on which one you clicked on
//            AccountController controller = loader.getController();
//            controller.setAccount(accountFromDB);

        // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        Main.stage.setScene(scene);
        Main.stage.show();

    }
}
