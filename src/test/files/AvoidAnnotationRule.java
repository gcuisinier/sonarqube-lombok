import lombok.Synchronized;


public class LombokSynchronized {

    public void testMethodWithoutAnnotation() {
        System.out.println("method one");
    }


    @Synchronized // Noncompliant {{Avoid using annotation @Synchronized from Lombok}}
    public void withAnnotation() {
        System.out.println("method synchronized");
    }
}
