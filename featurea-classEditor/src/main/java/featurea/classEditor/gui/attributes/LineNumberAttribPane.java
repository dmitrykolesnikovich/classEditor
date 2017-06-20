package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.LineNumberTableAttribute;
import featurea.classEditor.guihelper.attributes.LineNumberTableAttribTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LineNumberAttribPane
        extends JPanel
        implements AttribDisplay {
    private LineNumberTableAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private JScrollPane jScrollPane2;
    private JTable tblLineNumberEntries;
    private JPanel jPanel1;
    private JButton btnAdd;
    private JButton btnDelete;

    public LineNumberAttribPane(boolean paramBoolean) {
        this.bModifyMode = paramBoolean;
        initComponents();
        this.btnAdd.setEnabled(paramBoolean);
        this.btnDelete.setEnabled(paramBoolean);
    }

    public void setInput(Attribute paramAttribute, ConstantPool paramConstantPool) {
        this.attribute = ((LineNumberTableAttribute) paramAttribute);
        this.constPool = paramConstantPool;
        setTableModel();
    }

    private void setTableModel() {
        LineNumberTableAttribTableModel localLineNumberTableAttribTableModel = new LineNumberTableAttribTableModel(this.attribute);
        localLineNumberTableAttribTableModel.setEditable(this.bModifyMode);
        this.tblLineNumberEntries.setModel(localLineNumberTableAttribTableModel);
        TableColumn localTableColumn = this.tblLineNumberEntries.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(30);
        localTableColumn.setMaxWidth(80);
        localTableColumn = this.tblLineNumberEntries.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
        localTableColumn = this.tblLineNumberEntries.getColumnModel().getColumn(2);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
    }

    private void initComponents() {
        this.jScrollPane2 = new JScrollPane();
        this.tblLineNumberEntries = new JTable();
        this.tblLineNumberEntries.setSelectionMode(0);
        this.jPanel1 = new JPanel();
        this.btnAdd = new JButton();
        this.btnDelete = new JButton();
        setLayout(new GridBagLayout());
        this.tblLineNumberEntries.setModel(new LineNumberTableAttribTableModel(null));
        this.jScrollPane2.setViewportView(this.tblLineNumberEntries);
        GridBagConstraints localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.gridwidth = 0;
        localGridBagConstraints1.fill = 1;
        localGridBagConstraints1.insets = new Insets(10, 10, 10, 10);
        localGridBagConstraints1.weightx = 1.0D;
        localGridBagConstraints1.weighty = 1.0D;
        add(this.jScrollPane2, localGridBagConstraints1);
        this.jPanel1.setLayout(new GridBagLayout());
        this.btnAdd.setText("Add");
        this.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                LineNumberAttribPane.this.btnAddActionPerformed(paramAnonymousActionEvent);
            }
        });
        GridBagConstraints localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.anchor = 13;
        this.jPanel1.add(this.btnAdd, localGridBagConstraints2);
        this.btnDelete.setText("Delete");
        this.btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                LineNumberAttribPane.this.btnDeleteActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints2 = new GridBagConstraints();
        localGridBagConstraints2.anchor = 17;
        this.jPanel1.add(this.btnDelete, localGridBagConstraints2);
        localGridBagConstraints1 = new GridBagConstraints();
        localGridBagConstraints1.gridwidth = 0;
        localGridBagConstraints1.fill = 1;
        add(this.jPanel1, localGridBagConstraints1);
    }

    private void btnDeleteActionPerformed(ActionEvent paramActionEvent) {
        int i = this.tblLineNumberEntries.getSelectedRow();
        if (0 > i) {
            return;
        }
        this.attribute.deleteEntryAt(i);
        setTableModel();
    }

    private void btnAddActionPerformed(ActionEvent paramActionEvent) {
        this.attribute.addNewEntry();
        setTableModel();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/attributes/LineNumberAttribPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */