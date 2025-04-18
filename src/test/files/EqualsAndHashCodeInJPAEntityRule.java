import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode // Noncompliant
public static class EntitySampleNonCompliantEqualsHashcode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}


@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public static class EntitySampleCompliantEqualsHashcode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

}
