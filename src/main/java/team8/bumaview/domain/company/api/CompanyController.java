package team8.bumaview.domain.company.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team8.bumaview.domain.company.api.dto.request.CompanyDto;
import team8.bumaview.domain.company.application.CompanyService;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> createCompany(@RequestBody CompanyDto companyDto) {
        companyService.createCompany(companyDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<Void> modifyCompany(@PathVariable("companyId") Long companyId, @RequestBody CompanyDto companyDto) {
        companyService.modifyCompany(companyId, companyDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("companyId") Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
