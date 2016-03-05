/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class Util {

    private static Logger log = Logger.getLogger(Util.class.getName());

    public static Parent getParent(String fxmlUrl) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Util.class.getResource(fxmlUrl));
        } catch (IOException ex) {
            log.error(ex);
        }
        return parent;
    }
}
