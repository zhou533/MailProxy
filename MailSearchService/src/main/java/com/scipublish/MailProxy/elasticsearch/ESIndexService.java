package com.scipublish.MailProxy.elasticsearch;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-18
 * Time: PM5:48
 * com.scipublish.MailProxy.elasticsearch
 * MailProxy
 */
public interface ESIndexService {
    public boolean createIndex(String indices);
    public boolean deleteIndex(String indices);
    public boolean indexExists(String indices);

    public Collection<String> getAllMappingType(String indices);
    public boolean putMapping(String indices, String mappingType, XContentBuilder mapping);
    public boolean deleteMapping(String indices, String mappingType);

    public boolean updateIndex(String indices, String mappingType, String id, String field, Object value);
    public boolean putIndex(String indices, String mappingType, String id, HashMap<String, Object> hashMap);
    public boolean putIndexes(String indices, String mappingType, List<HashMap<String, Object>> hashMaps);
    public Map<String, Object> getIndex(String indices, String mappingType, String id);
    public boolean deleteIndexByID(String indices, String mappingType, String id);
}
