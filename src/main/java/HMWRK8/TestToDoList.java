package HMWRK8;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestToDoList {

    private static final String URL = "https://todo-app-sky.herokuapp.com/";

    @Test
    @DisplayName("Проверяем ополучение списка задач")
    public void methodGet() throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL);
        HttpResponse response = client.execute(request);
        String body = EntityUtils.toString(response.getEntity());
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("application/json; charset=utf-8", response.getHeaders("Content-Type")[0].getValue());
       }

    @Test
    @DisplayName("Провеоряем возможность создать задачу")
    public void methodPost() throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost requestPost = new HttpPost(URL);
        String newTask = "{\"title\": \"исследовать метод POST\", \"completed\": false}";
        StringEntity entity = new StringEntity(newTask, ContentType.APPLICATION_JSON);
        requestPost.setEntity(entity);
        HttpResponse response = client.execute(requestPost);
        String responseBody = EntityUtils.toString(response.getEntity());
        assertEquals(201, response.getStatusLine().getStatusCode());
        assertTrue(responseBody.contains("исследовать метод POST"));
        }

    @Test
    @DisplayName("Проверяем, можно ли создать пустую задачу")
    //Предположим, что нельзя и должно быть 400
    public void methodPost2() throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost requestPost = new HttpPost(URL);
        String newTask = "{\"title\": \" \", \"completed\": false}";
        StringEntity entity = new StringEntity(newTask, ContentType.APPLICATION_JSON);
        requestPost.setEntity(entity);
        HttpResponse response = client.execute(requestPost);
        String responseBody = EntityUtils.toString(response.getEntity());
        assertEquals(400, response.getStatusLine().getStatusCode());
          }

    @Test
    @DisplayName("Проверяем возможность переименования записи")
    public void methodPatch() throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost requestPost = new HttpPost(URL);
        String newTask = "{\"title\": \"исследовать метод POST и GET\", \"completed\": false}";
        StringEntity entity = new StringEntity(newTask, ContentType.APPLICATION_JSON);
        requestPost.setEntity(entity);
        HttpResponse response = client.execute(requestPost);
        String responseBody = EntityUtils.toString(response.getEntity());
        String id = responseBody.substring(6,12);

        HttpPatch renamePatch = new HttpPatch(URL + "/" + id);
        String newTask2 = "{\"title\": \"исследовать метод POST и GET и проверить изменение задачи\", \"completed\": false}";
        StringEntity entity2 = new StringEntity(newTask2, ContentType.APPLICATION_JSON);
        renamePatch.setEntity(entity2);
        HttpResponse response2 = client.execute(renamePatch);
        String responseBody2 = EntityUtils.toString(response2.getEntity());
        assertEquals(200, response2.getStatusLine().getStatusCode());
        assertTrue(responseBody2.contains("исследовать метод POST и GET и проверить изменение задачи"));
}

@Test
@DisplayName("Проверяем возможность удаления записи")
public void methodDelete() throws IOException {

    HttpClient client = HttpClientBuilder.create().build();
    HttpPost requestPost = new HttpPost(URL);
    String newTask = "{\"title\": \"исследовать метод POST и GET и DELETE\", \"completed\": false}";
    StringEntity entity = new StringEntity(newTask, ContentType.APPLICATION_JSON);
    requestPost.setEntity(entity);
    HttpResponse response = client.execute(requestPost);
    String responseBody = EntityUtils.toString(response.getEntity());
    String id = responseBody.substring(6,12);

    HttpDelete requestDel = new HttpDelete(URL + "/" + id);
    HttpResponse response2 = client.execute(requestDel);
    assertEquals(204, response2.getStatusLine().getStatusCode());
}

@Test
@DisplayName("Проверяем отметку о выполнении задачи")
public void methodPatchCompleted() throws IOException {

    HttpClient client = HttpClientBuilder.create().build();
    HttpPost requestPost = new HttpPost(URL);
    String newTask = "{\"title\": \"Проверим отметку выполнения задачи\", \"completed\": false}";
    StringEntity entity = new StringEntity(newTask, ContentType.APPLICATION_JSON);
    requestPost.setEntity(entity);
    HttpResponse response = client.execute(requestPost);
    String responseBody = EntityUtils.toString(response.getEntity());
    String id = responseBody.substring(6,12);

    HttpPatch completedTask = new HttpPatch(URL + "/" + id);
    String newTask2 = "{\"completed\": true}";
    StringEntity entity2 = new StringEntity(newTask2, ContentType.APPLICATION_JSON);
    completedTask.setEntity(entity2);
    HttpResponse response2 = client.execute(completedTask);
    String responseBody2 = EntityUtils.toString(response2.getEntity());
        assertTrue(responseBody2.contains("\"completed\":true"));
}

}


