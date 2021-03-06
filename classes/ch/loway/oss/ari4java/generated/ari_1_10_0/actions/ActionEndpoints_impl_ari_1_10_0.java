package ch.loway.oss.ari4java.generated.ari_1_10_0.actions;

// ----------------------------------------------------
//      THIS CLASS WAS GENERATED AUTOMATICALLY         
//               PLEASE DO NOT EDIT                    
//    Generated on: Sat Feb 04 15:23:09 CET 2017
// ----------------------------------------------------

import ch.loway.oss.ari4java.generated.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import ch.loway.oss.ari4java.tools.BaseAriAction;
import ch.loway.oss.ari4java.tools.RestException;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.HttpParam;
import ch.loway.oss.ari4java.tools.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import ch.loway.oss.ari4java.generated.ari_1_10_0.models.*;

/**********************************************************
 * 
 * Generated by: Apis
 *********************************************************/


public class ActionEndpoints_impl_ari_1_10_0 extends BaseAriAction  implements ActionEndpoints {
/**********************************************************
 * Asterisk endpoints
 * 
 * List all endpoints.
 *********************************************************/
private void buildList() {
reset();
url = "/endpoints";
method = "GET";
}

@Override
public List<Endpoint> list() throws RestException {
buildList();
String json = httpActionSync();
return deserializeJsonAsAbstractList( json,
   new TypeReference<List<Endpoint_impl_ari_1_10_0>>() {} ); 
}

@Override
public void list(AriCallback<List<Endpoint>> callback) {
buildList();
httpActionAsync(callback, new TypeReference<List<Endpoint_impl_ari_1_10_0>>() {});
}

/**********************************************************
 * Send a message to some technology URI or endpoint.
 * 
 * Send a message to some technology URI or endpoint.
 *********************************************************/
private void buildSendMessage(String to, String from, String body, Map<String,String> variables) {
reset();
url = "/endpoints/sendMessage";
method = "PUT";
lParamQuery.add( HttpParam.build( "to", to) );
lParamQuery.add( HttpParam.build( "from", from) );
lParamQuery.add( HttpParam.build( "body", body) );
lParamBody.addAll( HttpParam.build( "variables", variables) );
lE.add( HttpResponse.build( 400, "Invalid parameters for sending a message.") );
lE.add( HttpResponse.build( 404, "Endpoint not found") );
}

@Override
public void sendMessage(String to, String from, String body, Map<String,String> variables) throws RestException {
buildSendMessage(to, from, body, variables);
String json = httpActionSync();
}

@Override
public void sendMessage(String to, String from, String body, Map<String,String> variables, AriCallback<Void> callback) {
buildSendMessage(to, from, body, variables);
httpActionAsync(callback);
}

/**********************************************************
 * Asterisk endpoints
 * 
 * List available endoints for a given endpoint technology.
 *********************************************************/
private void buildListByTech(String tech) {
reset();
url = "/endpoints/" + tech + "";
method = "GET";
lE.add( HttpResponse.build( 404, "Endpoints not found") );
}

@Override
public List<Endpoint> listByTech(String tech) throws RestException {
buildListByTech(tech);
String json = httpActionSync();
return deserializeJsonAsAbstractList( json,
   new TypeReference<List<Endpoint_impl_ari_1_10_0>>() {} ); 
}

@Override
public void listByTech(String tech, AriCallback<List<Endpoint>> callback) {
buildListByTech(tech);
httpActionAsync(callback, new TypeReference<List<Endpoint_impl_ari_1_10_0>>() {});
}

/**********************************************************
 * Single endpoint
 * 
 * Details for an endpoint.
 *********************************************************/
private void buildGet(String tech, String resource) {
reset();
url = "/endpoints/" + tech + "/" + resource + "";
method = "GET";
lE.add( HttpResponse.build( 400, "Invalid parameters for sending a message.") );
lE.add( HttpResponse.build( 404, "Endpoints not found") );
}

@Override
public Endpoint get(String tech, String resource) throws RestException {
buildGet(tech, resource);
String json = httpActionSync();
return deserializeJson( json, Endpoint_impl_ari_1_10_0.class ); 
}

@Override
public void get(String tech, String resource, AriCallback<Endpoint> callback) {
buildGet(tech, resource);
httpActionAsync(callback, Endpoint_impl_ari_1_10_0.class);
}

/**********************************************************
 * Send a message to some endpoint in a technology.
 * 
 * Send a message to some endpoint in a technology.
 *********************************************************/
private void buildSendMessageToEndpoint(String tech, String resource, String from, String body, Map<String,String> variables) {
reset();
url = "/endpoints/" + tech + "/" + resource + "/sendMessage";
method = "PUT";
lParamQuery.add( HttpParam.build( "from", from) );
lParamQuery.add( HttpParam.build( "body", body) );
lParamBody.addAll( HttpParam.build( "variables", variables) );
lE.add( HttpResponse.build( 400, "Invalid parameters for sending a message.") );
lE.add( HttpResponse.build( 404, "Endpoint not found") );
}

@Override
public void sendMessageToEndpoint(String tech, String resource, String from, String body, Map<String,String> variables) throws RestException {
buildSendMessageToEndpoint(tech, resource, from, body, variables);
String json = httpActionSync();
}

@Override
public void sendMessageToEndpoint(String tech, String resource, String from, String body, Map<String,String> variables, AriCallback<Void> callback) {
buildSendMessageToEndpoint(tech, resource, from, body, variables);
httpActionAsync(callback);
}

/** No missing signatures from interface */
};

