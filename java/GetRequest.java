
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;


public class GetRequest {
	
	String url = "http://www.omdbapi.com/";

	
	@Test
	public void test01_getSearchByString() {
		String url = "http://www.omdbapi.com/?s=Batman&page=3i=tt3896198&apikey=be9424a2";

		//Verify the list of all results that matched that search string
	    given().get(url).then().statusCode(200).log().all();	
		int values = given().get(url).then().extract().jsonPath().getList("Search").size();
		for(int i=0;i<values;i++) {
			given().get(url).then().body("Search["+i+"].Title", Matchers.containsString("Batman")).extract().response();
		}
		
	}
	
	@Test
	public void test02_getById() {
		
		//Assertion to verify the result on id
		
	    given().queryParam("i","tt0944947").queryParam("apikey","be9424a2").get(url).then().statusCode(200).assertThat().body("Title",equalTo("Game of Thrones")).body("imdbID",equalTo("tt0944947"));
	   
	}
	
	@Test
	public void test03_getByTitle() {
		
		//Assertion to verify the title name
		
	    given().queryParam("t","Game of Thrones").queryParam("apikey","be9424a2").get(url).then().statusCode(200).log().all().body("Title",equalTo("Game of Thrones"));

	}
	
	
	@Test
	public  void test04_SearchBy() {
		
		//Assertion to verify that the total results is atleast 30
		Response res = given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").when().get(url);
		JsonPath js = new JsonPath(res.asString());
		int result = js.getInt("totalResults");
		System.out.println(res.asString());
		assertTrue(result>=30);
		
		
		//Assertion to verify the Title 
		given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").get(url).then().statusCode(200).log().all();		
		int values =given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").get(url).then().extract().jsonPath().getList("Search").size();
		System.out.println(values);

		String temp = "";
		for(int i=0;i<values;i++) {
	        temp = js.getString("Search["+i+"].Title");
	        if(temp.equals("Activision: STEM - in the Videogame Industry")||temp.equals("The STEM Journals")) {
	        	System.out.println("The title is"+js.getString("Search["+i+"].Title"));
				given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").get(url).then().body("Search.Title",hasItems("The STEM Journals"));
	        }
	        
		}
	
	}
	
	
	

	@Test
	public void test05_getImbIdforItem() {
		given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").get(url).then().statusCode(200).log().all();	
		
		int values =given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").get(url).then().extract().jsonPath().getList("Search").size();
		System.out.println(values);
		
		Response res = given().contentType("application/json").queryParam("s","stem").queryParam("apikey","be9424a2").when().get(url);
		JsonPath js = new JsonPath(res.asString());
		String temp = "";
    	String imdbId = "";
		for(int i=0;i<values;i++) {
	        temp = js.getString("Search["+i+"].Title");
	        if(temp.equals("Activision: STEM - in the Videogame Industry")) {
	        	System.out.println("The title is"+js.getString("Search["+i+"].Title"));
	        	imdbId =js.getString("Search["+i+"].imdbID");
	    	    given().queryParam("i",imdbId).queryParam("apikey","be9424a2").get(url).then().statusCode(200).log().all();
	    	    given().queryParam("i",imdbId).queryParam("apikey","be9424a2").get(url).then().statusCode(200).assertThat().body("Released",equalTo("23 Nov 2010")).body("Director",equalTo("Mike Feurstein"));
	        }
		}

	}
	
	@Test
	public void test06_verifyGetByTitle() {	
		
		//Verify the title name
	    given().queryParam("t","The STEM Journals").queryParam("apikey","be9424a2").get(url).then().assertThat().body("Plot", Matchers.containsStringIgnoringCase("Science, Technology, Engineering and Math"));
	    given().queryParam("t","The STEM Journals").queryParam("apikey","be9424a2").get(url).then().assertThat().body("Runtime",equalTo("22 min"));

	}
	
}

