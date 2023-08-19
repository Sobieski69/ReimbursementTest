import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReimbursementTest {

    @Test
    public void reimbursementCalculationApiTest() {
        String baseUrl = "http://localhost:8080/";
        String adminPatchBody = "{\"mileageRate\":\"0.3\",\"dailyAllowance\":\"130\",\"receiptParams\":[{\"receiptType\":\"Taxi\",\"value\":\"10\"},{\"receiptType\":\"Train\",\"value\":\"20\"},{\"receiptType\":\"Plane\",\"value\":\"40\"},{\"receiptType\":\"Hotel\",\"value\":\"15\"},{\"receiptType\":\"Restaurant\",\"value\":\"9\"}]}";
        String clientPostPayload = "{\"mileage\":\"100\",\"days\":\"5\",\"receipts\":[{\"receiptType\":\"Taxi\",\"value\":\"20\"},{\"receiptType\":\"Train\",\"value\":\"30\"},{\"receiptType\":\"Hotel\",\"value\":\"11.5\"}]}";
        given()
                .contentType("application/json")
                .body(adminPatchBody)
                .when()
                .log().all()
                .patch(baseUrl + "admin")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("data[0]", equalTo("saved"));


        given()
                .contentType("application/json")
                .body(clientPostPayload)
                .when()
                .log().all()
                .post(baseUrl + "data")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("data[0]", equalTo("71.5"));


    }

}