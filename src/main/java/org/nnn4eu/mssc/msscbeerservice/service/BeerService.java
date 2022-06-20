package org.nnn4eu.mssc.msscbeerservice.service;

import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerPagedList;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
                            Boolean showInventoryOnHand);

    BeerDto getById(UUID id,Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto dto);

    BeerDto updateBeer(UUID id, BeerDto beerDto);

    void deleteBeer(UUID id);
}
