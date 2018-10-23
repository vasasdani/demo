package hello;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static hello.RequestValidation.KEY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ApiRestController.class)
public class ApiRestControllerTest {

    @Autowired
    private MockMvc mvc;
    private JSONObject jsonObject;

    @Before
    public void beforeTest(){
        jsonObject = new JSONObject();
    }

    @Test
    public void apiTestWithCorrectValue() throws Exception {
        jsonObject.put(KEY, "dGVzdA==");
        this.mvc.perform(post("/api/image").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toJSONString())).andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void apiTestWithIncorrectValue() throws Exception {
        jsonObject.put(KEY, "MTIzNA==");
        this.mvc.perform(post("/api/image").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toJSONString())).andExpect(status().is4xxClientError())
                .andExpect(content().string("Incorrect value for the given key: " + KEY));
    }

    @Test
    public void apiTesNotBase64EncodedValue() throws Exception {
        jsonObject.put(KEY, "*@&@$=");
        this.mvc.perform(post("/api/image").contentType(MediaType.APPLICATION_JSON).content(jsonObject.toJSONString())).andExpect(status().is4xxClientError())
                .andExpect(content().string("Not Base64 encoded value for the given key: " + KEY));
    }

}