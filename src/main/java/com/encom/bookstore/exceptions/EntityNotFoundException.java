package com.encom.bookstore.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String entityName;

    private final Set<Object> entityIds;

    public EntityNotFoundException(String message, String entityName, Set<Object> entityIds) {
        super(message);
        this.entityName = entityName;
        this.entityIds = entityIds;
    }
}
