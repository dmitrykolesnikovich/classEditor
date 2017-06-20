package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ClassNames {
    public ConstantPoolInfo cpThisClass;
    public ConstantPoolInfo cpSuperClass;
    int iThisClass;
    int iSuperClass;

    void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iThisClass = paramDataInputStream.readUnsignedShort();
        this.iSuperClass = paramDataInputStream.readUnsignedShort();
        this.cpThisClass = paramConstantPool.getPoolInfo(this.iThisClass);
        this.cpThisClass.addRef();
        this.cpSuperClass = paramConstantPool.getPoolInfo(this.iSuperClass);
        this.cpSuperClass.addRef();
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iThisClass = paramConstantPool.getIndexOf(this.cpThisClass);
        this.iSuperClass = paramConstantPool.getIndexOf(this.cpSuperClass);
        paramDataOutputStream.writeShort(this.iThisClass);
        paramDataOutputStream.writeShort(this.iSuperClass);
    }

    public boolean verify(Vector paramVector) {
        boolean bool = true;
        if ((7 != this.cpThisClass.iTag) || (7 != this.cpSuperClass.iTag)) {
            paramVector.addElement("ClassName and SuperClassName indexes should point to constant pool of type Class.");
            bool = false;
        }
        if ((0 == this.cpThisClass.refUTF8.sUTFStr.length()) || (0 == this.cpSuperClass.refUTF8.sUTFStr.length())) {
            paramVector.addElement("Class and SuperClass names can not be empty.");
            bool = false;
        }
        return bool;
    }

    public String toString() {
        String str = "This class: " + this.cpThisClass + ", ";
        str = str + "Super class: " + this.cpSuperClass;
        return str;
    }

    public String getThisClassName() {
        return Utils.convertClassStrToStr(this.cpThisClass.refUTF8.sUTFStr);
    }

    public void setThisClassName(String paramString) {
        this.cpThisClass.refUTF8.sUTFStr = Utils.convertStrToClassStr(paramString);
    }

    public String getSuperClassName() {
        return Utils.convertClassStrToStr(this.cpSuperClass.refUTF8.sUTFStr);
    }

    public void setSuperClassName(String paramString) {
        this.cpSuperClass.refUTF8.sUTFStr = Utils.convertStrToClassStr(paramString);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/ClassNames.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */