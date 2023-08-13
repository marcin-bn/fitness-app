package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.scraper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ProductListScraperTest {

    private final ProductListScraper productListScraper = new ProductListScraper();

    @Test
    void scrap() throws IOException {
        System.out.println(productListScraper.getProductIds("aa"));
    }

}