package com.example.orders.parser.сonverter;

import com.example.orders.parser.vo.Entry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CsvConverterImplTest {

    private CsvConverterImpl converter = new CsvConverterImpl(null);

    @Test
    void map() {

        String line = "1,100,USD,оплата заказа";

        Entry expected = getEntry(1L, BigDecimal.valueOf(100), "USD", "оплата заказа", "ok");

        expected.setFilename("test.csv");
        expected.setLine(1L);

        Entry actual = converter.map("test.csv", 1, line);

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> mapNegativeProvider() {
        return Stream.of(
                Arguments.of(
                        getEntry(0L, BigDecimal.valueOf(100), "USD", "оплата заказа", "не верный формат поля 'orderId'"),
                        "!1,100,USD,оплата заказа"
                ),
                Arguments.of(
                        getEntry(1L, BigDecimal.ZERO, "USD", "оплата заказа", "не верный формат поля 'amount'"),
                        "1,!100,USD,оплата заказа"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("mapNegativeProvider")
    void mapNegative(Entry expected, String line) {

        expected.setFilename("test.csv");
        expected.setLine(1L);

        Entry actual = converter.map("test.csv", 1, line);

        assertEquals(expected, actual);
    }

    private static Entry getEntry(long id, BigDecimal amount, String currency, String comment, String ok) {
        Entry expected = new Entry();
        expected.setId(id);
        expected.setAmount(amount);
        expected.setCurrency(currency);
        expected.setComment(comment);
        expected.setResult(ok);
        return expected;
    }
}