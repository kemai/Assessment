package uk.ac.leedsbeckett.library;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import uk.ac.leedsbeckett.library.model.Book;
import uk.ac.leedsbeckett.library.model.BookRepository;
import uk.ac.leedsbeckett.library.service.IntegrationService;

import java.util.Locale;
import java.util.Set;

@Configuration
public class MiscellaneusBeans {
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.UK);
        return sessionLocaleResolver;
    }


    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {

            Book b1 = new Book();
            b1.setIsbn("0062515876");
            b1.setTitle("Weaving the Web");
            b1.setN_copies(10);

            Book b2 = new Book();
            b2.setIsbn("0465026567");
            b2.setTitle("Godel, Escher, Bach");
            b2.setN_copies(5);

            Book b3 = new Book();
            b3.setIsbn("0465030793");
            b3.setTitle("I Am A Strange Loop");
            b3.setN_copies(15);


            bookRepository.saveAllAndFlush(Set.of(b1,b2,b3));

        };
    }
}
