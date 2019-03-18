package app.Entities;


import app.annotations.Column;

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
}
