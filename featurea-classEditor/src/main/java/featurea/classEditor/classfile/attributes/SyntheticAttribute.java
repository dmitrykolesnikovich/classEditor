package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class SyntheticAttribute
        extends Attribute {
    public SyntheticAttribute() {
        this.sName = "Synthetic";
    }

    void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
    }

    void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
    }

    public boolean verify(String paramString, Vector paramVector) {
        boolean bool = true;
        if (this.iAttribLength != 0) {
            paramVector.addElement(": Synthetic attribute length must be zero.");
            bool = false;
        }
        return bool;
    }

    public String toString() {
        return "Synthetic";
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/SyntheticAttribute.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */