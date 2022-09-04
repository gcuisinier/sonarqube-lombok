import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Entity
@ToString
public static class EntityWithOneToManyNonCompliant1 {
    // One to Many
    @OneToMany()
    private Set<Object> oneToManyNonCompliant;// Noncompliant

    @OneToMany()
    @ToString.Exclude
    private Set<Object> oneToManyCompliantAsExcluded;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Object> oneToManyCompliantAsEager;

    // One to One
    @OneToOne()
    private Object oneToOneCompliant;

    @OneToOne(fetch = FetchType.LAZY)
    private Set<Object> oneToOneNonCompliantAsLazy; //Noncompliant

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Object> OneToOneCompliantAsLazyButExcluded;

    // Many to One
    @ManyToOne()
    private Object manyToOneNonCompliant;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Object manyToOneCompliantAsExcluded;

    @OneToOne(fetch = FetchType.LAZY)
    private Set<Object> manyToOneCompliantAsEager; //Noncompliant

    //ManyToMany

    @ManyToMany()
    private Set<Object> manyToManyNonCompliant;// Noncompliant

    @ManyToMany()
    @ToString.Exclude
    private Set<Object> manyToManyCompliantAsExcluded;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Object> manyToManyCompliantAsEager;



}






