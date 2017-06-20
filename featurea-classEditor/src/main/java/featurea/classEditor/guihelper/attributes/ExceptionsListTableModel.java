package featurea.classEditor.guihelper.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.classfile.Utils;
import featurea.classEditor.classfile.attributes.ExceptionsAttribute;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.util.Enumeration;
import java.util.Hashtable;

public class ExceptionsListTableModel
        extends AbstractTableModel {
    private final String[] columnNames = {"Index", "Exception"};
    private Object[][] data;
    private ExceptionsAttribute exceptAttrib;
    private ConstantPool constPool;
    private Hashtable hashDesc;
    private String[] strDesc;
    private boolean bEditMode;

    public ExceptionsListTableModel(ConstantPool paramConstantPool, ExceptionsAttribute paramExceptionsAttribute) {
        if (null != paramConstantPool) {
            this.constPool = paramConstantPool;
            this.exceptAttrib = paramExceptionsAttribute;
            extractConstPoolInfo();
        } else {
            this.constPool = null;
            this.exceptAttrib = null;
            this.hashDesc = null;
            this.strDesc = null;
        }
        createData();
    }

    public void setCellEditors(JTable paramJTable) {
        JComboBox localJComboBox = new JComboBox(this.strDesc);
        localJComboBox.setEditable(true);
        TableColumn localTableColumn = paramJTable.getColumnModel().getColumn(1);
        localTableColumn.setCellEditor(new DefaultCellEditor(localJComboBox));
    }

    private void createData() {
        if (null == this.exceptAttrib) {
            return;
        }
        int j = this.exceptAttrib.vectExceptionTypes.size();
        this.data = new Object[j][this.columnNames.length];
        for (int i = 0; i < j; i++) {
            ConstantPoolInfo localConstantPoolInfo = (ConstantPoolInfo) this.exceptAttrib.vectExceptionTypes.elementAt(i);
            setValueAt(localConstantPoolInfo, i, 0);
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
        if (0 == paramInt2) {
            return false;
        }
        return this.bEditMode;
    }

    public void setEditable(boolean paramBoolean) {
        this.bEditMode = paramBoolean;
    }

    private String GetExceptionDisplayStr(ConstantPoolInfo paramConstantPoolInfo) {
        return Utils.convertClassStrToStr(paramConstantPoolInfo.refUTF8.sUTFStr);
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        ConstantPoolInfo localConstantPoolInfo;
        if ((paramObject instanceof ConstantPoolInfo)) {
            localConstantPoolInfo = (ConstantPoolInfo) paramObject;
            String str1 = GetExceptionDisplayStr(localConstantPoolInfo);
            this.data[paramInt1][0] = new Integer(paramInt1 + 1);
            this.data[paramInt1][1] = str1;
        } else {
            localConstantPoolInfo = (ConstantPoolInfo) this.exceptAttrib.vectExceptionTypes.elementAt(paramInt1);
            switch (paramInt2) {
                case 1:
                    String str2 = (String) paramObject;
                    setCatchTypeUTF(localConstantPoolInfo, str2);
                    this.data[paramInt1][1] = GetExceptionDisplayStr(localConstantPoolInfo);
                    break;
            }
        }
    }

    public void addNewExceptionEntry() {
        ConstantPoolInfo localConstantPoolInfo1 = new ConstantPoolInfo();
        localConstantPoolInfo1.setConstPool(this.constPool);
        localConstantPoolInfo1.iTag = 7;
        ConstantPoolInfo localConstantPoolInfo2 = addNewExceptionClassUTFInConstPool(null);
        localConstantPoolInfo1.iNameIndex = this.constPool.getIndexOf(localConstantPoolInfo2);
        localConstantPoolInfo1.refUTF8 = localConstantPoolInfo2;
        this.constPool.addNewPoolInfo(localConstantPoolInfo1);
        this.exceptAttrib.vectExceptionTypes.add(localConstantPoolInfo1);
        this.exceptAttrib.iNumExceptions += 1;
    }

    private void setCatchTypeUTF(ConstantPoolInfo paramConstantPoolInfo, String paramString) {
        paramConstantPoolInfo.refUTF8.deleteRef();
        ConstantPoolInfo localConstantPoolInfo = searchAddOrModifyExceptionClassUTFInConstPool(paramString, paramConstantPoolInfo.refUTF8);
        paramConstantPoolInfo.iNameIndex = this.constPool.getIndexOf(localConstantPoolInfo);
        paramConstantPoolInfo.refUTF8 = localConstantPoolInfo;
        paramConstantPoolInfo.refUTF8.addRef();
    }

    private ConstantPoolInfo searchAddOrModifyExceptionClassUTFInConstPool(String paramString, ConstantPoolInfo paramConstantPoolInfo) {
        Integer localInteger = (Integer) this.hashDesc.get(paramString);
        if (null == localInteger) {
            if ((null == paramConstantPoolInfo) || (paramConstantPoolInfo.getRef() > 0)) {
                return addNewExceptionClassUTFInConstPool(paramString);
            }
            paramConstantPoolInfo.sUTFStr = Utils.convertStrToClassStr(paramString);
            return paramConstantPoolInfo;
        }
        return this.constPool.getPoolInfo(localInteger.intValue());
    }

    private ConstantPoolInfo addNewExceptionClassUTFInConstPool(String paramString) {
        String str = null;
        if (null != paramString) {
            str = Utils.getRawDesc(paramString);
            if (str.equals("unknown")) {
                return null;
            }
        }
        ConstantPoolInfo localConstantPoolInfo = new ConstantPoolInfo();
        localConstantPoolInfo.setConstPool(this.constPool);
        localConstantPoolInfo.iTag = 1;
        localConstantPoolInfo.sUTFStr = (null == str ? "java/lang/Exception" : str);
        this.constPool.addNewPoolInfo(localConstantPoolInfo);
        return localConstantPoolInfo;
    }

    private void extractConstPoolInfo() {
        int i = this.constPool.getPoolInfoCount();
        this.hashDesc = new Hashtable();
        for (int j = 0; j < i; j++) {
            Object localObject = this.constPool.getPoolInfo(j + 1);
            if ((1 == ((ConstantPoolInfo) localObject).iTag) && (Utils.isJavaClassString(((ConstantPoolInfo) localObject).sUTFStr))) {
                String str = Utils.convertClassStrToStr(((ConstantPoolInfo) localObject).sUTFStr);
                this.hashDesc.put(str, new Integer(j + 1));
            }
        }
        this.strDesc = new String[this.hashDesc.size()];
        Object localObject = this.hashDesc.keys();
        for (int j = 0; ((Enumeration) localObject).hasMoreElements(); j++) {
            this.strDesc[j] = ((String) ((Enumeration) localObject).nextElement());
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/attributes/ExceptionsListTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */