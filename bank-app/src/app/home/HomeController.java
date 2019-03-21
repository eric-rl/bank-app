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
    public

    @FXML
    void initialize(){
        name_label.setText("Välkommen " + LoginController.getUser().getFirst_name());
        generateAccounts();
        System.out.println("initialize home");
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
        controller.setAccount(number);
        Main.stage.setScene(scene);
        Main.stage.show();
    }


}
