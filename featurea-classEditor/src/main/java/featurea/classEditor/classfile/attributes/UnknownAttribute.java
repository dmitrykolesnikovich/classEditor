package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class UnknownAttribute
        extends Attribute {
    public byte[] abUnknAttr;

    public UnknownAttribute() {
        this.sName = "Unknown";
    }

    void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        this.abUnknAttr = new byte[this.iAttribLength];
        paramDataInputStream.read(this.abUnknAttr);
    }

    void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        paramDataOutputStream.write(this.abUnknAttr);
    }

    public String toString() {
        return "Unrecognized Attribute. Length=" + this.iAttribLength;
    }

    public boolean verify(String paramString, Vector paramVector) {
        return true;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/UnknownAttribute.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */