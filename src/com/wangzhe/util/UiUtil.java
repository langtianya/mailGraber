/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import com.wangzhe.test.ui.fxml.FXMLLoginDemoApp;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ocq
 */
public class UiUtil {

    public static Parent loadFxmlToParent(String fxmlName) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        InputStream in = UiUtil.class.getResourceAsStream(fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(FXMLLoginDemoApp.class.getResource(fxmlName));
//        AnchorPane page;
        try {
            return (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

    }
//     public static Parent loadFxmlToParent(String fxmlName) throws Exception {
}
