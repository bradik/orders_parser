package com.example.orders.parser.сonverter;

import com.example.orders.parser.vo.Entry;
import lombok.Synchronized;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CsvConverterImpl extends Converter {

    public CsvConverterImpl(TaskExecutor taskExecutor) {
        super(taskExecutor);
    }

    @Override
    @Synchronized
    protected Entry map(String fileName, long lineNumber, String line) {
        //1,100,USD,оплата заказа
        String[] values = line.split(",");


        StringBuilder errors = new StringBuilder();
        long orderId = parseLong(values[0], "не верный формат поля 'orderId'", errors);
        BigDecimal amount = BigDecimal.valueOf(parseLong(values[1], "не верный формат поля 'amount'", errors));

        String currency = values[2];
        String comment = values[3];

        Entry entry = new Entry();
        entry.setFilename(fileName);
        entry.setLine(lineNumber);

        entry.setId(orderId);
        entry.setAmount(amount);
        entry.setCurrency(currency);
        entry.setComment(comment);

        String result = errors.length() == 0 ? "ok" : errors.toString();
        entry.setResult(result);

        return entry;
    }

    private long parseLong(String value, String msg, StringBuilder errors) {
        long result = 0;
        try {
            result = Long.parseLong(value);
        } catch (NumberFormatException ex) {
            if (errors.length() != 0) {
                errors.append(";");
            }
            errors.append(msg);
        }
        return result;
    }

}
