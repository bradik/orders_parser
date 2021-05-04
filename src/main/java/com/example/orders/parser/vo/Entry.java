package com.example.orders.parser.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Entry {

    /**
     * идентификатор ордера
     */
    @JsonProperty("orderId")
    long id;

    /**
     * сумма ордера
     */
    BigDecimal amount;

    /**
     * Валюта суммы ордера
     */
    String currency;

    /**
     * комментарий по ордеру
     */
    String comment;

    /**
     * имя исходного файла
     */
    String filename;

    /**
     *  номер строки исходного файла
     */
    long line;

    /**
     * результат парсинга записи исходного файла.<br/>
     * OK - если запись конвертирована корректно или описание ошибки если запись не удалось конвертировать
     */
    String result;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", comment='" + comment + '\'' +
                ", filename='" + filename + '\'' +
                ", line=" + line +
                ", result=" + result +
                '}';
    }
}

