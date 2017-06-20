package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ConstantPool {
    int iConstantPoolCount;
    int iNumPoolInfos;
    Vector vectConstPool;

    void read(DataInputStream paramDataInputStream)
            throws IOException {
        this.iConstantPoolCount = paramDataInputStream.readUnsignedShort();
        this.iNumPoolInfos = (this.iConstantPoolCount - 1);
        this.vectConstPool = new Vector(this.iNumPoolInfos);
        for (int i = 0; i < this.iNumPoolInfos; i++) {
            ConstantPoolInfo localConstantPoolInfo = new ConstantPoolInfo();
            localConstantPoolInfo.read(paramDataInputStream);
            this.vectConstPool.addElement(localConstantPoolInfo);
            if (localConstantPoolInfo.isDoubleSizeConst()) {
                this.vectConstPool.addElement(localConstantPoolInfo);
                i++;
            }
        }
    }

    void write(DataOutputStream paramDataOutputStream)
            throws IOException {
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
        paramDataOutputStream.writeShort(this.iConstantPoolCount);
        for (int i = 0; i < this.iNumPoolInfos; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectConstPool.elementAt(i);
            localConstantPoolInfo.write(paramDataOutputStream, this);
            if (localConstantPoolInfo.isDoubleSizeConst()) {
                i++;
            }
        }
    }

    void resolveReferences() {
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
        for (int i = 0; i < this.iNumPoolInfos; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectConstPool.elementAt(i);
            localConstantPoolInfo.resolveReferences(this);
            if (localConstantPoolInfo.isDoubleSizeConst()) {
                i++;
            }
        }
    }

    void removeUnreferenced() {
        Vector localVector = new Vector(this.iConstantPoolCount / 2);
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
        for (int i = 0; i < this.iNumPoolInfos; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectConstPool.elementAt(i);
            if (localConstantPoolInfo.getRef() > 0) {
                localVector.addElement(localConstantPoolInfo);
            }
            if (localConstantPoolInfo.isDoubleSizeConst()) {
                if (localConstantPoolInfo.getRef() > 0) {
                    localVector.addElement(localConstantPoolInfo);
                }
                i++;
            }
        }
        this.vectConstPool = localVector;
    }

    public void addNewPoolInfo(ConstantPoolInfo paramConstantPoolInfo) {
        this.vectConstPool.addElement(paramConstantPoolInfo);
        paramConstantPoolInfo.resolveReferences(this);
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
    }

    public void removePoolInfo(ConstantPoolInfo paramConstantPoolInfo) {
        while (paramConstantPoolInfo.getRef() > 0) {
            paramConstantPoolInfo.deleteRef();
        }
        this.vectConstPool.removeElement(paramConstantPoolInfo);
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
    }

    public String toString() {
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
        String str = "Constant pool. Count: " + this.iConstantPoolCount;
        return str;
    }

    public ConstantPoolInfo getPoolInfo(int paramInt) {
        return (ConstantPoolInfo) this.vectConstPool.elementAt(paramInt - 1);
    }

    public int getIndexOf(ConstantPoolInfo paramConstantPoolInfo) {
        return this.vectConstPool.indexOf(paramConstantPoolInfo) + 1;
    }

    public int getPoolInfoCount() {
        this.iConstantPoolCount = ((this.iNumPoolInfos = this.vectConstPool.size()) + 1);
        return this.iNumPoolInfos;
    }

    public boolean verify(Vector paramVector) {
        boolean bool = true;
        if (0 == getPoolInfoCount()) {
            paramVector.addElement("Constant pool count must be greater than 0.");
            bool = false;
        }
        for (int i = 0; i < this.iNumPoolInfos; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.vectConstPool.elementAt(i);
            bool = (localConstantPoolInfo.verify("ConstPool " + (i + 1), paramVector)) && (bool);
        }
        return bool;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/ConstantPool.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */