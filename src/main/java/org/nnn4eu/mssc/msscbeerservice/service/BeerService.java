package org.nnn4eu.mssc.msscbeerservice.service;

import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID id);

    BeerDto saveNewBeer(BeerDto dto);

    BeerDto updateBeer(UUID id, BeerDto beerDto);

    void deleteBeer(UUID id);
}
