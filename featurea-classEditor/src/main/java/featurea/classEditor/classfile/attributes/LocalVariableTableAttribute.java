package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class LocalVariableTableAttribute
        extends Attribute {
    public Vector vectLocalVariableTable;
    private ConstantPool constPool;

    public LocalVariableTableAttribute() {
        this.sName = "LocalVariableTable";
    }

    void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.constPool = paramConstantPool;
        this.iAttribLength = paramDataInputStream.readInt();
        this.vectLocalVariableTable = new Vector();
        int i;
        if ((i = paramDataInputStream.readUnsignedShort()) > 0) {
            for (int j = 0; j < i; j++) {
                LocalVariableTableEntry localLocalVariableTableEntry = new LocalVariableTableEntry();
                localLocalVariableTableEntry.read(paramDataInputStream, this.constPool);
                localLocalVariableTableEntry.addRef();
                this.vectLocalVariableTable.add(localLocalVariableTableEntry);
            }
        }
    }

    void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        int i = this.vectLocalVariableTable.size();
        paramDataOutputStream.writeInt(this.iAttribLength);
        paramDataOutputStream.writeShort(i);
        if (i > 0) {
            for (int j = 0; j < i; j++) {
                LocalVariableTableEntry localLocalVariableTableEntry = (LocalVariableTableEntry) this.vectLocalVariableTable.elementAt(j);
                localLocalVariableTableEntry.write(paramDataOutputStream, paramConstantPool);
            }
        }
    }

    public void addEntry(LocalVariableTableEntry paramLocalVariableTableEntry) {
        paramLocalVariableTableEntry.constPool = this.constPool;
        paramLocalVariableTableEntry.iIndex = this.vectLocalVariableTable.size();
        this.vectLocalVariableTable.add(paramLocalVariableTableEntry);
        paramLocalVariableTableEntry.addRef();
    }

    public void deleteEntryAt(int paramInt) {
        LocalVariableTableEntry localLocalVariableTableEntry = (LocalVariableTableEntry) this.vectLocalVariableTable.elementAt(paramInt);
        localLocalVariableTableEntry.deleteRef();
        this.vectLocalVariableTable.removeElementAt(paramInt);
    }

    public String toString() {
        int i = this.vectLocalVariableTable.size();
        return "Attribute " + this.sName + ". Length=" + this.iAttribLength + ". TableLength=" + i;
    }

    public boolean verify(String paramString, Vector paramVector) {
        return true;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/LocalVariableTableAttribute.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */