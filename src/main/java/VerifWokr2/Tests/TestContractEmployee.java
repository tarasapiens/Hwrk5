package VerifWokr2.Tests;

import VerifWokr2.ConnectAPI.XClientsWebClient;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class TestContractEmployee {

    private static String TOKEN;


@BeforeAll
@DisplayName("Авторизация")
public static void authorization(){
    String creds = "{\"username\": \""+ XClientsWebClient.login +"\", \n " +
            "\"password\": \""+ XClientsWebClient.pass +"\"}";

     TOKEN = given().log().all()
            .body(creds)
            .contentType(ContentType.JSON)
            .when().post(XClientsWebClient.URL_AUTH)
            .then().log().all()
            .statusCode(201)
            .extract().path("userToken");
}

@Test
@DisplayName("Приходит ответ, проверка хедеров согласно контракту")
public void shouldReturnListOfCompanies() {

        given()
                .log().all()
                .header("ABC", "123")
                .get(XClientsWebClient.URL_POST_COMPANY)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Vary", "Accept-Encoding")
                .header("x-powered-by", "Express")
                .header("x-render-origin-server", "Render")
                .header("Server", "cloudflare")
                .body(is(notNullValue()))
                .log().all();
    }

@Test
@DisplayName("Компания создается, ответы приходят")
public void iCanCreateCompany() {
        String companyJSON = "{\"name\": \""+ XClientsWebClient.nameComp +"\", \n " +
                "\"description\": \""+ XClientsWebClient.descripComp +"\"}";

        int newCompanyId = given()
                .log().all()
                .body(companyJSON)
                .header("x-client-token", TOKEN)
                .contentType(ContentType.JSON)
                .when().post(XClientsWebClient.URL_POST_COMPANY)
                .then()
                .log().all()
                .statusCode(201)
                .body("id", greaterThan(0))
                .extract().path("id");

        given()
                .log().all()
                .get(XClientsWebClient.URL_POST_COMPANY + "/" + newCompanyId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(XClientsWebClient.nameComp))
                .body("description", equalTo(XClientsWebClient.descripComp));
    }

@Test
@DisplayName("Сотрудник добавляется, ответы приходят")
public void iCanCreateEmployee(){

    String companyJSON = "{\"name\": \""+ XClientsWebClient.nameComp +"\", \n " +
            "\"description\": \""+ XClientsWebClient.descripComp +"\"}";

    int idCompany = given()
            .log().all()
            .body(companyJSON)
            .header("x-client-token", TOKEN)
            .contentType(ContentType.JSON)
            .when().post(XClientsWebClient.URL_POST_COMPANY)
            .then()
            .extract().path("id");

    XClientsWebClient client = new XClientsWebClient();
    int idEmployee = client.createEmployee(XClientsWebClient.firstName,
            XClientsWebClient.lastName, XClientsWebClient.middleName, idCompany,
            XClientsWebClient.email, "",
            XClientsWebClient.phone,
            true, TOKEN);

    given()
            .log().all()
            .get(XClientsWebClient.URL_GET_EMPLOYEEID + idEmployee)
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .statusCode(200)
            .header("Vary", "Accept-Encoding")
            .body("firstName", equalTo(XClientsWebClient.firstName))
            .body("lastName", equalTo(XClientsWebClient.lastName))
            .body("middleName", equalTo(XClientsWebClient.middleName));
}

}
