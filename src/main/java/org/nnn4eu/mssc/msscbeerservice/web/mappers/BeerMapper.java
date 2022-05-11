package org.nnn4eu.mssc.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;
import org.nnn4eu.mssc.msscbeerservice.domain.Beer;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
