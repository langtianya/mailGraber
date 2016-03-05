/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import com.wangzhe.service.AdslHandler;
import com.wangzhe.util.ConfigManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class IpSettingPaneController implements Initializable {

    @FXML
    private Label lb_testResult;
    @FXML
    private TextField tf_netAccount;
    @FXML
    private TextField tf_netPassword;
    @FXML
    private TextField tf_reDialTimes;
    @FXML
    private TextField tf_disnetTime;

    /**
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf_netAccount.setText(ConfigManager.getNetAccount());
        tf_netPassword.setText(ConfigManager.getNetPassword());
        tf_reDialTimes.setText(ConfigManager.getReDialTimes());
        tf_disnetTime.setText(ConfigManager.getDisnetTime());

        tf_netAccount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConfigManager.setNetAccount(tf_netAccount.getText());
            }
        });
        tf_netPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConfigManager.setNetPassword(tf_netPassword.getText());
            }
        });
        tf_reDialTimes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConfigManager.setReDialTimes(tf_reDialTimes.getText());
            }
        });
        tf_disnetTime.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConfigManager.setDisnetTime(tf_reDialTimes.getText());
            }
        });
    }

    @FXML
    public void testConnect() {
        boolean isSuccess = AdslHandler.getInstance().connect("", "");
        if (isSuccess) {
            lb_testResult.setText("测试成功");
            AdslHandler.destory();
        }
    }
}
