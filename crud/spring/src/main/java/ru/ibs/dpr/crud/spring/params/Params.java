package ru.ibs.dpr.crud.spring.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class Params implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer limit;

    private List<Sort> sorts;

    private List<FilterItem> filters;
}
