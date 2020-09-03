package am.test.crud.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String serialize(Object obj) {
        String value = "";
        try {
            value = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("ERROR:", e);
        }
        return value;
    }

    public static <T> T deserialize(String payload, TypeReference<T> type) throws IOException {
        return objectMapper.readValue(payload, type);
    }
}
