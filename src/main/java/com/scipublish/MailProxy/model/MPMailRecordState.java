package com.scipublish.MailProxy.model;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-27
 * Time: PM3:25
 * To change this template use File | Settings | File Templates.
 */
public enum MPMailRecordState {
    MAIL_CREATED(0), MAIL_SENT(1), MAIL_DELIVERED(2), MAIL_DROPED(3);

    private int value;

    private MPMailRecordState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MPMailRecordState getState(int state){
        switch (state){
            case 0:
                return MPMailRecordState.MAIL_CREATED;
            case 1:
                return MPMailRecordState.MAIL_SENT;
            case 2:
                return MPMailRecordState.MAIL_DELIVERED;
            case 3:
                return MPMailRecordState.MAIL_DROPED;
            default:
                break;
        }
        return MPMailRecordState.MAIL_DROPED;
    }
}
