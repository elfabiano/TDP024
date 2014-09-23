package tdp024.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

public class TodoJsonSerializerImpl implements TodoJsonSerializer {

    private TodoLogger todoLogger = new TodoLoggerImpl();
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonMappingException e) {
            todoLogger.log(e);
            return null;
        } catch (IOException e) {
            todoLogger.log(e);
            return null;
        }

    }

    @Override
    public String toJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            todoLogger.log(e);
            return null;
        }
    }
}
