package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class SourceFileAttribute extends Attribute {

    public ConstantPoolInfo cpSourceFile;
    int iSourceFileIndex;

    public SourceFileAttribute() {
        this.sName = "SourceFile";
    }

    @Override
    public void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        this.iSourceFileIndex = paramDataInputStream.readUnsignedShort();
        this.cpSourceFile = paramConstantPool.getPoolInfo(this.iSourceFileIndex);
        this.cpSourceFile.addRef();
    }

    @Override
    public void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        this.iSourceFileIndex = paramConstantPool.getIndexOf(this.cpSourceFile);
        paramDataOutputStream.writeShort(this.iSourceFileIndex);
    }

    @Override
    public boolean verify(String paramString, Vector result) {
        if ((null == this.cpSourceFile) || (1 != this.cpSourceFile.iTag)) {
            result.addElement(paramString + ": Should point to a UTF8 constant pool.");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Attribute " + this.sName + ". Source=" + this.cpSourceFile.sUTFStr;
    }

}
