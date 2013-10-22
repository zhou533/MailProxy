package com.scipublish.MailProxy.model;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-9-22
 * Time: PM5:55
 * To change this template use File | Settings | File Templates.
 */
public enum MPMailState {
    STATE_OK(0),
    STATE_DROPED_HARD(1),
    STATE_DROPED_MAX(2),
    STATE_DROPED_OLD(3),
    STATE_COMPLAINT(4),
    STATE_BOUNCE(5),
    STATE_UNKNOWN(6);

    private int value;

    private MPMailState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MPMailState getState(int state){
        switch (state){
            case 0:
                return MPMailState.STATE_OK;
            case 1:
                return MPMailState.STATE_DROPED_HARD;
            case 2:
                return MPMailState.STATE_DROPED_MAX;
            case 3:
                return MPMailState.STATE_DROPED_OLD;
            case 4:
                return MPMailState.STATE_COMPLAINT;
            case 5:
                return MPMailState.STATE_BOUNCE;
            default:
                break;
        }
        return MPMailState.STATE_UNKNOWN;
    }
}
