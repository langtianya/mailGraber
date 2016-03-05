/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 *
 * @author ocq
 */
public class BasicAuthenticator extends Authenticator {

    String userName;
    String password;

    public BasicAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Called when password authorization is needed. Subclasses should override
     * the default implementation, which returns null.
     *
     * @return The PasswordAuthentication collected from the user, or null if
     * none is provided.
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password.toCharArray());
    }
}
