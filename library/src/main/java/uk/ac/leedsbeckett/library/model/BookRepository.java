package uk.ac.leedsbeckett.library.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long>{
    Book findBookByTitle(String title);
    Book findBookByIsbn(Long isbn);
}
