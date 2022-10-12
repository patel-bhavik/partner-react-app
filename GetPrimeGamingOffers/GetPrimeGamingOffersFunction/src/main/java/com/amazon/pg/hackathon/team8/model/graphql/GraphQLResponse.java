package com.amazon.pg.hackathon.team8.model.graphql;

import com.amazon.pg.hackathon.team8.model.graphql.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GraphQLResponse {
    private Data data;
}
