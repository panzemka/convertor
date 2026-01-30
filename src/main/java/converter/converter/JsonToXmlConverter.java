package converter.converter;

import java.io.File;
import java.util.Map;

public class JsonToXmlConverter extends JsonConverter {

    @Override
    public void convert(File input, File output) throws Exception {
        Map<String, Object> data = readJson(input);

        XmlConverter xmlConverter = new XmlConverter() {
            @Override
            public void convert(File input, File output) throws Exception {
            }
        };
        xmlConverter.writeXml(data, output);
    }
}