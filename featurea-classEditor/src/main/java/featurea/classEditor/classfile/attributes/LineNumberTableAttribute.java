package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class LineNumberTableAttribute extends Attribute {

    public Vector vectEntries;

    public LineNumberTableAttribute() {
        this.sName = "LineNumberTable";
    }

    @Override
    public void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        this.vectEntries = new Vector();
        int i;
        if ((i = paramDataInputStream.readUnsignedShort()) > 0) {
            for (int j = 0; j < i; j++) {
                LineNumberTableEntry localLineNumberTableEntry = new LineNumberTableEntry();
                localLineNumberTableEntry.read(paramDataInputStream);
                this.vectEntries.addElement(localLineNumberTableEntry);
            }
        }
    }

    @Override
    public void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        int i = this.vectEntries.size();
        paramDataOutputStream.writeShort(i);
        if (i > 0) {
            for (int j = 0; j < i; j++) {
                ((LineNumberTableEntry) this.vectEntries.elementAt(j)).write(paramDataOutputStream);
            }
        }
    }

    public void addNewEntry() {
        this.vectEntries.addElement(new LineNumberTableEntry());
    }

    public void deleteEntryAt(int paramInt) {
        this.vectEntries.removeElementAt(paramInt);
    }

    @Override
    public boolean verify(String paramString, Vector result) {
        return true;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        return "Attribute " + this.sName + ". Length=" + this.iAttribLength + ". TableLength=" + this.vectEntries.size();
    }

}
