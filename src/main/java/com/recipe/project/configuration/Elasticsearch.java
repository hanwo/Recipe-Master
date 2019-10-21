package com.recipe.project.configuration;

<<<<<<< HEAD
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
=======

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
>>>>>>> feature/Login
import org.springframework.context.annotation.Configuration;

@Configuration
public class Elasticsearch {
	/* connection create method*/
    public RestHighLevelClient createConnection() {
		System.out.println("Connet OK");
        return new RestHighLevelClient(RestClient.builder(
<<<<<<< HEAD
                 new HttpHost("127.0.0.1",9200,"http")
       ));
    }
    
    public String date() {
        Random random = new Random();
        int year = LocalDateTime.now().getYear();
        int tmp = random.nextInt(12)+1;
        String month = null;
        String day = null;
        
        if(tmp < 10) {month = "0"+tmp;}
        else {month = ""+tmp;}
        
        tmp = random.nextInt(31)+1;
        if(tmp < 10) { day = "0"+tmp; }else { day = ""+tmp; }
        String time = year+"-"+month+"-"+day;
        
        return time;
     }
=======
                            new HttpHost("127.0.0.1",9200,"http")
                    ));
    }
>>>>>>> feature/Login
}
