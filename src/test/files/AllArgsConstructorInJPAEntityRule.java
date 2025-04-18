import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EntitySample {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}

@Entity
@AllArgsConstructor // Noncompliant
public class EntitySampleNonCompliant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EntitySampleCompliantWithNoArgsConstructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}

@Entity
@AllArgsConstructor
public class EntitySampleCompliantWithExplicitConstructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public EntitySampleCompliantWithExplicitConstructor() {
        // Default constructor for JPA
    }
}
