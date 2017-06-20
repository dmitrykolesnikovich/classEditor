package featurea.classEditor.classfile.attributes;

import featurea.classEditor.classfile.ConstantPool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Code {
    public int iCodeLength;
    public int iCurrIndex;
    public int iCodeIndex;
    public Vector vectCode;

    void read(DataInputStream paramDataInputStream, ConstantPool paramConstantPool)
            throws IOException {
        this.iCodeLength = paramDataInputStream.readInt();
        this.vectCode = new Vector();
        int i = 0;
        while (i < this.iCodeLength) {
            Instruction localInstruction = new Instruction();
            localInstruction.readInstruction(paramDataInputStream, paramConstantPool, i);
            this.vectCode.addElement(localInstruction);
            i += localInstruction.iDataLength + 1;
        }
        this.iCurrIndex = 0;
        this.iCodeIndex = 0;
    }

    void write(DataOutputStream paramDataOutputStream, ConstantPool paramConstantPool)
            throws IOException {
        paramDataOutputStream.writeInt(this.iCodeLength);
        for (int i = 0; i < this.vectCode.size(); i++) {
            Instruction localInstruction = (Instruction) this.vectCode.elementAt(i);
            localInstruction.writeInstruction(paramDataOutputStream, paramConstantPool);
        }
    }

    public String getNextInstruction() {
        if (this.iCurrIndex >= this.vectCode.size()) {
            this.iCurrIndex = 0;
            this.iCodeIndex = 0;
            return null;
        }
        Instruction localInstruction = (Instruction) this.vectCode.elementAt(this.iCurrIndex);
        this.iCurrIndex += 1;
        this.iCodeIndex += localInstruction.iDataLength + 1;
        return Integer.toString(this.iCodeIndex - localInstruction.iDataLength - 1) + " " + localInstruction.toString();
    }

    public boolean verify(String paramString, Vector paramVector) {
        return true;
    }

    public int getCodeLength() {
        return this.iCodeLength;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/attributes/Code.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */