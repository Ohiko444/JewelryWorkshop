package com.example.jewelryworkshop;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonAutoDetect
public class User {
    public String name;
    public String surname;
    public String login;
    public String password;
    public String type;

    public User(){}

    public User(String name, String surname, String login, String password, String type){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public ArrayList<User> fillUsers(){
        ArrayList<User> users = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            users = mapper.readValue(new File("Users.json"), mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException error) {
            error.printStackTrace();
        }
        return users;
    }

    public String addUsers(String name, String surname, String login , String password){
        ArrayList<User> users = fillUsers();

        for (User prov: users){
            if (Objects.equals(login, prov.login)) {
                return "Логин уже существует";
            }
        }

        if (!Objects.equals(name, "") && !Objects.equals(surname, "") && !Objects.equals(login, "") && !Objects.equals(password, "")) {
            User user = new User(name, surname, login, password, "user");
            users.add(user);
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(new File("Users.json"), users);
            } catch (IOException error) {
                error.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Регистрация прошла успешно");
            alert.setContentText("Вы зарегистрировались");
            alert.showAndWait();
        }
        else {
            return "Попробуйте снова";
        }
        return "Удачно";
    }

    public String Check(String login_id, String password_id){
        ArrayList<User> users = fillUsers();
        for (User user: users){
            if (login_id.equals(user.login)){
                if (password_id.equals(user.password)){
                    this.type = user.type;
                    return "Пароль и логин найдены";
                } else {
                    return "Не верный пароль";
                }
            }
        }
        return "Пользователь не найден";
    }

    public String getPassword() {return password;}
    public String getLogin() {return login;}
    public String getSurname() {return surname;}
    public String getName() {return name;}
    public String getType() {return type;}
}
