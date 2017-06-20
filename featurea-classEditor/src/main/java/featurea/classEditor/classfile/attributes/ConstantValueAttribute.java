package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ConstantValueAttribute extends Attribute {

    public ConstantPoolInfo cpConstant;
    public String sConstType;
    public String sConstValue;
    private int iConstValueIndex;
    private ConstantPool constPool;

    public ConstantValueAttribute() {
        this.sName = "ConstantValue";
    }

    @Override
    public void readAttributeDetails(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.constPool = paramConstantPool;
        this.iAttribLength = paramDataInputStream.readInt();
        this.iConstValueIndex = paramDataInputStream.readUnsignedShort();
        this.cpConstant = this.constPool.getPoolInfo(this.iConstValueIndex);
        this.cpConstant.addRef();
        switch (this.cpConstant.iTag) {
            case 8:
                this.sConstValue = this.cpConstant.refUTF8.sUTFStr;
                this.sConstType = "java.lang.String";
                break;
            case 3:
                this.sConstValue = Integer.toString(this.cpConstant.iIntValue);
                this.sConstType = "int, short, char, byte, boolean";
                break;
            case 4:
                this.sConstValue = Float.toString(this.cpConstant.fFloatVal);
                this.sConstType = "float";
                break;
            case 5:
                this.sConstValue = Long.toString(this.cpConstant.lLongVal);
                this.sConstType = "long";
                break;
            case 6:
                this.sConstValue = Double.toString(this.cpConstant.dDoubleVal);
                this.sConstType = "double";
                break;
            case 7:
            default:
                this.sConstType = (this.sConstValue = "Unknown type: " + this.cpConstant.iTag);
        }
    }

    @Override
    public boolean verify(String paramString, Vector result) {
        switch (this.cpConstant.iTag) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                return true;
        }
        result.addElement(": Must point to a String, Integer, Float, Long or Double pool type.");
        return false;
    }

    @Override
    public void writeAttributeDetails(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        this.constPool = paramConstantPool;
        paramDataOutputStream.writeInt(this.iAttribLength);
        this.iConstValueIndex = this.constPool.getIndexOf(this.cpConstant);
        paramDataOutputStream.writeShort(this.iConstValueIndex);
    }

    public void setConstantValue(String paramString) {
        switch (this.cpConstant.iTag) {
            case 8:
                this.cpConstant.refUTF8.sUTFStr = paramString;
                break;
            case 3:
                this.cpConstant.iIntValue = Integer.parseInt(paramString);
                break;
            case 4:
                this.cpConstant.fFloatVal = Float.valueOf(paramString).floatValue();
                break;
            case 5:
                this.cpConstant.lLongVal = Long.parseLong(paramString);
                break;
            case 6:
                this.cpConstant.dDoubleVal = Double.valueOf(paramString).doubleValue();
        }
        this.sConstValue = paramString;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        return "Attribute " + this.sName + ". Type=" + this.sConstType + ". Value=" + this.sConstValue;
    }

}
