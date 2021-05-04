package com.example.orders.parser.сonverter;

import com.example.orders.parser.vo.Entry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterImplTest {

    private JsonConverterImpl converter = new JsonConverterImpl(null, new ObjectMapper());

    @Test
    void map() {
        String line = "{\"orderId\":3,\"amount\":1.23,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}";

        Entry expected = getEntry(3L, BigDecimal.valueOf(1.23), "USD", "оплата заказа", "ok");

        expected.setFilename("test.json");
        expected.setLine(1L);

        Entry actual = converter.map("test.json", 1, line);

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> mapNegativeProvider() {
        String result1 = "Unexpected character ('!' (code 33)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n" +
                " at [Source: (String)\"{\"orderId\":!3,\"amount\":1.23,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}\"; line: 1, column: 13]";
        String result2 = "Unexpected character ('!' (code 33)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n" +
                " at [Source: (String)\"{\"orderId\":3,\"amount\":!1.23,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}\"; line: 1, column: 24]";
        return Stream.of(
                Arguments.of(
                        getEntry(0L, null, null, null, result1),
                        "{\"orderId\":!3,\"amount\":1.23,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}"
                ),
                Arguments.of(
                        getEntry(0L, null, null, null, result2),
                        "{\"orderId\":3,\"amount\":!1.23,\"currency\":\"USD\",\"comment\":\"оплата заказа\"}"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("mapNegativeProvider")
    void mapNegative(Entry expected, String line) {

        expected.setFilename("test.json");
        expected.setLine(1L);

        Entry actual = converter.map("test.json", 1, line);

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