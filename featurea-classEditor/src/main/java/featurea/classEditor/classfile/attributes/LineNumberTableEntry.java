package featurea.classEditor.classfile.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LineNumberTableEntry {
    public int iStartPC;
    public int iLineNum;

    void read(DataInputStream paramDataInputStream)
            throws IOException {
        this.iStartPC = paramDataInputStream.readUnsignedShort();
        this.iLineNum = paramDataInputStream.readUnsignedShort();
    }

    void write(DataOutputStream paramDataOutputStream)
            throws IOException {
        paramDataOutputStream.writeShort(this.iStartPC);
        paramDataOutputStream.writeShort(this.iLineNum);
    }

    public String toString() {
        return "start_pc=" + this.iStartPC + " line_number=" + this.iLineNum;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/LineNumberTableEntry.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */