package com.perks.emilena.resource;

import com.perks.emilena.config.ApplicationConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

/**
 * Created by Geoff Perks
 * Date: 14/07/2016.
 */
@Path("/external")
@Produces(MediaType.APPLICATION_JSON)
public class ExternalServiceResource {

    private final Client client;
    private final ApplicationConfiguration configuration;

    public ExternalServiceResource(Client client, ApplicationConfiguration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    @GET
    @Path("address/{postCode}")
    public Response address(@PathParam("postCode") String postCode) {
        WebTarget webTarget = client.target(configuration.getPostCodeServiceUrl())
                .path("v2")
                .path("uk")
                .path(deleteWhitespace(postCode))
                .queryParam("api-key", configuration.getApiKey());
        return webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .get();
    }

    @GET
    @Path("address/{startPoint}/{endPoint}")
    public Response distance(@PathParam("startPoint") String startPoint, @PathParam("endPoint") String endPoint) {
        WebTarget webTarget = client.target(configuration.getGoogleMapsDistanceMatrixApiUrl())
                .path("maps")
                .path("api")
                .path("distancematrix")
                .path("json")
                .queryParam("units", "imperial")
                .queryParam("origins", startPoint)
                .queryParam("destinations", endPoint)
                .queryParam("key", configuration.getGoogleMapsDistanceMatrixApiKey());
        return webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .get();
    }
}
