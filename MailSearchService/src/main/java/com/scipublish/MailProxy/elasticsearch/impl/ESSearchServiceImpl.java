package com.scipublish.MailProxy.elasticsearch.impl;

import com.scipublish.MailProxy.elasticsearch.ESClient;
import com.scipublish.MailProxy.elasticsearch.ESSearchQueryBuilder;
import com.scipublish.MailProxy.elasticsearch.ESSearchService;
import com.scipublish.MailProxy.elasticsearch.common.*;
import com.scipublish.MailProxy.result.MPSearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.search.SearchOperationThreading;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-19
 * Time: AM7:43
 * com.scipublish.MailProxy.elasticsearch.impl
 * MailProxy
 */
public class ESSearchServiceImpl implements ESSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ESSearchServiceImpl.class);

    private Client client;

    public ESSearchServiceImpl(ESClient esClient) {
        this.client = esClient.getClient();
    }

    @Override
    public MPSearchResult search(String indices,
                         String mappingType,
                         List<ESKeyword> keywords,
                         ESSort sort,
                         ESHighlight highlight,
                         ESTimeRange timeRange,
                         ESFixedTerm fixedTerm,
                         int from,
                         int size) {
        logger.debug("Search:" + indices + "/" + mappingType + ":keywords:" + StringUtils.join(keywords, '-'));

        if (StringUtils.isEmpty(indices) ||
                StringUtils.isEmpty(mappingType) ||
                keywords == null || keywords.size() == 0){
            return null;
        }

        ESSearchQueryBuilder searchQueryBuilder = new ESSearchQueryBuilder();
        searchQueryBuilder.addKeywords(keywords);
        QueryBuilder queryBuilder = searchQueryBuilder.genQueryBuilder();
        if (queryBuilder == null){
            return null;
        }

        logger.info("Query String:");
        logger.info(queryBuilder.toString());

        int start = (from < 0) ? 0 : from;
        int length = (size <= 0) ? 25 : size;

        MPSearchResult result = new MPSearchResult();
        result.setFrom(start);

        long startTime = System.currentTimeMillis();
        try {
            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indices).setTypes(mappingType)
                    .setQuery(queryBuilder)
                    .setFrom(start).setSize(length)
                    .setOperationThreading(SearchOperationThreading.THREAD_PER_SHARD);
            if (null != sort){
                searchRequestBuilder.addSort(sort.getField(), ((sort.getType() == ESSortType.ASC) ? SortOrder.ASC : SortOrder.DESC));
            }
            if (null != highlight){
                for (String f : highlight.getFields()){
                    searchRequestBuilder.addHighlightedField(f,30,0);
                }
                searchRequestBuilder.setHighlighterPreTags(highlight.getPreTag());
                searchRequestBuilder.setHighlighterPostTags(highlight.getPostTag());
            }

            FilterBuilder rangeBuilder = (null != timeRange) ?
                    FilterBuilders.rangeFilter(timeRange.getDateField()).from(timeRange.getStartTime()).to(timeRange.getEndTime()) :
                    null;
            FilterBuilder termBuilder = (null != fixedTerm) ?
                    FilterBuilders.termFilter(fixedTerm.getField(), fixedTerm.getValue()) :
                    null;
            if (null != rangeBuilder && null != termBuilder){
                searchRequestBuilder.setFilter(FilterBuilders.andFilter(rangeBuilder, termBuilder));
            }else if (null != rangeBuilder){
                searchRequestBuilder.setFilter(rangeBuilder);
            }else if (null != termBuilder){
                searchRequestBuilder.setFilter(termBuilder);
            }

            logger.info("Search Request String:");
            logger.info(searchRequestBuilder.toString());
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            if (response.getHits().totalHits() <= 0){
                logger.debug("Response: no result");
                return result;
            }

            //
            SearchHits hits = response.getHits();
            long totalCount = hits.totalHits();
            result.setTotal(totalCount);
            int count = hits.getHits().length;
            result.setPage(count);

            // no search result, return
            if (count <= 0) {
                return result;
            }

            //
            List<Map> resultList = new ArrayList<Map>();
            for (int i = 0; i < count; i ++){
                SearchHit hit = hits.getAt(i);
                // add record source
                Map<String, Object> sourceMap = hit.sourceAsMap();

                //
                Map<String, HighlightField> highlightMap = hit.highlightFields();
                if (null != highlightMap){
                    for (String k : highlightMap.keySet()){
                        HighlightField hlField = highlightMap.get(k);
                        if (null != hlField){
                            org.elasticsearch.common.text.Text[] fragments = hlField.fragments();
                            if (fragments != null && fragments.length > 0)
                                sourceMap.put(k, fragments[0].toString());
                        }
                    }
                }

                // append to result list
                resultList.add(sourceMap);
            }
            // set record data
            result.setResultList(resultList);
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
        }

        long endTime = System.currentTimeMillis();
        result.setElapse(endTime - startTime);
        return result;
    }
}
