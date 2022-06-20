package org.nnn4eu.mssc.msscbeerservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.nnn4eu.mssc.msscbeerservice.domain.Beer;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;

@DecoratedWith(BeerMapperDecorator.class)
@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventoryDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
