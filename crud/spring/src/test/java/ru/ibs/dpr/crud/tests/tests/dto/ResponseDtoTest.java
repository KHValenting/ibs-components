package ru.ibs.dpr.crud.tests.tests.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ibs.dpr.crud.spring.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

class ResponseDtoTest {

    ResponseDto<String> responseDto;

    @BeforeEach
    public void before() {
        responseDto = new ResponseDto<>(new ArrayList<String>(), 1L);
    }

    @Test
    public void NotNullTest() {
        Assertions.assertNotNull(responseDto);
    }

    @Test
    public void getTotlaElementsTest() {
        Assertions.assertEquals( 1, responseDto.getTotalElements());
    }

    @Test
    public void getContentTest() {
        List<String> testcontent = new ArrayList<>();
        testcontent.add("Test");
        responseDto.setContent(testcontent);
        Assertions.assertEquals("Test", responseDto.getContent().get(0));
    }
}
