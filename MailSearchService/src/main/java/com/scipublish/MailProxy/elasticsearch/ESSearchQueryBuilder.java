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
    //private List<ESField> fields = new ArrayList<ESField>();

    public void addKeywords(List<ESKeyword> keywords){
        this.keywords.addAll(keywords);
    }

    /*public void addKeyword(ESKeyword keyword){
        keywords.add(keyword);
    }

    public void addKeyword(String keyword, ESKeywordType keywordType, ESOperationType operationType){
        ESKeyword kw = new ESKeyword(keyword, keywordType, operationType);
        keywords.add(kw);
    }

    public void addKeyword(String keyword){
        addKeyword(keyword, ESKeywordType.SHOULD, ESOperationType.OR);
    }*/

    /*public void addFields(List<ESField> fields){
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
    }*/

    public QueryBuilder genQueryBuilder(){
        if (this.keywords.size() <= 0){
            return null;
        }

        QueryBuilder queryBuilder = null;
        if (this.keywords.size() == 1){
            queryBuilder = createMatchQueryBuilder(this.keywords.get(0));
        }else {
            queryBuilder = createBoolQueryBuilder(this.keywords);
        }

        return queryBuilder;
    }

    private MatchQueryBuilder createAllMatchQueryBuilder(String keyword){
        ESField allField = new ESField("_all");
        return createSoloMatchQueryBuilder(keyword, allField);
    }

    private MatchQueryBuilder createSoloMatchQueryBuilder(String keyword, ESField field){
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(field.getField(), keyword);
        return matchQueryBuilder;
    }

    private MultiMatchQueryBuilder createMultiMatchQueryBuilder(String keyword, ESOperationType operationType, List<ESField> fields){
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword);
        for (ESField field :fields){
            if (1.0f == field.getBoost()){
                multiMatchQueryBuilder.field(field.getField());
            }else {
                multiMatchQueryBuilder.field(field.getField(), field.getBoost());
            }
        }
        if (operationType == ESOperationType.AND){
            multiMatchQueryBuilder.operator(MatchQueryBuilder.Operator.AND);
        }else {
            multiMatchQueryBuilder.operator(MatchQueryBuilder.Operator.OR);
        }

        return multiMatchQueryBuilder;
    }

    private QueryBuilder createMatchQueryBuilder(ESKeyword keyword){
        List<ESField> fieldList = keyword.getFields();
        if (fieldList == null || fieldList.size() == 0){
            return createAllMatchQueryBuilder(keyword.getKeyword());
        }
        if (fieldList.size() == 1){
            return createSoloMatchQueryBuilder(keyword.getKeyword(), fieldList.get(0));
        }

        return createMultiMatchQueryBuilder(keyword.getKeyword(), keyword.getOperationType(), fieldList);
    }

    private BoolQueryBuilder createBoolQueryBuilder(List<ESKeyword> keywords){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (ESKeyword kw : keywords){
            List<ESField> fieldList = kw.getFields();
            QueryBuilder queryBuilder = null;
            if (fieldList == null || fieldList.size() == 0){
                queryBuilder = createAllMatchQueryBuilder(kw.getKeyword());
            }else if(fieldList.size() == 1){
                queryBuilder = createSoloMatchQueryBuilder(kw.getKeyword(), fieldList.get(0));
            }else {
                queryBuilder = createMultiMatchQueryBuilder(kw.getKeyword(), kw.getOperationType(), fieldList);
            }

            if (queryBuilder != null){
                if (kw.getKeywordType() == ESKeywordType.MUST){
                    boolQueryBuilder.must(queryBuilder);
                }else {
                    boolQueryBuilder.should(queryBuilder);
                }
            }
        }
        return boolQueryBuilder;
    }
}
