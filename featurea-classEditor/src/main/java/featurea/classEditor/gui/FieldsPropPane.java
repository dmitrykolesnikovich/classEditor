package featurea.classEditor.gui;

import featurea.classEditor.classfile.ClassFile;
import featurea.classEditor.classfile.FieldInfo;
import featurea.classEditor.classfile.attributes.Attributes;
import featurea.classEditor.gui.attributes.AttributesDialog;
import featurea.classEditor.guihelper.AttributeTreeNode;
import featurea.classEditor.guihelper.FieldTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldsPropPane
        extends JPanel {
    private ClassFile currClassFile;
    private int iPrevFld = -1;
    private boolean bEditable;
    private JPanel attribPanel;
    private JButton btnAddFld;
    private JButton btnDelFld;
    private JButton btnFldSearch;
    private JButton btnShowFldAttribs;
    private JPanel editPanel;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea2;
    private JLabel lblNumFldAttribs;
    private JPanel srchPanel;
    private JTable tblField;
    private JTextField txtFldSearch;

    public FieldsPropPane() {
        initComponents();
    }

    public void setModifyMode(boolean paramBoolean) {
        this.bEditable = paramBoolean;
        FieldTableModel localFieldTableModel = (FieldTableModel) this.tblField.getModel();
        localFieldTableModel.setEditable(this.bEditable);
        this.btnAddFld.setEnabled(this.bEditable);
        this.btnDelFld.setEnabled(this.bEditable);
    }

    void clear() {
        this.iPrevFld = -1;
        this.tblField.setModel(new FieldTableModel(null));
        this.lblNumFldAttribs.setText("");
    }

    void refresh() {
        clear();
        if (null == this.currClassFile) {
            return;
        }
        FieldTableModel localFieldTableModel = new FieldTableModel(this.currClassFile);
        localFieldTableModel.setEditable(this.bEditable);
        this.tblField.setModel(localFieldTableModel);
        localFieldTableModel.setCellEditors(this.tblField);
        TableColumn localTableColumn = this.tblField.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(30);
        localTableColumn.setMaxWidth(80);
        localTableColumn = this.tblField.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
        localTableColumn = this.tblField.getColumnModel().getColumn(2);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
        localTableColumn = this.tblField.getColumnModel().getColumn(3);
        localTableColumn.setPreferredWidth(200);
        localTableColumn.setMaxWidth(400);
        this.tblField.changeSelection(0, 1, false, false);
    }

    void setClassFile(ClassFile paramClassFile) {
        this.currClassFile = paramClassFile;
    }

    private void searchField() {
        if (null == this.currClassFile) {
            return;
        }
        String str = this.txtFldSearch.getText().trim();
        if (str.length() <= 0) {
            return;
        }
        if (-1 == this.iPrevFld) {
            return;
        }
        FieldTableModel localFieldTableModel = (FieldTableModel) this.tblField.getModel();
        int i = localFieldTableModel.nextIndex(this.iPrevFld + 1, str);
        if (i >= 0) {
            this.iPrevFld = i;
            this.tblField.changeSelection(this.iPrevFld, 1, false, false);
        }
    }

    private void tblFieldValueChanged(ListSelectionEvent paramListSelectionEvent) {
        if (paramListSelectionEvent.getValueIsAdjusting()) {
            return;
        }
        if ((null == this.currClassFile) || (null == this.currClassFile.fields) || (this.currClassFile.fields.getFieldsCount() == 0)) {
            this.iPrevFld = -1;
            return;
        }
        int i = this.tblField.getSelectedRow();
        if (0 > i) {
            this.iPrevFld = -1;
            return;
        }
        this.iPrevFld = i;
        FieldInfo localFieldInfo = this.currClassFile.fields.getField(i);
        this.lblNumFldAttribs.setText(Integer.toString(null != localFieldInfo.attributes ? localFieldInfo.attributes.getAttribCount() : 0));
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.tblField = new JTable();
        this.tblField.setSelectionMode(0);
        ListSelectionModel localListSelectionModel = this.tblField.getSelectionModel();
        localListSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent paramAnonymousListSelectionEvent) {
                FieldsPropPane.this.tblFieldValueChanged(paramAnonymousListSelectionEvent);
            }
        });
        this.srchPanel = new JPanel();
        this.jTextArea2 = new JTextArea();
        this.txtFldSearch = new JTextField();
        this.btnFldSearch = new JButton();
        this.attribPanel = new JPanel();
        this.jLabel5 = new JLabel();
        this.lblNumFldAttribs = new JLabel();
        this.btnShowFldAttribs = new JButton();
        this.editPanel = new JPanel();
        this.btnAddFld = new JButton();
        this.btnDelFld = new JButton();
        setLayout(new GridBagLayout());
        this.jScrollPane1.setAutoscrolls(true);
        this.tblField.setModel(new FieldTableModel(null));
        this.tblField.setShowHorizontalLines(false);
        this.jScrollPane1.setViewportView(this.tblField);
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jScrollPane1, localGridBagConstraints);
        this.srchPanel.setLayout(new GridBagLayout());
        this.srchPanel.setBorder(new TitledBorder("Search"));
        this.jTextArea2.setText("Enter search string below and press Find \nto search from the current position");
        this.jTextArea2.setBackground(new Color(204, 204, 255));
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.weighty = 1.0D;
        this.srchPanel.add(this.jTextArea2, localGridBagConstraints);
        this.txtFldSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                FieldsPropPane.this.txtFldSearchActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        this.srchPanel.add(this.txtFldSearch, localGridBagConstraints);
        this.btnFldSearch.setText("Find/Find Next");
        this.btnFldSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                FieldsPropPane.this.btnFldSearchActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.anchor = 13;
        this.srchPanel.add(this.btnFldSearch, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.anchor = 17;
        localGridBagConstraints.weightx = 1.0D;
        add(this.srchPanel, localGridBagConstraints);
        this.attribPanel.setLayout(new GridBagLayout());
        this.attribPanel.setBorder(new TitledBorder("Field Attributes"));
        this.jLabel5.setText("Number of Attributes");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        this.attribPanel.add(this.jLabel5, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.attribPanel.add(this.lblNumFldAttribs, localGridBagConstraints);
        this.btnShowFldAttribs.setText("Show/Edit");
        this.btnShowFldAttribs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                FieldsPropPane.this.btnShowFldAttribsActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.anchor = 13;
        localGridBagConstraints.insets = new Insets(0, 4, 0, 0);
        this.attribPanel.add(this.btnShowFldAttribs, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 0.5D;
        add(this.attribPanel, localGridBagConstraints);
        this.editPanel.setLayout(new GridBagLayout());
        this.editPanel.setBorder(new TitledBorder("Edit"));
        this.btnAddFld.setText("Add New");
        this.btnAddFld.setEnabled(false);
        this.btnAddFld.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                FieldsPropPane.this.btnAddFldActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = -1;
        this.editPanel.add(this.btnAddFld, localGridBagConstraints);
        this.btnDelFld.setText("Delete");
        this.btnDelFld.setEnabled(false);
        this.btnDelFld.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                FieldsPropPane.this.btnDelFldActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        this.editPanel.add(this.btnDelFld, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 0.5D;
        add(this.editPanel, localGridBagConstraints);
    }

    private void btnShowFldAttribsActionPerformed(ActionEvent paramActionEvent) {
        if (null == this.currClassFile) {
            return;
        }
        if (-1 == this.iPrevFld) {
            return;
        }
        FieldInfo localFieldInfo = this.currClassFile.fields.getField(this.iPrevFld);
        if (null == localFieldInfo) {
            return;
        }
        Attributes localAttributes = localFieldInfo.attributes;
        if (null == localAttributes) {
            return;
        }
        AttributesDialog localAttributesDialog = new AttributesDialog(AttributeTreeNode.getFrameFrom(this), true);
        localAttributesDialog.setTitle("Field Attributes");
        localAttributesDialog.setInput(localAttributes, this.currClassFile.constantPool, this.bEditable);
        localAttributesDialog.show();
    }

    private void btnDelFldActionPerformed(ActionEvent paramActionEvent) {
        if (-1 == this.iPrevFld) {
            return;
        }
        FieldInfo localFieldInfo = this.currClassFile.fields.getField(this.iPrevFld);
        String str = localFieldInfo.accessFlags.toString() + " " + localFieldInfo.getFieldDescriptor() + " " + localFieldInfo.cpName.sUTFStr;
        int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the field\n" + str + "?", "Confirm Delete", 0);
        if (0 == i) {
            this.currClassFile.fields.deleteField(this.iPrevFld);
            refresh();
        }
    }

    private void btnAddFldActionPerformed(ActionEvent paramActionEvent) {
        if (null == this.currClassFile) {
            return;
        }
        ((FieldTableModel) this.tblField.getModel()).addNewField();
        refresh();
        this.tblField.changeSelection(this.tblField.getModel().getRowCount() - 1, 1, false, false);
    }

    private void txtFldSearchActionPerformed(ActionEvent paramActionEvent) {
        searchField();
    }

    private void btnFldSearchActionPerformed(ActionEvent paramActionEvent) {
        searchField();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/FieldsPropPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */