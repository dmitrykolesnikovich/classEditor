package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Methods {
    int iMethodsCount;
    Vector methodsVect;

    void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iMethodsCount = paramDataInputStream.readUnsignedShort();
        this.methodsVect = new Vector(this.iMethodsCount);
        for (int i = 0; i < this.iMethodsCount; i++) {
            MethodInfo localMethodInfo = new MethodInfo();
            localMethodInfo.read(paramDataInputStream, paramConstantPool);
            this.methodsVect.addElement(localMethodInfo);
        }
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iMethodsCount = this.methodsVect.size();
        paramDataOutputStream.writeShort(this.iMethodsCount);
        for (int i = 0; i < this.iMethodsCount; i++) {
            MethodInfo localMethodInfo = (MethodInfo) this.methodsVect.elementAt(i);
            localMethodInfo.write(paramDataOutputStream, paramConstantPool);
        }
    }

    public boolean verify(Vector paramVector) {
        boolean bool = true;
        for (int i = 0; i < this.iMethodsCount; i++) {
            MethodInfo localMethodInfo = (MethodInfo) this.methodsVect.elementAt(i);
            bool = (localMethodInfo.verify("Method " + (i + 1) + "(" + localMethodInfo.cpName.sUTFStr + ")", paramVector)) && (bool);
        }
        return bool;
    }

    public String toString() {
        String str2 = System.getProperty("line.separator");
        this.iMethodsCount = this.methodsVect.size();
        String str1 = "Methods count: " + this.iMethodsCount + str2;
        int i = 0;
        while (i < this.iMethodsCount) {
            str1 = str1 + this.methodsVect.elementAt(i++).toString() + str2;
        }
        return str1;
    }

    public int getMethodsCount() {
        return this.iMethodsCount = this.methodsVect.size();
    }

    public MethodInfo getMethod(int paramInt) {
        return (MethodInfo) this.methodsVect.elementAt(paramInt);
    }

    public void deleteMethod(int paramInt) {
        MethodInfo localMethodInfo = (MethodInfo) this.methodsVect.elementAt(paramInt);
        localMethodInfo.removeReferences();
        this.methodsVect.removeElementAt(paramInt);
        this.iMethodsCount -= 1;
    }

    public void addMethod(MethodInfo paramMethodInfo) {
        paramMethodInfo.addReferences();
        this.methodsVect.addElement(paramMethodInfo);
        this.iMethodsCount += 1;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/Methods.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */