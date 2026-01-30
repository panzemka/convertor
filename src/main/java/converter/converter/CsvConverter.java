package converter.converter;

import java.io.File;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public abstract class CsvConverter implements DataConverter {

    protected List<Map<String, String>> readCsv(File input) throws Exception {
        List<Map<String, String>> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(input))) {
            String[] headers = reader.readNext();
            if (headers == null) {
                throw new IllegalArgumentException("CSV file is empty");
            }

            for (int i = 0; i < headers.length; i++) {
                headers[i] = headers[i].trim();
            }

            String[] row;
            while ((row = reader.readNext()) != null) {
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < headers.length && i < row.length; i++) {
                    record.put(headers[i], row[i].trim());
                }
                data.add(record);
            }
        }

        return data;
    }

    protected void writeCsv(List<Map<String, String>> data, File output) throws Exception {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No data to write to CSV");
        }

        Set<String> headers = new LinkedHashSet<>();
        for (Map<String, String> row : data) {
            headers.addAll(row.keySet());
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(output))) {
            writer.writeNext(headers.toArray(new String[0]));

            for (Map<String, String> row : data) {
                String[] rowData = new String[headers.size()];
                int i = 0;
                for (String header : headers) {
                    rowData[i++] = row.getOrDefault(header, "");
                }
                writer.writeNext(rowData);
            }
        }
    }

    protected Map<String, Object> convertCsvToMap(List<Map<String, String>> csvData) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", csvData);
        return result;
    }

    @SuppressWarnings("unchecked")
    protected List<Map<String, String>> convertMapToCsv(Map<String, Object> data) {
        Object dataObj = data.get("data");
        if (dataObj instanceof List) {
            return (List<Map<String, String>>) dataObj;
        }
        return new ArrayList<>();
    }
}