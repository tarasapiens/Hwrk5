package VerifWokr2.Tests;


import VerifWokr2.ConnectAPI.XClientsWebClient;
import VerifWokr2.EntityDB.EmployeeEntity;
import VerifWokr2.EntityDB.PersUnInfo;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.*;

import java.util.Properties;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestEmployee {

  private static String TOKEN;
  private static int idCompany;
  private EntityManager entityManager;
  XClientsWebClient client = new XClientsWebClient();
    int idEmployeeCommon = client.createEmployee(XClientsWebClient.firstName,
            XClientsWebClient.lastName, XClientsWebClient.middleName, idCompany,
            XClientsWebClient.email, "",
            XClientsWebClient.phone,
            true, TOKEN);

@BeforeAll
@DisplayName("Авторизация и создание компании для тестирования")
    public static void CreateCompany() {
    XClientsWebClient client = new XClientsWebClient();

    TOKEN = client.getToken(XClientsWebClient.login, XClientsWebClient.pass);
    idCompany = client.createCompany(XClientsWebClient.nameComp, XClientsWebClient.descripComp, TOKEN);
}

@BeforeEach
@DisplayName("Подключение к базе данных")
public void setUpForTest(){

        String connectionString = "jdbc:postgresql://dpg-cn1542en7f5s73fdrigg-a.frankfurt-postgres.render.com/x_clients_xxet";
        String user = "x_clients_user";
        String pass = "x7ngHjC1h08a85bELNifgKmqZa8KIR40";

        Properties proper = new Properties();

        proper.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        proper.put("hibernate.connection.url", connectionString);
        proper.put("hibernate.connection.username", user);
        proper.put("hibernate.connection.password", pass);
        proper.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        PersistenceUnitInfo persistenceUnitInfo = new PersUnInfo(proper);
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory factory = hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo, persistenceUnitInfo.getProperties());
        entityManager = factory.createEntityManager();
    }

@Test
@DisplayName("1.1 Добавить/получить нового сотрудника для компании")
public void addEmployee(){

// Проверяем успешность добавления сотрудника через API
    given().log().all()
        .get(XClientsWebClient.URL_GET_EMPLOYEEID + idEmployeeCommon)
        .then().log().all()
        .statusCode(200)
        .body("id", equalTo(idEmployeeCommon))
        .body("firstName", equalTo(XClientsWebClient.firstName));

// Проверяем успешность добавления сотрудника через БД
    EmployeeEntity emp = entityManager.find(EmployeeEntity.class, idEmployeeCommon);
    assertEquals(idEmployeeCommon, emp.getId());
    assertEquals(XClientsWebClient.lastName, emp.getLastName());
    assertEquals(XClientsWebClient.phone, emp.getPhone());
    assertEquals(XClientsWebClient.middleName, emp.getMiddleName());
    assertEquals(XClientsWebClient.email, emp.getEmail());
}

@Test
@DisplayName("1.2 Добавить/получить несколько сотрудников для компании")
public void addSeveralEmployee(){

    EmployeeEntity emp = entityManager.find(EmployeeEntity.class, idEmployeeCommon);
    assertEquals(idEmployeeCommon, emp.getId());
    assertEquals(XClientsWebClient.lastName, emp.getLastName());
    assertEquals(XClientsWebClient.phone, emp.getPhone());
    assertEquals(XClientsWebClient.middleName, emp.getMiddleName());


    int idEmployee2 = client.createEmployee(XClientsWebClient.firstName,
            XClientsWebClient.lastName, XClientsWebClient.middleName, idCompany,
            XClientsWebClient.email, "",
            XClientsWebClient.phone,
            true, TOKEN);

    EmployeeEntity emp2 = entityManager.find(EmployeeEntity.class, idEmployee2);
    assertEquals(idEmployee2, emp2.getId());
    assertEquals(XClientsWebClient.lastName, emp2.getLastName());
    assertEquals(XClientsWebClient.phone, emp2.getPhone());
    assertEquals(XClientsWebClient.middleName, emp2.getMiddleName());
 }

 @Test
@DisplayName("1.3 Отправить запрос на получение списка сотрудников по id компании")

public void getSeveralEmployee(){

    given().log().headers()
            .when().get(XClientsWebClient.URL_GET_EMPLOYEELIST + idCompany)
            .then().log().all()
            .statusCode(200)
            .body(notNullValue());

    EmployeeEntity emp = entityManager.find(EmployeeEntity.class, idEmployeeCommon);
    assertEquals(idEmployeeCommon, emp.getId());
}

@Test
@DisplayName("1.4 Получение списка сотрудников по несуществующему id компании")
public void getEmployeeNonId(){

    given().log().headers()
            .when().get(XClientsWebClient.URL_GET_EMPLOYEELIST + 0)
            .then().log().all()
            .statusCode(200)
            .body(equalTo("[]"));
}

@Test
@DisplayName("1.5 Отправить запрос на получение списка сотрудников по существующему id компании методом POST")
public void getEmployeePost(){

    given().log().headers()
            .when().post(XClientsWebClient.URL_GET_EMPLOYEELIST + idCompany)
            .then().log().all()
            .statusCode(415);
}

@Test
@DisplayName("1.6 Добавить сотрудника c телом JSON и c content-type = XML")
public void addEmployeeXML(){

    String requestBodyEmployee = "{\"firstName\": \"Leo\",\n" + "  \"lastName\": \"Bender\",\n" +
           " \"companyId\": "+ idCompany +", \n" + ",\n" + " \"isActive\": true}";

    given().log().all()
            .body(requestBodyEmployee)
            .contentType(ContentType.XML)
            .header("x-client-token", TOKEN)
            .when().post(XClientsWebClient.URL_POST_EMPLOYEE)
            .then()
            .statusCode(500);
}

@Test
@DisplayName("1.7 Добавить сотрудника с полем, не допускающим значение NULL, но равен NULL в теле запроса")
//Пришлось хардкодить тело запроса, ибо через XClientsWebClient с пустым firstName фeйлится сам тест
public void addEmployeeErrorNull(){

    String requestBodyEmployee = "{\"firstName\": \"\",\n" + "  \"lastName\": \"Bender\",\n" +
            " \"middleName\": \"string\",\n" + " \"companyId\": "+ idCompany +", \n" +
            " \"email\": \"leobeo@mail.ru\",\n" + "  \"url\": \"string\",\n" +
            " \"phone\": \"19875513\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
            " \"isActive\": true}";

    given().log().all()
            .body(requestBodyEmployee)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().post(XClientsWebClient.URL_POST_EMPLOYEE)
            .then()
            .statusCode(400);
}

@Test
@DisplayName("1.8 Добавить сотрудника с использованием несуществующего адреса URL")
public void addEmployeeErrorURL(){

    String requestBodyEmployee = "{\"firstName\": \"Leo\",\n" + "  \"lastName\": \"Bender\",\n" +
            " \"middleName\": \"string\",\n" + " \"companyId\": "+ idCompany +", \n" +
            " \"email\": \"leobeo@mail.ru\",\n" + "  \"url\": \"string\",\n" +
            " \"phone\": \"19875513\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
            " \"isActive\": true}";

             given().log().all()
            .body(requestBodyEmployee)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().post("https://z-clients-be.onrender.com/employee")
            .then()
            .statusCode(404);
}

    @Test
    @DisplayName("1.9 Добавить сотрудника c пустым JSON-объектом")
    public void addEmployeeEmptyJSON(){

        String requestBodyEmployee = "{ }";

        given().log().all()
                .body(requestBodyEmployee)
                .contentType(ContentType.JSON)
                .header("x-client-token", TOKEN)
                .when().post(XClientsWebClient.URL_POST_EMPLOYEE)
                .then()
                .statusCode(500);
    }

@Test
@DisplayName("2.1 Изменить информацию во всех доступных полях на валидные значения")
public void changeAllEmployee(){

    String requestBodyEmployeeEdit = "{\"firstName\": \"Ivan\",\n" + "  \"lastName\": \"Ivanov\",\n" +
            " \"middleName\": \"Ivanovich\",\n" + " \"companyId\": "+ idCompany +", \n" +
            " \"email\": \"ivanov1987@rambler.ru\",\n" + "  \"url\": \"string\",\n" +
            " \"phone\": \"2533998\",\n" + "\"birthdate\": \"2024-03-17T23:42:04.438Z\",\n" +
            " \"isActive\": false}";

    int idChangeEmployee = given().log().all()
            .body(requestBodyEmployeeEdit)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().patch(XClientsWebClient.URL_POST_EMPLOYEE + "/" + idEmployeeCommon)
            .then().log().all()
            .statusCode(200)
            .extract().path("id");

    EmployeeEntity emp2 = entityManager.find(EmployeeEntity.class, idChangeEmployee);
    assertEquals(idChangeEmployee, emp2.getId());
    assertEquals("Ivan", emp2.getFirstName());
    assertEquals("Ivanov", emp2.getLastName());
    assertEquals("Ivanovich", emp2.getMiddleName());
    assertEquals("ivanov@rambler.ru", emp2.getEmail());
    assertEquals("2533998", emp2.getPhone());
    assertEquals("false", emp2.isActive());
}

@Test
@DisplayName("2.2 Изменить пустое значение на валидное значение")
public void changeEmptyEmployee(){

    String requestBodyEmployeeEdit = "{\"url\": \"www.memes.ru\"}";

   int idChangeEmployee = given().log().all()
            .body(requestBodyEmployeeEdit)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().patch(XClientsWebClient.URL_POST_EMPLOYEE + "/" + idEmployeeCommon)
            .then().log().all()
            .statusCode(200)
            .extract().path("id");

    EmployeeEntity emp2 = entityManager.find(EmployeeEntity.class, idChangeEmployee);
    assertEquals(idChangeEmployee, emp2.getId());
    assertEquals("www.memes.ru", emp2.getAvatarurl());
}

@Test
@DisplayName("2.3 Изменить значение на Null там, где это недопустимо")
public void changeNullEmployee(){

    String requestBodyEmployeeEdit = "{\"firstName\": \"\"}";

   int idChangeEmployee = given().log().all()
            .body(requestBodyEmployeeEdit)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().patch(XClientsWebClient.URL_POST_EMPLOYEE + "/" + idEmployeeCommon)
            .then().log().all()
            .statusCode(200)
           .extract().path("id");

    given().log().all()
            .get(XClientsWebClient.URL_GET_EMPLOYEEID + idChangeEmployee)
            .then().log().all()
            .statusCode(200)
            .body("firstName", is(notNullValue()));
}

@Test
@DisplayName("2.4 Изменить значение, недоступное для изменения")
public void changeUnavailableEmployee(){

    String requestBodyEmployeeEdit = "{\"companyId\": \"0\"}";

    int idChangeEmployee = given().log().all()
            .body(requestBodyEmployeeEdit)
            .contentType(ContentType.JSON)
            .header("x-client-token", TOKEN)
            .when().patch(XClientsWebClient.URL_POST_EMPLOYEE + "/" + idEmployeeCommon)
            .then().log().all()
            .statusCode(200)
            .extract().path("id");

    given().log().all()
            .get(XClientsWebClient.URL_GET_EMPLOYEEID + idChangeEmployee)
            .then().log().all()
            .statusCode(200)
            .body("companyId", equalTo(idCompany));
}

}


