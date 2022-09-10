import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Entity
@ToString
public static class EntityWithOneToManyNonCompliant1 {

    /***********************/

    @OneToMany()
    private Set<Object> oneToManyNonCompliant; // Noncompliant

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Object> oneToManyNonCompliant2; // Noncompliant

    @OneToMany()
    @ToString.Exclude
    private Set<Object> oneToManyCompliantAsExcluded;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Object> oneToManyCompliantAsEager;

    /***********************/
    @OneToOne(fetch = FetchType.LAZY)
    private Object oneToOneNonCompliantAsLazy; // Noncompliant

    @OneToOne()
    private Object oneToOneCompliant;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Object OneToOneCompliantAsLazyButExcluded;

    /***********************/
    @ManyToOne(fetch = FetchType.LAZY)
    private Object manyToOneCompliantAsEager; // Noncompliant

    @ManyToOne()
    private Object manyToOneNonCompliant;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Object manyToOneCompliantAsExcluded;

    /***********************/

    @ManyToMany()
    private Set<Object> manyToManyNonCompliant;// Noncompliant

    @ManyToMany()
    @ToString.Exclude
    private Set<Object> manyToManyCompliantAsExcluded;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Object> manyToManyCompliantAsEager;



}






