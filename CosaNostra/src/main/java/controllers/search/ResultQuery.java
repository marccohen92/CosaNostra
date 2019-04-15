package controllers.search;

import java.io.IOException;

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

public class ResultQuery {
	
	public static FinalResult getFinalResult(String pageId) throws IOException, ParseException {
		
		FinalResult fRes = new FinalResult(null, null, pageId, null, null, null, null, null, null); 
		
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		JSONParser parser = new JSONParser();
		GenericUrl baseUrl = new GenericUrl("https://www.wikidata.org/w/api.php?");
		baseUrl.put("action","wbgetentities");
		baseUrl.put("ids",pageId);
		baseUrl.put("props","labels|descriptions|claims");
		baseUrl.put("languages","fr");
		baseUrl.put("format", "json");
		HttpRequest finalRequest = requestFactory.buildGetRequest(baseUrl);
		HttpResponse finalHttpResponse = finalRequest.execute();
		JSONObject finalResponse = (JSONObject) parser.parse(finalHttpResponse.parseAsString());

		// AJOUT PHOTO DU RESULT
		Object entities =  finalResponse.get("entities");
		JSONObject Jentities = (JSONObject) entities;
		JSONObject Jid = (JSONObject) Jentities.get(fRes.getPageId());
		Object claims = Jid.get("claims");
		JSONObject Jclaims = (JSONObject) claims;
		Object photo = Jclaims.getOrDefault("P18", "wallou photo");
		if (photo.toString() != "wallou photo") {
			JSONArray Jphoto = (JSONArray) photo;
			Object mainsnak = Jphoto.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object photoName = Jvalue.get("value");
			String finalPhotoName = photoName.toString().replaceAll(" ", "_");
			fRes.setPhotoUrl("https://commons.wikimedia.org/wiki/Special:FilePath/"+finalPhotoName);
		}
		
		// AJOUT NOM DU RESULT
		Object name = Jclaims.getOrDefault("P1559", "wallou nom");
		if (name.toString() != "wallou nom") {
			JSONArray Jname = (JSONArray) name;
			Object mainsnak = Jname.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object namevalue = Jvalue.get("value");
			JSONObject Jnamevalue = (JSONObject) namevalue;
			Object nametext = Jnamevalue.get("text");
			fRes.setName(nametext.toString());
		}
		else {
			fRes.setName("No name");
		}
		
		//AJOUT DE LA DATE DE NAISSANCE
		Object dtBirth = Jclaims.getOrDefault("P569", "wallou date de naissance");
		if (dtBirth.toString() != "wallou date de naissance") {
			JSONArray JdtBirth = (JSONArray) dtBirth;
			Object mainsnak = JdtBirth.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object namevalue = Jvalue.get("value");
			JSONObject Jnamevalue = (JSONObject) namevalue;
			Object nametext = Jnamevalue.get("time");
			fRes.setDateOfBirth(nametext.toString());
		}
		else {
			fRes.setDateOfBirth("No date de naissance");
		}
		
		//AJOUT DU SEXE
		Object gender = Jclaims.getOrDefault("P21", "wallou sexe");
		if (gender.toString() != "wallou sexe") {
			JSONArray Jgender = (JSONArray) gender;
			Object mainsnak = Jgender.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			JSONObject Jvalue2 = (JSONObject) value2;
			String genderName = getPageName((String) Jvalue2.get("id"));
			fRes.setGender(genderName.toString());
		}
		else {
			fRes.setGender("No gender");
		}
		
		//AJOUT NATIONALITY
		Object nationality = Jclaims.getOrDefault("P27", "wallou nationality");
		if (nationality.toString() != "wallou nationality") {
			JSONArray Jnationality = (JSONArray) nationality;
			Object mainsnak = Jnationality.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			JSONObject Jvalue2 = (JSONObject) value2;
			String nationalityName = getPageName((String) Jvalue2.get("id"));
			fRes.setNationality(nationalityName.toString());
		}
		else {
			fRes.setNationality("No nationality");
		}
		
		//AJOUT ACCOUPATION
		Object occupation = Jclaims.getOrDefault("P106", "wallou occupation");
		if (occupation.toString() != "wallou occupation") {
			JSONArray Joccupation = (JSONArray) occupation;
			Object mainsnak = Joccupation.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			JSONObject Jvalue2 = (JSONObject) value2;
			String nationalityName = getPageName((String) Jvalue2.get("id"));
			fRes.setOccupation(nationalityName.toString());
		}
		else {
			fRes.setOccupation("No occupation");
		}
		
		//AJOUT STYLE
		Object style = Jclaims.getOrDefault("P136", "wallou style");
		if (style.toString() != "wallou style") {
			JSONArray Jstyle = (JSONArray) style;
			Object mainsnak = Jstyle.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			JSONObject Jvalue2 = (JSONObject) value2;
			String nationalityName = getPageName((String) Jvalue2.get("id"));
			fRes.setStyle(nationalityName.toString());
		}
		else {
			fRes.setStyle("No style");
		}
		
		//AJOUT SERVICES RELIES
		//MusicBrainz
		Object musicBrainz = Jclaims.getOrDefault("P434", "wallou musicBrainz");
		if (musicBrainz.toString() != "wallou musicBrainz") {
			JSONArray JmusicBrainz = (JSONArray) musicBrainz;
			Object mainsnak = JmusicBrainz.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			fRes.getRelatedServices().put("MusicBrainz", "https://musicbrainz.org/artist/"+value2.toString());
		}
		//Twitter
		Object twitter = Jclaims.getOrDefault("P2002", "wallou twitter");
		if (twitter.toString() != "wallou twitter") {
			JSONArray Jtwitter = (JSONArray) twitter;
			Object mainsnak = Jtwitter.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			fRes.getRelatedServices().put("Twitter", "https://twitter.com/"+value2.toString());
		}
		
		//Spotify
		Object spotify = Jclaims.getOrDefault("P1902", "wallou spotify");
		if (spotify.toString() != "wallou spotify") {
			JSONArray Jspotify = (JSONArray) spotify;
			Object mainsnak = Jspotify.get(0);
			JSONObject Jmainsnak = (JSONObject) mainsnak;
			Object datavalue = Jmainsnak.get("mainsnak");
			JSONObject Jdatavalue = (JSONObject) datavalue;
			Object value = Jdatavalue.get("datavalue");
			JSONObject Jvalue = (JSONObject) value;
			Object value2 = Jvalue.get("value");
			fRes.getRelatedServices().put("Spotify", "https://open.spotify.com/artist/"+value2.toString());
		}
				
		return fRes;
		
	}
	
	
	public static String getPageName(String pageId) throws IOException, ParseException {
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		JSONParser parser = new JSONParser();
		GenericUrl baseUrl = new GenericUrl("https://www.wikidata.org/w/api.php?");
		baseUrl.put("action","wbgetentities");
		baseUrl.put("ids",pageId);
		baseUrl.put("props","labels");
		baseUrl.put("languages","fr");
		baseUrl.put("format", "json");
		HttpRequest finalRequest = requestFactory.buildGetRequest(baseUrl);
		HttpResponse finalHttpResponse = finalRequest.execute();
		JSONObject finalResponse = (JSONObject) parser.parse(finalHttpResponse.parseAsString());
		
		Object entities = finalResponse.get("entities");
		JSONObject Jentities = (JSONObject) entities;
		Object page = Jentities.get(pageId);
		JSONObject Jpage = (JSONObject) page;
		Object labels = Jpage.get("labels");
		JSONObject Jlabels = (JSONObject) labels;
		Object lang = Jlabels.get("fr");
		JSONObject Jlang = (JSONObject) lang;
		Object value = Jlang.get("value");
		
		return value.toString();
	}
	
	/*
	public static String getSummary(String title) throws IOException, ParseException {
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		JSONParser parser = new JSONParser();
		GenericUrl baseUrl = new GenericUrl("https://www.wikipedia.org/w/api.php?");
		baseUrl.put("action","query");
		baseUrl.put("titles",title);
		baseUrl.put("prop","extracts");
		baseUrl.put("format", "json");
		HttpRequest finalRequest = requestFactory.buildGetRequest(baseUrl);
		HttpResponse finalHttpResponse = finalRequest.execute();
		JSONObject finalResponse = (JSONObject) parser.parse(finalHttpResponse.parseAsString());
		
		Object query = finalResponse.get("query");
		JSONObject Jquery = (JSONObject) entities;
		Object page = Jentities.get(pageId);
		JSONObject Jpage = (JSONObject) page;
		Object labels = Jpage.get("labels");
		JSONObject Jlabels = (JSONObject) labels;
		Object lang = Jlabels.get("fr");
		JSONObject Jlang = (JSONObject) lang;
		Object value = Jlang.get("value");
		
		return value.toString();
	}
	*/
	
	public static void main(String[] args) throws IOException, ParseException {
		System.out.println(getFinalResult("Q642477"));
	}

}