package app.Entities;


import app.annotations.Column;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    @Column
    private long id;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String social_number;
    @Column
    private String password;
    @Column
    private String mail;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", social_number='" + social_number + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }


    public String nameToString() {
        return "User{" +
                "first_name='" + first_name + '\'' +
                '}';
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSocial_number() {
        return social_number;
    }

    //    public User(ResultSet resultSet) {
//        try {
//            this.id = resultSet.getLong("id");
//            this.first_name = resultSet.getString("first_name");
//            this.last_name = resultSet.getString("last_name");
//            this.social_number = resultSet.getString("social_number");
//            this.password = resultSet.getString("password");
//            this.mail = resultSet.getString("mail");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
