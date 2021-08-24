package ru.ibs.dpr.crud.spring.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto<DTO> {
    private List<DTO> content;
    private Long totalElements;

    public ResponseDto(List<DTO> content, Long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }
}