package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.classfile.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ExceptionTableEntry {

    public int iStartPC;
    public int iEndPC;
    public int iHandlerPC;
    public int iCatchType;
    public ConstantPoolInfo cpCatchType;

    public void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool) throws IOException {
        this.iStartPC = paramDataInputStream.readUnsignedShort();
        this.iEndPC = paramDataInputStream.readUnsignedShort();
        this.iHandlerPC = paramDataInputStream.readUnsignedShort();
        this.iCatchType = paramDataInputStream.readUnsignedShort();
        if (0 != this.iCatchType) {
            this.cpCatchType = paramConstantPool.getPoolInfo(this.iCatchType);
            this.cpCatchType.addRef();
        }
    }

    public void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool) throws IOException {
        paramDataOutputStream.writeShort(this.iStartPC);
        paramDataOutputStream.writeShort(this.iEndPC);
        paramDataOutputStream.writeShort(this.iHandlerPC);
        if (null != this.cpCatchType) {
            this.iCatchType = paramConstantPool.getIndexOf(this.cpCatchType);
        }
        paramDataOutputStream.writeShort(this.iCatchType);
    }

    public void setCatchTypeClass(ConstantPoolInfo paramConstantPoolInfo, ConstantPool paramConstantPool) {
        if (null != this.cpCatchType) {
            this.cpCatchType.deleteRef();
            this.cpCatchType = null;
            this.iCatchType = 0;
        }
        if (null != paramConstantPoolInfo) {
            paramConstantPoolInfo.addRef();
            this.cpCatchType = paramConstantPoolInfo;
            this.iCatchType = paramConstantPool.getIndexOf(this.cpCatchType);
        }
    }

    /*technical stuff*/

    @Override
    public String toString() {
        return "type=" + (null == this.cpCatchType ? "all" : Utils.convertClassStrToStr(this.cpCatchType.refUTF8.sUTFStr)) + " start=" + this.iStartPC + " end=" + this.iEndPC + " handler=" + this.iHandlerPC;
    }

}
