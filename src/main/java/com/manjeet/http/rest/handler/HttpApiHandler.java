package com.manjeet.http.rest.handler;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class HttpApiHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(HttpApiHandler.class);
	
	@Path("/myget")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getMsisdn(@Context UriInfo info) {
		//curl -vv localhost:8888/myget?msisdn=9899716596
        MultivaluedMap<String, String> params = info.getQueryParameters();
        String msisdn = params.getFirst("msisdn");
        JSONObject response = new JSONObject();
        response.put("msisdn", "9899716596");
        System.out.println("Response for getMsisdn: "+  response);
        return response;
    }

    @Path("/myset")
	@POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
    public JSONObject setMsisdn(MultivaluedMap<String, String> params) {
    	//curl -vv --data "msisdn=9899716596" localhost:8888/myset
    	JSONObject response = new JSONObject();
    	String msisdn = params.getFirst("msisdn");
        response.put("msisdn", msisdn);
        System.out.println("Response for setMsisdn: "+  response);
        response.put("msisdn", msisdn);
        return response;
    }

}
