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
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
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
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
		List<String> ingre = null;
		try {
			for (int i = 6920000; i < 6920031; i++) {
				content = new ArrayList<>();
				ingre = new ArrayList<>();

				// title
				doc = Jsoup.connect(url + i).get();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// recipe
				Elements co = doc.select("#contents_area > div:nth-child(10) > div");
				for (int j = 1; j < co.size() - 2; j++) {
					content.add(co.get(j).text());
				}
				Elements in = doc.select("#divConfirmedMaterialArea > ul:nth-child(1) > li");
				for (int k = 0; k < in.size(); k++) {
					ingre.add(in.get(k).text());
				}

				// image
				Elements img = doc.select("#contents_area > div.view2_pic > div.centeredcrop img");
				String imgUrl = img.attr("src");
				URL imgU = new URL(img.attr("src"));
				BufferedImage jpg = ImageIO.read(imgU);
				File file = new File(
						"C:\\0.encore\\00.Project\\step14_recipeMaster\\src\\main\\resources\\static\\Project_img"
								+ "\\" + i + ".jpg");
				ImageIO.write(jpg, "jpg", file);
				recipe.setTitle(doc.title());
				recipe.setContent(content);
				recipe.setIngre(ingre);
				indexing(recipe.getTitle(), recipe.getContent(), recipe.getIngre());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String deleteIndex() throws IOException {
		boolean acknowleged = false;
		try (RestHighLevelClient client = elasticsearch.createConnection();) {
			String index = "recipe";
			DeleteIndexRequest request = new DeleteIndexRequest(index);
			DeleteIndexResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
			acknowleged = response.isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
			return "index 삭제 실패";
		}
		return acknowleged == true ? "삭제 완료" : "index 삭제를 실패하였습니다.";
	}

	public void indexing(String title, List<String> content, List<String> ingre) {
		try (RestHighLevelClient client = elasticsearch.createConnection();) {
			String index = "recipe";
			String type = "_doc";
			String docId = null;
			System.out.println("elasticsearch.date():  " + elasticsearch.date());
			// doc Indexing
			IndexRequest request = new IndexRequest(index, type, docId);
			request.source(XContentFactory.jsonBuilder().startObject().field("title", title).field("content", content)
					.field("ingre", ingre).field("date", elasticsearch.date()).endObject());
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONArray matchAll() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.size(1000);
		searchSourceBuilder.sort(new FieldSortBuilder("date").order(SortOrder.DESC));

		SearchRequest searchRequest = new SearchRequest("recipe");
		searchRequest.source(searchSourceBuilder);

		SearchResponse response = null;
		SearchHits searchHits = null;

		JSONArray jsonArray = new JSONArray();

		try (RestHighLevelClient client = elasticsearch.createConnection();) {
			response = client.search(searchRequest, RequestOptions.DEFAULT);
			searchHits = response.getHits();
			for (SearchHit hit : searchHits.getHits()) {
				jsonArray.add(hit);
			}
			return jsonArray;
		}
	}

	public JSONArray search(String keyword) throws IOException {
	      SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	      searchSourceBuilder.sort(new FieldSortBuilder("date").order(SortOrder.DESC));
	      SearchRequest searchRequest = new SearchRequest("recipe");
	      JSONArray jsonArray = new JSONArray();
	      System.out.println("searchService1:   "+keyword);
	      searchSourceBuilder.query(QueryBuilders.boolQuery()
	            .must(QueryBuilders.wildcardQuery("title", "*"+keyword+"*"))
	            .must(QueryBuilders.wildcardQuery("ingre", "*"+keyword+"*"))
	            .should(QueryBuilders.wildcardQuery("title", "*"+keyword+"*"))
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
	         System.out.println("searchRequest: "+searchRequest);
	         System.out.println("searchHits: "+searchHits);
	         System.out.println("response: "+response);
//	         Map<String, Object> sourceAsMap = null;
	         
	         for(SearchHit hit : searchHits.getHits()) {
//	            sourceAsMap = hit.getSourceAsMap();
	            jsonArray.add(hit);
//	            System.out.println("sourceAsMap: "+hit);
	         }
	         return jsonArray;
	      }
	   }

	public Object getDoc(String docId) {
		// 문서 요청
		GetRequest request = new GetRequest("recipe", "_doc", docId);
		GetResponse response = null;

		try (RestHighLevelClient client = elasticsearch.createConnection();) {
			response = client.get(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return "문서 조회 실패";
		}
		System.out.println(response);

		return response;
	}

}
