package featurea.classEditor.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class AccessFlags {
    public static final int FIELD_VALID_FLAGS = 0;
    public static final int METHOD_VALID_FLAGS = 2;
    public static final int CLASS_VALID_FLAGS = 4;
    static final int ACC_PUBLIC = 1;
    static final int ACC_PRIVATE = 2;
    static final int ACC_PROTECTED = 4;
    static final int ACC_STATIC = 8;
    static final int ACC_FINAL = 16;
    static final int ACC_SUPER = 32;
    static final int ACC_VOLATILE = 64;
    static final int ACC_TRANSIENT = 128;
    static final int ACC_INTERFACE = 512;
    static final int ACC_ABSTRACT = 1024;
    static final int ACC_NATIVE = 256;
    static final int ACC_SYNCHRONIZED = 32;
    static final int ACC_STRICT = 2048;
    int iAccessFlags;
    boolean bSuperFlagSet;
    boolean bSynchronizedFlagSet;

    public static AccessFlags getValidFlags(int paramInt) {
        AccessFlags localAccessFlags = new AccessFlags();
        localAccessFlags.setPublic(true);
        if ((paramInt == 0) || (paramInt == 2)) {
            localAccessFlags.setPrivate(true);
            localAccessFlags.setProtected(true);
            localAccessFlags.setStatic(true);
            localAccessFlags.setFinal(true);
        }
        if (paramInt == 0) {
            localAccessFlags.setVolatile(true);
            localAccessFlags.setTransient(true);
        } else if (paramInt == 2) {
            localAccessFlags.setSynchronized(true);
            localAccessFlags.setNative(true);
            localAccessFlags.setAbstract(true);
            localAccessFlags.setStrict(true);
        } else if (paramInt == 4) {
            localAccessFlags.setFinal(true);
            localAccessFlags.setSuper(true);
            localAccessFlags.setInterface(true);
            localAccessFlags.setAbstract(true);
        }
        return localAccessFlags;
    }

    public void read(DataInputStream paramDataInputStream)
            throws IOException {
        this.iAccessFlags = paramDataInputStream.readUnsignedShort();
        if (32 == (0x20 & this.iAccessFlags)) {
            setSynchronized(true);
        }
        if (32 == (0x20 & this.iAccessFlags)) {
            setSuper(true);
        }
    }

    public void write(DataOutputStream paramDataOutputStream)
            throws IOException {
        paramDataOutputStream.writeShort(this.iAccessFlags);
    }

    public boolean verify(String paramString, Vector paramVector, boolean paramBoolean) {
        boolean bool = true;
        int i = 0;
        if (isPublic()) {
            i++;
        }
        if (isPrivate()) {
            i++;
        }
        if (isProtected()) {
            i++;
        }
        if (1 < i) {
            paramVector.addElement(paramString + ": Only one of private, public and protected can be set.");
            bool = false;
        }
        if (isInterface()) {
            if (!isAbstract()) {
                paramVector.addElement(paramString + ": Interfaces must be abstract.");
                bool = false;
            }
            if (isFinal()) {
                paramVector.addElement(paramString + ": Interfaces can not be final.");
                bool = false;
            }
        }
        if ((isFinal()) && (isVolatile())) {
            paramVector.addElement(paramString + ": Final and Volatile flags can not be set together.");
            bool = false;
        }
        if (isAbstract()) {
            if (isFinal()) {
                paramVector.addElement(paramString + ": Abstract and final flags can not be set together.");
                bool = false;
            }
            if (isNative()) {
                paramVector.addElement(paramString + ": Abstract and native flags can not be set together.");
                bool = false;
            }
            if ((!paramBoolean) && (isSynchronized())) {
                paramVector.addElement(paramString + ": Abstract and synchronized flags can not be set together.");
                bool = false;
            }
        }
        return bool;
    }

    public String getAccessString() {
        String str = "";
        if (isPublic()) {
            str = str + " public";
        }
        if (isPrivate()) {
            str = str + " private";
        }
        if (isProtected()) {
            str = str + " protected";
        }
        return str.trim();
    }

    public String getModifierString() {
        String str = "";
        if (isStatic()) {
            str = str + " static";
        }
        if (isFinal()) {
            str = str + " final";
        }
        if (isSuper()) {
            str = str + " super";
        }
        if (isVolatile()) {
            str = str + " volatile";
        }
        if (isTransient()) {
            str = str + " transient";
        }
        if (isInterface()) {
            str = str + " interface";
        }
        if (isAbstract()) {
            str = str + " abstract";
        }
        if (isNative()) {
            str = str + " native";
        }
        if (isSynchronized()) {
            str = str + " synchronized";
        }
        if (isStrict()) {
            str = str + " strict";
        }
        return str.trim();
    }

    public String toString() {
        return (getAccessString() + " " + getModifierString()).trim();
    }

    public boolean isPublic() {
        return 1 == (0x1 & this.iAccessFlags);
    }

    public void setPublic(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x1;
        } else if (isPublic()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x1);
        }
    }

    public boolean isPrivate() {
        return 2 == (0x2 & this.iAccessFlags);
    }

    public void setPrivate(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x2;
        } else if (isPrivate()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x2);
        }
    }

    public boolean isProtected() {
        return 4 == (0x4 & this.iAccessFlags);
    }

    public void setProtected(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x4;
        } else if (isProtected()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x4);
        }
    }

    public boolean isStatic() {
        return 8 == (0x8 & this.iAccessFlags);
    }

    public void setStatic(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x8;
        } else if (isStatic()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x8);
        }
    }

    public boolean isFinal() {
        return 16 == (0x10 & this.iAccessFlags);
    }

    public void setFinal(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x10;
        } else if (isFinal()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x10);
        }
    }

    public boolean isSuper() {
        if (this.bSuperFlagSet) {
            return 32 == (0x20 & this.iAccessFlags);
        }
        return false;
    }

    public void setSuper(boolean paramBoolean) {
        if (paramBoolean) {
            this.bSuperFlagSet = true;
            this.iAccessFlags |= 0x20;
        } else if (isSuper()) {
            if (!isSynchronized()) {
                this.iAccessFlags &= (this.iAccessFlags ^ 0x20);
            }
            this.bSuperFlagSet = false;
        }
    }

    public boolean isVolatile() {
        return 64 == (0x40 & this.iAccessFlags);
    }

    public void setVolatile(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x40;
        } else if (isVolatile()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x40);
        }
    }

    public boolean isTransient() {
        return 128 == (0x80 & this.iAccessFlags);
    }

    public void setTransient(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x80;
        } else if (isTransient()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x80);
        }
    }

    public boolean isInterface() {
        return 512 == (0x200 & this.iAccessFlags);
    }

    public void setInterface(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x200;
        } else if (isInterface()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x200);
        }
    }

    public boolean isAbstract() {
        return 1024 == (0x400 & this.iAccessFlags);
    }

    public void setAbstract(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x400;
        } else if (isAbstract()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x400);
        }
    }

    public boolean isNative() {
        return 256 == (0x100 & this.iAccessFlags);
    }

    public void setNative(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x100;
        } else if (isNative()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x100);
        }
    }

    public boolean isSynchronized() {
        if (this.bSynchronizedFlagSet) {
            return 32 == (0x20 & this.iAccessFlags);
        }
        return false;
    }

    public void setSynchronized(boolean paramBoolean) {
        if (paramBoolean) {
            this.bSynchronizedFlagSet = true;
            this.iAccessFlags |= 0x20;
        } else if (isSynchronized()) {
            if (!isSuper()) {
                this.iAccessFlags &= (this.iAccessFlags ^ 0x20);
            }
            this.bSynchronizedFlagSet = false;
        }
    }

    public boolean isStrict() {
        return 2048 == (0x800 & this.iAccessFlags);
    }

    public void setStrict(boolean paramBoolean) {
        if (paramBoolean) {
            this.iAccessFlags |= 0x800;
        } else if (isStrict()) {
            this.iAccessFlags &= (this.iAccessFlags ^ 0x800);
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/AccessFlags.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */