package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class DeprecatedAttribute extends Attribute {

    public DeprecatedAttribute() {
        this.sName = "Deprecated";
    }

    @Override
    public void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
    }

    @Override
    public void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
    }

    @Override
    public boolean verify(String paramString, Vector result) {
        boolean bool = true;
        if (this.iAttribLength != 0) {
            result.addElement(": Deprecated attribute length must be zero.");
            bool = false;
        }
        return bool;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        return "Deprecated";
    }

}
