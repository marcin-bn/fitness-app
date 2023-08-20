package pl.dundersztyc.fitnessapp.food.externalapi.openfoodfacts.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductListScraper {

    public List<String> getProductIds(String searchTerm, int numberOfIds) throws IOException {
        String URL = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" + searchTerm + "&search_simple=1&action=process";
        Document doc = Jsoup.connect(URL).timeout(50000).get();
        var products = doc.select(".products > li");
        return products.stream()
                .map(this::extractHrefFromProduct)
                .map(this::extractProductIdFromHref)
                .limit(numberOfIds)
                .collect(Collectors.toList());
    }

    private String extractHrefFromProduct(Element product) {
        return product.select("a").first().attr("href");
    }

    private String extractProductIdFromHref(String href) {
        String startString = "/product/";
        int startIndex = href.indexOf(startString) + startString.length();
        int endIndex = href.indexOf("/", startIndex);
        return href.substring(startIndex, endIndex);
    }

}
