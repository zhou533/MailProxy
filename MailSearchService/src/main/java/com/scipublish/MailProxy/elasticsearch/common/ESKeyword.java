package com.scipublish.MailProxy.elasticsearch.common;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: AM11:22
 * com.scipublish.MailProxy.elasticsearch.common
 * MailProxy
 */
public class ESKeyword {
    private String keyword;
    private ESKeywordType keywordType;
    private ESOperationType operationType;

    public ESKeyword(String keyword, ESKeywordType keywordType, ESOperationType operationType) {
        this.keyword = keyword;
        this.keywordType = keywordType;
        this.operationType = operationType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ESKeywordType getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(ESKeywordType keywordType) {
        this.keywordType = keywordType;
    }

    public ESOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(ESOperationType operationType) {
        this.operationType = operationType;
    }
}
