package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.AccessFlags;
import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.classfile.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class InnerClassInfo {

    public AccessFlags accFlags = new AccessFlags();
    public ConstantPoolInfo cpInnerClass;
    public ConstantPoolInfo cpOuterClass;
    public ConstantPoolInfo cpInnerName;
    int iInnerClassInfoIndex;
    int iOuterClassInfoIndex;
    int iInnerNameIndex;

    public void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iInnerClassInfoIndex = paramDataInputStream.readUnsignedShort();
        this.iOuterClassInfoIndex = paramDataInputStream.readUnsignedShort();
        this.iInnerNameIndex = paramDataInputStream.readUnsignedShort();
        this.accFlags.read(paramDataInputStream);
        this.cpInnerClass = (this.cpOuterClass = this.cpInnerName = null);
        if (0 != this.iInnerClassInfoIndex) {
            this.cpInnerClass = paramConstantPool.getPoolInfo(this.iInnerClassInfoIndex);
        }
        if (0 != this.iOuterClassInfoIndex) {
            this.cpOuterClass = paramConstantPool.getPoolInfo(this.iOuterClassInfoIndex);
        }
        if (0 != this.iInnerNameIndex) {
            this.cpInnerName = paramConstantPool.getPoolInfo(this.iInnerNameIndex);
        }
    }

    public void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        this.iInnerClassInfoIndex = (this.iOuterClassInfoIndex = this.iInnerNameIndex = 0);
        if (null != this.cpInnerClass) {
            this.iInnerClassInfoIndex = paramConstantPool.getIndexOf(this.cpInnerClass);
        }
        if (null != this.cpOuterClass) {
            this.iOuterClassInfoIndex = paramConstantPool.getIndexOf(this.cpOuterClass);
        }
        if (null != this.cpInnerName) {
            this.iInnerNameIndex = paramConstantPool.getIndexOf(this.cpInnerName);
        }
        paramDataOutputStream.writeShort(this.iInnerClassInfoIndex);
        paramDataOutputStream.writeShort(this.iOuterClassInfoIndex);
        paramDataOutputStream.writeShort(this.iInnerNameIndex);
        this.accFlags.write(paramDataOutputStream);
    }

    public boolean verify(String paramString, Vector result) {
        boolean bool = true;
        if ((null != this.cpInnerClass) && (7 != this.cpInnerClass.iTag)) {
            result.addElement(": InnerClassInfoIndex must point to a constant pool of type Class.");
            bool = false;
        }
        if ((null != this.cpOuterClass) && (7 != this.cpOuterClass.iTag)) {
            result.addElement(": OuterClassInfoIndex must point to a constant pool of type Class.");
            bool = false;
        }
        if ((null != this.cpInnerName) && (1 != this.cpInnerName.iTag)) {
            result.addElement(": InnerNameIndex must point to a constant pool of type UTF8.");
            bool = false;
        }
        return bool;
    }

    @Override
    public String toString() {
        String str = "";
        if (null != this.cpInnerClass) {
            str = str + "inner_class=" + Utils.convertClassStrToStr(this.cpInnerClass.refUTF8.sUTFStr) + ",";
        }
        if (null != this.cpOuterClass) {
            str = str + "outer_class=" + Utils.convertClassStrToStr(this.cpOuterClass.refUTF8.sUTFStr) + ",";
        }
        if (null != this.cpInnerName) {
            str = str + "name=" + this.cpInnerName.sUTFStr + ",";
        }
        str = str + this.accFlags.toString();
        return str;
    }

}
