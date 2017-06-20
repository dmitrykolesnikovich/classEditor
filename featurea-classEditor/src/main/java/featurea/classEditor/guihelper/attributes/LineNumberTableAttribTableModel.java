package featurea.classEditor.guihelper.attributes;

import featurea.classEditor.classfile.attributes.LineNumberTableAttribute;
import featurea.classEditor.classfile.attributes.LineNumberTableEntry;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class LineNumberTableAttribTableModel
        extends AbstractTableModel {
    private final String[] columnNames = {"", "Start PC", "Line Number"};
    private Object[][] data;
    private LineNumberTableAttribute attribute;
    private boolean bEditMode;

    public LineNumberTableAttribTableModel(LineNumberTableAttribute paramLineNumberTableAttribute) {
        this.attribute = paramLineNumberTableAttribute;
        createData();
    }

    private void createData() {
        if (null == this.attribute) {
            return;
        }
        Vector localVector = this.attribute.vectEntries;
        int j = localVector.size();
        this.data = new Object[j][this.columnNames.length];
        for (int i = 0; i < j; i++) {
            setValueAt(localVector.elementAt(i), i, 0);
        }
    }

    public String getColumnName(int paramInt) {
        return this.columnNames[paramInt];
    }

    public Class getColumnClass(int paramInt) {
        if ((null != this.data) && (this.data.length > 0)) {
            return this.data[0][paramInt].getClass();
        }
        return new Object().getClass();
    }

    public Object getValueAt(int paramInt1, int paramInt2) {
        if ((null != this.data) && (this.data.length > 0)) {
            return this.data[paramInt1][paramInt2];
        }
        return null;
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
        if (0 == paramInt2) {
            return false;
        }
        return this.bEditMode;
    }

    public void setEditable(boolean paramBoolean) {
        this.bEditMode = paramBoolean;
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        LineNumberTableEntry localLineNumberTableEntry;
        if ((paramObject instanceof LineNumberTableEntry)) {
            localLineNumberTableEntry = (LineNumberTableEntry) paramObject;
            this.data[paramInt1][0] = new Integer(paramInt1 + 1);
            this.data[paramInt1][1] = new Integer(localLineNumberTableEntry.iStartPC);
            this.data[paramInt1][2] = new Integer(localLineNumberTableEntry.iLineNum);
        } else {
            localLineNumberTableEntry = (LineNumberTableEntry) this.attribute.vectEntries.elementAt(paramInt1);
            switch (paramInt2) {
                case 1:
                    this.data[paramInt1][1] = paramObject;
                    localLineNumberTableEntry.iStartPC = ((Integer) paramObject).intValue();
                    break;
                case 2:
                    this.data[paramInt1][2] = paramObject;
                    localLineNumberTableEntry.iLineNum = ((Integer) paramObject).intValue();
            }
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/attributes/LineNumberTableAttribTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */