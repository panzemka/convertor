package converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.*;

public class Converter {
    
    public void convert(String inputPath, String outputPath) throws Exception {
        String inputExt = getExtension(inputPath).toLowerCase();
        String outputExt = getExtension(outputPath).toLowerCase();
        
        System.out.println("Converting from " + inputExt + " to " + outputExt);
        
        switch (inputExt + "->" + outputExt) {
            case "csv->json":
                csvToJson(inputPath, outputPath);
                break;
            case "csv->xml":
                csvToXml(inputPath, outputPath);
                break;
            case "json->csv":
                jsonToCsv(inputPath, outputPath);
                break;
            case "json->xml":
                jsonToXml(inputPath, outputPath);
                break;
            case "xml->csv":
                xmlToCsv(inputPath, outputPath);
                break;
            case "xml->json":
                xmlToJson(inputPath, outputPath);
                break;
            default:
                throw new IllegalArgumentException("Unsupported conversion");
        }
    }
    
    private void csvToJson(String input, String output) throws Exception {
        List<Map<String, String>> data = readCsv(input);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(output), data);
        System.out.println("CSV -> JSON: " + data.size() + " records converted");
    }
    
    private void csvToXml(String input, String output) throws Exception {
        List<Map<String, String>> data = readCsv(input);
        XmlMapper mapper = new XmlMapper();
        Map<String, Object> xmlData = new HashMap<>();
        xmlData.put("records", data);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(output), xmlData);
        System.out.println("CSV -> XML: " + data.size() + " records converted");
    }
    
    private void jsonToCsv(String input, String output) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> data = mapper.readValue(new File(input), List.class);
        writeCsv(data, output);
        System.out.println("JSON -> CSV: " + data.size() + " records converted");
    }
    
    private void jsonToXml(String input, String output) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        Object data = jsonMapper.readValue(new File(input), Object.class);
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(output), data);
        System.out.println("JSON -> XML: conversion successful");
    }
    
    private void xmlToCsv(String input, String output) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, Object> xmlData = xmlMapper.readValue(new File(input), Map.class);
        
        List<Map<String, String>> data = new ArrayList<>();
        if (xmlData.containsKey("records")) {
            Object records = xmlData.get("records");
            if (records instanceof List) {
                data = (List<Map<String, String>>) records;
            }
        }
        
        if (data.isEmpty() && !xmlData.isEmpty()) {
            Map<String, String> singleRecord = new HashMap<>();
            for (Map.Entry<String, Object> entry : xmlData.entrySet()) {
                singleRecord.put(entry.getKey(), entry.getValue().toString());
            }
            data.add(singleRecord);
        }
        
        writeCsv(data, output);
        System.out.println("XML -> CSV: " + data.size() + " records converted");
    }
    
    private void xmlToJson(String input, String output) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();
        Object data = xmlMapper.readValue(new File(input), Object.class);
        jsonMapper.writerWithDefaultPrettyPrinter().writeValue(new File(output), data);
        System.out.println("XML -> JSON: conversion successful");
    }
    
    private List<Map<String, String>> readCsv(String filePath) throws Exception {
        List<Map<String, String>> data = new ArrayList<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext();
            if (headers == null) return data;
            
            String[] row;
            while ((row = reader.readNext()) != null) {
                Map<String, String> record = new HashMap<>();
                for (int i = 0; i < headers.length && i < row.length; i++) {
                    record.put(headers[i].trim(), row[i].trim());
                }
                data.add(record);
            }
        }
        
        return data;
    }
    
    private void writeCsv(List<Map<String, String>> data, String filePath) throws Exception {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No data to write");
        }
        
        Set<String> headers = new LinkedHashSet<>();
        for (Map<String, String> row : data) {
            headers.addAll(row.keySet());
        }
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
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
    
    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            throw new IllegalArgumentException("File has no extension: " + filename);
        }
        return filename.substring(dotIndex + 1);
    }
}