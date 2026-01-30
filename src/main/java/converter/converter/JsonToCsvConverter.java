package converter.converter;

import java.io.File;
import java.util.*;

public class JsonToCsvConverter extends JsonConverter {

    @Override
    public void convert(File input, File output) throws Exception {
        Map<String, Object> data = readJson(input);
        List<Map<String, String>> csvData = convertJsonToCsv(data);

        CsvConverter csvConverter = new CsvConverter() {
            @Override
            public void convert(File input, File output) throws Exception {
            }
        };
        csvConverter.writeCsv(csvData, output);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, String>> convertJsonToCsv(Map<String, Object> jsonData) {
        List<Map<String, String>> result = new ArrayList<>();

        for (Map.Entry<String, Object> entry : jsonData.entrySet()) {
            if (entry.getValue() instanceof List) {
                List<?> list = (List<?>) entry.getValue();
                for (Object item : list) {
                    if (item instanceof Map) {
                        Map<String, String> row = new HashMap<>();
                        Map<?, ?> itemMap = (Map<?, ?>) item;
                        for (Map.Entry<?, ?> field : itemMap.entrySet()) {
                            row.put(field.getKey().toString(),
                                    field.getValue() != null ? field.getValue().toString() : "");
                        }
                        result.add(row);
                    }
                }
            } else if (entry.getValue() instanceof Map) {
                Map<String, String> row = new HashMap<>();
                Map<?, ?> nestedMap = (Map<?, ?>) entry.getValue();
                for (Map.Entry<?, ?> field : nestedMap.entrySet()) {
                    row.put(field.getKey().toString(),
                            field.getValue() != null ? field.getValue().toString() : "");
                }
                result.add(row);
            } else {
                Map<String, String> row = new HashMap<>();
                row.put(entry.getKey(), entry.getValue().toString());
                result.add(row);
            }
        }

        return result;
    }
}