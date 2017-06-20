package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public final class Version {
    int iMagicNumber;
    int iMinorVersion;
    int iMajorVersion;

    void read(DataInputStream paramDataInputStream)
            throws IOException {
        this.iMagicNumber = paramDataInputStream.readInt();
        this.iMinorVersion = paramDataInputStream.readUnsignedShort();
        this.iMajorVersion = paramDataInputStream.readUnsignedShort();
    }

    void write(DataOutputStream paramDataOutputStream)
            throws IOException {
        paramDataOutputStream.writeInt(this.iMagicNumber);
        paramDataOutputStream.writeShort(this.iMinorVersion);
        paramDataOutputStream.writeShort(this.iMajorVersion);
    }

    public boolean verify(Vector paramVector) {
        boolean bool = true;
        if (-889275714 != this.iMagicNumber) {
            paramVector.addElement("Magic number should be CAFEBABE.");
            bool = false;
        }
        if (45 != this.iMajorVersion) {
            paramVector.addElement("Major version should be 45.");
            bool = false;
        }
        if (3 != this.iMinorVersion) {
            paramVector.addElement("Minor version should be 3.");
            bool = false;
        }
        return bool;
    }

    public String toString() {
        String str = "Magic number: " + getMagicNumberString() + ", ";
        str = str + "Minor version: " + this.iMinorVersion + ", ";
        str = str + "Major version: " + this.iMajorVersion + ", ";
        return str;
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
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/Version.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */