import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
@Data // Noncompliant
public static class EntityWithJakartaDataAnnotation {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

}
