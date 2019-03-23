package app.db;

import app.Entities.Account;
import app.Entities.Transaction;
import app.Entities.User;
import com.mysql.cj.protocol.Resultset;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public static Account getAccount(Long accountNumber) {
        Account accounts = null;
        PreparedStatement ps = prep("SELECT * FROM account WHERE number = ? ");
        try {
            ps.setLong(1, accountNumber);
            accounts = (Account) new ObjectMapper<>(Account.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static List<?> getAccounts(String owner) {
        List<?> accounts = null;
        PreparedStatement ps = prep("SELECT * FROM account WHERE owner = ?");
        try {
            ps.setString(1, owner);
            accounts = new ObjectMapper<>(Account.class).map(ps.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static void changeType(String type, long number) {
        PreparedStatement ps = prep("UPDATE account SET type = ? WHERE number = ?");
        try{
            ps.setString(1, type);
            ps.setLong(2, number);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAccount(String name, String type, String owner){
        PreparedStatement ps = prep("INSERT INTO account SET name = ?, type = ?, owner = ?");
        try {
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setString(3, owner);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addToScheduled(Timestamp date, String message, long sender, long receiver, float amount) {
        PreparedStatement ps = prep("INSERT INTO scheduled_transaction SET date = ?, message = ?, " +
                "sender = ?, receiver = ?, amount = ?");
        try {
            ps.setTimestamp(1, date);
            ps.setString(2, message);
            ps.setLong(3, sender);
            ps.setLong(4, receiver);
            ps.setFloat(5, amount);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(Long number) {
        PreparedStatement ps = prep("DELETE FROM account WHERE number = ?");
        try {
            ps.setLong(1, number);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeAccountName(long number, String name) {
        PreparedStatement ps = prep("UPDATE account SET name = ? WHERE number = ?");
        try {
            ps.setString(1, name);
            ps.setLong(2,number);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    public static List<?> getTransactions(long accountId){ return getTransactions(accountId, 0, 10); }
    public static List<?> getTransactions(long accountId, int offset){ return getTransactions(accountId, offset, offset + 10); }
    public static List<?> getTransactions(long accountId, int offset, int limit){
        List<?> result = null;
        PreparedStatement ps = prep("SELECT * FROM transaction WHERE sender = ? OR receiver = ? ORDER BY `date` DESC LIMIT ? OFFSET ?");
        try {
            ps.setLong(1, accountId);
            ps.setLong(2, accountId);
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            result = new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }

    public static void moveMoney(String message, long sender, long receiver, float amount) {
        CallableStatement stmt;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call create_transaction(?,?,?,?)}");
            stmt.setString(1, message);
            stmt.setLong(2, sender);
            stmt.setLong(3, receiver);
            stmt.setFloat(4, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAccountIntoSalary(long receiverAccount) {
        PreparedStatement ps = prep("INSERT INTO accounts_getting_salary SET number = ?");
        try {
            ps.setLong(1, receiverAccount);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}