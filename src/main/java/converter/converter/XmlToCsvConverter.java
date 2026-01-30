package converter.converter;

import java.io.File;
import java.util.*;

public class XmlToCsvConverter extends XmlConverter {

    @Override
    public void convert(File input, File output) throws Exception {
        Map<String, Object> data = readXml(input);
        List<Map<String, String>> csvData = convertXmlToCsv(data);

        CsvConverter csvConverter = new CsvConverter() {
            @Override
            public void convert(File input, File output) throws Exception {
            }
        };
        csvConverter.writeCsv(csvData, output);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, String>> convertXmlToCsv(Map<String, Object> xmlData) {
        List<Map<String, String>> result = new ArrayList<>();
        convertXmlNodeToCsv("", xmlData, result, new HashMap<>());
        return result;
    }

    @SuppressWarnings("unchecked")
    private void convertXmlNodeToCsv(String prefix, Object node,
                                     List<Map<String, String>> result,
                                     Map<String, String> currentRow) {

        if (node instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) node;

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();

                if (entry.getValue() instanceof Map || entry.getValue() instanceof List) {
                    convertXmlNodeToCsv(key, entry.getValue(), result, currentRow);
                } else {
                    currentRow.put(key, entry.getValue() != null ? entry.getValue().toString() : "");
                }
            }

            if (!currentRow.isEmpty() && !hasNestedStructures(map)) {
                result.add(new HashMap<>(currentRow));
                currentRow.clear();
            }

        } else if (node instanceof List) {
            List<?> list = (List<?>) node;
            for (Object item : list) {
                convertXmlNodeToCsv(prefix, item, result, currentRow);
            }
        } else {
            currentRow.put(prefix, node != null ? node.toString() : "");
        }
    }

    private boolean hasNestedStructures(Map<String, Object> map) {
        for (Object value : map.values()) {
            if (value instanceof Map || value instanceof List) {
                return true;
            }
        }
        return false;
    }
}