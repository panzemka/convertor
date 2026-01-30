package converter.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;

public abstract class JsonConverter implements DataConverter {

    protected final ObjectMapper jsonMapper = new ObjectMapper();

    protected Map<String, Object> readJson(File input) throws Exception {
        return jsonMapper.readValue(input, Map.class);
    }

    protected void writeJson(Map<String, Object> data, File output) throws Exception {
        jsonMapper.writerWithDefaultPrettyPrinter()
                .writeValue(output, data);
    }
}