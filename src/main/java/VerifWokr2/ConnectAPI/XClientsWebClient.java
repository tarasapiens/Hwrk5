package VerifWokr2.ConnectAPI;

import VerifWokr2.model.Company;
import VerifWokr2.model.CreateCompany;
import VerifWokr2.model.CreateEmployee;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class XClientsWebClient {

    public static final String login = "raphael";
    public static final String pass = "cool-but-crude";
    public static final String nameComp = "Fender-Test-Company";
    public static final String descripComp = "Top-Guitars";
    public static final String firstName = "Leonardo";
    public static final String lastName = "DaVinchy";
    public static final String middleName = "DiSerPiero";
    public static final String email = "leo.Dava@gmail.com";
    public static final String phone = "+78985623561";
    public static final String URL = "https://x-clients-be.onrender.com/";
    public static final String URL_ID = "https://x-clients-be.onrender.com/company/{id}";
    public static final String URL_AUTH = "https://x-clients-be.onrender.com/auth/login";
    public static final String URL_POST_EMPLOYEE = "https://x-clients-be.onrender.com/employee";
    public static final String URL_POST_COMPANY = "https://x-clients-be.onrender.com/company";
    public static final String URL_GET_EMPLOYEELIST = "https://x-clients-be.onrender.com/employee?company=";
    public static final String URL_GET_EMPLOYEEID = "https://x-clients-be.onrender.com/employee/";

     public String getToken(String login, String pass) {
        String creds = "{\"username\": \"" + login + "\",\"password\": \"" + pass + "\"}";

        // Авторизация
        return given().log().all()
                .body(creds)
                .contentType(ContentType.JSON)
                .when().post(URL_AUTH)
                .then().log().all()
                .extract().path("userToken");
    }

    public Company getCompanyInfo(int compId) {
        return given()
                .log().all()
                .pathParams("id", compId)
                .get(URL_ID)
                .then()
                .log().all()
                .extract().as(Company.class);
    }

    public int createCompany(String name, String desc, String token) {
        CreateCompany createCompany = new CreateCompany(name, desc);

        // Создание тестовой компании
        return given()
                .log().all()
                .body(createCompany)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .when()
                .post(URL_POST_COMPANY)
                .then()
                .log().all()
                .extract().path("id");
    }

public int createEmployee(String firstName, String lastName, String middleName,
                          int companyId, String email, String url,
                          String phone, boolean isActive, String token){
        CreateEmployee createEmployee = new CreateEmployee(firstName, lastName, middleName, companyId, email, url, phone, isActive, token);

       //Добавление сотрудника

    return given()
                .log().all()
                .body(createEmployee)
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .post(URL_POST_EMPLOYEE)
                .then()
                .log().all()
                .extract().path("id");
}

}
