package com.scipublish.MailProxy.search;

import com.scipublish.MailProxy.result.MPSearchServiceResult;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-20
 * Time: AM9:23
 * com.scipublish.MailProxy.search
 * MailProxy
 */
public interface MailProxySearch {

    public MPSearchServiceResult initSearchService();

    public MPSearchServiceResult putMailData(long mid,
                                             String name,
                                             String email,
                                             String institution,
                                             String subject,
                                             String subject_cn,
                                             String keywords,
                                             String keywords_cn,
                                             String summary,
                                             String summary_cn,
                                             String doi,
                                             String pdf_url,
                                             String url,
                                             String journal,
                                             String isan,
                                             String isan_online,
                                             String category,
                                             String subcategory,
                                             String pubdate,
                                             String hashId);
}
