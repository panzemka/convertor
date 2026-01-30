package converter.converter;

import java.io.File;
import java.util.Map;

public class XmlToJsonConverter extends XmlConverter {

    @Override
    public void convert(File input, File output) throws Exception {
        Map<String, Object> data = readXml(input);

        JsonConverter jsonConverter = new JsonConverter() {
            @Override
            public void convert(File input, File output) throws Exception {
            }
        };
        jsonConverter.writeJson(data, output);
    }
}