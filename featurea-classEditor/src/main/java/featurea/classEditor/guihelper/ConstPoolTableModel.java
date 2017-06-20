package featurea.classEditor.guihelper;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class ConstPoolTableModel
        extends AbstractTableModel {
    public static int NO_FILTER = -1;
    private static ImageIcon imgClass;
    private static ImageIcon imgField;
    private static ImageIcon imgMethod;
    private static ImageIcon imgIface;
    private static ImageIcon imgString;
    private static ImageIcon imgInt;
    private static ImageIcon imgFloat;
    private static ImageIcon imgLong;
    private static ImageIcon imgDouble;
    private static ImageIcon imgNameAndType;
    private static ImageIcon imgUTF;
    private static ImageIcon imgUnknown;
    private final String[] columnNames = {"Index", "Type", "Entry"};
    private int iFilterTag = NO_FILTER;
    private Object[][] data;
    private Object[][] filteredData;
    private ConstantPool constPool;

    public ConstPoolTableModel(ConstantPool paramConstantPool) {
        this.constPool = paramConstantPool;
        imgClass = new ImageIcon(getClass().getResource("/res/class.gif"));
        imgField = new ImageIcon(getClass().getResource("/res/field.gif"));
        imgMethod = new ImageIcon(getClass().getResource("/res/method.gif"));
        imgIface = new ImageIcon(getClass().getResource("/res/interface.gif"));
        imgString = new ImageIcon(getClass().getResource("/res/string.gif"));
        imgInt = new ImageIcon(getClass().getResource("/res/integer.gif"));
        imgFloat = new ImageIcon(getClass().getResource("/res/float.gif"));
        imgLong = new ImageIcon(getClass().getResource("/res/long.gif"));
        imgDouble = new ImageIcon(getClass().getResource("/res/double.gif"));
        imgNameAndType = new ImageIcon(getClass().getResource("/res/nameandtype.gif"));
        imgUTF = new ImageIcon(getClass().getResource("/res/utf.gif"));
        imgUnknown = new ImageIcon(getClass().getResource("/res/report1.gif"));
        createData();
    }

    private void createData() {
        if (null == this.constPool) {
            return;
        }
        int j = this.constPool.getPoolInfoCount();
        this.data = new Object[j][this.columnNames.length];
        for (int i = 0; i < j; i++) {
            ConstantPoolInfo localConstantPoolInfo = this.constPool.getPoolInfo(i + 1);
            setValueAt(this.data, localConstantPoolInfo, i, 0);
        }
        applyFilter();
    }

    public void setFilter(int paramInt) {
        this.iFilterTag = paramInt;
    }

    public void applyFilter() {
        int i = this.data.length;
        int j;
        ConstantPoolInfo localConstantPoolInfo;
        if (NO_FILTER != this.iFilterTag) {
            i = 0;
            for (j = 0; j < this.data.length; j++) {
                localConstantPoolInfo = this.constPool.getPoolInfo(j + 1);
                if (localConstantPoolInfo.iTag == this.iFilterTag) {
                    i++;
                }
            }
        }
        this.filteredData = ((Object[][]) null);
        if (i > 0) {
            this.filteredData = new Object[i][this.columnNames.length];
            int k;
            for (j = k = 0; j < this.data.length; j++) {
                localConstantPoolInfo = this.constPool.getPoolInfo(j + 1);
                if ((localConstantPoolInfo.iTag == this.iFilterTag) || (NO_FILTER == this.iFilterTag)) {
                    setValueAt(this.filteredData, localConstantPoolInfo, k, 0);
                    k++;
                }
            }
        }
    }

    public String getColumnName(int paramInt) {
        return this.columnNames[paramInt];
    }

    public Class getColumnClass(int paramInt) {
        return this.data[0][paramInt].getClass();
    }

    public Object getValueAt(int paramInt1, int paramInt2) {
        if ((null != this.filteredData) && (this.filteredData.length > 0)) {
            return this.filteredData[paramInt1][paramInt2];
        }
        return null;
    }

    public int getRowCount() {
        if (null == this.filteredData) {
            return 0;
        }
        return this.filteredData.length;
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public boolean isCellEditable(int paramInt1, int paramInt2) {
        return false;
    }

    public int nextIndex(int paramInt, String paramString) {
        if (null == this.filteredData) {
            return -1;
        }
        while ((paramInt < this.filteredData.length) && (((String) this.filteredData[paramInt][2]).indexOf(paramString) < 0)) {
            paramInt++;
        }
        return paramInt < this.filteredData.length ? paramInt : -1;
    }

    public int getModelIndex(int paramInt) {
        if ((paramInt <= 0) || (paramInt > this.data.length) || (null == this.filteredData)) {
            return -1;
        }
        for (int i = 0; i < this.filteredData.length; i++) {
            if (paramInt == ((Integer) getValueAt(i, 0)).intValue()) {
                return i;
            }
        }
        return -1;
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        setValueAt(this.filteredData, paramObject, paramInt1, paramInt2);
        ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) paramObject;
        setValueAt(this.data, paramObject, this.constPool.getIndexOf(localConstantPoolInfo) - 1, paramInt2);
    }

    private void setValueAt(Object[][] paramArrayOfObject, Object paramObject, int paramInt1, int paramInt2) {
        ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) paramObject;
        paramArrayOfObject[paramInt1][0] = new Integer(this.constPool.getIndexOf(localConstantPoolInfo));
        paramArrayOfObject[paramInt1][1] = tag2Image(localConstantPoolInfo.iTag);
        paramArrayOfObject[paramInt1][2] = localConstantPoolInfo.getExtraInfoString();
    }

    private ImageIcon tag2Image(int paramInt) {
        ImageIcon localImageIcon = imgUnknown;
        switch (paramInt) {
            case 7:
                localImageIcon = imgClass;
                break;
            case 9:
                localImageIcon = imgField;
                break;
            case 10:
                localImageIcon = imgMethod;
                break;
            case 11:
                localImageIcon = imgIface;
                break;
            case 8:
                localImageIcon = imgString;
                break;
            case 3:
                localImageIcon = imgInt;
                break;
            case 4:
                localImageIcon = imgFloat;
                break;
            case 5:
                localImageIcon = imgLong;
                break;
            case 6:
                localImageIcon = imgDouble;
                break;
            case 12:
                localImageIcon = imgNameAndType;
                break;
            case 1:
                localImageIcon = imgUTF;
        }
        return localImageIcon;
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/ConstPoolTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */