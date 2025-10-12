package team8.bumaview.domain.interview.api.dto.response;

import lombok.*;
import team8.bumaview.domain.category.api.dto.response.CategoryList;
import team8.bumaview.domain.company.api.dto.response.CompanyDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInfoDto {
    private List<CategoryList> categoryList;
    private List<CompanyDto> companyList;
}
