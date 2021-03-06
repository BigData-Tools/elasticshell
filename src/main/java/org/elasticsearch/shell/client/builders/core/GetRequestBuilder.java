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
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.shell.client.builders.AbstractRequestBuilderToXContent;
import org.elasticsearch.shell.json.JsonToString;
import org.elasticsearch.shell.json.StringToJson;

/**
 * @author Luca Cavanna
 *
 * Request builder for get API
 */
@SuppressWarnings("unused")
public class GetRequestBuilder<JsonInput, JsonOutput> extends AbstractRequestBuilderToXContent<GetRequest, GetResponse, JsonInput, JsonOutput> {

    public GetRequestBuilder(Client client, JsonToString<JsonInput> jsonToString, StringToJson<JsonOutput> stringToJson) {
        super(client, new GetRequest(null), jsonToString, stringToJson);
    }

    public GetRequestBuilder<JsonInput, JsonOutput> index(String index) {
        request.index(index);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> type(String type) {
        request.type(type);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> id(String id) {
        request.id(id);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> parent(String parent) {
        request.parent(parent);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> routing(String routing) {
        request.routing(routing);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> preference(String preference) {
        request.preference(preference);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> fields(String... fields) {
        request.fields(fields);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> refresh(boolean refresh) {
        request.refresh(refresh);
        return this;
    }

    public GetRequestBuilder<JsonInput, JsonOutput> realtime(boolean realtime) {
        request.realtime(realtime);
        return this;
    }

    @Override
    protected ActionFuture<GetResponse> doExecute(GetRequest request) {
        return client.get(request);
    }
}
