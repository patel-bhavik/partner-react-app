package com.amazon.pg.hackathon.team8.model.graphql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Content {
    private String[] categories;
    private String publisher;
    private String externalURL;
}
