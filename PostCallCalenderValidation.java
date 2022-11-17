package backendassignmenttest;

import TestBase.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.hwpf.usermodel.DateAndTime;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import payloads.GetFareCalenderPayload;

import java.io.File;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PostCallCalenderValidation extends GetFareCalenderPayload {
        public int statusCode;
        public DateTimeFormatter formatter;
        HashMap<String,String> map=new HashMap<String,String>();

        @Test(priority=1)
        public void validatePostCallgetfarecalender(){
            try {
                File myFile = new File("C:\\Vijaya\\BackendAssignment\\src\\test\\resources\\payload");

                RestAssured.baseURI= "https://ae.almosafer.com/api/v3/flights/flight/get-fares-calender"; // get-fares-calender API is taken for the POST call validation

                JSONObject requestParams = new JSONObject();
                //requestParams.put("leg": []);
               // requestParams.put("originId": "RUH");
               // requestParams.put("destinationId": "JED");
                requestParams.put("departureFrom", "2022-11-16");
                requestParams.put("departureTo", "2022-11-20");

                 //LocalDate.now().plusDays(5);
                LocalDate fromdate = LocalDate.now().plusDays(5);
                LocalDate todate = LocalDate.now().plusDays(10);
                map.put("depatureFrom",fromdate.toString());
                map.put("departureTo",todate.toString());
                System.out.println(fromdate);
                System.out.println(todate);

                Response response=RestAssured
                        .given()
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .body(GetFareCalenderPayload.ValidateGetFareCalenderPayload(map))
                        //.body(requestParams.toJSONString());
                        .log().all()
                        .post().then().extract().response();


                //1: Assertion to verify Response HTTP Status code should be 200 for the POST call of get fares calender
                response.prettyPrint();
                System.out.println("1:Assertion to verify Response HTTP Status code should be 200 for the POST call of get fares calender");
                statusCode=response.getStatusCode();
                System.out.println("The get fares calender API Status Code is  " +statusCode);
                Assert.assertEquals(response.getStatusCode(),200);
                Reporter.log(" The get fares calender API Status code is validated and code is  ---> " +statusCode);
                System.out.println("*************************************************************************************************************************");

                //2:  get fare calender response "Price” should not be null validation
                String strArray = response.getBody().jsonPath().getString(todate.toString());
                String[] price = strArray.split(",",-1);
                System.out.println("2:  get fare calender response \"Price” should not be null validation");
                Assert.assertNotNull(price[0]); //("price is not null in response");
                System.out.println("Validated the price for the get fare calender API and validation is assertNotNull");
                System.out.println("The fare price for the First Array in API Response is    " +price[0]);
                Reporter.log(" The get fare API price NOT NULL is validated and price of first array  is  --> " +price[0]);
                System.out.println("***************************************************************************************************************************");
                System.out.println("3:  get fare calender response UpdatedAt Date should not be null validation");
                Assert.assertNotNull(price[1]); //("UpdatedAT date&Time is not null in response");
                System.out.println("Validated the Updated Date for the get fare calender API and validation is assertNotNull");
                System.out.println("The get fare calender UpdatedAT for the First Array in API Response is    " +price[1]);
                 Reporter.log(" The get fare UpdatedAT date NOT NULL is validated and Date&Time of first array  is  -->  " +price[1]);
                System.out.println("***************************************************************************************************************************");

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



