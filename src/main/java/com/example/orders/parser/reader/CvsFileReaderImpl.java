package com.example.orders.parser.reader;

import com.example.orders.parser.constats.FileType;
import com.example.orders.parser.сonverter.CsvConverterImpl;
import org.springframework.stereotype.Component;

@Component
public class CvsFileReaderImpl extends FileReader {

    public CvsFileReaderImpl(CsvConverterImpl convector) {
        super(convector);
    }

    @Override
    public FileType getType() {
        return FileType.csv;
    }
}
