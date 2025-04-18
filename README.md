# SonarQube Rules for Lombok

A plugin for SonarQube (>= 10.x) that detects Lombok usage and identifies potential issues with the integration between Lombok and JPA. This plugin helps developers avoid common pitfalls when using Lombok annotations in JPA entities.

## Features

- Detects problematic patterns when using Lombok with JPA entities
- Provides recommendations for best practices
- Helps prevent performance issues and bugs related to Lombok usage

## Installation

1. Download the latest release JAR file from the [releases page](https://github.com/gcuisinier/sonar-lombok-rules/releases)
2. Place the JAR file in the `extensions/plugins` directory of your SonarQube installation
3. Restart your SonarQube server
4. The rules will be available in the Java quality profile

## Rules

| Key                                | Description                                                                                 | Default Severity |
|------------------------------------|---------------------------------------------------------------------------------------------|------------------|
| Lombok-Synchronized                | Detects the usage of @Synchronized annotation                                               | Minor            |
| Lombok-ToStringJPA                 | Checks that all Lazy-Loaded collections are not part of the toString generation             | Major            |
| Lombok-JPAWithEqualsAndHashCode    | Detects bad pattern when using Lombok @EqualsAndHashCode in JPA Entity                      | Major            |
| Lombok-JPAWithData                 | Checks that @Data annotation is not used on @Entity classes                                 | Major            |
| Lombok-BuilderWithInheritance      | Avoid Lombok @Builder annotations in classes that extend another class                      | Major            |
| Lombok-JPAWithAllArgsConstructor   | JPA Entity with @AllArgsConstructor must also have a default constructor                    | Major            |
| Lombok-ValueOnJPAEntity          | Checks that @Value annotation is not used on @Entity classes                                | Major            |

## Why Use This Plugin?

Lombok is a great tool for reducing boilerplate code, but it can cause issues when used with JPA entities:

- Using `@Data` on JPA entities can lead to performance issues and stack overflows
- Including lazy-loaded collections in `toString()` methods can cause unexpected database queries
- Improper use of `@EqualsAndHashCode` can lead to circular references and stack overflows
- Using `@Builder` with inheritance can lead to incomplete builders
- JPA entities with `@AllArgsConstructor` but no default constructor will cause issues with JPA providers
- Using `@Value` on JPA entities creates immutable objects that are incompatible with JPA's requirements

This plugin helps you identify and fix these issues before they cause problems in production.

## License

The MIT License (MIT)
