import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
@Data // Noncompliant
public static class EntityWithDataAnnotation {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

}




