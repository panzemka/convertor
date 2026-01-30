package converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    void testJsonToXmlConversion() throws Exception {
        String jsonContent = """
            {
                "person": {
                    "name": "John Doe",
                    "age": 30,
                    "email": "john@example.com"
                }
            }
            """;
        
        Path jsonFile = tempDir.resolve("test.json");
        Files.writeString(jsonFile, jsonContent);
        
        Path xmlFile = tempDir.resolve("test.xml");
        
        Converter converter = new Converter();
        converter.convert(jsonFile.toString(), xmlFile.toString());
        
        assertTrue(Files.exists(xmlFile));
        String xmlContent = Files.readString(xmlFile);
        
        assertTrue(xmlContent.contains("<person>"));
        assertTrue(xmlContent.contains("<name>John Doe</name>"));
        assertTrue(xmlContent.contains("<age>30</age>"));
    }
    
    @Test
    void testCsvToJsonConversion() throws Exception {
        String csvContent = """
            name,age,city
            John,30,New York
            Alice,25,London
            """;
        
        Path csvFile = tempDir.resolve("test.csv");
        Files.writeString(csvFile, csvContent);
        
        Path jsonFile = tempDir.resolve("test.json");
        
        Converter converter = new Converter();
        converter.convert(csvFile.toString(), jsonFile.toString());
        
        assertTrue(Files.exists(jsonFile));
        String jsonContent = Files.readString(jsonFile);
        
        assertTrue(jsonContent.contains("\"name\" : \"John\""));
        assertTrue(jsonContent.contains("\"age\" : \"30\""));
        assertTrue(jsonContent.contains("\"city\" : \"New York\""));
    }
    
    @Test
    void testInvalidFileExtension() {
        Converter converter = new Converter();
        Path inputFile = tempDir.resolve("test.txt");
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(inputFile.toString(), "output.json");
        });
    }
    
    @Test
    void testSameFormatConversion() {
        Converter converter = new Converter();
        Path jsonFile = tempDir.resolve("test.json");
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(jsonFile.toString(), "output.json");
        });
    }
    
    @Test
    void testNonExistentInputFile() {
        Converter converter = new Converter();
        
        assertThrows(Exception.class, () -> {
            converter.convert("nonexistent.json", "output.xml");
        });
    }
}