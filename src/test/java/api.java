import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.APIRequest.NewContextOptions;
import com.microsoft.playwright.options.RequestOptions;

public class api {

    private Playwright playwright;
    private APIRequestContext requestContext;
    private APIResponse response;
    int bookingID;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new NewContextOptions().setBaseURL("https://restful-booker.herokuapp.com"));
    }

    @Test(priority = 1)
    public void getMethod() {

        System.out.println("------------------------------------");

        response = requestContext.get("/booking/"+ bookingID);

        System.out.println(response.status());
        System.out.println(response.text());

        Assert.assertEquals(response.status(), 200);


    }

    @Test(priority = 0)
    public void postmethod() {
        response = requestContext.post("/booking",
                RequestOptions.create().setHeader("Content-Type", "application/json")
                .setData("""
                                        {
                            "firstname" : "Jim",
                            "lastname" : "Brown",
                            "totalprice" : 111,
                            "depositpaid" : true,
                            "bookingdates" : {
                                "checkin" : "2018-01-01",
                                "checkout" : "2019-01-01"
                            },
                            "additionalneeds" : "Breakfast"
                        }
                        """)
        );


        System.out.println(response.status());
        System.out.println(response.text());

       String responseBody = response.text();
        JSONObject jsonObject = new JSONObject(responseBody);
        bookingID = jsonObject.getInt("bookingid");

        System.out.println("BookingID: "+bookingID);
    }

}
