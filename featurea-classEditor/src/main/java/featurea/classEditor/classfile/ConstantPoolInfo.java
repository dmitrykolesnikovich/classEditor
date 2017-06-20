package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ConstantPoolInfo {

    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_Fieldref = 9;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_InterfaceMethodref = 11;
    public static final int CONSTANT_String = 8;
    public static final int CONSTANT_Integer = 3;
    public static final int CONSTANT_Float = 4;
    public static final int CONSTANT_Long = 5;
    public static final int CONSTANT_Double = 6;
    public static final int CONSTANT_NameAndType = 12;
    public static final int CONSTANT_Utf8 = 1;
    public int iTag;
    public int iNameIndex;
    public int iClassIndex;
    public int iNameAndTypeIndex;
    public int iStringIndex;
    public int iIntValue;
    public float fFloatVal;
    public long lLongVal;
    public int iDescriptorIndex;
    public double dDoubleVal;
    public String sUTFStr;
    public ConstantPoolInfo refUTF8;
    public ConstantPoolInfo refExtraUTF8;
    public ConstantPoolInfo refClass;
    public ConstantPoolInfo refNameAndType;
    public int iNumRefs;
    public ConstantPool constPool;

    public boolean verify(String paramString, Vector result) {
        boolean bool = true;
        switch (this.iTag) {
            case 7:
                if ((null == this.refUTF8) || (1 != this.refUTF8.iTag)) {
                    result.addElement(paramString + ": Constant class should point to a UTF8 pool item.");
                    bool = false;
                }
                break;
            case 8:
                if ((null == this.refUTF8) || (1 != this.refUTF8.iTag)) {
                    result.addElement(paramString + ": Constant string should point to a UTF8 pool item.");
                    bool = false;
                }
                break;
            case 9:
            case 10:
            case 11:
                if ((null == this.refClass) || (7 != this.refClass.iTag)) {
                    result.addElement(paramString + ": Class index of constant field/method/interfacemethod ref should point to a Class pool item.");
                    bool = false;
                }
                if ((null == this.refNameAndType) || (12 != this.refNameAndType.iTag)) {
                    result.addElement(paramString + ": NameAndType index of constant field/method/interfacemethod ref should point to a NameAndType pool item.");
                    bool = false;
                }
                break;
            case 12:
                if ((null == this.refUTF8) || (1 != this.refUTF8.iTag)) {
                    result.addElement(paramString + ": Name index of NameAndType ref should point to a UTF8 pool item.");
                    bool = false;
                }
                if ((null == this.refExtraUTF8) || (1 != this.refExtraUTF8.iTag)) {
                    result.addElement(paramString + ": Descriptor index of NameAndType ref should point to a UTF8 pool item.");
                    bool = false;
                }
                break;
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            case 2:
            default:
                result.addElement(paramString + ": Constant pool type not recognized.");
                bool = false;
        }
        return bool;
    }

    public void read(DataInputStream paramDataInputStream) throws IOException {
        this.iTag = paramDataInputStream.readByte();
        switch (this.iTag) {
            case 7:
                this.iNameIndex = paramDataInputStream.readUnsignedShort();
                break;
            case 8:
                this.iStringIndex = paramDataInputStream.readUnsignedShort();
                break;
            case 9:
            case 10:
            case 11:
                this.iClassIndex = paramDataInputStream.readUnsignedShort();
                this.iNameAndTypeIndex = paramDataInputStream.readUnsignedShort();
                break;
            case 3:
                this.iIntValue = paramDataInputStream.readInt();
                break;
            case 4:
                this.fFloatVal = paramDataInputStream.readFloat();
                break;
            case 12:
                this.iNameIndex = paramDataInputStream.readUnsignedShort();
                this.iDescriptorIndex = paramDataInputStream.readUnsignedShort();
                break;
            case 5:
                this.lLongVal = paramDataInputStream.readLong();
                break;
            case 6:
                this.dDoubleVal = paramDataInputStream.readDouble();
                break;
            case 1:
                this.sUTFStr = paramDataInputStream.readUTF();
                break;
            case 2:
            default:
                System.out.println("Unknown constant pool type: " + this.iTag);
        }
    }

    public void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        if (null != paramConstantPool) {
            this.constPool = paramConstantPool;
        }
        paramDataOutputStream.writeByte(this.iTag);
        switch (this.iTag) {
            case 7:
                this.iNameIndex = this.constPool.getIndexOf(this.refUTF8);
                paramDataOutputStream.writeShort(this.iNameIndex);
                break;
            case 8:
                this.iStringIndex = this.constPool.getIndexOf(this.refUTF8);
                paramDataOutputStream.writeShort(this.iStringIndex);
                break;
            case 9:
            case 10:
            case 11:
                this.iClassIndex = this.constPool.getIndexOf(this.refClass);
                this.iNameAndTypeIndex = this.constPool.getIndexOf(this.refNameAndType);
                paramDataOutputStream.writeShort(this.iClassIndex);
                paramDataOutputStream.writeShort(this.iNameAndTypeIndex);
                break;
            case 3:
                paramDataOutputStream.writeInt(this.iIntValue);
                break;
            case 4:
                paramDataOutputStream.writeFloat(this.fFloatVal);
                break;
            case 12:
                this.iNameIndex = this.constPool.getIndexOf(this.refUTF8);
                this.iDescriptorIndex = this.constPool.getIndexOf(this.refExtraUTF8);
                paramDataOutputStream.writeShort(this.iNameIndex);
                paramDataOutputStream.writeShort(this.iDescriptorIndex);
                break;
            case 5:
                paramDataOutputStream.writeLong(this.lLongVal);
                break;
            case 6:
                paramDataOutputStream.writeDouble(this.dDoubleVal);
                break;
            case 1:
                paramDataOutputStream.writeUTF(this.sUTFStr);
                break;
            case 2:
            default:
                System.out.println("Unknown constant pool type: " + this.iTag);
        }
    }

    public void setNameIndex(int paramInt) {
        this.iNameIndex = paramInt;
        if (null != this.refUTF8) {
            this.refUTF8.deleteRef();
        }
        this.refUTF8 = this.constPool.getPoolInfo(this.iNameIndex);
        this.refUTF8.addRef();
    }

    public void setConstPool(ConstantPool paramConstantPool) {
        this.constPool = paramConstantPool;
    }

    public void setStringIndex(int paramInt) {
        this.iStringIndex = paramInt;
        if (null != this.refUTF8) {
            this.refUTF8.deleteRef();
        }
        this.refUTF8 = this.constPool.getPoolInfo(this.iStringIndex);
        this.refUTF8.addRef();
    }

    public void setDescriptorIndex(int paramInt) {
        this.iDescriptorIndex = paramInt;
        if (null != this.refExtraUTF8) {
            this.refExtraUTF8.deleteRef();
        }
        this.refExtraUTF8 = this.constPool.getPoolInfo(this.iDescriptorIndex);
        this.refExtraUTF8.addRef();
    }

    public void setClassIndex(int paramInt) {
        this.iClassIndex = paramInt;
        if (null != this.refClass) {
            this.refClass.deleteRef();
        }
        this.refClass = this.constPool.getPoolInfo(this.iClassIndex);
        this.refClass.addRef();
    }

    public void setNameAndTypeIndex(int paramInt) {
        this.iNameAndTypeIndex = paramInt;
        if (null != this.refNameAndType) {
            this.refNameAndType.deleteRef();
        }
        this.refNameAndType = this.constPool.getPoolInfo(this.iNameAndTypeIndex);
        this.refNameAndType.addRef();
    }

    void resolveReferences(ConstantPool paramConstantPool) {
        this.constPool = paramConstantPool;
        switch (this.iTag) {
            case 7:
                this.refUTF8 = paramConstantPool.getPoolInfo(this.iNameIndex);
                this.refUTF8.addRef();
                break;
            case 8:
                this.refUTF8 = paramConstantPool.getPoolInfo(this.iStringIndex);
                this.refUTF8.addRef();
                break;
            case 9:
            case 10:
            case 11:
                this.refClass = paramConstantPool.getPoolInfo(this.iClassIndex);
                this.refClass.addRef();
                this.refNameAndType = paramConstantPool.getPoolInfo(this.iNameAndTypeIndex);
                this.refNameAndType.addRef();
                break;
            case 12:
                this.refUTF8 = paramConstantPool.getPoolInfo(this.iNameIndex);
                this.refUTF8.addRef();
                this.refExtraUTF8 = paramConstantPool.getPoolInfo(this.iDescriptorIndex);
                this.refExtraUTF8.addRef();
        }
    }

    public String tag2Column() {
        String str = "Unknown";
        switch (this.iTag) {
            case 7:
                str = "CLASS";
                break;
            case 9:
                str = "FIELDREF";
                break;
            case 10:
                str = "METHODREF";
                break;
            case 11:
                str = "INTERFACEMETHODREF";
                break;
            case 8:
                str = "STRING";
                break;
            case 3:
                str = "INTEGER";
                break;
            case 4:
                str = "FLOAT";
                break;
            case 5:
                str = "LONG";
                break;
            case 6:
                str = "DOUBLE";
                break;
            case 12:
                str = "NAMEANDTYPE";
                break;
            case 1:
                str = "UTF8";
        }
        return str;
    }

    public String getExtraInfoString() {
        String str = "";
        switch (this.iTag) {
            case 7:
                str = "name=" + this.refUTF8.sUTFStr;
                break;
            case 9:
            case 10:
            case 11:
                str = "class=" + this.refClass.refUTF8.sUTFStr + ", name=" + this.refNameAndType.refUTF8.sUTFStr + ", type=" + this.refNameAndType.refExtraUTF8.sUTFStr;
                break;
            case 8:
                str = "string=" + this.refUTF8.sUTFStr;
                break;
            case 3:
                str = "int_value=" + this.iIntValue;
                break;
            case 4:
                str = "float_value=" + this.fFloatVal;
                break;
            case 5:
                str = "long_value=" + this.lLongVal;
                break;
            case 6:
                str = "double_value=" + this.dDoubleVal;
                break;
            case 12:
                str = "name=" + this.refUTF8.sUTFStr + ", descriptor=" + this.refExtraUTF8.sUTFStr;
                break;
            case 1:
                str = "string=" + this.sUTFStr;
        }
        return str;
    }

    public boolean isDoubleSizeConst() {
        return (5 == this.iTag) || (6 == this.iTag);
    }

    public void addRef() {
        this.iNumRefs += 1;
    }

    public void deleteRef() {
        this.iNumRefs -= 1;
        if (0 == this.iNumRefs) {
            switch (this.iTag) {
                case 7:
                    this.refUTF8.deleteRef();
                    break;
                case 8:
                    this.refUTF8.deleteRef();
                    break;
                case 9:
                case 10:
                case 11:
                    this.refClass.deleteRef();
                    this.refNameAndType.deleteRef();
                    break;
                case 12:
                    this.refUTF8.deleteRef();
                    this.refExtraUTF8.deleteRef();
            }
        }
    }

    public int getRef() {
        return this.iNumRefs;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        return tag2Column() + ": " + getExtraInfoString();
    }

}
