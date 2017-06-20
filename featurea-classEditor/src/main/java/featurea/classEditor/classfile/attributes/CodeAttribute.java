package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class CodeAttribute
        extends Attribute {
    public static final String NAME = "Code";
    public int iMaxStack;
    public int iMaxLocals;
    public Code code;
    public Vector vectExceptionTableEntries;
    public Attributes codeAttributes;
    int iAttribLength;

    public CodeAttribute() {
        this.sName = "Code";
    }

    void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        this.iMaxStack = paramDataInputStream.readUnsignedShort();
        this.iMaxLocals = paramDataInputStream.readUnsignedShort();
        this.code = new Code();
        this.code.read(paramDataInputStream, paramConstantPool);
        this.vectExceptionTableEntries = new Vector();
        int i = paramDataInputStream.readUnsignedShort();
        for (int j = 0; j < i; j++) {
            ExceptionTableEntry localExceptionTableEntry = new ExceptionTableEntry();
            localExceptionTableEntry.read(paramDataInputStream, paramConstantPool);
            this.vectExceptionTableEntries.add(localExceptionTableEntry);
        }
        this.codeAttributes = new Attributes();
        this.codeAttributes.read(paramDataInputStream, paramConstantPool);
    }

    void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        paramDataOutputStream.writeShort(this.iMaxStack);
        paramDataOutputStream.writeShort(this.iMaxLocals);
        this.code.write(paramDataOutputStream, paramConstantPool);
        int i = this.vectExceptionTableEntries.size();
        paramDataOutputStream.writeShort(i);
        for (int j = 0; j < i; j++) {
            ExceptionTableEntry localExceptionTableEntry = (ExceptionTableEntry) this.vectExceptionTableEntries.elementAt(j);
            localExceptionTableEntry.write(paramDataOutputStream, paramConstantPool);
        }
        this.codeAttributes.write(paramDataOutputStream, paramConstantPool);
    }

    public boolean verify(String paramString, Vector paramVector) {
        return true;
    }

    public String toString() {
        return "Attribute " + this.sName + ". Length=" + this.iAttribLength;
    }

    public void addNewExceptionTableEntry() {
        ExceptionTableEntry localExceptionTableEntry = new ExceptionTableEntry();
        this.vectExceptionTableEntries.add(localExceptionTableEntry);
    }

    public void deleteExceptionTableEntryAt(int paramInt) {
        ExceptionTableEntry localExceptionTableEntry = (ExceptionTableEntry) this.vectExceptionTableEntries.elementAt(paramInt);
        if (null == localExceptionTableEntry) {
            return;
        }
        if (null != localExceptionTableEntry.cpCatchType) {
            localExceptionTableEntry.cpCatchType.deleteRef();
        }
        this.vectExceptionTableEntries.removeElementAt(paramInt);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/CodeAttribute.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */