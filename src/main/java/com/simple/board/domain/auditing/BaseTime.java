package com.simple.board.domain.auditing;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    public String getLastModifiedDate(){
        LocalDateTime now = LocalDateTime.now();

        long years = ChronoUnit.YEARS.between(lastModifiedDate, now);
        if (years > 0) {
            return years + "년 전";
        }

        long months = ChronoUnit.MONTHS.between(lastModifiedDate, now);
        if (months > 0) {
            return months + "개월 전";
        }

        long days = ChronoUnit.DAYS.between(lastModifiedDate, now);
        if (days > 0) {
            return days + "일 전";
        }

        long hours = ChronoUnit.HOURS.between(lastModifiedDate, now);
        if (hours > 0) {
            return hours + "시간 전";
        }

        long minutes = ChronoUnit.MINUTES.between(lastModifiedDate, now);
        if (minutes > 0) {
            return minutes + "분 전";
        }

        return "방금전";
    }

}
