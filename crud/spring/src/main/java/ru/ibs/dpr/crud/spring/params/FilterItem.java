package ru.ibs.dpr.crud.spring.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class FilterItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String property;
    private String operator;
    private Object value;
    private Object value2;
}