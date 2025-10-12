package team8.bumaview.domain.company.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team8.bumaview.domain.company.api.dto.request.CompanyDto;
import team8.bumaview.domain.company.domain.Company;
import team8.bumaview.domain.company.persistence.CompanyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyRepository companyRepository;


    public void createCompany(CompanyDto companyDto) {
        Company company = Company.builder()
                .name(companyDto.getCompanyName())
                .link(companyDto.getLink())
                .build();
        companyRepository.save(company);
    }

    public void modifyCompany(Long companyId, CompanyDto companyDto) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("회사가 존재하지 않음"));
        company.modify(companyDto);
        companyRepository.save(company);
    }

    public void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }
}
