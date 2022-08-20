package com.projetinho.userDept.exception;

import com.projetinho.userDept.handler.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExecptionDetails extends ExceptionDetails {
    private final String fields;
    private final String fieldsMessage;
}
