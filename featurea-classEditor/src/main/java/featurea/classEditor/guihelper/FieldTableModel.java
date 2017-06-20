package featurea.classEditor.guihelper;

import featurea.classEditor.classfile.*;
import featurea.classEditor.classfile.attributes.Attributes;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

public class FieldTableModel
        extends AbstractTableModel {
    private final String[] columnNames = {"Index", "Access & Modifier Flags", "Descriptor", "Name"};
    private Object[][] data;
    private ClassFile currClassFile;
    private ConstantPool constPool;
    private Hashtable hashUTF;
    private Hashtable hashDesc;
    private String[] strUTF;
    private String[] strDesc;
    private boolean bEditMode;

    public FieldTableModel(ClassFile paramClassFile) {
        this.currClassFile = paramClassFile;
        if (null != this.currClassFile) {
            this.constPool = this.currClassFile.constantPool;
            extractConstPoolInfo();
        } else {
            this.constPool = null;
            this.hashUTF = null;
            this.strUTF = null;
        }
        createData();
    }

    private JFrame getFrameFrom(JTable paramJTable) {
        int i = 0;
        Object localObject = paramJTable;
        for (i = 0; (i < 1000) && (!(localObject instanceof JFrame)); i++) {
            localObject = ((Component) localObject).getParent();
        }
        if ((localObject instanceof JFrame)) {
            return (JFrame) localObject;
        }
        return null;
    }

    public void setCellEditors(JTable paramJTable) {
        JButton localJButton = new JButton();
        AccessFlagEditor localAccessFlagEditor = new AccessFlagEditor(localJButton);
        AccessFlagEditorDialog localAccessFlagEditorDialog = new AccessFlagEditorDialog(getFrameFrom(paramJTable), true);
        localAccessFlagEditorDialog.setValidAccessFlags(AccessFlags.getValidFlags(0));
        TableColumn localTableColumn = paramJTable.getColumnModel().getColumn(1);
        localTableColumn.setCellEditor(localAccessFlagEditor);
        localJButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        /*localAccessFlagEditorDialog.setAccessFlags(localAccessFlagEditorDialog.currFlags);
        localAccessFlagEditorDialog.setLocationRelativeTo(localJButton);
        localAccessFlagEditorDialog.show();*/
            }
        });
        JComboBox localJComboBox1 = new JComboBox(this.strDesc);
        localJComboBox1.setEditable(true);
        localTableColumn = paramJTable.getColumnModel().getColumn(2);
        localTableColumn.setCellEditor(new DefaultCellEditor(localJComboBox1));
        JComboBox localJComboBox2 = new JComboBox(this.strUTF);
        localJComboBox2.setEditable(true);
        localTableColumn = paramJTable.getColumnModel().getColumn(3);
        localTableColumn.setCellEditor(new DefaultCellEditor(localJComboBox2));
    }

    private void createData() {
        if (null == this.currClassFile) {
            return;
        }
        int j = this.currClassFile.fields.getFieldsCount();
        this.data = new Object[j][this.columnNames.length];
        for (int i = 0; i < j; i++) {
            FieldInfo localFieldInfo = this.currClassFile.fields.getField(i);
            setValueAt(localFieldInfo, i, 0);
        }
    }

    public String getColumnName(int paramInt) {
        return this.columnNames[paramInt];
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

    public int nextIndex(int paramInt, String paramString) {
        if (null == this.data) {
            return -1;
        }
        while ((paramInt < this.data.length) && (((String) this.data[paramInt][2]).indexOf(paramString) < 0) && (((String) this.data[paramInt][3]).indexOf(paramString) < 0)) {
            paramInt++;
        }
        return paramInt < this.data.length ? paramInt : -1;
    }

    public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
        FieldInfo localFieldInfo;
        String str1;
        String str2;
        if ((paramObject instanceof FieldInfo)) {
            localFieldInfo = (FieldInfo) paramObject;
            str1 = localFieldInfo.getFieldDescriptor();
            str2 = localFieldInfo.cpName.sUTFStr;
            this.data[paramInt1][0] = new Integer(paramInt1 + 1);
            this.data[paramInt1][1] = localFieldInfo.accessFlags;
            this.data[paramInt1][2] = str1;
            this.data[paramInt1][3] = str2;
        } else {
            localFieldInfo = this.currClassFile.fields.getField(paramInt1);
            switch (paramInt2) {
                case 1:
                    localFieldInfo.accessFlags = ((AccessFlags) paramObject);
                    this.data[paramInt1][1] = localFieldInfo.accessFlags;
                    break;
                case 2:
                    str1 = (String) paramObject;
                    localFieldInfo.cpDescriptor.deleteRef();
                    localFieldInfo.cpDescriptor = searchAddOrModifyFieldDescInConstPool(str1, localFieldInfo.cpDescriptor);
                    localFieldInfo.cpDescriptor.addRef();
                    localFieldInfo.setFieldDescriptor(str1);
                    this.data[paramInt1][2] = str1;
                    break;
                case 3:
                    str2 = (String) paramObject;
                    localFieldInfo.cpName.deleteRef();
                    localFieldInfo.cpName = searchAddOrModifyFieldNameInConstPool(str2, localFieldInfo.cpName);
                    localFieldInfo.cpName.addRef();
                    localFieldInfo.setFieldName(str2);
                    this.data[paramInt1][3] = str2;
                    break;
            }
        }
    }

    public void addNewField() {
        FieldInfo localFieldInfo = new FieldInfo();
        AccessFlags localAccessFlags = new AccessFlags();
        localAccessFlags.setPublic(false);
        localAccessFlags.setFinal(false);
        localAccessFlags.setPrivate(false);
        localAccessFlags.setProtected(false);
        localAccessFlags.setStatic(false);
        localAccessFlags.setTransient(false);
        localAccessFlags.setVolatile(false);
        localFieldInfo.accessFlags = localAccessFlags;
        localFieldInfo.attributes = new Attributes();
        if (this.hashDesc.size() > 0) {
            String str = (String) this.hashDesc.keys().nextElement();
            int i = ((Integer) this.hashDesc.get(str)).intValue();
            localFieldInfo.cpDescriptor = this.currClassFile.constantPool.getPoolInfo(i);
            localFieldInfo.setFieldDescriptor(str);
        } else {
            localFieldInfo.cpDescriptor = addNewFieldDescInConstPool(null);
            localFieldInfo.setFieldDescriptor(Utils.getReadableDesc(localFieldInfo.cpDescriptor.sUTFStr));
        }
        localFieldInfo.cpName = addNewFieldNameInConstPool(null);
        localFieldInfo.setFieldName(localFieldInfo.cpName.sUTFStr);
        this.currClassFile.fields.addField(localFieldInfo);
    }

    private String getUniqueFieldName() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = "NewField" + i;
            if (null == this.hashUTF.get(str)) {
                return str;
            }
        }
        return "Phew";
    }

    private ConstantPoolInfo searchAddOrModifyFieldDescInConstPool(String paramString, ConstantPoolInfo paramConstantPoolInfo) {
        Integer localInteger = (Integer) this.hashDesc.get(paramString);
        if (null == localInteger) {
            if (paramConstantPoolInfo.getRef() > 0) {
                return addNewFieldDescInConstPool(paramString);
            }
            paramConstantPoolInfo.sUTFStr = Utils.getRawDesc(paramString);
            return paramConstantPoolInfo;
        }
        return this.currClassFile.constantPool.getPoolInfo(localInteger.intValue());
    }

    private ConstantPoolInfo searchAddOrModifyFieldNameInConstPool(String paramString, ConstantPoolInfo paramConstantPoolInfo) {
        Integer localInteger = (Integer) this.hashUTF.get(paramString);
        if (null == localInteger) {
            if (paramConstantPoolInfo.getRef() > 0) {
                return addNewFieldNameInConstPool(paramString);
            }
            paramConstantPoolInfo.sUTFStr = paramString;
            return paramConstantPoolInfo;
        }
        return this.currClassFile.constantPool.getPoolInfo(localInteger.intValue());
    }

    private ConstantPoolInfo addNewFieldDescInConstPool(String paramString) {
        String str = null;
        if (null != paramString) {
            str = Utils.getRawDesc(paramString);
            if (str.equals("unknown")) {
                return null;
            }
        }
        ConstantPoolInfo localConstantPoolInfo = new ConstantPoolInfo();
        localConstantPoolInfo.setConstPool(this.currClassFile.constantPool);
        localConstantPoolInfo.iTag = 1;
        localConstantPoolInfo.sUTFStr = (null == str ? "I" : str);
        this.currClassFile.constantPool.addNewPoolInfo(localConstantPoolInfo);
        return localConstantPoolInfo;
    }

    private ConstantPoolInfo addNewFieldNameInConstPool(String paramString) {
        ConstantPoolInfo localConstantPoolInfo = new ConstantPoolInfo();
        localConstantPoolInfo.setConstPool(this.currClassFile.constantPool);
        localConstantPoolInfo.iTag = 1;
        localConstantPoolInfo.sUTFStr = (null == paramString ? getUniqueFieldName() : paramString);
        this.currClassFile.constantPool.addNewPoolInfo(localConstantPoolInfo);
        return localConstantPoolInfo;
    }

    private void extractConstPoolInfo() {
        int i = this.constPool.getPoolInfoCount();
        this.hashUTF = new Hashtable();
        this.hashDesc = new Hashtable();
        for (int j = 0; j < i; j++) {
            Object localObject = this.constPool.getPoolInfo(j + 1);
            if (1 == ((ConstantPoolInfo) localObject).iTag) {
                if (Utils.isJavaIdentifier(((ConstantPoolInfo) localObject).sUTFStr)) {
                    this.hashUTF.put(((ConstantPoolInfo) localObject).sUTFStr, new Integer(j + 1));
                }
                String str = Utils.getReadableDesc(((ConstantPoolInfo) localObject).sUTFStr);
                if (!"unknown".equals(str)) {
                    this.hashDesc.put(str, new Integer(j + 1));
                }
            }
        }
        this.strUTF = new String[this.hashUTF.size()];
        Object localObject = this.hashUTF.keys();
        for (int j = 0; ((Enumeration) localObject).hasMoreElements(); j++) {
            this.strUTF[j] = ((String) ((Enumeration) localObject).nextElement());
        }
        this.strDesc = new String[this.hashDesc.size()];
        localObject = this.hashDesc.keys();
        for (int j = 0; ((Enumeration) localObject).hasMoreElements(); j++) {
            this.strDesc[j] = ((String) ((Enumeration) localObject).nextElement());
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/guihelper/FieldTableModel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */