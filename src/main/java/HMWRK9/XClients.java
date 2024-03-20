package HMWRK9;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class XClients {
    public static  final String URL = "https://x-clients-be.onrender.com/";
    static String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";
    static String URL_GET_COMPANY = "https://x-clients-be.onrender.com/employee?company=";
    static String URL_POST_EMPLOYEE = "https://x-clients-be.onrender.com/employee";
    static String URL_POST_COMPANY = "https://x-clients-be.onrender.com/company";
    static String URL_GET_EMPLOYEE = "https://x-clients-be.onrender.com/employee?company=";
    public static String TOKEN;
    public static int idCompany;

@BeforeAll
@DisplayName("Авторизация под Admin")
public static void tokenForTest(){
             String logPass = """
            {
              "username": "raphael",
              "password": "cool-but-crude"
            }
            """;

            TOKEN = given().log().all()
            .body(logPass)
            .contentType(ContentType.JSON)
            .when().post(URL_AUTH)
            .then().log().all()
            .extract().path("userToken");
}

@BeforeAll
@DisplayName("Создать компанию, получить ее ID")
public static void createCompany() {
        String company = """
                {
                  "name": "Fender",
                  "description": "Top-Guitars"
                }
                """;
             idCompany = given()
                .body(company)
                .contentType(ContentType.JSON)
                .header("x-client-token", TOKEN)
                .when().post(URL_POST_COMPANY)
                .then()
                .statusCode(201)
                .extract().path("id");
}

@Test
@DisplayName("Добавить нового сотрудника в компанию")
        public void createEmployee(){
String requestBodyEmployee = "{\"firstName\": \"Leo\",\n" + "  \"lastName\": \"Bender\",\n" +
        " \"middleName\": \"string\",\n" + " \"companyId\": "+ idCompany +", \n" +
        " \"email\": \"leobeo@mail.ru\",\n" + "  \"url\": \"string\",\n" +
        " \"phone\": \"19875513\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
        " \"isActive\": true}";

   given().log().all()
           .body(requestBodyEmployee)
           .contentType(ContentType.JSON)
           .header("x-client-token", TOKEN)
           .when().post(URL_POST_EMPLOYEE)
           .then()
           .statusCode(201)
           .body("id", is(notNullValue()));
}

@Test
@DisplayName("Добавить нового сотрудника без указания id компании")
public  void createEmptyEmployee() {
    String requestBodyEmployee = "{\"firstName\": \"Leo\",\n" + "  \"lastName\": \"Bender\",\n" +
            " \"middleName\": \"string\",\n" + " \"companyId\": "+", \n" +
            " \"email\": \"leobeo@mail.ru\",\n" + "  \"url\": \"string\",\n" +
            " \"phone\": \"19875513\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
            " \"isActive\": true}";

    given().log().all()
            .body(requestBodyEmployee)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().post(URL_POST_EMPLOYEE)
            .then()
            .statusCode(400);
}


@Test
@DisplayName("Получить список сотрудников для компании")
    public void getEmployee(){
    given().log().headers()
            .when().get(URL_GET_EMPLOYEE + idCompany)
            .then().log().all()
            .statusCode(200);
}

@Test
@DisplayName("Получить сотрудника по ID")
public void getIdEmplotee(){
    String requestBodyEmployee = "{\"firstName\": \"Roman\",\n" + "  \"lastName\": \"Golovin\",\n" +
            " \"middleName\": \"Igorevich\",\n" + " \"companyId\": "+ idCompany +", \n" +
            " \"email\": \"roma@gmail.com\",\n" + "  \"url\": \"string\",\n" +
            " \"phone\": \"7924578454\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
            " \"isActive\": true}";

    int idEmployee = given().log().all()
            .body(requestBodyEmployee)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().post(URL_POST_EMPLOYEE)
            .then().statusCode(201)
            .extract().path("id");

    given().log().all()
            .get(URL_POST_EMPLOYEE + "/" + idEmployee)
            .then().log().all()
            .statusCode(200)
            .body("id", equalTo(idEmployee));
}

@Test
@DisplayName("Изменить информацию о сотруднике")
//Баг тут в том, что по документации при успешной выполнении ожидается статус-код "201",
// но возвращает "200". Само изменение проходит успешно.
public void editEmployee() {
    String requestBodyEmployee = "{\"firstName\": \"Tor\",\n" + "  \"lastName\": \"Linus\",\n" +
            " \"middleName\": \"string\",\n" + " \"companyId\": "+ idCompany +", \n" +
            " \"email\": \"linus@gmail.com\",\n" + "  \"url\": \"string\",\n" +
            " \"phone\": \"123456789\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
            " \"isActive\": true}";

    int idEmployee = given().log().all()
            .body(requestBodyEmployee)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().post(URL_POST_EMPLOYEE)
            .then().statusCode(201)
            .extract().path("id");

    String requestBodyEmployeeEdit = "{\n" +
            "  \"lastName\": \"Ivanov\",\n" +
            "  \"email\": \"ivanov@rambler.ru\",\n" +
            "  \"url\": \"string\",\n" +
            "  \"phone\": \"987654321\",\n" +
            "  \"isActive\": true\n" +
            "}";

    given().log().all()
            .body(requestBodyEmployeeEdit)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().patch(URL_POST_EMPLOYEE + "/" + idEmployee)
            .then().log().all()
            .statusCode(201)
            .body("lastName", equalTo("Ivanov"))
            .body("phone", equalTo("987654321"));
}

}







