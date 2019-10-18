package com.recipe.project.configuration;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Elasticsearch {
	/* connection create method*/
    public RestHighLevelClient createConnection() {
		System.out.println("Connet OK");
        return new RestHighLevelClient(RestClient.builder(
                            new HttpHost("127.0.0.1",9200,"http")
                    ));
    }
}
