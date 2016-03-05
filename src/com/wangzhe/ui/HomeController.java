/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import com.wangzhe.service.impl.MailGraber;
import com.wangzhe.util.ConfigManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author ocq
 */
public class HomeController implements Initializable {

    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HomeController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_voteUrl.setText(ConfigManager.getVoteUrl());
        tf_voteIds.setText(ConfigManager.getProductionFlag());
        tf_voteUrl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConfigManager.setVoteUrl(tf_voteUrl.getText());
            }
        });
        tf_voteIds.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ConfigManager.setProductionFlag(tf_voteIds.getText());
            }
        });
//        tf_voteUrl.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                ConfigManager.setVoteUrl(tf_voteUrl.getText());
//            }
//        });
//        tf_voteIds.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                ConfigManager.setProductionFlag(tf_voteIds.getText());
//            }
//        });
        return;
    }

    @FXML
    private CheckBox fx_checkFengzhiyan;

    @FXML
    private CheckBox fx_checkShengFang;

    @FXML
    private TextField fx_maxVoteTime;//最大投票多少

    @FXML
    public Button fx_startVoteButtom;

    @FXML
    public Button fxstopVoteButtom;

    @FXML
    public TextArea fx_txtareaLog;
    @FXML
    public Label lb_successDis;

    @FXML
    private TextField tx_minS;//间隔最小时间
    @FXML
    private TextField tx_maxS;
    @FXML
    private TextField tf_voteUrl;
    @FXML
    private TextField tf_voteIds;

    @FXML
    public void startVote() {
        MailGraber.isStop = false;
        HomeController instance = this;
        fxstopVoteButtom.setDisable(false);
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<String> ids = new ArrayList<String>(5);
                if (fx_checkFengzhiyan.isSelected()) {
                    ids.add("71");
                }
                if (fx_checkShengFang.isSelected()) {
                    ids.add("70");
                }
                String[] idsArr = null;
                String idsString = tf_voteIds.getText();
                if (MailGraber.isStop) {
                    return;
                }
                if (idsString != null) {
                    idsArr = idsString.split(",|，");
                }
                for (String id : idsArr) {
                    ids.add(id);
                }
                if (ids.size() == 0) {
                    return;
                }
                log.error("开始投票");
                new MailGraber().startGrab(ids, Integer.valueOf(fx_maxVoteTime.getText()), instance, Integer.valueOf(tx_minS.getText()), Integer.valueOf(tx_maxS.getText()), tf_voteUrl.getText());
            }
        }).start();
//        fx_startVoteButtom.setText("投票中....");
        fx_startVoteButtom.setDisable(true);

//        
    }

    @FXML
    public void stopVote() {
        MailGraber.isStop = true;
        fx_startVoteButtom.setText("开始投票");
        fxstopVoteButtom.setDisable(true);
        fx_startVoteButtom.setDisable(false);
    }

}
