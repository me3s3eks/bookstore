package com.encom.bookstore.dto;

import com.encom.bookstore.model.AvailabilityStatus;
import com.encom.bookstore.model.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVariantDto {

    private Long bookId;

    private BookType bookType;

    private BigDecimal price;

    private AvailabilityStatus availabilityStatus;

    private PaperBookPropertiesDto paperBookPropertiesDto;
}
