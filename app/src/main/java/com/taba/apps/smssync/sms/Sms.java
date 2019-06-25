package com.taba.apps.smssync.sms;

public class Sms {

    public static final int STATUS_SYNCHRONIZED = 1;
    public static final int STATUS_UNSYNCHRONIZED = 0;

    private int id;
    private String senderPhone;
    private String message;
    private String receivedTime;
    private int status;

    public Sms(){

    }

    public Sms(String senderPhone, String message, String receivedTime){
        this();
        this.senderPhone = senderPhone;
        this.message = message;
        this.receivedTime = receivedTime;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public int getStatus() {
        return status;
    }

    public String getReceivedTime() {
        return receivedTime;
    }

    public void setId(int id) {
        this.id = id;
    }
}
