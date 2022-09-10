package org.sonar.samples;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity

public class EntitySample {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    Set<Object> test;

    @OneToOne(fetch = FetchType.LAZY)
    Object test2;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    Object test4;

    @OneToOne(fetch = FetchType.LAZY)
    Object test5;

    @ManyToOne(fetch = FetchType.LAZY)
    Object test3;

    @Entity
    @EqualsAndHashCode
    public static class EntitySampleNonCompliantEqualsHashcode {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;

    }

    @Entity
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class EntitySampleCompliantEqualsHashcode {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        @EqualsAndHashCode.Include
        private Long id;

    }

    @Entity
    @Data
    public static class EntitySampleNonCompliantData {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;

    }

    @Entity
    @Data
    @NoArgsConstructor
    public static class EntitySampleCompliantData {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private Long id;

        @Override
        public int hashCode() {
            // We don't care about the implementation
            // The important for the rules is that the function is overrided
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            // We don't care about the implementation
            // The important for the rules is that the function is overrided
            return super.equals(obj);
        }
    }




}


