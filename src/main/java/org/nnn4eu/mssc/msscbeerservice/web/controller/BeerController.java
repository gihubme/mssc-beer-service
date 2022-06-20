package org.nnn4eu.mssc.msscbeerservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.nnn4eu.mssc.msscbeerservice.service.BeerService;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerPagedList;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
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
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "beerName", required = false) String beerName,
            @RequestParam(value = "beerStyle", required = false)
                    BeerStyleEnum beerStyle,
            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList =
                beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("id") UUID id,
                                               @RequestParam(value = "showInventoryOnHand", required = false)
                                                       Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity(beerService.getById(id, showInventoryOnHand), HttpStatus.OK);
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
