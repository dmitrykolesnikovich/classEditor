package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Fields {
    int iFieldsCount;
    Vector fieldsVect;

    void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iFieldsCount = paramDataInputStream.readUnsignedShort();
        this.fieldsVect = new Vector(this.iFieldsCount);
        for (int i = 0; i < this.iFieldsCount; i++) {
            FieldInfo localFieldInfo = new FieldInfo();
            localFieldInfo.read(paramDataInputStream, paramConstantPool);
            this.fieldsVect.addElement(localFieldInfo);
        }
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iFieldsCount = this.fieldsVect.size();
        paramDataOutputStream.writeShort(this.iFieldsCount);
        for (int i = 0; i < this.iFieldsCount; i++) {
            FieldInfo localFieldInfo = (FieldInfo) this.fieldsVect.elementAt(i);
            localFieldInfo.write(paramDataOutputStream, paramConstantPool);
        }
    }

    public boolean verify(Vector paramVector) {
        boolean bool = true;
        for (int i = 0; i < this.iFieldsCount; i++) {
            FieldInfo localFieldInfo = (FieldInfo) this.fieldsVect.elementAt(i);
            bool = (localFieldInfo.verify("Field " + (i + 1) + "(" + localFieldInfo.getFieldName() + ")", paramVector)) && (bool);
        }
        return bool;
    }

    public String toString() {
        String str2 = System.getProperty("line.separator");
        this.iFieldsCount = this.fieldsVect.size();
        String str1 = "Fields count: " + this.iFieldsCount + str2;
        int i = 0;
        while (i < this.iFieldsCount) {
            str1 = str1 + this.fieldsVect.elementAt(i++).toString() + str2;
        }
        return str1;
    }

    public FieldInfo getField(int paramInt) {
        if (0 == this.iFieldsCount) {
            return null;
        }
        return (FieldInfo) this.fieldsVect.elementAt(paramInt);
    }

    public int getFieldsCount() {
        return this.iFieldsCount;
    }

    public void deleteField(int paramInt) {
        FieldInfo localFieldInfo = (FieldInfo) this.fieldsVect.elementAt(paramInt);
        localFieldInfo.removeReferences();
        this.fieldsVect.removeElementAt(paramInt);
        this.iFieldsCount -= 1;
    }

    public void addField(FieldInfo paramFieldInfo) {
        paramFieldInfo.addReference();
        this.fieldsVect.addElement(paramFieldInfo);
        this.iFieldsCount += 1;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/Fields.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */