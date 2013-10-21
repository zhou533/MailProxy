package com.scipublish.MailProxy.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-10-18
 * Time: PM5:44
 * ${PACKAGE_NAME}
 * ${PROJECT_NAME}
 */
public class ESClient {

    private String host = "172.16.3.60";
    private int port = 9300;
    private String clusterName = "elasticsearch";

    private Client client = null;


    public  ESClient(String host, int port, String clusterName){
        this.host = host;
        this.port = port;
        this.clusterName = clusterName;

        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build();
        this.client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(host, port));

    }

    /**
     *
     */
    public Client getClient() {
        return client;
    }
}
