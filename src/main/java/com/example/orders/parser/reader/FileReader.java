package com.example.orders.parser.reader;

import com.codepoetics.protonpack.StreamUtils;
import com.example.orders.parser.vo.Entry;
import com.example.orders.parser.сonverter.Converter;
import com.example.orders.parser.factory.FactoryLocator;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class FileReader implements FactoryLocator {

    public FileReader(Converter converter) {
        this.converter = converter;
    }

    private Converter converter;

    /**
     * Прочитать файл с данными и выполнить конвертацию всех его строк
     * @param file файл
     * @return Stream<Entry> результат конвертации данных
     */
    @SneakyThrows
    synchronized public Stream<Entry> read(Path file) {
        final String fileName = file.getFileName().toString();

        Stream<String> strings = Files.lines(file);
        StreamUtils
                .zipWithIndex(strings)
                .forEach(stringIndexed ->
                        converter.add(fileName, stringIndexed.getIndex() + 1, stringIndexed.getValue())
                );
        strings.close();//обязательно закрываем

        return converter.get();
    }
}
