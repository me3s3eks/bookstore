package com.encom.bookstore.dto;

import java.util.Set;

public record UserRolesUpdateDto(

    Set<Long> roleIds) {
}
