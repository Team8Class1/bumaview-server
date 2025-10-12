package team8.bumaview.domain.category.api.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryList {
    private Long categoryId;
    private String categoryName;
}
