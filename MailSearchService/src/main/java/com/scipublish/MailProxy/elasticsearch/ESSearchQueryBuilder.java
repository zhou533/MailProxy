package com.scipublish.MailProxy.elasticsearch;

import com.scipublish.MailProxy.elasticsearch.common.ESField;
import com.scipublish.MailProxy.elasticsearch.common.ESKeyword;
import com.scipublish.MailProxy.elasticsearch.common.ESKeywordType;
import com.scipublish.MailProxy.elasticsearch.common.ESOperationType;

import org.elasticsearch.index.query.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: AM10:46
 * com.scipublish.MailProxy.elasticsearch
 * MailProxy
 */
public class ESSearchQueryBuilder {
    private List<ESKeyword> keywords = new ArrayList<ESKeyword>();
    private List<ESField> fields = new ArrayList<ESField>();

    public void addKeywords(List<ESKeyword> keywords){
        this.keywords.addAll(keywords);
    }

    public void addKeyword(ESKeyword keyword){
        keywords.add(keyword);
    }

    public void addKeyword(String keyword, ESKeywordType keywordType, ESOperationType operationType){
        ESKeyword kw = new ESKeyword(keyword, keywordType, operationType);
        keywords.add(kw);
    }

    public void addKeyword(String keyword){
        addKeyword(keyword, ESKeywordType.SHOULD, ESOperationType.OR);
    }

    public void addFields(List<ESField> fields){
        this.fields.addAll(fields);
    }

    public void addField(ESField field){
        fields.add(field);
    }

    public void addField(String field, float boost){
        ESField f = new ESField(field, boost);
        fields.add(f);
    }

    public void addField(String field){
        addField(field, 1.0f);
    }

    public QueryBuilder genQueryBuilder(){
        if (this.keywords.size() <= 0){
            return null;
        }

        if (this.fields.size() <= 0){

            return null;
        }

        QueryBuilder queryBuilder = null;
        if (this.keywords.size() == 1){
            queryBuilder = createMatchQueryBuilder(this.keywords.get(0), this.fields);
        }else {
            queryBuilder = createBoolQueryBuilder(this.keywords, this.fields);
        }

        return queryBuilder;
    }

    private MatchQueryBuilder createSoloMatchQueryBuilder(ESKeyword keyword, ESField field){
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field.getField(), keyword.getKeyword());
        return matchQueryBuilder;
    }

    private MultiMatchQueryBuilder createMultiMatchQueryBuilder(ESKeyword keyword, List<ESField> fields){
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword.getKeyword());
        for (ESField field :fields){
            if (1.0f == field.getBoost()){
                multiMatchQueryBuilder.field(field.getField());
            }else {
                multiMatchQueryBuilder.field(field.getField(), field.getBoost());
            }
        }
        if (keyword.getOperationType() == ESOperationType.AND){
            multiMatchQueryBuilder.operator(MatchQueryBuilder.Operator.AND);
        }else {
            multiMatchQueryBuilder.operator(MatchQueryBuilder.Operator.OR);
        }

        return multiMatchQueryBuilder;
    }

    private QueryBuilder createMatchQueryBuilder(ESKeyword keyword, List<ESField> fields){
        QueryBuilder queryBuilder = (fields.size() > 1) ?
                createMultiMatchQueryBuilder(keyword, fields) :
                ((fields.size() == 1) ? createSoloMatchQueryBuilder(keyword, fields.get(0)) : null);
        return queryBuilder;
    }

    private BoolQueryBuilder createBoolQueryBuilder(List<ESKeyword> keywords, List<ESField> fields){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (ESKeyword kw : keywords){
            QueryBuilder queryBuilder = (fields.size() > 1) ?
                    createMultiMatchQueryBuilder(kw, fields) :
                    ((fields.size() == 1) ? createSoloMatchQueryBuilder(kw, fields.get(0)) : null);
            if (kw.getKeywordType() == ESKeywordType.MUST){
                boolQueryBuilder.must(queryBuilder);
            }else {
                boolQueryBuilder.should(queryBuilder);
            }
        }
        return boolQueryBuilder;
    }
}
