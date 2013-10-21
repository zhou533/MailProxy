package com.scipublish.MailProxy.elasticsearch.impl;

import com.scipublish.MailProxy.elasticsearch.ESClient;
import com.scipublish.MailProxy.elasticsearch.ESIndexService;
import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-18
 * Time: PM5:49
 * com.scipublish.MailProxy.elasticsearch.impl
 * MailProxy
 */
public class ESIndexServiceImpl implements ESIndexService{

    private static final Logger logger = LoggerFactory.getLogger(ESIndexServiceImpl.class);

    private Client client;

    public ESIndexServiceImpl(ESClient esClient) {
        this.client = esClient.getClient();
    }

    /**
     *
     */
    @Override
    public boolean createIndex(String indices) {

        CreateIndexResponse response = null;
        try {
            response = client.admin().indices().prepareCreate(indices).execute().actionGet();
            logger.debug("I: create indices: " + indices);
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }

        return response.isAcknowledged();
    }

    /**
     *
     */
    @Override
    public boolean deleteIndex(String indices) {

        DeleteIndexResponse response = null;
        try {
            response = client.admin().indices().prepareDelete(indices).execute().actionGet();
            logger.debug("I: delete indices: " + indices);
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return response.isAcknowledged();
    }

    @Override
    public boolean indexExists(String indices) {
        try {
            IndicesExistsResponse response = client.admin().indices().prepareExists(indices).execute().actionGet();
            logger.debug("IsExist indices: " + indices);
            return response.isExists();
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public Collection<String> getAllMappingType(String indices) {
        ClusterStateResponse clusterResponse = client.admin().cluster().prepareState().execute().actionGet();
        ImmutableMap<String, MappingMetaData> mappings =  clusterResponse.getState().getMetaData().index(indices).getMappings();
        return mappings.keySet();
    }

    @Override
    public boolean putMapping(String indices, String mappingType, XContentBuilder mapping) {
        PutMappingResponse response = null;
        try {
            PutMappingRequest request = Requests.putMappingRequest(indices).type(mappingType).source(mapping);
            response = client.admin().indices().putMapping(request).actionGet();
            logger.debug("I: put mapping type: " + indices + "/" + mappingType);
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return response.isAcknowledged();
    }

    @Override
    public boolean deleteMapping(String indices, String mappingType) {

        try {
            DeleteMappingRequest request = Requests.deleteMappingRequest(indices).type(mappingType);
            client.admin().indices().deleteMapping(request).actionGet();
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateIndex(String indices, String mappingType, String id, String field, Object value) {

        try {
            String script = "ctx._source." + field + "=\"" + value.toString() + "\"";
            client.prepareUpdate(indices, mappingType, id).setScript(script).execute().actionGet();
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean putIndex(String indices, String mappingType, String id, HashMap<String, Object> hashMap) {
        try {
            XContentBuilder index = jsonBuilder().startObject();
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                String field = entry.getKey().toString();
                Object value = entry.getValue();
                index = index.field(field, value);
            }
            index = index.endObject();

            IndexResponse response = null;
            if (id != null){
                response = client.prepareIndex(indices, mappingType, id).setSource(index).execute().actionGet();
            }else {
                response = client.prepareIndex(indices, mappingType).setSource(index).execute().actionGet();
                id = response.getId();
            }
            logger.debug("I: put index " + response.getIndex() + "/" + response.getType() + ": id=" + response.getId() + ", version=" + response.getVersion());

        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }catch (IOException e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean putIndexes(String indices, String mappingType, List<HashMap<String, Object>> hashMaps) {
        try {
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
            for (HashMap<String, Object> hashMap : hashMaps){

                Object id = hashMap.get("id");
                if (null == id || id.getClass() != String.class){
                    continue;
                }
                hashMap.remove("id");
                XContentBuilder index = jsonBuilder().startObject();
                Iterator it = hashMap.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry entry = (Map.Entry)it.next();
                    String field = entry.getKey().toString();
                    Object value = entry.getValue();
                    index = index.field(field, value);
                }
                index = index.endObject();


                IndexRequestBuilder indexRequestBuilder = client.prepareIndex(indices, mappingType, (String)id).setSource(index);
                bulkRequestBuilder.add(indexRequestBuilder);
            }

            if (bulkRequestBuilder.numberOfActions() > 0){
                BulkResponse responses = bulkRequestBuilder.execute().actionGet();
                if (responses.hasFailures()){
                    logger.error(responses.buildFailureMessage());
                    return false;
                }
            }else {
                return false;
            }

        }catch (IOException e){
            logger.error(e.getMessage(), e);
            return false;
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> getIndex(String indices, String mappingType, String id) {
        try {
            GetResponse response = client.prepareGet(indices, mappingType, id).execute().actionGet();
            if (response != null && response.isExists()){
                return response.getSource();
            }
            logger.debug("I: get index not exist: " + indices + "/" + mappingType + "/" + id);
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public boolean deleteIndexByID(String indices, String mappingType, String id) {
        DeleteResponse response = null;
        try {
            response = client.prepareDelete(indices, mappingType, id).execute().actionGet();
        }catch (ElasticSearchException e){
            logger.error(e.getMessage(), e);
            return false;
        }

        if (response != null) {
            if (! response.isNotFound())
                logger.debug("I: delete index : " + indices + "/" + mappingType + "/" + id);
            else
                logger.debug("I: Not exist for index : " + indices + "/" + mappingType + "/" + id);
            return true;
        }

        return false;
    }
}
