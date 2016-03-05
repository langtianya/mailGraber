/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import com.wangzhe.beans.ConfigParam;
import com.wangzhe.service.MailAddrGraber;
import com.wangzhe.service.MailAddrGraberFactory;
import com.wangzhe.util.ConfigManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class HomeController implements Initializable {

    private  static  final Logger log = org.apache.log4j.Logger.getLogger(HomeController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_grabIds.setText(ConfigManager.getProductionFlag());
        tf_grabUrl.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ConfigManager.setGrabUrl(tf_grabUrl.getText());
        });
        tf_grabIds.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            ConfigManager.setProductionFlag(tf_grabIds.getText());
        });
//        tf_grabUrl.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                ConfigManager.setGrabUrl(tf_grabUrl.getText());
//            }
//        });
//        tf_grabIds.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                ConfigManager.setProductionFlag(tf_grabIds.getText());
//            }
//        });
    }

    @FXML
    private CheckBox fx_checkFengzhiyan;

    @FXML
    private CheckBox fx_checkShengFang;

    @FXML
    private TextField fx_maxGrabTime;//最大抓取多少

    @FXML
    public Button fx_startGrabButtom;

    @FXML
    public Button fxstopGrabButtom;

    @FXML
    public TextArea fx_txtareaLog;
    @FXML
    public Label lb_successDis;

    @FXML
    private TextField tx_minS;//间隔最小时间
    @FXML
    private TextField tx_maxS;
    @FXML
    private TextField tf_grabUrl;
    @FXML
    private TextField tf_grabIds;
   public  static  HomeController instance;
   
    @FXML
    public void startGrab() {
        MailAddrGraber.isStop = false;
         instance = this;
        fxstopGrabButtom.setDisable(false);
        new Thread(() -> {
            List<String> ids = new ArrayList<>(5);
            if (fx_checkFengzhiyan.isSelected()) {
                ids.add("71");
            }
            if (fx_checkShengFang.isSelected()) {
                ids.add("70");
            }
            String[] idsArr = null;
            String idsString = tf_grabIds.getText();
            if (MailAddrGraber.isStop) {
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
            log.error("开始抓取");
            ConfigParam cp=new ConfigParam();
            cp.setGrabIds(ids).setGrabUrls(tf_grabUrl.getText().split(",")).setMaxNum(Integer.valueOf(fx_maxGrabTime.getText())).setMaxSpeed(Integer.valueOf(tx_maxS.getText())).setMinSpeed(Integer.valueOf(tx_minS.getText()));
            MailAddrGraberFactory.getInstance("CnledwImpl").startGrab(cp);
                            }).start();
//        fx_startGrabButtom.setText("抓取中....");
        fx_startGrabButtom.setDisable(true);

    }

    @FXML
    public void stopGrab() {
        MailAddrGraber.isStop = true;
        fx_startGrabButtom.setText("开始抓取");
        fxstopGrabButtom.setDisable(true);
        fx_startGrabButtom.setDisable(false);
    }

}
