package featurea.classEditor.classfile.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LineNumberTableEntry {

    public int iStartPC;
    public int iLineNum;

    public void read(DataInputStream paramDataInputStream) throws IOException {
        this.iStartPC = paramDataInputStream.readUnsignedShort();
        this.iLineNum = paramDataInputStream.readUnsignedShort();
    }

    public void write(DataOutputStream paramDataOutputStream) throws IOException {
        paramDataOutputStream.writeShort(this.iStartPC);
        paramDataOutputStream.writeShort(this.iLineNum);
    }

    @Override
    public String toString() {
        return "start_pc=" + this.iStartPC + " line_number=" + this.iLineNum;
    }

}
