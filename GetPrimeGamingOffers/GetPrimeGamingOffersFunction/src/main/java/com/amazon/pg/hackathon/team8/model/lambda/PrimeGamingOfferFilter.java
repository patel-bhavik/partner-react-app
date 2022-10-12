package com.amazon.pg.hackathon.team8.model.lambda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PrimeGamingOfferFilter {
    private String startDate;
    private String endDate;
    private String gameTitle;
    private String publisher;
}
