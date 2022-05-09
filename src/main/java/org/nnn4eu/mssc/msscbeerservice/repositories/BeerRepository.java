package org.nnn4eu.mssc.msscbeerservice.repositories;

import org.nnn4eu.mssc.msscbeerservice.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
