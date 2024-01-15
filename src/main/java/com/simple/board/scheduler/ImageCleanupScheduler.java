package com.simple.board.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.EnumSet;

@Component
@Slf4j
public class ImageCleanupScheduler {

    private static final Path FOLDER_PATH = Paths.get("storage/upload/temp");
    private static final int DAYS_TO_KEEP = 1;
    
    @Scheduled(cron = "0 0 0 * * *")//매일 자정에 실행
    public void cleanUpTempImages() throws Exception{
        log.info("cleanUpTemImages 스케쥴러 실행됨");
        Files.walkFileTree(FOLDER_PATH, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                LocalDateTime lastModifiedTime = LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault());

                if (lastModifiedTime.isBefore(LocalDateTime.now().minusMinutes(DAYS_TO_KEEP))) {
                    Files.delete(file);
                    log.info("파일 자동 삭제 : {} ",file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
