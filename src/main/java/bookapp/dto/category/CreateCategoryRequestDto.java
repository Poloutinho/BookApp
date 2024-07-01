package bookapp.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryRequestDto {
    private String name;
    private String description;
}
