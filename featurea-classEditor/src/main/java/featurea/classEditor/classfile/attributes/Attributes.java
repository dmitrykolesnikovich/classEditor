package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Attributes {
    public int iAttributesCount = 0;
    public Vector attribVect = new Vector();

    public void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttributesCount = paramDataInputStream.readUnsignedShort();
        this.attribVect = new Vector(this.iAttributesCount);
        for (int i = 0; i < this.iAttributesCount; i++) {
            Attribute localAttribute = Attribute.readAndCreate(paramDataInputStream, paramConstantPool);
            this.attribVect.addElement(localAttribute);
        }
    }

    public void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttributesCount = this.attribVect.size();
        paramDataOutputStream.writeShort(this.iAttributesCount);
        for (int i = 0; i < this.iAttributesCount; i++) {
            Attribute localAttribute = (Attribute) this.attribVect.elementAt(i);
            localAttribute.write(paramDataOutputStream, paramConstantPool);
        }
    }

    public String toString() {
        this.iAttributesCount = this.attribVect.size();
        String str = "Attributes count: " + this.iAttributesCount + Utils.sNewLine;
        int i = 0;
        while (i < this.iAttributesCount) {
            str = str + this.attribVect.elementAt(i++).toString() + Utils.sNewLine;
        }
        return str;
    }

    public boolean verify(String paramString, Vector paramVector) {
        boolean bool = true;
        for (int i = 0; i < this.iAttributesCount; i++) {
            Attribute localAttribute = (Attribute) this.attribVect.elementAt(i);
            bool = (localAttribute.verify(paramString, paramVector)) && (bool);
        }
        return bool;
    }

    public int getAttribCount() {
        this.iAttributesCount = this.attribVect.size();
        return this.iAttributesCount;
    }

    public Attribute getAttribute(int paramInt) {
        return (Attribute) this.attribVect.elementAt(paramInt);
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/Attributes.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */