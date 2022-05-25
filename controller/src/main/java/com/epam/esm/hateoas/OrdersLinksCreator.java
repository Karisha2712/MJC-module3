package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.pagination.Page;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrdersLinksCreator implements LinksCreator<OrderDto> {
    @Override
    public void createLinks(OrderDto order) {
        order.add(linkTo(methodOn(OrderController.class)
                .receiveSingleOrder(order.getId()))
                .withSelfRel());
        order.getCertificates().forEach(certificate -> {
            certificate.add(linkTo(methodOn(CertificateController.class)
                    .receiveSingleCertificate(certificate.getId()))
                    .withSelfRel());
            certificate.getTags().forEach(tag ->
                    tag.add(linkTo(methodOn(TagController.class)
                            .receiveSingleTag(tag.getId()))
                            .withSelfRel()));
        });
    }

    @Override
    public void createPaginationLinks(Page<OrderDto> page) {
        if (page.getCurrentPage() > 1) {
            page.add(linkTo(methodOn(OrderController.class)
                    .receivePageOfOrders(page.getCurrentPage() - 1, page.getElementsPerPageNumber()))
                    .withRel("Previous page"));
        }

        if (page.getCurrentPage() < page.getTotalPageNumber()) {
            page.add(linkTo(methodOn(OrderController.class)
                    .receivePageOfOrders(page.getCurrentPage() + 1, page.getElementsPerPageNumber()))
                    .withRel("Next page"));
        }
    }
}
