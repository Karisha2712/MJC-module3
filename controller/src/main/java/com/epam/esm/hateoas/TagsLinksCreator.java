package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagsLinksCreator implements LinksCreator<TagDto> {
    @Override
    public void createLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class)
                .receiveSingleTag(tag.getId()))
                .withSelfRel());
    }

    @Override
    public void createPaginationLinks(Page<TagDto> page) {
        if (page.getCurrentPage() > 1) {
            page.add(linkTo(methodOn(TagController.class)
                    .receivePageOfTags(page.getCurrentPage() - 1, page.getElementsPerPageNumber()))
                    .withRel("Previous page"));
        }

        if (page.getCurrentPage() < page.getTotalPageNumber()) {
            page.add(linkTo(methodOn(TagController.class)
                    .receivePageOfTags(page.getCurrentPage() + 1, page.getElementsPerPageNumber()))
                    .withRel("Next page"));
        }
    }
}
