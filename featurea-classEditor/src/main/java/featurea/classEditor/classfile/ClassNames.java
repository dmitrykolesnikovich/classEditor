package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ClassNames {

    public ConstantPoolInfo cpThisClass;
    public ConstantPoolInfo cpSuperClass;
    private int iThisClass;
    private int iSuperClass;

    public void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iThisClass = paramDataInputStream.readUnsignedShort();
        this.iSuperClass = paramDataInputStream.readUnsignedShort();
        this.cpThisClass = paramConstantPool.getPoolInfo(this.iThisClass);
        this.cpThisClass.addRef();
        this.cpSuperClass = paramConstantPool.getPoolInfo(this.iSuperClass);
        this.cpSuperClass.addRef();
    }

    public void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        this.iThisClass = paramConstantPool.getIndexOf(this.cpThisClass);
        this.iSuperClass = paramConstantPool.getIndexOf(this.cpSuperClass);
        paramDataOutputStream.writeShort(this.iThisClass);
        paramDataOutputStream.writeShort(this.iSuperClass);
    }

    public boolean verify(Vector result) {
        boolean bool = true;
        if ((7 != this.cpThisClass.iTag) || (7 != this.cpSuperClass.iTag)) {
            result.addElement("ClassName and SuperClassName indexes should point to constant pool of type Class.");
            bool = false;
        }
        if ((0 == this.cpThisClass.refUTF8.sUTFStr.length()) || (0 == this.cpSuperClass.refUTF8.sUTFStr.length())) {
            result.addElement("Class and SuperClass names can not be empty.");
            bool = false;
        }
        return bool;
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

    /*technical stuff*/

    @Override
    public String toString() {
        String str = "This class: " + this.cpThisClass + ", ";
        str = str + "Super class: " + this.cpSuperClass;
        return str;
    }

}
