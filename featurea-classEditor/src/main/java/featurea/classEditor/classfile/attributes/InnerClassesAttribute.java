package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class InnerClassesAttribute
        extends Attribute {
    int iNumberOfClasses;
    Vector vectInnerClassInfo;

    public InnerClassesAttribute() {
        this.sName = "InnerClasses";
    }

    void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttribLength = paramDataInputStream.readInt();
        this.vectInnerClassInfo = null;
        if ((this.iNumberOfClasses = paramDataInputStream.readUnsignedShort()) > 0) {
            this.vectInnerClassInfo = new Vector();
        }
        for (int i = 0; i < this.iNumberOfClasses; i++) {
            InnerClassInfo localInnerClassInfo = new InnerClassInfo();
            localInnerClassInfo.read(paramDataInputStream, paramConstantPool);
            this.vectInnerClassInfo.add(localInnerClassInfo);
        }
    }

    void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        paramDataOutputStream.writeInt(this.iAttribLength);
        paramDataOutputStream.writeInt(this.iNumberOfClasses);
        for (int i = 0; i < this.iNumberOfClasses; i++) {
            InnerClassInfo localInnerClassInfo = (InnerClassInfo) this.vectInnerClassInfo.elementAt(i);
            localInnerClassInfo.write(paramDataOutputStream, paramConstantPool);
        }
    }

    public int getNumClasses() {
        return this.iNumberOfClasses;
    }

    public InnerClassInfo getInnerClassInfo(int paramInt) {
        return (InnerClassInfo) this.vectInnerClassInfo.elementAt(paramInt);
    }

    public boolean verify(String paramString, Vector paramVector) {
        boolean bool = true;
        if (this.iNumberOfClasses > 0) {
            for (int i = 0; i < this.iNumberOfClasses; i++) {
                InnerClassInfo localInnerClassInfo = (InnerClassInfo) this.vectInnerClassInfo.elementAt(i);
                if (false == localInnerClassInfo.verify(paramString, paramVector)) {
                    bool = false;
                }
            }
        }
        return bool;
    }

    public String toString() {
        String str = "Attribute " + this.sName + ". Number=" + this.iNumberOfClasses;
        return str;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/InnerClassesAttribute.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */