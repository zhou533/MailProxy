package com.scipublish.MailProxy.mailgun;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: chouchris
 * Date: 13-8-21
 * Time: PM5:23
 * To change this template use File | Settings | File Templates.
 */
public class MailGunAPI {

    public static String SendMail(String key,
                                      String domain,
                                      MailGunSendBuilder builder){
        Client client =Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", key));
        WebResource webResource = client.resource("https://api.mailgun.net/v2/" + domain + "/messages");
        MultivaluedMapImpl formData = builder.getParams();
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
        return response.getEntity(String.class);
    }
}
