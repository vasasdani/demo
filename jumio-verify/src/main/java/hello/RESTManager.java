package hello;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

import static hello.RequestValidation.KEY;
import static hello.RequestValidation.VALUE;

@RestController
public class RESTManager {

    @RequestMapping(value = "/api/image")
    ResponseEntity<Object> apiImage(@RequestBody JSONObject jsonObject) {
        String imageValue = jsonObject.get(KEY).toString();

        if (!isBase64Encoded(imageValue)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Base64 encoded value for the given key: " + KEY);
        }
        if (!isCodedValueAsIsExpected(imageValue)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect value for the given key: " + KEY);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    private boolean isBase64Encoded(String imageValue) {
        Pattern regex = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$");
        return regex.matcher(imageValue).find();
    }

    private boolean isCodedValueAsIsExpected(String imageValue) {
        return encodeBase64ToString(VALUE).equals(imageValue);
    }

    private String encodeBase64ToString(String value) {
        return new String(Base64.getEncoder().encode(value.getBytes(StandardCharsets.UTF_8)));
    }

}
