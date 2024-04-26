//package bookapp.controller;
//
//import bookapp.dto.book.BookDto;
//import bookapp.dto.category.CategoryDto;
//import bookapp.dto.category.CreateCategoryRequestDto;
//import bookapp.dto.shoppingcart.CartItemDto;
//import bookapp.dto.shoppingcart.CartItemRequestDto;
//import bookapp.dto.shoppingcart.ShoppingCartDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.MediaType;
//import org.springframework.jdbc.datasource.init.ScriptUtils;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ShoppingCartControllerTest {
//    protected static MockMvc mockMvc;
//    private static final String URL = "/api/cart";
//
//    private static final String SCRIPT_FOR_ADD_DATA_IN_DB =
//            "classpath:database/add-cart-item-to-cart-items-table.sql";
//    private static final String SCRIPT_FOR_REMOVE_DATA_IN_DB =
//            "classpath:database/remove-data-from-all-tables.sql";
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeAll
//    static void beforeAll(
//            @Autowired DataSource dataSource,
//            @Autowired WebApplicationContext applicationContext
//    ) throws SQLException {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(applicationContext)
//                .apply(SecurityMockMvcConfigurers.springSecurity())
//                .build();
//        try (Connection connection = dataSource.getConnection()) {
//            connection.setAutoCommit(true);
//            ScriptUtils.executeSqlScript(
//                    connection,
//                    new ClassPathResource("database/add-cart-item-to-cart-items-table.sql")
//            );
//        }
//    }
//
//    @AfterAll
//    static void afterAll(
//            @Autowired DataSource dataSource
//    ) {
//        teardown(dataSource);
//    }
//
//    @SneakyThrows
//    static void teardown(DataSource dataSource) {
//        try (Connection connection = dataSource.getConnection()) {
//            connection.setAutoCommit(true);
//            ScriptUtils.executeSqlScript(
//                    connection,
//                    new ClassPathResource("database/remove-data-from-all-tables.sql")
//            );
//        }
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    @DisplayName("Create a new cart item")
//    @Sql(scripts = SCRIPT_FOR_REMOVE_DATA_IN_DB,
//            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    void createCartItem_ValidRequestDto_Success() throws Exception {
//        CartItemDto requestDto = createCartItemRequestDto();
//        CartItemDto expected = new CartItemDto();
//        expected.setId(1L);
//        expected.setBookId(requestDto.getBookId());
//        expected.setBookTitle(requestDto.getBookTitle());
//        expected.setQuantity(requestDto.getQuantity());
//        String jsonRequest = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult mvcResult = mockMvc.perform(post(URL)
//                        .content(jsonRequest)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().is2xxSuccessful())
//                .andReturn();
//
//        BookDto actual = objectMapper.readValue(mvcResult.getResponse()
//                .getContentAsString(), BookDto.class);
//
//        Assertions.assertNotNull(actual);
//        EqualsBuilder.reflectionEquals(expected, actual, "id");
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    @DisplayName("Update a cart item by id")
//    @Sql(scripts = SCRIPT_FOR_ADD_DATA_IN_DB,
//            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = SCRIPT_FOR_REMOVE_DATA_IN_DB,
//            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    void updateShoppingCart_ValidData_Success() throws Exception {
//        Long cartItemId = 1L;
//        CartItemDto requestDto = createCartItemRequestDto();
//        requestDto.setQuantity(2);
//
//        CartItemDto expected = createCartItemRequestDto();
//        expected.setQuantity(requestDto.getQuantity());
//
//        String jsonRequest = objectMapper.writeValueAsString(requestDto);
//
//        MvcResult mvcResult = mockMvc.perform(put("/api/categories/cart-items/1", cartItemId)
//                        .content(jsonRequest)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        CartItemDto actual = objectMapper.readValue(mvcResult.getResponse()
//                .getContentAsString(), CartItemDto.class);
//
//        Assertions.assertNotNull(actual);
//        EqualsBuilder.reflectionEquals(expected, actual, "id");
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    @DisplayName("Delete a cart item by id")
//    void deleteCategory_ValidId_Success() throws Exception {
//        mockMvc.perform(delete("/api/cart/cart-items/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent())
//                .andReturn();
//    }
//
//    private CartItemDto createCartItemRequestDto() {
//        CartItemDto cartItemDto = new CartItemDto();
//        cartItemDto.setId(1L);
//        cartItemDto.setBookId(1L);
//        cartItemDto.setBookTitle("Sample");
//        cartItemDto.setQuantity(10);
//        return cartItemDto;
//    }
//}
