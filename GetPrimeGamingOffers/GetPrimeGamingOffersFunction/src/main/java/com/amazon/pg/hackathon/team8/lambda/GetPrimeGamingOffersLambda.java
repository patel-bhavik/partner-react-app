package com.amazon.pg.hackathon.team8.lambda;

import com.amazon.pg.hackathon.team8.graphql.GraphQLClient;
import com.amazon.pg.hackathon.team8.model.graphql.PrimeOffer;
import com.amazon.pg.hackathon.team8.model.lambda.PrimeGamingOfferFilter;
import com.amazon.pg.hackathon.team8.model.lambda.PrimeGamingOffers;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Map;
import java.util.AbstractMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetPrimeGamingOffersLambda {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final static Map<String, String> CORS = Stream
    .of(new AbstractMap.SimpleEntry<String, String>("Access-Control-Allow-Origin", "*"))
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public APIGatewayProxyResponseEvent getPrimeGamingOffers(APIGatewayProxyRequestEvent request) {
        final PrimeGamingOfferFilter filter;
        try {
            filter = objectMapper.readValue(request.getBody(), PrimeGamingOfferFilter.class);
        } catch (JsonProcessingException e) {
            String error = "JSON parsing failed. Invalid JSON request.";
            System.out.println(error);
            return new APIGatewayProxyResponseEvent().withHeaders(CORS).withStatusCode(400).withBody(error);
        }

        if (validateRequest(filter)) {
            List<PrimeOffer> primeOffers = GraphQLClient.getPrimeOffers();
            List<PrimeOffer> eligiblePrimeOffers = primeOffers.stream()
                    .filter(primeOffer -> applyFilter(primeOffer, filter))
                    .collect(Collectors.toList());
            try {
                String output = objectMapper.writeValueAsString(new PrimeGamingOffers(eligiblePrimeOffers));
                return new APIGatewayProxyResponseEvent().withHeaders(CORS).withStatusCode(200).withBody(output);
            } catch (JsonProcessingException e) {
                String error = "JSON parsing failed. JSON output creation failed.";
                System.out.println(error);
                return new APIGatewayProxyResponseEvent().withHeaders(CORS).withStatusCode(500).withBody(error);
            }

        } else {
            String error = "Invalid request. If you specify both start and end date then start date has to be earlier than end date.";
            return new APIGatewayProxyResponseEvent().withHeaders(CORS).withStatusCode(400).withBody(error);
        }
    }

    private boolean validateRequest(PrimeGamingOfferFilter filter) {
        String startDate = filter.getStartDate();
        String endDate = filter.getEndDate();
        if (Objects.nonNull(startDate)) {
            if (Objects.nonNull(endDate)) {
                return Instant.parse(startDate).isBefore(Instant.parse(endDate));
            }
        }
        return true;
    }

    private boolean applyFilter(PrimeOffer offer, PrimeGamingOfferFilter filter) {
        String filterGameTitle = filter.getGameTitle();
        String filterPublisher = filter.getPublisher();
        String filterStartDate = filter.getStartDate();
        String filterEndDate = filter.getEndDate();

        if (Objects.nonNull(filterGameTitle) && !filterGameTitle.equals(offer.getRelatedGameTitle())) {
            return false;
        }

        if (Objects.nonNull(filterPublisher) && !filterPublisher.equals(offer.getContent().getPublisher())) {
            return false;
        }

        if (Objects.nonNull(filterStartDate) && Instant.parse(offer.getStartTime()).isBefore(Instant.parse(filterStartDate))) {
            return false;
        }

        if (Objects.nonNull(filterEndDate) && Instant.parse(offer.getStartTime()).isAfter(Instant.parse(filterEndDate))) {
            return false;
        }

       return true;
    }
}
