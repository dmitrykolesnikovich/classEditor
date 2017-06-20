package featurea.classEditor.classfile;

import org.junit.Test;

import java.io.IOException;

import static featurea.classEditor.util.Util.getInputStream;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClassFileTest {

    @Test
    public void read1() throws IOException {
        ClassFile classFile = new ClassFile();
        classFile.read(getInputStream("A.class"));
        Methods methods = classFile.methods;
        assertEquals(2, methods.getMethodsCount());
        {
            MethodInfo method = methods.getMethod(0);
            assertEquals("<init>", method.getMethodName());
            assertTrue(method.accessFlags.isPublic());
            assertFalse(method.accessFlags.isStatic());
        }
        {
            MethodInfo method = methods.getMethod(1);
            assertEquals("main", method.getMethodName());
            assertTrue(method.accessFlags.isPublic());
            assertTrue(method.accessFlags.isStatic());
        }
    }

    @Test
    public void test() {
        System.out.println((int) 'Ѐ');
    }

}
