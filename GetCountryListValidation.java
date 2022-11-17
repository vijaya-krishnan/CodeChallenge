package backendassignmenttest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class GetCountryListValidation {
        public int statusCode;

        @Test(priority=1)
        public void validateGetCountryList(){
            try {
                RestAssured.baseURI= "https://ae.almosafer.com/api/system/country/list"; // Country list API is taken for the Get call validation

                Response response=RestAssured
                        .given()
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .log().all()
                        .get();
                //1:Assertion to verify Response HTTP Status code should be 200 for the Country List API call
                response.prettyPrint();
                System.out.println("1:Assertion to verify Response HTTP Status code should be 200 for the Country List API call");
                statusCode=response.getStatusCode();
                System.out.println("The backend API Status Code is " +statusCode);
                Assert.assertEquals(response.getStatusCode(),200);
                Reporter.log(" The Backend API with invalid API key is validated---> " +statusCode);
                System.out.println("*************************************************************************************************");

                //2: Country code” should not be null validation
                System.out.println("2: Country code  should not be null validation");
                Assert.assertNotNull(response.jsonPath().get("code")); //("Country code is not null in response");
                System.out.println("Validated the Country code assertNotNull");
                Object code =response.jsonPath().get("Code[0]");
                System.out.println("The country code for the First Array in API Response is   " +code);
                System.out.println("*************************************************************************************************");
                // Reporter.log(" The Country code NOT NULL is validated and country code is --> " +code);

                //3:Dialcode should should not be null
                System.out.println("3:Dialcode should should not be null validation");
                String arraydialcode = response.jsonPath().get("DialCode[0]".toString());
                System.out.println("The Dial code is validated and value for first array is "+arraydialcode);
                Assert.assertEquals(arraydialcode, "+966"); //("Dial code matches expected result in response");
                Assert.assertNotNull(arraydialcode); //("Dial code is not null in response");
                //Object dialcode= response.jsonPath().get("Dialcode[0]");
                System.out.println("Assertion for dial code is validated and the dial code for the " +code+ " is  " +arraydialcode);
                Reporter.log(" Assertion for dial code is validated and the dial code for the --->  " +code+ " is  " +arraydialcode);
                System.out.println("*************************************************************************************************");

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



