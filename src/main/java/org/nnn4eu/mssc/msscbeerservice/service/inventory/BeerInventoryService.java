package org.nnn4eu.mssc.msscbeerservice.service.inventory;

import java.util.UUID;

public interface BeerInventoryService {
    Integer getOnhandInventory(UUID beerId);
}
