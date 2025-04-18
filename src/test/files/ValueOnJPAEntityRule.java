import lombok.Value;

import javax.persistence.Entity;

@Entity
@Value // Noncompliant
public class User {
    Long id;
    String name;
}
