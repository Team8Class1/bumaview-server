package team8.bumaview.domain.company.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team8.bumaview.domain.company.api.dto.request.CompanyDto;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String link;

    public void modify(CompanyDto companyDto) {
        this.name = companyDto.getCompanyName();
        this.link = companyDto.getLink();
    }
}
