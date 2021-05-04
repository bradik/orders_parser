package com.example.orders.parser.factory;

import com.example.orders.parser.reader.FileReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileReaderFactory extends BaseFactory<FileReader> {

    public FileReaderFactory(List<FileReader> beans) {
        super(beans);
    }

    @Override
    String getName() {
        return "FileReader";
    }
}
