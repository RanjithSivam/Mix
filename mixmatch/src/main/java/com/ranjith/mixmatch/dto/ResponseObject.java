package com.ranjith.mixmatch.dto;

import com.ranjith.mixmatch.util.ResponseStatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseObject {
	private ResponseStatusEnum status;
    private String message;
    private Object payload;
    private boolean isError;
}
