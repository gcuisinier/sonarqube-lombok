/*
 * The MIT License
 * Copyright © 2022 @gcuisinier
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import lombok.*;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;



@Data
public class LombokData { // Noncompliant {{This class is using Lombok annotations}}
    private String var1;
    private int var2;
}

@EqualsAndHashCode
public class LombokEqualsAndHashCode { // Noncompliant {{This class is using Lombok annotations}}
    private String var1;
    private int var2;
}

@RequiredArgsConstructor
public class LombokRequiredArgsConstructor { // Noncompliant {{This class is using Lombok annotations}}
    private String var1;
    private int var2;
}

@AllArgsConstructor
public class LombokAllArgsConstructor { // Noncompliant {{This class is using Lombok annotations}}

    private String var1;
    private int var2;

}

@ToString
public class LombokToString { // Noncompliant {{This class is using Lombok annotations}}
    private String var1;
    private int var2;
}

@Slf4j
public static class LombokSlf4j { // Noncompliant {{This class is using Lombok annotations}}
    public void myMethod(){
        //doNothing
    }
}

@Log
public static class LombokLog { // Noncompliant {{This class is using Lombok annotations}}
    public void myMethod(){
        //doNothing
    }
}

@Builder
public static class LombokBuilder { // Noncompliant {{This class is using Lombok annotations}}
    private String var1;
    private int var2;
}

public static class LombokGetter { // Noncompliant {{This class is using Lombok annotations}}
    @Getter
    private String myValue;
}

public static class LombokSetter { // Noncompliant {{This class is using Lombok annotations}}
    @Setter
    private String myValue;
}

public static class LombokSneakyThrows { // Noncompliant {{This class is using Lombok annotations}}
    @SneakyThrows
    public void myMethod(){
        //doNothing
    }
}






