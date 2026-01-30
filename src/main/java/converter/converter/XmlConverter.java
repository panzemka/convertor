package converter.converter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.util.Map;

public abstract class XmlConverter implements DataConverter {

    protected final XmlMapper xmlMapper = new XmlMapper();

    protected Map<String, Object> readXml(File input) throws Exception {
        return xmlMapper.readValue(input, Map.class);
    }

    protected void writeXml(Map<String, Object> data, File output) throws Exception {
        xmlMapper.writerWithDefaultPrettyPrinter()
                .writeValue(output, data);
    }
}