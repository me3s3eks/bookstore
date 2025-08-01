package com.encom.bookstore.converters;

import com.encom.bookstore.model.BookType;
import com.encom.bookstore.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBookTypeConverter implements Converter<String, BookType> {

    @Override
    public BookType convert(String source) {
        String bookTypeName = StringUtils.pathVariableToBookTypeString(source);
        return BookType.valueOf(bookTypeName);
    }
}
