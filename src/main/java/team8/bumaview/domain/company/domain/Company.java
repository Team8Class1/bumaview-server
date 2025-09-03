package team8.bumaview.domain.company.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String link;
}
