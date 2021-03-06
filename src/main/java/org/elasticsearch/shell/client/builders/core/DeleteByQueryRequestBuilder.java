/*
 * Licensed to Luca Cavanna (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.shell.client.builders.core;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequest;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.deletebyquery.IndexDeleteByQueryResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.shell.client.builders.AbstractRequestBuilderJsonOutput;
import org.elasticsearch.shell.json.JsonToString;
import org.elasticsearch.shell.json.StringToJson;

import java.io.IOException;

/**
 * @author Luca Cavanna
 *
 * Request builder for delete by query API
 */
@SuppressWarnings("unused")
public class DeleteByQueryRequestBuilder<JsonInput, JsonOutput>  extends AbstractRequestBuilderJsonOutput<DeleteByQueryRequest, DeleteByQueryResponse, JsonInput, JsonOutput> {

    public DeleteByQueryRequestBuilder(Client client, JsonToString<JsonInput> jsonToString, StringToJson<JsonOutput> stringToJson) {
        super(client, new DeleteByQueryRequest(), jsonToString, stringToJson);
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> types(String... types) {
        request.types(types);
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> routing(String routing) {
        request.routing(routing);
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> routing(String... routing) {
        request.routing(routing);
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> queryBuilder(QueryBuilder queryBuilder) {
        request.query(queryBuilder);
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> query(JsonInput query) {
        request.query(jsonToString(query));
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> replicationType(String replicationType) {
        request.replicationType(replicationType);
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> consistencyLevel(String consistencyLevel) {
        request.consistencyLevel(WriteConsistencyLevel.fromString(consistencyLevel));
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> timeout(String timeout) {
        request.timeout(timeout);
        return this;
    }

    public DeleteByQueryRequestBuilder<JsonInput, JsonOutput> indices(String... indices) {
        request.indices(indices);
        return this;
    }

    @Override
    protected ActionFuture<DeleteByQueryResponse> doExecute(DeleteByQueryRequest request) {
        return client.deleteByQuery(request);
    }

    @Override
    protected XContentBuilder toXContent(DeleteByQueryRequest request, DeleteByQueryResponse response, XContentBuilder builder) throws IOException {
        builder.startObject().field(Fields.OK, true);
        builder.startObject("_indices");
        for (IndexDeleteByQueryResponse indexDeleteByQueryResponse : response.getIndices().values()) {
            builder.startObject(indexDeleteByQueryResponse.getIndex(), XContentBuilder.FieldCaseConversion.NONE);
            builder.startObject("_shards");
            builder.field("total", indexDeleteByQueryResponse.getTotalShards());
            builder.field("successful", indexDeleteByQueryResponse.getSuccessfulShards());
            builder.field("failed", indexDeleteByQueryResponse.getFailedShards());
            builder.endObject();
            builder.endObject();
        }
        builder.endObject();
        builder.endObject();
        return builder;
    }
}