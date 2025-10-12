package team8.bumaview.domain.company.api.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String companyName;
    private String link;
}
