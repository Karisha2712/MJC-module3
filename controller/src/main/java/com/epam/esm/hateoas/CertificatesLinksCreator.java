package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.filter.CertificatesFilter;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificatesLinksCreator implements LinksCreator<CertificateDto> {
    private CertificatesFilter filter;

    public void setFilter(CertificatesFilter filter) {
        this.filter = filter;
    }

    @Override
    public void createLinks(CertificateDto certificate) {
        certificate.add(linkTo(methodOn(CertificateController.class)
                .receiveSingleCertificate(certificate.getId()))
                .withSelfRel());
        certificate.getTags().forEach(tag ->
                tag.add(linkTo(methodOn(TagController.class)
                        .receiveSingleTag(tag.getId()))
                        .withSelfRel()));
    }

    @Override
    public void createPaginationLinks(Page<CertificateDto> page) {
        if (page.getCurrentPage() > 1) {
            page.add(linkTo(methodOn(CertificateController.class)
                    .receivePageOfCertificates(page.getCurrentPage() - 1, page.getElementsPerPageNumber(),
                            filter.getTextPart(),
                            filter.getSortBy(),
                            filter.getOrder().toString(),
                            filter.getTagNames() == null ? Collections.emptySet() : filter.getTagNames()))
                    .withRel("Previous page"));
        }

        if (page.getCurrentPage() < page.getTotalPageNumber()) {
            page.add(linkTo(methodOn(CertificateController.class)
                    .receivePageOfCertificates(page.getCurrentPage() + 1, page.getElementsPerPageNumber(),
                            filter.getTextPart(),
                            filter.getSortBy(),
                            filter.getOrder().toString(),
                            filter.getTagNames() == null ? Collections.emptySet() : filter.getTagNames()))
                    .withRel("Next page"));
        }
    }
}
