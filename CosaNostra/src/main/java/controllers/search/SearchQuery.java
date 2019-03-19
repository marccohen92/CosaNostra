package controllers.search;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;


public class SearchQuery {
	  public static Properties properties = new Properties();

	private String query;
	private String nbResult;
	public SearchQuery(String query,String nbResult) {
		this.query=Objects.requireNonNull(query);
		this.nbResult=Objects.requireNonNull(nbResult);
	}
	
	public JSONObject search(String query) {
		return null;
	}
	
	public JSONObject  wikiSearch() throws IOException, ParseException {
		
		  String page_id="";
	      HttpTransport httpTransport = new NetHttpTransport();
	      HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	      JSONParser parser = new JSONParser();
	      GenericUrl url = new GenericUrl("https://www.wikidata.org/w/api.php?");
	      url.put("action","wbgetentities");
	      url.put("sites", "enwiki");
	      url.put("titles", this.query);
	      url.put("format", "json");
	      HttpRequest request = requestFactory.buildGetRequest(url);
	      HttpResponse httpResponse = request.execute();
	      JSONObject response = (JSONObject) parser.parse(httpResponse.parseAsString());
	     
	      Iterator it = response.keySet().iterator();
	      if(it.hasNext()) {
		     JSONObject values = (JSONObject) response.get(it.next());
		     for(Object id : values.keySet())
		    	 page_id=(String) id;
	      }
	     
	      GenericUrl baseUrl = new GenericUrl("https://www.wikidata.org/w/api.php?");
	      baseUrl.put("action","wbgetentities");
	      baseUrl.put("ids",page_id);
	      baseUrl.put("props","labels");
	      baseUrl.put("languages","fr");
	      baseUrl.put("format", "json");
	      HttpRequest finalRequest = requestFactory.buildGetRequest(baseUrl);
	      HttpResponse finalHttpResponse = finalRequest.execute();
	      JSONObject finalResponse = (JSONObject) parser.parse(finalHttpResponse.parseAsString());

	      return finalResponse;
	      
	}
	
  public JSONObject kgSearch () {
	  JSONObject response=null;
    try {
      properties.load(new FileInputStream("src/main/resources/kgsearch.properties"));

      HttpTransport httpTransport = new NetHttpTransport();
      HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
      JSONParser parser = new JSONParser();
      GenericUrl url = new GenericUrl("https://kgsearch.googleapis.com/v1/entities:search");
      url.put("query", this.query);
      url.put("limit", this.nbResult);
      url.put("indent", "true");
      url.put("key", properties.get("API_KEY"));
      HttpRequest request = requestFactory.buildGetRequest(url);
      HttpResponse httpResponse = request.execute();
      response = (JSONObject) parser.parse(httpResponse.parseAsString());
      
      /*
      JSONArray elements = (JSONArray) response.get("itemListElement");
      System.out.println(response);
      System.out.println("------------------------");
      for (Object element : elements) {
    	try {  
        System.out.println(JsonPath.read(element, "$.result.name").toString());
        System.out.println(JsonPath.read(element, "$.result.@type").toString());
        System.out.println(JsonPath.read(element, "$.result.image.url").toString());
        System.out.println(JsonPath.read(element, "$.result.detailedDescription.url").toString());
        System.out.println(JsonPath.read(element, "$.result.description").toString());
        System.out.println(JsonPath.read(element, "$.result.detailedDescription.articleBody").toString());
    	}catch(Exception e) {
    		System.out.println(" ");
    	}
    	}
    	*/
    } catch (Exception ex) {
      ex.printStackTrace();
    }
   
    return response;
  }
  
  
  public static void main(String[] args) throws IOException, ParseException {
	
	  SearchQuery sq=new SearchQuery("Neymar","1");
	  System.out.println(sq.kgSearch());
	  System.out.println(sq.wikiSearch());
	  
	  
	  
}
}
