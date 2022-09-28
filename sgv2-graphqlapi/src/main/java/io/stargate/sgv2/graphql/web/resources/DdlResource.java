/*
 * Copyright The Stargate Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.stargate.sgv2.graphql.web.resources;

import graphql.GraphQL;
import io.stargate.sgv2.graphql.web.models.GraphqlJsonBody;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 * A GraphQL service that allows users to execute CQL DDL queries directly (e.g. create a keyspace,
 * drop a table, etc).
 */
@Path(ResourcePaths.DDL)
@Singleton
public class DdlResource extends StargateGraphqlResourceBase {

  private final GraphQL graphql;

  @Inject
  public DdlResource(GraphqlCache graphqlCache) {
    this.graphql = graphqlCache.getDdl();
  }

  @GET
  public void get(
      @QueryParam("query") String query,
      @QueryParam("operationName") String operationName,
      @QueryParam("variables") String variables,
      @Suspended AsyncResponse asyncResponse) {

    get(query, operationName, variables, graphql, newContext(), asyncResponse);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void postJson(
      GraphqlJsonBody jsonBody,
      @QueryParam("query") String queryFromUrl,
      @Suspended AsyncResponse asyncResponse) {

    postJson(jsonBody, queryFromUrl, graphql, newContext(), asyncResponse);
  }

  @POST
  @Consumes(APPLICATION_GRAPHQL)
  public void postGraphql(String query, @Suspended AsyncResponse asyncResponse) {

    postGraphql(query, graphql, newContext(), asyncResponse);
  }
}
