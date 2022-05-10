package org.nnn4eu.mssc.msscbeerservice.web.controller;

import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    @GetMapping("/{id}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("id") UUID id) {
//        todo impl
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BeerDto> handlePost(@Validated @RequestBody BeerDto dto) {
//        todo impl
        return new ResponseEntity<>(BeerDto.builder().build(), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBeerById(@PathVariable("id") UUID id) {
//        todo impl
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBeerById(@PathVariable("id") UUID id, @RequestBody @Validated BeerDto beerDto) {
//        todo impl
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
