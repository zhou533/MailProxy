package com.scipublish.MailProxy.result;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-20
 * Time: AM9:24
 * com.scipublish.MailProxy.result
 * MailProxy
 */
public class MPSearchServiceResult implements Serializable {

    private int code;
    private String description;
    private Object object = null;

    public static final MPSearchServiceResult OK = new MPSearchServiceResult(0, "OK");
    public static final MPSearchServiceResult ERR_PARAM = new MPSearchServiceResult(1, "input error param");
    public static final MPSearchServiceResult ERR_MAPPING = new MPSearchServiceResult(100, "put mapping exception");
    public static final MPSearchServiceResult ERR_INDEX_REBUILD = new MPSearchServiceResult(200, "rebuild index exception");
    public static final MPSearchServiceResult ERR_INDEX_PUT = new MPSearchServiceResult(201, "put one index exception");
    public static final MPSearchServiceResult ERR_INDEX_DEL = new MPSearchServiceResult(202, "delete one index exception");
    public static final MPSearchServiceResult ERR_INDEX_UPDATE = new MPSearchServiceResult(203, "update one index exception");
    public static final MPSearchServiceResult UNKNOWN = new MPSearchServiceResult(999, "unknown");

    public MPSearchServiceResult(int code) {
        this.code = code;
    }

    public MPSearchServiceResult(int code, String description) {

        this.code = code;
        this.description = description;
    }

    public MPSearchServiceResult(int code, String description, Object object) {

        this.code = code;
        this.description = description;
        this.object = object;
    }

    /**
     *
     */
    public static MPSearchServiceResult createOKResult(Object object){
        return new MPSearchServiceResult(0, "OK", object);
    }

    public static MPSearchServiceResult createCodeResult(int code, String description){
        return new MPSearchServiceResult(code, description);
    }

    public static MPSearchServiceResult createCodeResult(int code, Exception e){
        if (null == e.getCause()){
            return createCodeResult(code, e.toString());
        }
        return createCodeResult(code, e.getCause().toString());
    }

    /**
     * getter
     */
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Object getObject() {
        return object;
    }
}
