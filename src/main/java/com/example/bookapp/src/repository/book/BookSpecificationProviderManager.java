package com.example.bookapp.src.repository.book;

import com.example.bookapp.src.model.Book;
import com.example.bookapp.src.repository.SpecificationProvider;
import com.example.bookapp.src.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Can`t find correct specification "
                                + "provider for this key" + key));

    }
}
