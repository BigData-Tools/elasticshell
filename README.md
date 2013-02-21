h1. elasticshell - a shell for elasticsearch

The elasticshell is a javascript shell written in Java that allows to interact with a running [elasticsearch](http://www.elasticsearch.org) cluster using the [Java API](http://www.elasticsearch.org/guide/reference/java-api/).

h2. Getting Started

h3. Installation
* "Download":http://www.javanna.net/downloads/elasticshell-0.20.5.zip and unzip the elasticshell distribution
* Run @bin/elasticshell@ on unix, or @bin/elasticshell.bat@ on windows

h3. Help
Use the help() command to have a look at the elasticshell help.
Every command is exposed as a javascript function. If you want to read the help for a specific command, just tpe its name without curly brackets.

h3. Auto-suggestions
Make use of the auto-suggestions through the tab key to have a look at the available commands and variables.
JSON is native within the elasticshell, thus auto-suggestions are available within a JSON objects too.

h3. Connecting to a cluster
The elasticshell will automatically try to create a new transport client connected to a node running on localhost:9300.
The default transport client will be registered with the es variable name, same result as the following command:
@var es = transportClient('localhost:9300')@

You can manually connect to a running elasticsearch cluster using the following commands:
* transportClient('hostname:port'): it creates a new "transport client":http://www.elasticsearch.org/guide/reference/java-api/client.html . You can provide a list of addresses too
* nodeClient('clusterName'): it creates a new "node client":http://www.elasticsearch.org/guide/reference/java-api/client.html .

h3. elasticsearch API
All of the elasticsearch API are exposed through the elasticshell, let's see how they can be used.

h3. Let's index a document

<pre>
var jsonDoc = {
   "user": "kimchy",
   "postDate": "2009-11-15T13:12:00",
   "message": "Trying out Elastic Search, so far so good?"
}
</pre>

@es.index('twitter','tweet','1', jsonDoc);@

You can also use the available index builder, which allows to use all the options available when "indexing a document":http://www.elasticsearch.org/guide/reference/api/index_.html:

@es.indexBuilder().index('twitter').type('tweet').id('1').source(jsonDoc).execute();@

h3. Interact with a specific index or type
You can easily execute operations on a specific index or type like this:

@es.<index>.<type>.search();@

If the elasticshell does not accept the name of the index or type, for instance if the name contains a space or starts with a number, you can use the following alternate syntax:

@es['index name'].search();@


h3. Let's retrieve a document

@es.twitter.tweet.get('1');@

The above command retrieves the previously indexed document using the "get API":http://www.elasticsearch.org/guide/reference/api/get.html .

h3. Let's search

<pre>
var termQuery = {
    "query" : {
        "term" : { "user": "kimchy" }
    }
}

es.search(termQuery);
</pre>

We can also use the search builder:
<pre>
es.searchBuilder().query(termQuery);
</pre>

Through the search builder we can also make use of the elasticsearch "query builders":http://www.elasticsearch.org/guide/reference/java-api/query-dsl-queries.html like this:
<pre>
es.searchBuilder().query(QueryBuilders.termQuery('user','kimchy')).execute();
</pre>

h3. Let's add a facet to the previous query

<pre>
var userFacet = {
   "user" : { "terms" : {"field" : "user"} }
}

es.searchBuilder().query(termQuery).facets(userFacet).execute();
</pre>

<pre>
es.searchBuilder().query(QueryBuilders.termQuery('user','kimchy')).facet(FacetBuilders.termsFacet('user').field('user')).execute();
</pre>

h2. Have fun!
Remember that the elasticshell is a javascript shell, thus you can have fun executing javascript code.
On the other hand, the elasticshell has been built on top of the Rhino engine, which means that you can execute Java code too.