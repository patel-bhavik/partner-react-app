package com.amazon.pg.hackathon.team8.model.graphql;

import com.amazon.pg.hackathon.team8.model.graphql.Content;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PrimeOffer {
    private String title;
    private String relatedGameTitle;
    private Content content;
    private String deliveryMethod;
    private String description;
    private String startTime;
    private String endTime;
}
