package featurea.classEditor.classfile;

import featurea.classEditor.classfile.attributes.Attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class FieldInfo {

    public AccessFlags accessFlags;
    public int iNameIndex;
    public int iDescriptorIndex;
    public Attributes attributes;
    public ConstantPoolInfo cpName;
    public ConstantPoolInfo cpDescriptor;

    public void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.accessFlags = new AccessFlags();
        this.accessFlags.read(paramDataInputStream);
        this.iNameIndex = paramDataInputStream.readUnsignedShort();
        this.cpName = paramConstantPool.getPoolInfo(this.iNameIndex);
        this.iDescriptorIndex = paramDataInputStream.readUnsignedShort();
        this.cpDescriptor = paramConstantPool.getPoolInfo(this.iDescriptorIndex);
        this.attributes = new Attributes();
        this.attributes.read(paramDataInputStream, paramConstantPool);
        addReference();
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.accessFlags.write(paramDataOutputStream);
        this.iNameIndex = paramConstantPool.getIndexOf(this.cpName);
        paramDataOutputStream.writeShort(this.iNameIndex);
        this.iDescriptorIndex = paramConstantPool.getIndexOf(this.cpDescriptor);
        paramDataOutputStream.writeShort(this.iDescriptorIndex);
        this.attributes.write(paramDataOutputStream, paramConstantPool);
    }

    public void removeReferences() {
        this.cpName.deleteRef();
        this.cpDescriptor.deleteRef();
    }

    public void addReference() {
        this.cpName.addRef();
        this.cpDescriptor.addRef();
    }

    public String getFieldName() {
        return this.cpName.sUTFStr;
    }

    public void setFieldName(String paramString) {
        this.cpName.sUTFStr = paramString;
    }

    public String getFieldDescriptor() {
        String str = this.cpDescriptor.sUTFStr;
        return Utils.getReadableDesc(str);
    }

    public void setFieldDescriptor(String paramString) {
        String str = Utils.getRawDesc(paramString);
        this.cpDescriptor.sUTFStr = str;
    }

    public boolean verify(String value, Vector result) {
        boolean bool = true;
        bool = (this.accessFlags.verify(value, result, false)) && (bool);
        bool = (this.attributes.verify(value, result)) && (bool);
        if ((1 != this.cpName.iTag) || (this.cpName.sUTFStr.length() == 0)) {
            result.addElement(value + ": Field name must point to a constant UTF8 and can not be empty.");
            bool = false;
        }
        if ((1 != this.cpDescriptor.iTag) || (this.cpDescriptor.sUTFStr.length() == 0)) {
            result.addElement(value + ": Field descriptor must point to a constant UTF8 and can not be empty.");
            bool = false;
        }
        return bool;
    }

    @Override
    public String toString() {
        String str = "FieldInfo:" + this.accessFlags.toString() + " Name: " + this.cpName + " Desc: " + this.cpDescriptor + " Attribs: " + this.attributes.toString();
        return str;
    }

}
