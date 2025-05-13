package com.encom.bookstore.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ForeignKeyDeleteConstraintException extends RuntimeException {

    private final String dependentEntityName;

    public ForeignKeyDeleteConstraintException(String message, String dependentEntityName) {
        super(message);
        this.dependentEntityName = dependentEntityName;
    }
}
