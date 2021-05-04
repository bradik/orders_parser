package com.example.orders.parser.factory;

import com.example.orders.parser.constats.FileType;

public interface FactoryLocator {
    FileType getType();
}
