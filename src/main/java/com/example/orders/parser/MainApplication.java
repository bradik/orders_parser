package com.example.orders.parser;

import com.example.orders.parser.constats.FileType;
import com.example.orders.parser.vo.Entry;
import com.example.orders.parser.factory.FileReaderFactory;
import com.example.orders.parser.reader.FileReader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainApplication implements ApplicationRunner {

    private final Executor executor;
    private final FileReaderFactory fileReaderFactory;


    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<String> files = args.getNonOptionArgs();

        List<CompletableFuture<Stream<Entry>>> futures = new ArrayList<>();

        for (String file : files) {
            Path path = Paths.get(file);
            if (path.toFile().exists()) {
                FileReader factory = fileReaderFactory.findFactory(getFileType(file));
                futures.add(CompletableFuture.supplyAsync(() -> factory.read(path), executor));
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]))
                .thenApply(aVoid -> futures.stream().map(CompletableFuture::join))
                .get(1, TimeUnit.MINUTES)
        .flatMap(Function.identity())
        .forEach(System.out::println);

        System.exit(0);
    }

    private FileType getFileType(String file) {
        return FileType.valueOf(FilenameUtils.getExtension(file));
    }
}

