package org.nnn4eu.mssc.msscbeerservice.service;

import lombok.RequiredArgsConstructor;
import org.nnn4eu.mssc.msscbeerservice.domain.Beer;
import org.nnn4eu.mssc.msscbeerservice.repositories.BeerRepository;
import org.nnn4eu.mssc.msscbeerservice.web.controller.NotFoundException;
import org.nnn4eu.mssc.msscbeerservice.web.mappers.BeerMapper;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getById(UUID id) {
        return beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto dto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(dto)));
    }

    @Override
    public BeerDto updateBeer(UUID id, BeerDto beerDto) {
        Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public void deleteBeer(UUID id) {
        beerRepository.deleteById(id);
    }
}
