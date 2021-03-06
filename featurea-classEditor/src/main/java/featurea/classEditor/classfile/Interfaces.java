/*
 * Interfaces.java
 *
 * Created on March 12, 1999
 *
 * Modification Log:
 * 1.01  18th Mar 1999   Tanmay   throws IOexception instead of Exception
 * 1.02  12th Jun 1999   Tanmay   Methods to verify and get text summary added.
 * 1.03  03rd Jul 1999   Tanmay   Method to add interface introduced
 *-----------------------------------------------------------------------------------------
 *       10th Sep 2003   Tanmay   Moved to SourceForge (http://classeditor.sourceforge.net)
 *-----------------------------------------------------------------------------------------
 * 1.04  28th Sep 2003   Tanmay   Moved text summary method to visitor.
 * 1.05  23rd Apr 2004   Tanmay   New method to get the interface constant pool object.
 */


package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class Interfaces {

    private int iInterfacesCount;
    private Vector vectInterfaces; // contains CONSTANT_Class

    public void read(DataInputStream dis, ConstantPool constPool) throws IOException {
        int iIndex;
        iInterfacesCount = dis.readUnsignedShort();
        vectInterfaces = new Vector(iInterfacesCount);
        for (iIndex = 0; iIndex < iInterfacesCount; iIndex++) {
            int iInterfaceIndex = dis.readUnsignedShort();
            ConstantPoolInfo cpInfo = constPool.getPoolInfo(iInterfaceIndex);
            vectInterfaces.addElement(cpInfo);
            cpInfo.addRef();
        }
    }

    public void write(DataOutputStream dos, ConstantPool constPool) throws IOException {
        int iIndex;
        iInterfacesCount = vectInterfaces.size();
        dos.writeShort(iInterfacesCount);
        for (iIndex = 0; iIndex < iInterfacesCount; iIndex++) {
            int iInterfaceIndex = constPool.getIndexOf(getInterface(iIndex));
            dos.writeShort(iInterfaceIndex);
        }
    }

    public boolean verify(Vector vectVerifyErrors) {
        boolean bRet = true;
        for (int iIndex = 0; iIndex < iInterfacesCount; iIndex++) {
            ConstantPoolInfo cpClass = getInterface(iIndex);
            if (ConstantPoolInfo.CONSTANT_Class != cpClass.iTag) {
                vectVerifyErrors.addElement("Interface index " + (iIndex + 1) + ": Interfaces must point to constant pool of type Class.");
                bRet = false;
            }
        }
        return bRet;
    }

    public ConstantPoolInfo getInterface(int iIndex) {
        ConstantPoolInfo cpClass = (ConstantPoolInfo) vectInterfaces.elementAt(iIndex);
        return cpClass;
    }

    public String getInterfaceName(int iIndex) {
        ConstantPoolInfo cpClass = getInterface(iIndex);
        return Utils.convertClassStrToStr(cpClass.refUTF8.sUTFStr);
    }

    public void setInterfaceName(int iIndex, String sNewName) {
        ConstantPoolInfo cpClass = getInterface(iIndex);
        cpClass.refUTF8.sUTFStr = Utils.convertStrToClassStr(sNewName);
    }

    public void removeInterface(int iIndex) {
        ConstantPoolInfo cpClass = getInterface(iIndex);
        cpClass.deleteRef();
        vectInterfaces.removeElementAt(iIndex);
        iInterfacesCount--;
    }

    public void addInterface(ConstantPoolInfo cpClass) {
        if (ConstantPoolInfo.CONSTANT_Class == cpClass.iTag) {
            iInterfacesCount++;
            vectInterfaces.addElement(cpClass);
            cpClass.addRef();
        }
    }

    public int getInterfacesCount() {
        return iInterfacesCount;
    }

    /*technical stuff*/

    @Override
    public String toString() {
        String sRet;
        String sNewLine = System.getProperty("line.separator");

        iInterfacesCount = vectInterfaces.size();
        sRet = "Interfaces count: " + iInterfacesCount + sNewLine;
        for (int iIndex = 0; iIndex < iInterfacesCount; iIndex++) {
            sRet += ("Interface " + (iIndex + 1) + ": const_pool_entry=" + getInterface(iIndex) + sNewLine);
        }

        return sRet;
    }

}