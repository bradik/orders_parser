package com.example.orders.parser.сonverter;

import com.example.orders.parser.vo.Entry;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.springframework.core.task.TaskExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public abstract class Converter {

    private final List<CompletableFuture<Entry>> futures = new CopyOnWriteArrayList<>();

    private final Executor executor;

    public Converter(TaskExecutor taskExecutor) {
        this.executor = taskExecutor;
    }

    /**
     * Добавить строку из файла в очередь конвертирования
     * @param fileName имя файла
     * @param lineNumber номер строки
     * @param line строка для конвертации
     */
    @Synchronized
    @SneakyThrows
    public void add(String fileName, long lineNumber, String line) {
        CompletableFuture<Entry> future = CompletableFuture.supplyAsync(() -> map(fileName, lineNumber, line), executor);
        futures.add(future);
    }

    /**
     * Получить дождаться выполнения результата и получить сконвертированные данные
     * @return Stream<Entry> сконвертированные данные
     */
    @Synchronized
    @SneakyThrows
    public Stream<Entry> get() {
        Stream<Entry> entryStream = CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(aVoid -> futures.stream().map(CompletableFuture::join))
                .get(1, TimeUnit.MINUTES);

        futures.clear();

        return entryStream;
    }

    protected abstract Entry map(String fileName, long lineNumber, String line);

}
