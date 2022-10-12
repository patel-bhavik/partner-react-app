package com.amazon.pg.hackathon.team8.graphql;

import com.amazon.pg.hackathon.team8.model.graphql.GraphQLResponse;
import com.amazon.pg.hackathon.team8.model.graphql.PrimeOffer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class GraphQLClient {
    private static final String ENDPOINT = "https://gaming.amazon.com/graphql";
    private static final HttpClient CLIENT = HttpClientBuilder.create().build();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String GET_PRIME_OFFERS_QUERY =
            "{ \n" +
            "   \"query\": \"query primeOffers { \\n primeOffers { \\n title \\n relatedGameTitle \\n content { \\n categories \\n publisher \\n externalURL \\n } \\n deliveryMethod \\n description \\n startTime \\n endTime \\n } \\n }\" \n" +
            "}";

    public static List<PrimeOffer> getPrimeOffers() {
        HttpResponse response = executeGraphQLQuery();
        String actualResponse = null;
        try {
            actualResponse = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            System.out.println("Error occurred while converting the response to string.");
            return Collections.emptyList();
        }
        GraphQLResponse graphQLResponse;
        try {
            graphQLResponse = objectMapper.readValue(actualResponse, GraphQLResponse.class);
        } catch (JsonProcessingException e) {
            System.out.println("Error occurred while parsing JSON string.");
            System.out.println(actualResponse);
            return Collections.emptyList();
        }
        return graphQLResponse.getData().getPrimeOffers();
    }

    private static HttpResponse executeGraphQLQuery() {
        StringEntity requestEntity = new StringEntity(GET_PRIME_OFFERS_QUERY, ContentType.APPLICATION_JSON);
        HttpPost request = new HttpPost(ENDPOINT);
        request.setEntity(requestEntity);
        try {
            return CLIENT.execute(request);
        } catch (IOException e) {
            System.out.println("Error occurred while executing HTTP request.");
            return new BasicHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "");
        }
    }
}
