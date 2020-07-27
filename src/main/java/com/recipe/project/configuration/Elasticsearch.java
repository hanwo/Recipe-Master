package com.recipe.project.configuration;


import java.time.LocalDateTime;
import java.util.Random;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Elasticsearch {
	/* connection create method*/
    public RestHighLevelClient createConnection() {
		System.out.println("Connet OK");
        return new RestHighLevelClient(
        		RestClient.builder(
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
}
