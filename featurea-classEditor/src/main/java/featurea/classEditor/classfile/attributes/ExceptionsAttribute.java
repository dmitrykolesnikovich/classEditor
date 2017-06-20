package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ExceptionsAttribute extends Attribute {

    public int iNumExceptions;
    public Vector vectExceptionTypes;

    public ExceptionsAttribute() {
        this.sName = "Exceptions";
    }

    @Override
    public void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        if ((this.iNumExceptions = paramDataInputStream.readUnsignedShort()) > 0) {
            this.vectExceptionTypes = new Vector(this.iNumExceptions);
        }
        for (int i = 0; i < this.iNumExceptions; i++) {
            int j = paramDataInputStream.readUnsignedShort();
            ConstantPoolInfo localConstantPoolInfo = paramConstantPool.getPoolInfo(j);
            localConstantPoolInfo.addRef();
            this.vectExceptionTypes.addElement(localConstantPoolInfo);
        }
    }

    @Override
    public void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        this.iNumExceptions = this.vectExceptionTypes.size();
        paramDataOutputStream.writeShort(this.iNumExceptions);
        for (int i = 0; i < this.iNumExceptions; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectExceptionTypes.elementAt(i);
            int j = paramConstantPool.getIndexOf(localConstantPoolInfo);
            paramDataOutputStream.writeShort(j);
        }
    }

    public void deleteExceptionAt(int paramInt) {
        if (paramInt >= this.iNumExceptions) {
            return;
        }
        ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectExceptionTypes.elementAt(paramInt);
        localConstantPoolInfo.deleteRef();
        this.iNumExceptions -= 1;
        this.vectExceptionTypes.removeElementAt(paramInt);
    }

    public int getExceptionCount() {
        return this.iNumExceptions;
    }

    public String[] getExceptionNames() {
        String[] arrayOfString = null;
        if (this.iNumExceptions > 0) {
            arrayOfString = new String[this.iNumExceptions];
            for (int i = 0; i < this.iNumExceptions; i++) {
                ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectExceptionTypes.elementAt(i);
                arrayOfString[i] = localConstantPoolInfo.refUTF8.sUTFStr;
            }
        }
        return arrayOfString;
    }

    @Override
    public boolean verify(String paramString, Vector result) {
        boolean bool = true;
        if (this.iNumExceptions > 0) {
            for (int i = 0; i < this.iNumExceptions; i++) {
                ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectExceptionTypes.elementAt(i);
                if (7 != localConstantPoolInfo.iTag) {
                    result.addElement(": Exception " + (i + 1) + " must point to a constant pool of type Class.");
                    bool = false;
                }
            }
        }
        return bool;
    }

    @Override
    public String toString() {
        String str = "Attribute " + this.sName + ". Number=" + this.iNumExceptions;
        return str;
    }
}
