import lombok.Builder;
import lombok.experimental.SuperBuilder;

class ParentClass {
    private String parentField;
}

@Builder // Noncompliant
class ChildWithBuilder extends ParentClass {
    private String childField;
}

@SuperBuilder
class ChildWithSuperBuilder extends ParentClass {
    private String childField;
}

@Builder
class StandaloneClass {
    private String field;
}

class GrandParentClass {
    private String grandParentField;
}

class ParentClass2 extends GrandParentClass {
    private String parentField;
}

@Builder // Noncompliant
class ChildWithBuilderMultiLevel extends ParentClass2 {
    private String childField;
}

@SuperBuilder
class ChildWithSuperBuilderMultiLevel extends ParentClass2 {
    private String childField;
}