package com.ssolution.admin.system.domain.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomErrorVO {
    private int status;

    private String message;

    private String error;

}
