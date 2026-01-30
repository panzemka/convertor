package converter.converter;

import java.io.File;
import java.util.*;

public class CsvToJsonConverter extends CsvConverter {

    @Override
    public void convert(File input, File output) throws Exception {
        List<Map<String, String>> csvData = readCsv(input);
        Map<String, Object> jsonData = convertCsvToJson(csvData);

        JsonConverter jsonConverter = new JsonConverter() {
            @Override
            public void convert(File input, File output) throws Exception {
            }
        };
        jsonConverter.writeJson(jsonData, output);
    }

    private Map<String, Object> convertCsvToJson(List<Map<String, String>> csvData) {
        Map<String, Object> result = new HashMap<>();

        if (csvData.size() == 1) {
            result.putAll(csvData.get(0));
        } else {
            result.put("data", csvData);
        }

        return result;
    }
}