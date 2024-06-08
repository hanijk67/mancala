package com.example.mancala.entity.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorMessage (Integer statusCode,
                            String status,
                            Date time,
                            String content
                            ){

}
