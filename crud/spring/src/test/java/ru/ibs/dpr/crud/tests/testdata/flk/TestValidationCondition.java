package ru.ibs.dpr.crud.tests.testdata.flk;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ibs.dpr.crud.spring.flk.ValidationCondition;

@Data
@AllArgsConstructor
public class TestValidationCondition implements ValidationCondition<Long, String> {

    private Long errorCode;
    private String errorMessage;
}
