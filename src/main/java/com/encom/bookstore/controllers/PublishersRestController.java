package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.PublisherBaseInfoDto;
import com.encom.bookstore.dto.PublisherDto;
import com.encom.bookstore.dto.PublisherRequestDto;
import com.encom.bookstore.model.Country;
import com.encom.bookstore.services.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/catalogue/publishers")
@RequiredArgsConstructor
public class PublishersRestController {

    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(@Valid @RequestBody PublisherRequestDto publisherRequestDto,
                                                        BindingResult bindingResult,
                                                        UriComponentsBuilder uriBuilder)
        throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            PublisherDto publisherDto = publisherService.createPublisher(publisherRequestDto);
            return ResponseEntity
                .created(uriBuilder
                    .replacePath("/catalogue/publishers/{publisherId}")
                    .build(publisherDto.id()))
                .body(publisherDto);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PublisherBaseInfoDto>> getAllPublishers(
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "countries", required = false) List<Country> countries,
        Pageable pageable) {
        Page<PublisherBaseInfoDto> publisherPage = publisherService.findAllPublishersByParams(pageable, keyword, countries);
        return ResponseEntity.ok(publisherPage);
    }

    @GetMapping("/{publisherId:\\d+}")
    public ResponseEntity<PublisherDto> getPublisher(@PathVariable long publisherId) {
        PublisherDto publisherDto = publisherService.findPublisher(publisherId);
        return ResponseEntity.ok(publisherDto);
    }

    @PutMapping("/{publisherId:\\d+}")
    public ResponseEntity<Void> updatePublisher(@PathVariable long publisherId,
                                                @Valid @RequestBody PublisherRequestDto publisherRequestDto,
                                                BindingResult bindingResult)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        publisherService.updatePublisher(publisherId, publisherRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{publisherId:\\d+}")
    public ResponseEntity<Void> deletePublisher(@PathVariable long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.noContent().build();
    }
}
