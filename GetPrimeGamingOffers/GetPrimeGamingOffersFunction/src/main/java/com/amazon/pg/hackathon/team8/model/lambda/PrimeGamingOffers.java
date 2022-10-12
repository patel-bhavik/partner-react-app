package com.amazon.pg.hackathon.team8.model.lambda;

import com.amazon.pg.hackathon.team8.model.graphql.PrimeOffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PrimeGamingOffers {
    private List<PrimeOffer> primeGamingOfferList;
}
