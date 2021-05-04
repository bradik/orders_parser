package com.example.orders.parser.сonverter;

import com.example.orders.parser.vo.Entry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class JsonConverterImpl extends Converter {

    private final ObjectMapper objectMapper;

    public JsonConverterImpl(TaskExecutor taskExecutor, ObjectMapper objectMapper) {
        super(taskExecutor);
        this.objectMapper = objectMapper;
    }

    @Override
    protected Entry map(String fileName, long lineNumber, String line) {
        String result = "ok";

        Entry entry = null;
        try {
            entry = objectMapper.readValue(line, Entry.class);
        } catch (JsonProcessingException e) {
            result = e.getMessage();
        }
        if (entry == null) {
            entry = new Entry();
        }

        entry.setLine(lineNumber);
        entry.setFilename(fileName);
        entry.setResult(result);

        return entry;
    }
}

