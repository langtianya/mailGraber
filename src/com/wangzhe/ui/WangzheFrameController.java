/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import com.wangzhe.test.ui.fxml.FXMLLoginDemoApp;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ocq
 */
public class WangzheFrameController extends Application implements Initializable {

    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WangzheFrameController.class.getName());
    //variables for storing initial position of the stage at the beginning of drag
    private double initX;
    private double initY;

    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    private StackPane rootStack = new StackPane();
//    private Group rootGroup = new Group();
    private Parent toolButtom_setting;
    private Parent toolButtom_home;
    public static Stage mainStage;
    @FXML
    private HBox hbox_center;

    public void setCenterPane(Parent parent) {
        if (parent == null) {
            return;
        }
        hbox_center.getChildren().clear();
        VBox.setVgrow(parent, Priority.ALWAYS);
        HBox.setHgrow(parent, Priority.ALWAYS);
        hbox_center.getChildren().add(parent);
    }

    public Parent createContent(Stage primaryStage) {

        //when mouse button is pressed, save the initial position of screen
        rootStack.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                initX = me.getScreenX() - primaryStage.getX();
                initY = me.getScreenY() - primaryStage.getY();
            }
        });

        //when screen is dragged, translate it accordingly
        rootStack.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                primaryStage.setX(me.getScreenX() - initX);
                primaryStage.setY(me.getScreenY() - initY);
            }
        });

        // CREATE MIN AND CLOSE BUTTONS
        //create button for closing application
        Button close = new Button("Close me");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        //create button for minimalising application
        Button min = new Button("Minimize me");
        min.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setIconified(true);
            }
        });

        gotoLogin();

        return rootStack;
    }

    private void gotoLogin() {
        try {
//            WangzheFrameController login = (WangzheFrameController) 
            replaceSceneContent("WangzheFrame.fxml");
//            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(FXMLLoginDemoApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {

//        Parent root1 = FXMLLoader.load(getClass().getResource("Login.fxml"));
        FXMLLoader loader = new FXMLLoader();
        InputStream in = getClass().getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(getClass().getResource(fxml));
        Parent page;
        try {
            page = (Parent) loader.load(in);
        } finally {
            in.close();
        }
        DropShadow ds = new DropShadow();
        ds.setOffsetY(1.0);
        ds.setOffsetX(1.0);
        ds.setColor(Color.GREY);
//        rootGroup.setEffect(ds);
//        page.setEffect(null);
        rootStack.getChildren().removeAll();
        VBox.setVgrow(rootStack, Priority.ALWAYS);
        HBox.setHgrow(rootStack, Priority.ALWAYS);
        rootStack.getChildren().addAll(page);
        return (Initializable) loader.getController();

//          StackPane main = new StackPane();
//            main.setStyle("-fx-background-color: transparent;-fx-background-radius: 0px;");
//            main.setPadding(new Insets(10));
//            main.getChildren().addAll(rootGroup);
    }

    //下面方法不用理会默认即可
    @Override
    public void start(Stage primaryStage) throws Exception {

//        StageStyle.TRANSPARENT
        mainStage = primaryStage;
        createContent(primaryStage);

        //create scene with set width, height and color
        //把组件放到场景
        Scene scene = new Scene(rootStack, 770, 338);

        //set scene to stage
        //primaryStage.setScene(new Scene(createContent()));
        //把场景放到舞台
        primaryStage.setScene(scene);

        //center stage on screen
//        primaryStage.centerOnScreen();
        //设置为透明的场景，意味着只有它的子组件可能可见
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        //把场景显示在最前面
        primaryStage.toFront();

        //设置软件显示在显示器的中间
        Toolkit tk = Toolkit.getDefaultToolkit();
//        f.setLocation((tk.getScreenSize().width - f.getSize().width) / 2, (tk.getScreenSize().height - f.getSize().height) / 2);
//        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        //(tk.getScreenSize().width 与bounds.getWidth()是等价的
        primaryStage.setX((tk.getScreenSize().width - scene.getWidth()) / 2);
        primaryStage.setY((tk.getScreenSize().height - scene.getHeight()) / 2);
//        primaryStage.setFullScreen(true);

        //显示
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goHome();
        return;
    }

    @FXML
    public void toMin() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                mainStage.setIconified(true);
            }
        });
    }
    private boolean isMaximized = false;
    private double x, y;
    private double width, height;
    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    @FXML
    public void toMax() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (isMaximized) {
                    isMaximized = false;
                    StackPane s = (StackPane) mainStage.getScene().getRoot();
                    s.setPadding(new Insets(10));
                    mainStage.setWidth(width);
                    mainStage.setHeight(height);
                    if (x <= 0.00 || y <= 0.00) {
                        mainStage.centerOnScreen();
                    } else {
                        mainStage.setX(x);
                        mainStage.setY(y);
                    }
                } else {
                    isMaximized = true;
                    StackPane rootStackPane = (StackPane) mainStage.getScene().getRoot();
                    rootStackPane.setPadding(Insets.EMPTY);
                    x = mainStage.getX();
                    y = mainStage.getY();
                    width = mainStage.getWidth();
                    height = mainStage.getHeight();

                    mainStage.setX(primaryScreenBounds.getMinX());
                    mainStage.setY(primaryScreenBounds.getMinY());
                    mainStage.setWidth(primaryScreenBounds.getWidth());
                    mainStage.setHeight(primaryScreenBounds.getHeight());
                }
            }
        });
    }

    @FXML
    public void onClose() {
        System.exit(0);
        Platform.exit();
    }

    @FXML
    public void goHome() {
        setCenterPane(toolButtom_home == null ? toolButtom_home = Util.getParent("Home.fxml") : toolButtom_home);
    }

    @FXML
    public void goProxyList() {
//        setCenterPane(toolButtom_setting == null ? toolButtom_setting = Util.getParent("Home.fxml") : toolButtom_setting);
    }

    @FXML
    public void goToSetup() {
        setCenterPane(toolButtom_setting == null ? toolButtom_setting = getParent("/com/wangzhe/ui/SettingTabpane.fxml") : toolButtom_setting);
    }

    @FXML
    public void gotoHelp() {

    }

    public Parent getParent(String fxmlUrl) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(fxmlUrl));
        } catch (IOException ex) {
            log.error(ex);
        }
        return parent;
    }
}
