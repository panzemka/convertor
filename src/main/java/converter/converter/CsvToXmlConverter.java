package converter.converter;

import java.io.File;
import java.util.*;

public class CsvToXmlConverter extends CsvConverter {

    @Override
    public void convert(File input, File output) throws Exception {
        List<Map<String, String>> csvData = readCsv(input);
        Map<String, Object> xmlData = convertCsvToXml(csvData);

        XmlConverter xmlConverter = new XmlConverter() {
            @Override
            public void convert(File input, File output) throws Exception {
            }
        };
        xmlConverter.writeXml(xmlData, output);
    }

    private Map<String, Object> convertCsvToXml(List<Map<String, String>> csvData) {
        Map<String, Object> result = new HashMap<>();

        if (csvData.isEmpty()) {
            result.put("empty", true);
            return result;
        }

        if (csvData.size() == 1) {
            result.putAll(csvData.get(0));
        } else {
            Map<String, Object> items = new HashMap<>();
            for (int i = 0; i < csvData.size(); i++) {
                items.put("item" + (i + 1), csvData.get(i));
            }
            result.put("items", items);
        }

        return result;
    }
}