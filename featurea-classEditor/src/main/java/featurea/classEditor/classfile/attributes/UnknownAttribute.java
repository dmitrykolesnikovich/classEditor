package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class UnknownAttribute extends Attribute {

    public byte[] abUnknAttr;

    public UnknownAttribute() {
        this.sName = "Unknown";
    }

    @Override
    public void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        this.abUnknAttr = new byte[this.iAttribLength];
        paramDataInputStream.read(this.abUnknAttr);
    }

    @Override
    public void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        paramDataOutputStream.write(this.abUnknAttr);
    }

    @Override
    public boolean verify(String paramString, Vector result) {
        return true;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        return "Unrecognized Attribute. Length=" + this.iAttribLength;
    }

}
