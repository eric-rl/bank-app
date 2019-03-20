package app.db;

import app.Entities.Account;
import app.Entities.Transaction;
import app.Entities.User;
import com.mysql.cj.protocol.Resultset;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Helper class for interacting with the Database using short-commands
 */
public abstract class DB {
    Map<String, Account> accounts = new HashMap<>();
    Resultset resultSet = null;

//    List<User> getUsers(String social_number, String password) {
//        List<User> result = new ArrayList<>();
//        PreparedStatement ps = prep("SELECT * FROM user WHERE social_number = ? AND password = ?");
//        try {
//            ps.setString(1, social_number);
//            ps.setString(2, password);
//
//            resultSet = ps.executeQuery();
//
//            while (resultSet.next()) {
//                result.add(new User(
//                        resultSet
//                ));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    public static PreparedStatement prep(String SQLQuery) {
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static Account getAccount(String owner) {
        Account accounts = null;
        PreparedStatement ps = prep("SELECT * FROM account WHERE owner = ? ");
        try {
            ps.setString(1, owner);
            accounts = (Account) new ObjectMapper<>(Account.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static List<Object> getAccounts(String owner) {
        List<Object> accounts = null;
        PreparedStatement ps = prep("SELECT * FROM account WHERE owner = ?");
        try {
            ps.setString(1, owner);
            accounts = new ObjectMapper<>(Account.class).map(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static Transaction getTransaction(long id) {
        Transaction transactions = null;
        PreparedStatement ps = prep("SELECT * FROM transaction WHERE id = ? ");
        try {
            ps.setLong(1, id);
            transactions = (Transaction) new ObjectMapper<>(Transaction.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static User getMatchingUser(String social_number, String password) {
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM user WHERE social_number = ? AND password = ?");
        try {
            ps.setString(1, social_number);
            ps.setString(2, password);
            result = (User) new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result; // return User;
    }

/*
        Example method with default parameters
    public static List<Transaction> getTransactions(int accountId){ return getTransactions(accountId, 0, 10); }
    public static List<Transaction> getTransactions(int accountId, int offset){ return getTransactions(accountId, offset, offset + 10); }
    public static List<Transaction> getTransactions(int accountId, int offset, int limit){
        List<Transaction> result = null;
        PreparedStatement ps = prep("bla bla from transactions WHERE account-id = "+accountId+" OFFSET "+offset+" LIMIT "+limit);
        try {
            result = (List<Transaction>)new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }

*/


}