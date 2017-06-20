package featurea.classEditor.classfile;

import featurea.classEditor.classfile.attributes.Attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class MethodInfo {

    public AccessFlags accessFlags;
    public Attributes attributes;
    public ConstantPoolInfo cpName;
    public ConstantPoolInfo cpDescriptor;
    private int iNameIndex;
    private int iDescriptorIndex;
    private ConstantPool constPool;

    public void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.constPool = paramConstantPool;
        this.accessFlags = new AccessFlags();
        this.accessFlags.read(paramDataInputStream);
        this.iNameIndex = paramDataInputStream.readUnsignedShort();
        this.cpName = paramConstantPool.getPoolInfo(this.iNameIndex);
        this.iDescriptorIndex = paramDataInputStream.readUnsignedShort();
        this.cpDescriptor = paramConstantPool.getPoolInfo(this.iDescriptorIndex);
        this.attributes = new Attributes();
        this.attributes.read(paramDataInputStream, paramConstantPool);
        addReferences();
    }

    public void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        this.accessFlags.write(paramDataOutputStream);
        this.iNameIndex = paramConstantPool.getIndexOf(this.cpName);
        paramDataOutputStream.writeShort(this.iNameIndex);
        this.iDescriptorIndex = paramConstantPool.getIndexOf(this.cpDescriptor);
        paramDataOutputStream.writeShort(this.iDescriptorIndex);
        this.attributes.write(paramDataOutputStream, paramConstantPool);
    }

    public void addReferences() {
        this.cpName.addRef();
        this.cpDescriptor.addRef();
    }

    public void removeReferences() {
        this.cpName.deleteRef();
        this.cpDescriptor.deleteRef();
    }

    public String getMethodName() {
        return this.cpName.sUTFStr;
    }

    public void setMethodName(String paramString) {
        this.cpName.sUTFStr = paramString;
    }

    public String[] getMethodDesc() {
        return Utils.getReadableMethodDesc(this.cpDescriptor.sUTFStr);
    }

    public void setMethodDesc(String[] paramArrayOfString) {
        String str = Utils.getRawMethodDesc(paramArrayOfString);
        this.cpDescriptor.sUTFStr = str;
    }

    public boolean verify(String paramString, Vector result) {
        boolean bool = true;
        bool = (this.accessFlags.verify(paramString, result, false)) && (bool);
        bool = (this.attributes.verify(paramString, result)) && (bool);
        if ((1 != this.cpName.iTag) || (this.cpName.sUTFStr.length() == 0)) {
            result.addElement(paramString + ": Method name must point to a constant UTF8 and can not be empty.");
            bool = false;
        }
        if ((1 != this.cpDescriptor.iTag) || (this.cpDescriptor.sUTFStr.length() == 0)) {
            result.addElement(paramString + ": Method descriptor must point to a constant UTF8 and can not be empty.");
            bool = false;
        }
        return bool;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        String str = "MethodInfo:" + this.accessFlags.toString() + " Name: " + this.cpName.sUTFStr + " Desc: " + this.cpDescriptor.sUTFStr + " Attribs: " + this.attributes.toString();
        return str;
    }

}
