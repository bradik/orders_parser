package com.example.orders.parser.factory;

import com.example.orders.parser.constats.FileType;

import java.util.List;

public abstract class BaseFactory <T extends FactoryLocator> {
    private List<T> beans;

    public BaseFactory(List<T> beans) {
        this.beans = beans;
    }

    public T findFactory(FileType type) {
        return beans.stream()
                .filter(t -> type == t.getType())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(getName() + " for " + type + " not found"));
    }

    abstract String getName();
}
