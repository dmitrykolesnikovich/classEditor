package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public abstract class Attribute {
    static final String SOURCE_FILE = "SourceFile";
    static final String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    static final String LINE_NUMBER_TABLE = "LineNumberTable";
    static final String EXCEPTIONS = "Exceptions";
    static final String CONSTANT_VALUE = "ConstantValue";
    static final String CODE = "Code";
    static final String INNER_CLASSES = "InnerClasses";
    static final String SYNTHETIC = "Synthetic";
    static final String DEPRECATED = "Deprecated";
    static final String UNKNOWN = "Unknown";
    public int iAttribNameIndex;
    public ConstantPoolInfo cpAttribName;
    public String sName;
    int iAttribLength;

    static Attribute readAndCreate(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        int i = paramDataInputStream.readUnsignedShort();
        ConstantPoolInfo localConstantPoolInfo = paramConstantPool.getPoolInfo(i);
        localConstantPoolInfo.addRef();
        Attribute localAttribute = createAttrib(localConstantPoolInfo.sUTFStr);
        localAttribute.cpAttribName = localConstantPoolInfo;
        localAttribute.iAttribNameIndex = i;
        localAttribute.readAttributeDetails(paramDataInputStream, paramConstantPool);
        return localAttribute;
    }

    static Attribute createAttrib(String paramString) {
        if ("SourceFile".equals(paramString)) {
            return new SourceFileAttribute();
        }
        if ("ConstantValue".equals(paramString)) {
            return new ConstantValueAttribute();
        }
        if ("Exceptions".equals(paramString)) {
            return new ExceptionsAttribute();
        }
        if ("Code".equals(paramString)) {
            return new CodeAttribute();
        }
        if ("LineNumberTable".equals(paramString)) {
            return new LineNumberTableAttribute();
        }
        if ("LocalVariableTable".equals(paramString)) {
            return new LocalVariableTableAttribute();
        }
        if ("InnerClasses".equals(paramString)) {
            return new InnerClassesAttribute();
        }
        if ("Synthetic".equals(paramString)) {
            return new SyntheticAttribute();
        }
        if ("Deprecated".equals(paramString)) {
            return new DeprecatedAttribute();
        }
        return new UnknownAttribute();
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iAttribNameIndex = paramConstantPool.getIndexOf(this.cpAttribName);
        paramDataOutputStream.writeShort(this.iAttribNameIndex);
        writeAttributeDetails(paramDataOutputStream, paramConstantPool);
    }

    abstract void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException;

    abstract void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException;

    public abstract String toString();

    public abstract boolean verify(String paramString, Vector paramVector);
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/Attribute.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */