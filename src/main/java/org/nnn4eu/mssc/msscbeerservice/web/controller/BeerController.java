package org.nnn4eu.mssc.msscbeerservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.nnn4eu.mssc.msscbeerservice.service.BeerService;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @GetMapping("/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("id") UUID id) {
        return new ResponseEntity(beerService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BeerDto> handlePost(@Validated @RequestBody BeerDto dto) {
        return new ResponseEntity<>(beerService.saveNewBeer(dto), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBeerById(@PathVariable("id") UUID id) {
        beerService.deleteBeer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBeerById(@PathVariable("id") UUID id, @RequestBody @Validated BeerDto beerDto) {
        return new ResponseEntity<>(beerService.updateBeer(id, beerDto), HttpStatus.NO_CONTENT);
    }
}
