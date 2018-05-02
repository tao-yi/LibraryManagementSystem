package com.mycompany.controller;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterWindowController implements Initializable{
    @FXML private TextField account;
    @FXML private TextField password;
    @FXML private Button registerButton;
    @FXML private Button backButton;

    private AbstractUserService userService;

    public void backToMainWindow(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    private void register(ActionEvent event) throws SQLException, IOException {
       userService = new UserServiceImpl();
       String account_input = account.getText();
       String pwd_input = password.getText();
       if(account_input.isEmpty() || pwd_input.isEmpty()){
           showAlert(Alert.AlertType.ERROR, "请输入账户和密码");
           return;
       }
       User u = new User();
       u.setAccount(account_input);
       u.setPassword(pwd_input);
       if(userService.register(u)!=null){
           showAlert(Alert.AlertType.INFORMATION, "注册成功");
           backToMainWindow(event);
       }else {
           showAlert(Alert.AlertType.WARNING, "该用户名已经存在");
       }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹窗提示
     * @param alertType 警告类型
     * @param message 提示消息
     * */
    private void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(300,100);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                alert.close();
            }
        });
    }

}
