package team8.bumaview.domain.company.api.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long CompanyId;
    private String companyName;
}
