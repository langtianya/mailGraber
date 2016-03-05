/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class RabbitUtils {

    private static ConnectionFactory factory;
    private static Connection connection;
    private static final Logger log = Logger.getLogger(RabbitUtils.class.getName());

    public static Connection getConnection() {
        try {
            connection = factory.newConnection();
        } catch (IOException ex) {
            log.error(ex);
        }
        return connection;
    }

    public static void Initialized() {
        try {
            factory = new ConnectionFactory();
            factory.setHost("220.231.197.132");
            factory.setUsername("test");
            factory.setPassword("test1");
            factory.setVirtualHost("logs");
            factory.setAutomaticRecoveryEnabled(true);
            log.info("消息中间件启动完成");
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}
