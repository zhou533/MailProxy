package com.scipublish.MailProxy.elasticsearch;

import com.scipublish.MailProxy.elasticsearch.common.*;
import com.scipublish.MailProxy.result.MPSearchResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-18
 * Time: PM6:09
 * com.scipublish.MailProxy.elasticsearch
 * MailProxy
 */
public interface ESSearchService {

    /**
     *
     * @param indices
     * @param mappingType
     * @param keywords
     * @param fields
     * @param sort
     * @param highlight
     * @param timeRange
     * @param fixedTerm
     * @param from
     * @param size
     * @return
     */
    public MPSearchResult search(String indices,
                                String mappingType,
                                List<ESKeyword> keywords,
                                List<ESField> fields,
                                ESSort sort,
                                ESHighlight highlight,
                                ESTimeRange timeRange,
                                ESFixedTerm fixedTerm,
                                int from, int size);

}
