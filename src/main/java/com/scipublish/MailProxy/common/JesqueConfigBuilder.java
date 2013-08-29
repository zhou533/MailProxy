package com.scipublish.MailProxy.common;

import net.greghaines.jesque.Config;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-29
 * Time: PM5:52
 * To change this template use File | Settings | File Templates.
 */
public class JesqueConfigBuilder {

    private String host;
    private int port;
    private String namespace;
    private int timeout;
    private String password;

    public Config build(){
        return new Config(host, port, timeout, password, namespace, 0);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
