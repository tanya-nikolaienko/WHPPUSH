import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static constants.Constants.Actions.*;
import static constants.Constants.Path.WhpPUSH_PATH;
import static constants.Constants.Servers.Base_URL;
//import   constants.Constants.Variables.ref;

import static constants.Constants.Servers.WhpPUSH_URL;
import static constants.Constants.Variables.*;
import static io.restassured.RestAssured.*;
import static javafx.scene.input.KeyCode.Y;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestWHPPUSH extends TestConfig {

         public String DeleteClientBlockXMLBody="<pk sid=\""+Sid+"\">" +
            "<mes t='DeleteClientBlock' event=\""+Event_VBR+"\" addrid=\""+AdrId+"\" addrtype=\""+AddrTypeCi+"\" extref=\"\"/>\n" +  "</pk>";

         public String AddEventJsonBody="{\n" +
            "\"evt\":\""+Event_VBR+"\",\n" +
            "\"st\":\"WT\",\n" +
            "\"addrid\":\"42933175\",\n" +
            "\"addrtype\":\"CI\",\n" +
            "\"bank\":\"PB\",\n" +
            "\"lg\":\"dn301182nti\",\n" +
            "\"lang\":\"UA\"\n" +
            "}";
/*
         public String WHPpushBodyVBR ="{\n" +
                 "\"status\":\"delivered\",\n" +
                 "\"channel\":\"viber\",\n" +
                 "\"timestamp\":\""+Timestamp1+"\",\n" +
                 "\"order_id\":\""+ref1+"\",\n" +
                 "}";
*/
    /*
    public String WhppushVBRbody="{\n" +
            "\"status\":\"delivered\",\n" +
            "\"channel\":\"viber\",\n" +
            "\"timestamp\":\"15-06-2022 21:16:47\",\n" +
            "\"order_id\":\""+ref1+"\"\n" +
            "}";
*/
    //public static String ModifyEventJsonBody = "{\"ref\": \""+ref1+"\",\n" +
     //       "\"st\": \"IN\"}";


    @Test
    public void DeleteClientBlock(){
        System.out.println("-----DeleteClientBlock-------");
        given().body(DeleteClientBlockXMLBody).log().uri().
        when().post(Base_URL).
        then().spec(responseSpecification).log().body();
        System.out.println();
    }

/*
    @Test
    public void AddEvent(){
        //given().body(AddEventJsonBody).log().all().header("sid",Sid).//логируем наш запрос
        given().spec(requestSpecificationAdd).body(AddEventJsonBody).log().all().//header("sid",Sid).//логируем наш запрос
        when().post(AddEvent_ADD).
        then().spec(responseSpecification).log().body() ;
    }
*/
    @Test
    public void FindRef(){
        System.out.println("-----FindRef-------"); ///AddEvent +  извлекаю переменную реф из ответа для передачи дальше
        Response response =
        given().spec(requestSpecificationAdd).body(AddEventJsonBody).log().body().
        when().post().
        then().extract().response();

        String responseBody  = response.asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        ref = jsonPath.getString("ref");
        ref1=ref;
        stateS = jsonPath.getString("st");
        System.out.println("ref1->"+ref1);
        System.out.println("state->"+stateS);
        System.out.println();
    }



    @Test
    public void ModifyState(){ // через запрос к БД меняю статус рефа с WT на IN
        System.out.println("-----ModifyState-------");
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        )
        {
            String QUERY = "SELECT State FROM JEvent WHERE JE_REF='"+ref1+"'";
            String UpdInitialState = "UPDATE JEvent " + "SET State = 'IN' WHERE JE_REF='"+ref1+"'";
            stmt.executeUpdate(UpdInitialState);
            ResultSet rs = stmt.executeQuery(QUERY); // Extract data from result set
            while (rs.next()){   // Retrieve by column name
                System.out.print("State: " + rs.getString("State"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    @Test
    public void N_DateTime() {  // метод для получения Timestamp, его извлечения из ответа и передачи дальше
        System.out.println("-----N_DateTime-------");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(System.currentTimeMillis());

        String Timestamp = formatter.format(date);
        System.out.println(Timestamp);
        Timestamp1=Timestamp;
        System.out.println();
    }

    @Test
    public void WhppushVBR(){ // отправка первого коллбека
        System.out.println("----WhppushVBR----");
       // System.out.println(Timestamp1);
        JSONObject requestBody = new JSONObject();
        requestBody.put("status",status);
        requestBody.put("channel",Ch_VBR);
        requestBody.put("timestamp", Timestamp1);
        requestBody.put("order_id",ref1);

        RequestSpecification request = RestAssured.given().log().uri();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString()).log().body();


        Response response =request.post(WhpPUSH_URL+WhpPUSH_PATH);

        System.out.println(response.getBody().asString());

        System.out.println();
    }

    @Test
    public void X_WhppushVBR(){ // отправка второго  коллбека
        System.out.println("----WhppushVBR----");

        System.out.println(Timestamp1);
        JSONObject requestBody = new JSONObject();
        requestBody.put("status",status);
        requestBody.put("channel",Ch_VBR);
        requestBody.put("timestamp", Timestamp1);
        requestBody.put("order_id",ref1);

        RequestSpecification request = RestAssured.given().log().uri();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString()).log().body();
       // Response response = request.post(WhpPUSH_URL+WhpPUSH_PATH+"?"+ref1+"&prior="+priority);//.then().assertThat().body(equalTo("ok"));
        Response response = request.post(WhpPUSH_URL+WhpPUSH_PATH);//.then().assertThat().body(equalTo("ok"));

        System.out.println(response.getBody().asString());
        System.out.println();
    }

    @Test
    public void Y_SelectState(){  /// проверка статуса рефа после коллбеков
        System.out.println("-----SelectState-------");
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        )
        {

            String QUERY = "SELECT State FROM JEvent WHERE JE_REF='"+ref1+"'";

            ResultSet rs = stmt.executeQuery(QUERY); // Extract data from result set
            while (rs.next()){   // Retrieve by column name
                System.out.print("State: " + rs.getString("State"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }





/*

    @Test
    public void WhppushVBRsmscallback(){
        System.out.println("ref1->"+ref1);
        Response response =
                given().spec(requestSpecificationWhppushVBR).body(WhppushVBRbody).log().body().
                when().post().
                then().extract().response();
                //then().log().body();//then().extract().response();//spec(responseSpecification).log().body() ;
    }



    @Test(dependsOnMethods ={"FindRef"})
    public void ModifyEvent(){

            String ModifyEventJsonBody = "{\"ref\": \""+ref1+"\",\n" +
                "\"st\": \""+InitialState+"\",\n" +
                "\"ignoreStFinite\": \"Y"\"n};

        String ModifyEventJsonBody ="{\n" +
                "\"ref\":\""+ref1+"\",\n" +
                "\"st\":\""+InitialState+"\",\n" +
                "\"ignoreStFinite\":\"Y\"\n" +
                "}";


        //System.out.println("ref1 after->"+ref1);
        given().spec(requestSpecificationModify).body(ModifyEventJsonBody).log().body().
        when().post(ModifyEvent).
        then().spec(responseSpecification).log().all();

    }
*/
}




