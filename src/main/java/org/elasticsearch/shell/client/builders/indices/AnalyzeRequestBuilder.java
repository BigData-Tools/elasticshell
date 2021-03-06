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
package org.elasticsearch.shell.client.builders.indices;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.shell.client.builders.AbstractRequestBuilderToXContent;
import org.elasticsearch.shell.json.JsonToString;
import org.elasticsearch.shell.json.StringToJson;

import java.io.IOException;

/**
 * @author Luca Cavanna
 *
 * Request builder for analyze API
 */
@SuppressWarnings("unused")
public class AnalyzeRequestBuilder<JsonInput, JsonOutput> extends AbstractRequestBuilderToXContent<AnalyzeRequest, AnalyzeResponse, JsonInput, JsonOutput> {

    public AnalyzeRequestBuilder(Client client, JsonToString<JsonInput> jsonToString, StringToJson<JsonOutput> stringToJson) {
        super(client, new AnalyzeRequest(null), jsonToString, stringToJson);
    }

    public AnalyzeRequestBuilder<JsonInput, JsonOutput> text(String text) {
        //we need to create a new request since there's no setter for index
        AnalyzeRequest newRequest = new AnalyzeRequest(text);
        newRequest.index(request.index())
                .analyzer(request.analyzer())
                .field(request.field())
                .tokenizer(request.tokenizer())
                .tokenFilters(request.tokenFilters());
        this.request = newRequest;
        return this;
    }

    public AnalyzeRequestBuilder<JsonInput, JsonOutput> index(String index) {
        request.index(index);
        return this;
    }

    public AnalyzeRequestBuilder<JsonInput, JsonOutput> analyzer(String analyzer) {
        request.analyzer(analyzer);
        return this;
    }

    public AnalyzeRequestBuilder<JsonInput, JsonOutput> field(String field) {
        request.field(field);
        return this;
    }

    public AnalyzeRequestBuilder<JsonInput, JsonOutput> tokenizer(String tokenizer) {
        request.tokenizer(tokenizer);
        return this;
    }

    public AnalyzeRequestBuilder<JsonInput, JsonOutput> tokenFilters(String... tokenFilters) {
        request.tokenFilters(tokenFilters);
        return this;
    }

    @Override
    protected ActionFuture<AnalyzeResponse> doExecute(AnalyzeRequest request) {
        return client.admin().indices().analyze(request);
    }

    @Override
    protected XContentBuilder initContentBuilder() throws IOException {
        return super.initContentBuilder().startObject();
    }

    @Override
    protected XContentBuilder toXContent(AnalyzeRequest request, AnalyzeResponse response, XContentBuilder builder) throws IOException {
        return super.toXContent(request, response, builder).endObject();
    }
}
