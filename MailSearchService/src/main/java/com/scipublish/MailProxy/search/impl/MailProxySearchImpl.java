package com.scipublish.MailProxy.search.impl;

import com.scipublish.MailProxy.elasticsearch.ESIndexService;
import com.scipublish.MailProxy.elasticsearch.ESSearchService;
import com.scipublish.MailProxy.elasticsearch.common.ESField;
import com.scipublish.MailProxy.elasticsearch.common.ESKeyword;
import com.scipublish.MailProxy.elasticsearch.common.ESKeywordType;
import com.scipublish.MailProxy.elasticsearch.common.ESOperationType;
import com.scipublish.MailProxy.result.MPSearchResult;
import com.scipublish.MailProxy.result.MPSearchServiceResult;
import com.scipublish.MailProxy.search.MailProxySearch;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-20
 * Time: PM4:51
 * com.scipublish.MailProxy.search.impl
 * MailProxy
 */
public class MailProxySearchImpl implements MailProxySearch{

    private static final Logger logger = LoggerFactory.getLogger(MailProxySearchImpl.class);

    private static final String MP_INDEX = "mailproxy";
    private static final String MAIL_TYPE = "mails";

    private ESIndexService esIndexService;
    private ESSearchService esSearchService;

    public MailProxySearchImpl(ESIndexService esIndexService, ESSearchService esSearchService) {
        this.esIndexService = esIndexService;
        this.esSearchService = esSearchService;
    }

    @Override
    public MPSearchServiceResult initSearchService() {
        if (!esIndexService.indexExists(MP_INDEX)){
            logger.debug("[" + MP_INDEX + "] create index!");
            esIndexService.createIndex(MP_INDEX);
        }else {
            logger.debug("[" + MP_INDEX + "] index already exist!");
        }

        Collection<String> mappingTypes = esIndexService.getAllMappingType(MP_INDEX);
        if (!mappingTypes.contains(MAIL_TYPE)){
            return buildMailsMapping();
        }

        logger.debug("[" + MP_INDEX + "/" + MAIL_TYPE + "] mapping already exist!");
        return MPSearchServiceResult.OK;
    }

    @Override
    public MPSearchServiceResult putMailData(long mid, String name, String email, String institution, String subject, String subject_cn, String keywords, String keywords_cn, String summary, String summary_cn, String doi, String pdf_url, String url, String journal, String isan, String isan_online, String category, String subcategory, String pubdate, String hashId) {
        if (StringUtils.isEmpty(email)){
            return MPSearchServiceResult.ERR_PARAM;
        }

        HashMap<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("mailId", mid);
        fieldMap.put("name", name);
        fieldMap.put("email", email);
        fieldMap.put("institution", institution);
        fieldMap.put("subject", subject);
        fieldMap.put("subject_cn", subject_cn);
        fieldMap.put("keywords", keywords);
        fieldMap.put("keywords_cn", keywords_cn);
        fieldMap.put("abstract", summary);
        fieldMap.put("abstract_cn", summary_cn);
        fieldMap.put("doi", doi);
        fieldMap.put("pdf_url", pdf_url);
        fieldMap.put("url", url);
        fieldMap.put("journal", journal);
        fieldMap.put("isan", isan);
        fieldMap.put("isan_online", isan_online);
        fieldMap.put("category", category);
        fieldMap.put("vol", pubdate);
        fieldMap.put("pub_date", System.currentTimeMillis()/1000);
        fieldMap.put("hashId", hashId);

        if (!esIndexService.putIndex(MP_INDEX, MAIL_TYPE, String.valueOf(mid), fieldMap)){
            logger.debug("[" + MP_INDEX + "/" + MAIL_TYPE + "] put index failed !!!");
            return MPSearchServiceResult.ERR_INDEX_PUT;
        }
        return MPSearchServiceResult.OK;
    }

    @Override
    public MPSearchServiceResult searchMails(String keywords, String isan, String publisher, String category) {
        if (StringUtils.isEmpty(keywords) &&
                StringUtils.isEmpty(isan) &&
                StringUtils.isEmpty(publisher) &&
                StringUtils.isEmpty(category)){
            return MPSearchServiceResult.ERR_PARAM;
        }

        List<ESKeyword> esKeywordList = new ArrayList<ESKeyword>();

        //keywords
        String[] kwList = StringUtils.split(keywords, " ");
        for (int i = 0; (kwList != null && i < kwList.length); i++){
            ESKeyword esKeyword = new ESKeyword(kwList[i], ESKeywordType.MUST, ESOperationType.OR);
            List<ESField> fieldList = new ArrayList<ESField>();
            fieldList.add(new ESField("subject"));
            fieldList.add(new ESField("subject_cn"));
            fieldList.add(new ESField("keywords"));
            fieldList.add(new ESField("keywords_cn"));
            fieldList.add(new ESField("abstract"));
            fieldList.add(new ESField("abstract_cn"));
            esKeyword.setFields(fieldList);
            esKeywordList.add(esKeyword);
        }

        //isan
        if (!StringUtils.isEmpty(isan)){
            ESKeyword esISAN = new ESKeyword(isan, ESKeywordType.SHOULD, ESOperationType.OR);
            List<ESField> isanFieldList = new ArrayList<ESField>();
            isanFieldList.add(new ESField("isan"));
            isanFieldList.add(new ESField("isan_online"));
            esISAN.setFields(isanFieldList);
            esKeywordList.add(esISAN);
        }


        //publisher
        if (!StringUtils.isEmpty(publisher)){
            ESKeyword esPublisher = new ESKeyword(publisher, ESKeywordType.SHOULD, ESOperationType.OR);
            List<ESField> publisherFieldList = new ArrayList<ESField>();
            publisherFieldList.add(new ESField("journal"));
            esPublisher.setFields(publisherFieldList);
            esKeywordList.add(esPublisher);
        }

        MPSearchResult searchResult = esSearchService.search(MP_INDEX, MAIL_TYPE, esKeywordList, null, null, null, null, 0, 20);
        if (searchResult == null){
            return MPSearchServiceResult.ERR_PARAM;
        }

        return MPSearchServiceResult.createOKResult(searchResult);
    }

    /****************
     * private
     ****************/

    public MPSearchServiceResult buildMailsMapping(){
        logger.debug("[" + MP_INDEX + "/" + MAIL_TYPE + "] build mapping");

        //
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                        .startObject(MAIL_TYPE)
                            .startObject("properties")
                                .startObject("mailId")
                                    .field("type", "long")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("name")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("email")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("institution")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("subject")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("subject_cn")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "ik")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("keywords")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("keywords_cn")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "ik")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("abstract")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("abstract_cn")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "ik")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("doi")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("pdf_url")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("url")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("journal")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("isan")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("isan_online")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                                .startObject("category")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("subcategory")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("vol")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("analyzer", "standard")
                                    .field("term_vector", "with_positions_offsets")
                                .endObject()
                                .startObject("pub_date")
                                    .field("type", "date")
                                    .field("store", "yes")
                                    .field("format", "YYYY-MM-dd")
                                .endObject()
                                .startObject("hashId")
                                    .field("type", "string")
                                    .field("store", "yes")
                                    .field("index", "not_analyzed")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
        }catch (IOException e){
            logger.debug(e.getMessage(), e);
            return MPSearchServiceResult.createCodeResult(MPSearchServiceResult.ERR_MAPPING.getCode(),e);
        }

        if (!esIndexService.putMapping(MP_INDEX, MAIL_TYPE, mapping)){
            logger.debug("[" + MP_INDEX + "/" + MAIL_TYPE + "] put mapping failed !!!");
            return MPSearchServiceResult.ERR_MAPPING;
        }

        return MPSearchServiceResult.OK;
    }


}
