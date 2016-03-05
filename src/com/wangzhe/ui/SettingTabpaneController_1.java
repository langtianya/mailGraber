package com.wangzhe.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class SettingTabpaneController_1 implements Initializable {

    private Logger log = Logger.getLogger(SettingTabpaneController.class.getName());
    @FXML
    public TabPane tp_SettingPane;
    @FXML
    private Tab tab_changeIp;//换ip
    @FXML
    private Tab tab_system;//系统设置
    @FXML
    private Tab tab_other;//其他

    /**
     * Initializes the controller class.
     *
     * @param url 描述
     * @param rb 描述
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tp_SettingPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                switch (newValue.getText()) {

                    case "换IP设置":
//                        tab_changeIp.setContent(Util.getParent("SettingPanel.fxml"));
                        break;
                    case "系统设置":

                        break;
                    case "其他设置":

                        break;
                }
            }
        });
        tp_SettingPane.getSelectionModel().select(0);
    }

}
