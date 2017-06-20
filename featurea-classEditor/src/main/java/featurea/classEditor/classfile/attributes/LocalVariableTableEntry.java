package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalVariableTableEntry {
    public int iStartPC;
    public int iLength;
    public int iIndex;
    public ConstantPoolInfo cpName;
    public ConstantPoolInfo cpDescriptor;
    public ConstantPool constPool;

    void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.constPool = paramConstantPool;
        this.iStartPC = paramDataInputStream.readUnsignedShort();
        this.iLength = paramDataInputStream.readUnsignedShort();
        int i = paramDataInputStream.readUnsignedShort();
        int j = paramDataInputStream.readUnsignedShort();
        this.iIndex = paramDataInputStream.readUnsignedShort();
        this.cpName = (i > 0 ? this.constPool.getPoolInfo(i) : null);
        this.cpDescriptor = (j > 0 ? this.constPool.getPoolInfo(j) : null);
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.constPool = paramConstantPool;
        paramDataOutputStream.writeShort(this.iStartPC);
        paramDataOutputStream.writeShort(this.iLength);
        int i = null != this.cpName ? this.constPool.getIndexOf(this.cpName) : 0;
        paramDataOutputStream.writeShort(i);
        int j = null != this.cpDescriptor ? this.constPool.getIndexOf(this.cpDescriptor) : 0;
        paramDataOutputStream.writeShort(j);
        paramDataOutputStream.writeShort(this.iIndex);
    }

    public void addRef() {
        if (null != this.cpName) {
            this.cpName.addRef();
        }
        if (null != this.cpDescriptor) {
            this.cpDescriptor.addRef();
        }
    }

    public void deleteRef() {
        if (null != this.cpName) {
            this.cpName.deleteRef();
        }
        if (null != this.cpDescriptor) {
            this.cpDescriptor.deleteRef();
        }
    }

    public String toString() {
        int i = null != this.cpName ? this.constPool.getIndexOf(this.cpName) : 0;
        int j = null != this.cpDescriptor ? this.constPool.getIndexOf(this.cpDescriptor) : 0;
        return "start_pc=" + Integer.toString(this.iStartPC) + " length=" + Integer.toString(this.iLength) + " name_index=" + Integer.toString(i) + " desc_index=" + Integer.toString(j) + " index=" + Integer.toString(this.iIndex);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/LocalVariableTableEntry.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */