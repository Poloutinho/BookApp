package bookapp.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBookRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
    private List<Long> categories;
}
