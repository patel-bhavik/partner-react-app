package com.amazon.pg.hackathon.team8.model.graphql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Data {
    private List<PrimeOffer> primeOffers;
}
