package featurea.classEditor.classfile;

import featurea.classEditor.classfile.attributes.Attributes;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class ClassFile {
    public Version version = new Version();
    public ConstantPool constantPool = new ConstantPool();
    public AccessFlags accessFlags = new AccessFlags();
    public ClassNames classNames = new ClassNames();
    public Interfaces interfaces = new Interfaces();
    public Fields fields = new Fields();
    public Methods methods = new Methods();
    public Attributes attributes = new Attributes();
    public int iReadWriteStage;

    public void createSimplestClass()
            throws IOException {
        byte[] arrayOfByte = {-54, -2, -70, -66, 0, 3, 0, 45, 0, 10, 10, 0, 3, 0, 7, 7, 0, 8, 7, 0, 9, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 12, 0, 4, 0, 5, 1, 0, 6, 115, 105, 109, 112, 108, 101, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 0, 33, 0, 2, 0, 3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 4, 0, 5, 0, 1, 0, 6, 0, 0, 0, 17, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 0, 0, 0};
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
        DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);
        read(localDataInputStream);
    }

    public void read(DataInputStream paramDataInputStream)
            throws IOException {
        this.iReadWriteStage = 0;
        this.version.read(paramDataInputStream);
        this.iReadWriteStage = 1;
        this.constantPool.read(paramDataInputStream);
        this.constantPool.resolveReferences();
        this.iReadWriteStage = 2;
        this.accessFlags.read(paramDataInputStream);
        this.iReadWriteStage = 3;
        this.classNames.read(paramDataInputStream, this.constantPool);
        this.iReadWriteStage = 4;
        this.interfaces.read(paramDataInputStream, this.constantPool);
        this.iReadWriteStage = 5;
        this.fields.read(paramDataInputStream, this.constantPool);
        this.iReadWriteStage = 6;
        this.methods.read(paramDataInputStream, this.constantPool);
        this.iReadWriteStage = 7;
        this.attributes.read(paramDataInputStream, this.constantPool);
        this.iReadWriteStage = 8;
    }

    public void write(DataOutputStream paramDataOutputStream)
            throws IOException {
        this.iReadWriteStage = 0;
        this.version.write(paramDataOutputStream);
        this.iReadWriteStage = 1;
        this.constantPool.write(paramDataOutputStream);
        this.iReadWriteStage = 2;
        this.accessFlags.write(paramDataOutputStream);
        this.iReadWriteStage = 3;
        this.classNames.write(paramDataOutputStream, this.constantPool);
        this.iReadWriteStage = 4;
        this.interfaces.write(paramDataOutputStream, this.constantPool);
        this.iReadWriteStage = 5;
        this.fields.write(paramDataOutputStream, this.constantPool);
        this.iReadWriteStage = 6;
        this.methods.write(paramDataOutputStream, this.constantPool);
        this.iReadWriteStage = 7;
        this.attributes.write(paramDataOutputStream, this.constantPool);
        this.iReadWriteStage = 8;
    }

    public boolean verify(Vector paramVector) {
        boolean bool = true;
        bool = (this.version.verify(paramVector)) && (bool);
        bool = (this.constantPool.verify(paramVector)) && (bool);
        bool = (this.accessFlags.verify("ClassFile", paramVector, true)) && (bool);
        bool = (this.classNames.verify(paramVector)) && (bool);
        bool = (this.interfaces.verify(paramVector)) && (bool);
        bool = (this.fields.verify(paramVector)) && (bool);
        bool = (this.methods.verify(paramVector)) && (bool);
        bool = (this.attributes.verify("ClassFile", paramVector)) && (bool);
        return bool;
    }

    public String toString() {
        return "ClassEditor: " + this.classNames.getThisClassName();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/classfile/ClassFile.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */