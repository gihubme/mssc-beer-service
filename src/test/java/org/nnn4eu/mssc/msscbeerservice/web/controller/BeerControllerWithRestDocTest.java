package org.nnn4eu.mssc.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nnn4eu.mssc.msscbeerservice.bootstrap.BeerLoader;
import org.nnn4eu.mssc.msscbeerservice.service.BeerService;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerDto;
import org.nnn4eu.mssc.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springfamework.nk", uriPort = 80)
//@ComponentScan(basePackages = "guru.springframework.sfgrestdocsexample.web.mappers")

@WebMvcTest(BeerController.class)
class BeerControllerWithRestDocTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @DisplayName("Get /beerById ok")
    @Test
    void getBeerById() throws Exception {
        given(beerService.getById(any())).willReturn(getValidBeerDto());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                        .param("iscold", "yes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/beer-get",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("beerId")
                                        .description("UUID of desired beer to get.")
                        ),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("iscold")
                                        .description("Is beer Cold Query Param as example")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("Id of Beer"),
                                PayloadDocumentation.fieldWithPath("version").description("Version number"),
                                PayloadDocumentation.fieldWithPath("createdDate").description("Date Created"),
                                PayloadDocumentation.fieldWithPath("lastModifiedDate").description("Date Updated"),
                                PayloadDocumentation.fieldWithPath("beerName").description("Beer Name"),
                                PayloadDocumentation.fieldWithPath("beerStyle").description("Beer Style"),
                                PayloadDocumentation.fieldWithPath("upc").description("UPC of Beer"),
                                PayloadDocumentation.fieldWithPath("price").description("Price"),
                                PayloadDocumentation.fieldWithPath("quantityOnHand").description("Quantity On hand")
                        )
                ));
    }

    @DisplayName("POST /beer created")
    @Test
    void saveNewBeer() throws Exception {
        BeerDto dto = getValidBeerDto();
        String dtoJson = objectMapper.writeValueAsString(dto);
        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcRestDocumentation.document("v1/beer-new",
                        PayloadDocumentation.requestFields(
//                                PayloadDocumentation.fieldWithPath("id").ignored(),
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Beer UPC").attributes(),
                                fields.withPath("price").description("Beer Price"),
                                fields.withPath("quantityOnHand").ignored()
                        )
                ));
    }

    @DisplayName("DELETE /beerById noContent")
    @Test
    void deleteBeerById() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/beer/" + UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());
//        then(service).should().deleteById(any());
    }

    @DisplayName("PUT /beerById noContent")
    @Test
    void updateBeerById() throws Exception {
        BeerDto dto = getValidBeerDto();
        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());
        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("My Beerschatz")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return PayloadDocumentation.fieldWithPath(path).attributes(Attributes.key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}
