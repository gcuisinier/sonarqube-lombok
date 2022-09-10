SonarQube Rules for Lombok
=======

A plugin for SonarQube ( >= 8.9 ) that detect Lombok usage and some bad pattern of integration between Lombok and JPA

| Key                             | Description                                                                                 | Default Severity |
|---------------------------------|---------------------------------------------------------------------------------------------|------------------|
| Lombok-CoreUsage                | Rule that detects the usage of Lombok annotations                                           | Info             |
| Lombok-Synchronized             | Rule that detects the usage of @Synchronized annotation                                     | Info             |
| Lombok-ToStringJPA              | Rule that check that all the Lazy-Loaded collection are not part of the toString generation | Major            |
| Lombok-JPAWithEqualsAndHashCode | Rule that check that EqualsAndHashCode only use specific field and not all                  | Minor            |
| Lombok-JPAWithData              | Rule that check that @Data annotation is not used on @Entity classe                         | Minor            |


### License

The MIT License (MIT)