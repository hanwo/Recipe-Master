package com.recipe.project.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.project.configuration.Elasticsearch;
import com.recipe.project.domain.Recipe;

@Service
public class RecipeService {
	@Autowired
	private Elasticsearch elasticsearch;
	
	@Autowired
	private Recipe recipe;
	
	public void webCrawling() {
		String url = "http://www.10000recipe.com/recipe/";
		Document doc = null;
		List<String> content = null;
		try {
			for (int i = 6920000; i < 6920200; i++) {
				content = new ArrayList<>();
				
				//title
				doc = Jsoup.connect(url + i).get();
				
				// recipe
				Elements re = doc.select("#contents_area > div:nth-child(10) > div");
				for (int j = 1; j < re.size() - 2; j++) {
					content.add(re.get(j).text());
				}
					
				// image
				Elements img = doc.select("#contents_area > div.view2_pic > div.centeredcrop img");
				String imgUrl = img.attr("src");
				URL imgU = new URL(img.attr("src"));
				BufferedImage jpg = ImageIO.read(imgU);
				File file = new File("C:\\Project_img" + "\\" + i + ".jpg");
				ImageIO.write(jpg, "jpg", file);
					
				recipe.setTitle(doc.title());
				recipe.setContent(content);
				recipe.setPath(file.toString());
				System.out.println("title : " + doc.title());
				System.out.println("content:  "+content);
				System.out.println("file.toString():   "+file.toString());
				indexing(recipe.getTitle(), recipe.getContent(), recipe.getPath());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	public String deleteIndex() throws IOException {
		boolean acknowleged = false;
		try(RestHighLevelClient client = elasticsearch.createConnection();) {
			String index ="recipe";
			DeleteIndexRequest request = new DeleteIndexRequest(index);
			DeleteIndexResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
			acknowleged = response.isAcknowledged();
		}catch (Exception e) {
			e.printStackTrace();
			return "index 삭제 실패";
		}
		return acknowleged == true ? "삭제 완료":"index 삭제를 실패하였습니다.";
	}
	
	public void indexing(String title, List<String> content, String path) {
        try(RestHighLevelClient client = elasticsearch.createConnection();){
            String index = "recipe";
            String type = "_doc";
            String docId = null;
            
            //doc Indexing
            IndexRequest request = new IndexRequest(index,type,docId);
            request.source(
                XContentFactory.jsonBuilder()
                    .startObject()
                        .field("title",title)
                        .field("content", content)
                        .field("path",path)
                    .endObject());
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            
        }catch (ElasticsearchException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	public JSONArray matchAll() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.size(1000);
		
		SearchRequest searchRequest = new SearchRequest("recipe");
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse response = null;
		SearchHits searchHits = null;
		
		JSONArray jsonArray = new JSONArray();
		
		try(RestHighLevelClient client = elasticsearch.createConnection();){
			response = client.search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();

			Map<String, Object> sourceAsMap = null;
			
			for(SearchHit hit : searchHits.getHits()) {
				sourceAsMap = hit.getSourceAsMap();
				jsonArray.add(sourceAsMap);
			}
			System.out.println(jsonArray);
			return jsonArray;
		}
	}
	
	
	public JSONArray search() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = new SearchRequest("recipe");
		JSONArray jsonArray = new JSONArray();

		searchSourceBuilder.query(QueryBuilders.boolQuery()
//				.must(QueryBuilders.termQuery("title", "레시피"))
//				.must(QueryBuilders.termQuery("content", "가시오이"))
//				.must(QueryBuilders.matchQuery("title", "레시피 끝부분은"))
//				.must(QueryBuilders.matchQuery("content", "레시피 끝부분은"))
				.should(QueryBuilders.matchQuery("title", "덮밥"))
				.must(QueryBuilders.matchQuery("content", "밥"))
				);
		searchSourceBuilder.size(99);
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse response = null;
		SearchHits searchHits = null;
		
		try(RestHighLevelClient client = elasticsearch.createConnection();){
			List<Recipe> recipes= new ArrayList<>();
			Recipe recipe = new Recipe();
			response = client.search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();
			
			Map<String, Object> sourceAsMap = null;
			
			for(SearchHit hit : searchHits.getHits()) {
				sourceAsMap = hit.getSourceAsMap();
				jsonArray.add(sourceAsMap);
			}
			return jsonArray;
		}
	}
	
}

