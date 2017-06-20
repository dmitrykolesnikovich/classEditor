package featurea.classEditor.guihelper;

import featurea.classEditor.classfile.ClassFile;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class ClassFileStatus
        extends DefaultMutableTreeNode {
    public ClassFile classFile;
    public boolean bBackupCreated;
    public String sClassName;
    public String sPath;
    public String sFileName;
    boolean bDirty;

    public ClassFileStatus(String paramString, ClassFile paramClassFile) {
        this.sFileName = paramString;
        File localFile = new File(this.sFileName);
        this.sClassName = localFile.getName();
        this.sPath = localFile.getAbsolutePath();
        this.classFile = paramClassFile;
        setUserObject(getTreeDisplayString());
    }

    public String getTreeDisplayString() {
        String str = this.sClassName;
        if (this.bDirty) {
            str = str + "*";
        }
        str = str + " (" + this.sPath + ")";
        return str;
    }

    public void setDirtyFlag(boolean paramBoolean) {
        if (this.bDirty != paramBoolean) {
            this.bDirty = paramBoolean;
            setUserObject(getTreeDisplayString());
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/ClassFileStatus.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */