package uk.ac.leedsbeckett.library.service;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.library.model.Book;

public class IntegrationService {
    private final RestTemplate restTemplate;
    String url_isbn = "https://api2.isbndb.com/book/9781934759486";

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Book getBookByISBN(String isbn){
        return restTemplate.getForObject(url_isbn + isbn, Book.class);
    }
}
