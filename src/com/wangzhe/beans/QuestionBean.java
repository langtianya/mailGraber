/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.beans;

/**
 * 验证问题bean
 *
 * @author ocq
 */
public class QuestionBean {
    
    //验证码识别状态
    private int validateStatus;
    //验证码识别结果
    private String recognitionResult;//识别结果
    //是否是手工输入的验证问题
     private boolean isManual;
     
    public boolean isIsManual() {
        return isManual;
    }

    public void setIsManual(boolean isManual) {
        this.isManual = isManual;
    }
    
    public String getRecognitionResult() {
        return recognitionResult;
    }

    public void setRecognitionResult(String recognitionResult) {
        this.recognitionResult = recognitionResult;
    }

    public int getValidateStatus() {
        return validateStatus;
    }

    public void setValidateStatus(int validateStatus) {
        this.validateStatus = validateStatus;
    }

}
