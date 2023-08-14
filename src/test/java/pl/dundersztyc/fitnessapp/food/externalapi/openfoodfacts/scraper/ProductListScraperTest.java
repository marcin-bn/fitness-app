package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.scraper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ProductListScraperTest {

    private final ProductListScraper productListScraper = new ProductListScraper();

    @Test
    void shouldGetProductIds() throws IOException {
        var productIds = productListScraper.getProductIds("pizza", 5);
        assertThat(productIds).hasSize(5);
    }

}