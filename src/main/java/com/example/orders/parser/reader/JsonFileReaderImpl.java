package com.example.orders.parser.reader;

import com.example.orders.parser.constats.FileType;
import com.example.orders.parser.сonverter.JsonConverterImpl;
import org.springframework.stereotype.Component;

@Component
public class JsonFileReaderImpl extends FileReader {

    public JsonFileReaderImpl(JsonConverterImpl convector) {
        super(convector);
    }

    @Override
    public FileType getType() {
        return FileType.json;
    }
}
