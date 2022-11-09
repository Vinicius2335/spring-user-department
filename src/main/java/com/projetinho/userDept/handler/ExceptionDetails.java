package com.projetinho.userDept.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
@Schema(name = "ExceptionDetails")
public class ExceptionDetails {
	@Schema(example = "Bad Request Exception")
    protected String title;
	@Schema(example = "404")
    protected int status;
	@Schema(example = "details")
    protected String details;
	@Schema(example = "develloperMessage")
    protected String develloperMessage;
	@Schema(example = "2022-07-15T11:21:50.902245498Z")
    protected LocalDateTime timestamp;
}
