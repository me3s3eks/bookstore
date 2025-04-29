package com.encom.bookstore.services;


import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.model.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    AuthorDto findAuthor(long id);

    AuthorBaseInfoDto findAuthorBaseInfo(long authorId);

    Author getAuthor(long authorId);

    List<Author> getAuthors(Set<Long> authorsIds);
}
