package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public final class Version {

    private int iMagicNumber;
    private int iMinorVersion;
    private int iMajorVersion;

    public void read(DataInputStream paramDataInputStream) throws IOException {
        this.iMagicNumber = paramDataInputStream.readInt();
        this.iMinorVersion = paramDataInputStream.readUnsignedShort();
        this.iMajorVersion = paramDataInputStream.readUnsignedShort();
    }

    public void write(DataOutputStream paramDataOutputStream) throws IOException {
        paramDataOutputStream.writeInt(this.iMagicNumber);
        paramDataOutputStream.writeShort(this.iMinorVersion);
        paramDataOutputStream.writeShort(this.iMajorVersion);
    }

    public boolean verify(Vector result) {
        boolean bool = true;
        if (-889275714 != this.iMagicNumber) {
            result.addElement("Magic number should be CAFEBABE.");
            bool = false;
        }
        if (45 != this.iMajorVersion) {
            result.addElement("Major version should be 45.");
            bool = false;
        }
        if (3 != this.iMinorVersion) {
            result.addElement("Minor version should be 3.");
            bool = false;
        }
        return bool;
    }

    public int getMagicNumberInteger() {
        return this.iMagicNumber;
    }

    public String getMagicNumberString() {
        return Integer.toHexString(this.iMagicNumber);
    }

    public void setMagicNumberString(String paramString) {
        this.iMagicNumber = Long.valueOf(paramString, 16).intValue();
    }

    public String getMajorVersionString() {
        return Integer.toString(this.iMajorVersion);
    }

    public void setMajorVersionString(String paramString) {
        this.iMajorVersion = Integer.parseInt(paramString);
    }

    public String getMinorVersionString() {
        return Integer.toString(this.iMinorVersion);
    }

    public void setMinorVersionString(String paramString) {
        this.iMinorVersion = Integer.parseInt(paramString);
    }

    @Override
    public String toString() {
        String str = "Magic number: " + getMagicNumberString() + ", ";
        str = str + "Minor version: " + this.iMinorVersion + ", ";
        str = str + "Major version: " + this.iMajorVersion + ", ";
        return str;
    }

}
