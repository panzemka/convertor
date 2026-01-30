package converter.converter;

import java.io.File;

public interface DataConverter {
    void convert(File input, File output) throws Exception;
}