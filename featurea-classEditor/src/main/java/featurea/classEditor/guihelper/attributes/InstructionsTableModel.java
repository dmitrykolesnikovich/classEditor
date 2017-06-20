package featurea.classEditor.guihelper.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Code;
import featurea.classEditor.classfile.attributes.Instruction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class InstructionsTableModel
        extends AbstractTableModel {
    private final String[] columnNames = {"Index", "Byte Index", "Instruction"};
    private Object[][] data;
    private Code code;
    private ConstantPool constPool;
    private boolean bEditMode;

    public InstructionsTableModel(ConstantPool paramConstantPool, Code paramCode) {
        if (null != paramConstantPool) {
            this.constPool = paramConstantPool;
            this.code = paramCode;
        } else {
            this.constPool = null;
            this.code = null;
        }
        createData();
    }

    public void setCellEditors(JTable paramJTable) {
    }

    private void createData() {
        if (null == this.code) {
            return;
        }
        int j = this.code.vectCode.size();
        this.data = new Object[j][this.columnNames.length];
        for (int i = 0; i < j; i++) {
            Instruction localInstruction = (Instruction) this.code.vectCode.elementAt(i);
            setValueAt(localInstruction, i, 0);
        }
    }

    public Class getColumnClass(int paramInt) {
        return this.data[0][paramInt].getClass();
    }

    public Object getValueAt(int paramInt1, int paramInt2) {
        if ((null != this.data) && (this.data.length > 0)) {
            return this.data[paramInt1][paramInt2];
        }
        return null;
    }

    public String getColumnName(int paramInt) {
        return this.columnNames[paramInt];
    }

    public int getRowCount() {
        if (null == this.data) {
            return 0;
        }
        return this.data.length;
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public boolean isCellEditable(int paramInt1, int paramInt2) {
        if ((0 == paramInt2) || (1 == paramInt2)) {
            return false;
        }
        return this.bEditMode;
    }

    public void setEditable(boolean paramBoolean) {
        this.bEditMode = paramBoolean;
    }

    private int getByteIndex(int paramInt) {
        if (paramInt == 0) {
            return 0;
        }
        Instruction localInstruction = (Instruction) this.code.vectCode.elementAt(paramInt - 1);
        return ((Integer) this.data[(paramInt - 1)][1]).intValue() + localInstruction.iDataLength + 1;
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        Instruction localInstruction;
        if ((paramObject instanceof Instruction)) {
            localInstruction = (Instruction) paramObject;
            this.data[paramInt1][0] = new Integer(paramInt1);
            this.data[paramInt1][1] = new Integer(getByteIndex(paramInt1));
            this.data[paramInt1][2] = paramObject.toString();
        } else {
            localInstruction = (Instruction) this.code.vectCode.elementAt(paramInt1);
            switch (paramInt2) {
            }
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/attributes/InstructionsTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */