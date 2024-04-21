package bookapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookapp.dto.book.BookDto;
import bookapp.dto.category.CategoryDto;
import bookapp.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static final String URL = "/api/categories";

    private static final String SCRIPT_FOR_ADD_DATA_IN_DB =
            "classpath:database/add-category-to-categories-table.sql";
    private static final String SCRIPT_FOR_REMOVE_DATA_IN_DB =
            "classpath:database/remove-data-from-all-tables.sql";
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/add-category-to-categories-table.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/remove-data-from-all-tables.sql")
            );
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Create a new category")
    @Sql(scripts = SCRIPT_FOR_REMOVE_DATA_IN_DB,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createCategory_ValidRequestDto_Success() throws Exception {
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        CategoryDto expected = new CategoryDto();
        expected.setId(1L);
        expected.setName(requestDto.getName());
        expected.setDescription(requestDto.getDescription());
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(post(URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BookDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), BookDto.class);

        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("Find a category by Id")
    @Sql(scripts = SCRIPT_FOR_ADD_DATA_IN_DB,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SCRIPT_FOR_REMOVE_DATA_IN_DB,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findCategoryById_ValidId_Success() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("Comedy", actual.getName());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update a category by id")
    @Sql(scripts = SCRIPT_FOR_ADD_DATA_IN_DB,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = SCRIPT_FOR_REMOVE_DATA_IN_DB,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateCategory_ValidData_Success() throws Exception {
        Long categoryId = 1L;
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        requestDto.setDescription("Funny");

        CategoryDto expected = createCategoryDto();
        expected.setDescription(requestDto.getDescription());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(put("/api/categories/{id}", categoryId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), CategoryDto.class);

        Assertions.assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete a category by id")
    void deleteCategory_ValidId_Success() throws Exception {
        mockMvc.perform(delete("/api/categories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Comedy");
        requestDto.setDescription("Makes happy");
        return requestDto;
    }

    private CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Comedy");
        categoryDto.setDescription("Makes happy");
        return categoryDto;
    }
}
