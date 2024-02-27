package com.example.BookApp;

import com.example.BookApp.model.Book;
import com.example.BookApp.service.BookService;
import com.example.BookApp.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class BookAppApplication {
	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(BookAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				Book Sinderellawoman = new Book();
				Sinderellawoman.setTitle("Sinderellawoman");
				Sinderellawoman.setAuthor("Valera");
				Sinderellawoman.setDescription("About woman");
				Sinderellawoman.setCoverImage("Image");
				Sinderellawoman.setIsbn("ss");
				Sinderellawoman.setPrice(BigDecimal.valueOf(700));

				bookService.save(Sinderellawoman);

				List<Book> allBooks = bookService.getAllBooks();
				for (Book book : allBooks) {
					System.out.println(book.getTitle());
				}
			}
		};
	}
}
