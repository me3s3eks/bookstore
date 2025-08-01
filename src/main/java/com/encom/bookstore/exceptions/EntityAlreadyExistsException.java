package com.encom.bookstore.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    private final Object entityId;
}
