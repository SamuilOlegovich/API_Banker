package com.samuilolegovich.APIBanker.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorCodeAndDescription {
    private String code;
    private String text;
}
