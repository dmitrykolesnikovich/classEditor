package featurea.classEditor.gui;

import featurea.classEditor.classfile.ClassFile;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.guihelper.ConstPoolTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConstantPoolPropPane
        extends JPanel {
    static String sClassDesc = "The name_index item points to a Utf8 structure representing a valid fully qualified Java class name.";
    static String sRefDesc = "The class_index item points to a Class structure representing the class or interface type that contains the declaration of the field or method. The name_and_type_index item points to the name and descriptor of the field or method.";
    static String sStringDesc = "The string_index item points to a Utf8 structure";
    static String sIntFltDesc = "Four bytes of data holding the value.";
    static String sLongDoubleDesc = "Eight bytes of data holding the value.";
    static String sNameAndTypeDesc = "The name_index points to a Utf8 structure denoting the field or method name. The descriptor_index points points to a Utf8 structure denoting a valid field or method descriptor.";
    static String sUtfDesc = "String in Utf format. Utf format stores the length and the data after that.";
    private ClassFile currClassFile;
    private int iFilterTag;
    private ConstantPoolInfo prevConstPoolEntry;
    private boolean bEditable;
    private JButton btnAddNew;
    private JButton btnApplyFilter;
    private JButton btnConstPoolSearch;
    private JButton btnDelete;
    private JButton btnJumpTo;
    private JButton btnModify;
    private JComboBox cmbFilter;
    private JComboBox cmbJumpTo;
    private JComboBox cmbPoolEntryType;
    private JPanel descriptionPanel;
    private JPanel detailsPanel;
    private JPanel editPanel;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel5;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane2;
    private JTextArea jTextArea2;
    private JPanel navigatePanel;
    private JPanel searchPanel;
    private JTable tblConstPool;
    private JTextField txtClassIndex;
    private JTextField txtConstPoolSearch;
    private JTextField txtDescriptorIndex;
    private JTextField txtNameAndTypeIndex;
    private JTextField txtNameIndex;
    private JTextArea txtPoolTypeDesc;
    private JTextField txtStringIndex;
    private JTextField txtValue;

    public ConstantPoolPropPane() {
        initComponents();
        this.iFilterTag = ConstPoolTableModel.NO_FILTER;
    }

    public void setModifyMode(boolean paramBoolean) {
        this.bEditable = paramBoolean;
        this.cmbPoolEntryType.setEnabled(this.bEditable);
        this.txtStringIndex.setEnabled(this.bEditable);
        this.txtClassIndex.setEnabled(this.bEditable);
        this.txtNameAndTypeIndex.setEnabled(this.bEditable);
        this.txtNameIndex.setEnabled(this.bEditable);
        this.txtDescriptorIndex.setEnabled(this.bEditable);
        this.txtValue.setEnabled(this.bEditable);
        this.btnAddNew.setEnabled(this.bEditable);
        this.btnModify.setEnabled(this.bEditable);
        this.btnDelete.setEnabled(this.bEditable);
    }

    void clear() {
        this.tblConstPool.setModel(new ConstPoolTableModel(null));
        this.cmbPoolEntryType.setSelectedIndex(0);
        this.cmbFilter.setSelectedIndex(0);
        this.txtStringIndex.setText("");
        this.txtClassIndex.setText("");
        this.txtNameAndTypeIndex.setText("");
        this.txtNameIndex.setText("");
        this.txtDescriptorIndex.setText("");
        this.txtValue.setText("");
        this.cmbJumpTo.setEditable(true);
        this.cmbJumpTo.setModel(new DefaultComboBoxModel());
        this.txtPoolTypeDesc.setText("");
        this.txtConstPoolSearch.setText("");
        this.prevConstPoolEntry = null;
    }

    void refresh() {
        clear();
        if (null == this.currClassFile) {
            return;
        }
        ConstPoolTableModel localConstPoolTableModel = new ConstPoolTableModel(this.currClassFile.constantPool);
        localConstPoolTableModel.setFilter(this.iFilterTag);
        localConstPoolTableModel.applyFilter();
        this.tblConstPool.setModel(localConstPoolTableModel);
        TableColumn localTableColumn = this.tblConstPool.getColumnModel().getColumn(0);
        localTableColumn.setPreferredWidth(30);
        localTableColumn.setMaxWidth(100);
        localTableColumn = this.tblConstPool.getColumnModel().getColumn(1);
        localTableColumn.setPreferredWidth(15);
        localTableColumn.setMaxWidth(60);
        localTableColumn = this.tblConstPool.getColumnModel().getColumn(2);
        localTableColumn.setPreferredWidth(400);
        localTableColumn.setMaxWidth(1000);
        this.tblConstPool.changeSelection(0, 1, false, false);
        setConstantPoolTypeSelector(this.cmbFilter, this.iFilterTag);
    }

    void setClassFile(ClassFile paramClassFile) {
        this.currClassFile = paramClassFile;
    }

    private void setConstantPoolTypeSelector(JComboBox paramJComboBox, int paramInt) {
        switch (paramInt) {
            case 7:
                paramJComboBox.setSelectedIndex(1);
                break;
            case 6:
                paramJComboBox.setSelectedIndex(2);
                break;
            case 9:
                paramJComboBox.setSelectedIndex(3);
                break;
            case 4:
                paramJComboBox.setSelectedIndex(4);
                break;
            case 3:
                paramJComboBox.setSelectedIndex(5);
                break;
            case 11:
                paramJComboBox.setSelectedIndex(6);
                break;
            case 5:
                paramJComboBox.setSelectedIndex(7);
                break;
            case 10:
                paramJComboBox.setSelectedIndex(8);
                break;
            case 12:
                paramJComboBox.setSelectedIndex(9);
                break;
            case 8:
                paramJComboBox.setSelectedIndex(10);
                break;
            case 1:
                paramJComboBox.setSelectedIndex(11);
                break;
            case 2:
            default:
                paramJComboBox.setSelectedIndex(0);
        }
    }

    private void searchPool() {
        if (null == this.currClassFile) {
            return;
        }
        String str = this.txtConstPoolSearch.getText();
        str = str.trim();
        if (str.length() <= 0) {
            return;
        }
        int i = this.tblConstPool.getSelectedRow();
        ConstPoolTableModel localConstPoolTableModel = (ConstPoolTableModel) this.tblConstPool.getModel();
        i = localConstPoolTableModel.nextIndex(i + 1, str);
        if (i >= 0) {
            this.tblConstPool.changeSelection(i, 1, false, false);
        }
    }

    private void tblConstPoolValueChanged(ListSelectionEvent paramListSelectionEvent) {
        if (paramListSelectionEvent.getValueIsAdjusting()) {
            return;
        }
        DefaultComboBoxModel localDefaultComboBoxModel = new DefaultComboBoxModel();
        this.txtStringIndex.setText("");
        this.txtClassIndex.setText("");
        this.txtNameAndTypeIndex.setText("");
        this.txtNameIndex.setText("");
        this.txtDescriptorIndex.setText("");
        this.txtValue.setText("");
        this.cmbJumpTo.setEditable(true);
        this.cmbJumpTo.setModel(localDefaultComboBoxModel);
        int i = this.tblConstPool.getSelectedRow();
        if (0 > i) {
            return;
        }
        Integer localInteger = (Integer) this.tblConstPool.getValueAt(i, 0);
        if (null == localInteger) {
            return;
        }
        i = localInteger.intValue();
        ConstantPoolInfo localConstantPoolInfo = this.currClassFile.constantPool.getPoolInfo(i);
        this.prevConstPoolEntry = localConstantPoolInfo;
        this.tblConstPool.setToolTipText("Selected: " + localConstantPoolInfo.getExtraInfoString());
        if (null == localConstantPoolInfo) {
            return;
        }
        setConstantPoolTypeSelector(this.cmbPoolEntryType, localConstantPoolInfo.iTag);
        switch (localConstantPoolInfo.iTag) {
            case 7:
                this.txtNameIndex.setText(Integer.toString(localConstantPoolInfo.iNameIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iNameIndex));
                this.cmbJumpTo.setModel(localDefaultComboBoxModel);
                break;
            case 6:
                this.txtValue.setText(Double.toString(localConstantPoolInfo.dDoubleVal));
                break;
            case 9:
                this.txtClassIndex.setText(Integer.toString(localConstantPoolInfo.iClassIndex));
                this.txtNameAndTypeIndex.setText(Integer.toString(localConstantPoolInfo.iNameAndTypeIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iClassIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iNameAndTypeIndex));
                this.cmbJumpTo.setModel(localDefaultComboBoxModel);
                break;
            case 4:
                this.txtValue.setText(Float.toString(localConstantPoolInfo.fFloatVal));
                break;
            case 3:
                this.txtValue.setText(Integer.toString(localConstantPoolInfo.iIntValue));
                break;
            case 11:
                this.txtClassIndex.setText(Integer.toString(localConstantPoolInfo.iClassIndex));
                this.txtNameAndTypeIndex.setText(Integer.toString(localConstantPoolInfo.iNameAndTypeIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iClassIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iNameAndTypeIndex));
                this.cmbJumpTo.setModel(localDefaultComboBoxModel);
                break;
            case 5:
                this.txtValue.setText(Long.toString(localConstantPoolInfo.lLongVal));
                break;
            case 10:
                this.txtClassIndex.setText(Integer.toString(localConstantPoolInfo.iClassIndex));
                this.txtNameAndTypeIndex.setText(Integer.toString(localConstantPoolInfo.iNameAndTypeIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iClassIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iNameAndTypeIndex));
                this.cmbJumpTo.setModel(localDefaultComboBoxModel);
                break;
            case 12:
                this.txtNameIndex.setText(Integer.toString(localConstantPoolInfo.iNameIndex));
                this.txtDescriptorIndex.setText(Integer.toString(localConstantPoolInfo.iDescriptorIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iNameIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iDescriptorIndex));
                this.cmbJumpTo.setModel(localDefaultComboBoxModel);
                break;
            case 8:
                this.txtStringIndex.setText(Integer.toString(localConstantPoolInfo.iStringIndex));
                localDefaultComboBoxModel.addElement(Integer.toString(localConstantPoolInfo.iStringIndex));
                this.cmbJumpTo.setModel(localDefaultComboBoxModel);
                break;
            case 1:
                this.txtValue.setText(localConstantPoolInfo.sUTFStr);
        }
    }

    private void initComponents() {
        this.jScrollPane2 = new JScrollPane();
        this.tblConstPool = new JTable();
        this.tblConstPool.setSelectionMode(0);
        ListSelectionModel localListSelectionModel = this.tblConstPool.getSelectionModel();
        localListSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent paramAnonymousListSelectionEvent) {
                ConstantPoolPropPane.this.tblConstPoolValueChanged(paramAnonymousListSelectionEvent);
            }
        });
        this.detailsPanel = new JPanel();
        this.jLabel5 = new JLabel();
        this.cmbPoolEntryType = new JComboBox(new String[]{"", "Class", "Double", "Fieldref", "Float", "Integer", "InterfaceMethodref", "Long", "Methodref", "NameAndType", "String", "Utf8"});
        this.jLabel7 = new JLabel();
        this.txtStringIndex = new JTextField();
        this.jLabel8 = new JLabel();
        this.txtClassIndex = new JTextField();
        this.jLabel9 = new JLabel();
        this.txtNameAndTypeIndex = new JTextField();
        this.jLabel10 = new JLabel();
        this.txtNameIndex = new JTextField();
        this.jLabel11 = new JLabel();
        this.txtDescriptorIndex = new JTextField();
        this.jLabel12 = new JLabel();
        this.txtValue = new JTextField();
        this.searchPanel = new JPanel();
        this.jTextArea2 = new JTextArea();
        this.txtConstPoolSearch = new JTextField();
        this.btnConstPoolSearch = new JButton();
        this.jLabel1 = new JLabel();
        this.cmbFilter = new JComboBox(new String[]{"", "Class", "Double", "Fieldref", "Float", "Integer", "InterfaceMethodref", "Long", "Methodref", "NameAndType", "String", "Utf8"});
        this.btnApplyFilter = new JButton();
        this.editPanel = new JPanel();
        this.btnAddNew = new JButton();
        this.btnModify = new JButton();
        this.btnDelete = new JButton();
        this.navigatePanel = new JPanel();
        this.jLabel13 = new JLabel();
        this.cmbJumpTo = new JComboBox();
        this.btnJumpTo = new JButton();
        this.descriptionPanel = new JPanel();
        this.txtPoolTypeDesc = new JTextArea();
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Constant Pool"));
        this.tblConstPool.setModel(new ConstPoolTableModel(null));
        this.tblConstPool.setShowHorizontalLines(false);
        this.jScrollPane2.setViewportView(this.tblConstPool);
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        add(this.jScrollPane2, localGridBagConstraints);
        this.detailsPanel.setLayout(new GridBagLayout());
        this.detailsPanel.setBorder(new TitledBorder("Details"));
        this.jLabel5.setText("Type");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel5, localGridBagConstraints);
        this.cmbPoolEntryType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent paramAnonymousItemEvent) {
                ConstantPoolPropPane.this.cmbPoolEntryTypeItemStateChanged(paramAnonymousItemEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        this.detailsPanel.add(this.cmbPoolEntryType, localGridBagConstraints);
        this.jLabel7.setText("String");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel7, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.detailsPanel.add(this.txtStringIndex, localGridBagConstraints);
        this.jLabel8.setText("Class");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel8, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.detailsPanel.add(this.txtClassIndex, localGridBagConstraints);
        this.jLabel9.setText("Name and Type");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel9, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.detailsPanel.add(this.txtNameAndTypeIndex, localGridBagConstraints);
        this.jLabel10.setText("Name Index");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel10, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.detailsPanel.add(this.txtNameIndex, localGridBagConstraints);
        this.jLabel11.setText("Descriptor");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel11, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.detailsPanel.add(this.txtDescriptorIndex, localGridBagConstraints);
        this.jLabel12.setText("Value");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        this.detailsPanel.add(this.jLabel12, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.gridheight = -1;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.weightx = 1.0D;
        this.detailsPanel.add(this.txtValue, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        add(this.detailsPanel, localGridBagConstraints);
        this.searchPanel.setLayout(new GridBagLayout());
        this.searchPanel.setBorder(new TitledBorder("Search"));
        this.jTextArea2.setWrapStyleWord(true);
        this.jTextArea2.setEditable(false);
        this.jTextArea2.setText("Enter search string below and press Find \nto search from the current position");
        this.jTextArea2.setBackground(new Color(204, 204, 255));
        this.jTextArea2.setRequestFocusEnabled(false);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        this.searchPanel.add(this.jTextArea2, localGridBagConstraints);
        this.txtConstPoolSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.txtConstPoolSearchActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 2;
        localGridBagConstraints.anchor = 17;
        this.searchPanel.add(this.txtConstPoolSearch, localGridBagConstraints);
        this.btnConstPoolSearch.setText("Find/Find Next");
        this.btnConstPoolSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.btnConstPoolSearchActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.anchor = 13;
        this.searchPanel.add(this.btnConstPoolSearch, localGridBagConstraints);
        this.jLabel1.setText("Filter");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.insets = new Insets(10, 0, 0, 0);
        this.searchPanel.add(this.jLabel1, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.insets = new Insets(10, 4, 0, 0);
        this.searchPanel.add(this.cmbFilter, localGridBagConstraints);
        this.btnApplyFilter.setText("Apply");
        this.btnApplyFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.btnApplyFilterActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.insets = new Insets(10, 4, 0, 0);
        this.searchPanel.add(this.btnApplyFilter, localGridBagConstraints);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        add(this.searchPanel, localGridBagConstraints);
        this.editPanel.setBorder(new TitledBorder("Edit"));
        this.btnAddNew.setText("Add New");
        this.btnAddNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.btnAddNewActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.editPanel.add(this.btnAddNew);
        this.btnModify.setText("Modify");
        this.btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.btnModifyActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.editPanel.add(this.btnModify);
        this.btnDelete.setText("Delete");
        this.btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.btnDeleteActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.editPanel.add(this.btnDelete);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        add(this.editPanel, localGridBagConstraints);
        this.navigatePanel.setBorder(new TitledBorder("Navigate"));
        this.jLabel13.setText("Go to");
        this.navigatePanel.add(this.jLabel13);
        this.navigatePanel.add(this.cmbJumpTo);
        this.btnJumpTo.setText("Go");
        this.btnJumpTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                ConstantPoolPropPane.this.btnJumpToActionPerformed(paramAnonymousActionEvent);
            }
        });
        this.navigatePanel.add(this.btnJumpTo);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        add(this.navigatePanel, localGridBagConstraints);
        this.descriptionPanel.setLayout(new GridLayout(1, 0));
        this.descriptionPanel.setBorder(new TitledBorder("Description"));
        this.txtPoolTypeDesc.setWrapStyleWord(true);
        this.txtPoolTypeDesc.setLineWrap(true);
        this.txtPoolTypeDesc.setEditable(false);
        this.txtPoolTypeDesc.setRows(10);
        this.txtPoolTypeDesc.setBackground(new Color(204, 204, 255));
        this.txtPoolTypeDesc.setRequestFocusEnabled(false);
        this.descriptionPanel.add(this.txtPoolTypeDesc);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.gridheight = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 0.2D;
        add(this.descriptionPanel, localGridBagConstraints);
    }

    private void btnApplyFilterActionPerformed(ActionEvent paramActionEvent) {
        int i = this.cmbFilter.getSelectedIndex();
        switch (i) {
            case 1:
                this.iFilterTag = 7;
                break;
            case 2:
                this.iFilterTag = 6;
                break;
            case 3:
                this.iFilterTag = 9;
                break;
            case 4:
                this.iFilterTag = 4;
                break;
            case 5:
                this.iFilterTag = 3;
                break;
            case 6:
                this.iFilterTag = 11;
                break;
            case 7:
                this.iFilterTag = 5;
                break;
            case 8:
                this.iFilterTag = 10;
                break;
            case 9:
                this.iFilterTag = 12;
                break;
            case 10:
                this.iFilterTag = 8;
                break;
            case 11:
                this.iFilterTag = 1;
                break;
            default:
                this.iFilterTag = ConstPoolTableModel.NO_FILTER;
        }
        refresh();
    }

    private void txtConstPoolSearchActionPerformed(ActionEvent paramActionEvent) {
        searchPool();
    }

    private void btnConstPoolSearchActionPerformed(ActionEvent paramActionEvent) {
        searchPool();
    }

    private void btnModifyActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.prevConstPoolEntry) {
            setConstantPoolTypeSelector(this.cmbPoolEntryType, this.prevConstPoolEntry.iTag);
            switch (this.prevConstPoolEntry.iTag) {
                case 7:
                    this.prevConstPoolEntry.setNameIndex(Integer.parseInt(this.txtNameIndex.getText()));
                    break;
                case 6:
                    this.prevConstPoolEntry.dDoubleVal = Double.valueOf(this.txtValue.getText()).doubleValue();
                    break;
                case 4:
                    this.prevConstPoolEntry.fFloatVal = Float.valueOf(this.txtValue.getText()).floatValue();
                    break;
                case 3:
                    this.prevConstPoolEntry.iIntValue = Integer.parseInt(this.txtValue.getText());
                    break;
                case 9:
                case 10:
                case 11:
                    this.prevConstPoolEntry.setClassIndex(Integer.parseInt(this.txtClassIndex.getText()));
                    this.prevConstPoolEntry.setNameAndTypeIndex(Integer.parseInt(this.txtNameAndTypeIndex.getText()));
                    break;
                case 5:
                    this.prevConstPoolEntry.lLongVal = Long.parseLong(this.txtValue.getText());
                    break;
                case 12:
                    this.prevConstPoolEntry.setNameIndex(Integer.parseInt(this.txtNameIndex.getText()));
                    this.prevConstPoolEntry.setDescriptorIndex(Integer.parseInt(this.txtDescriptorIndex.getText()));
                    break;
                case 8:
                    this.prevConstPoolEntry.setStringIndex(Integer.parseInt(this.txtStringIndex.getText()));
                    break;
                case 1:
                    this.prevConstPoolEntry.sUTFStr = this.txtValue.getText();
            }
        }
        ConstPoolTableModel localConstPoolTableModel = (ConstPoolTableModel) this.tblConstPool.getModel();
        int i = this.tblConstPool.getSelectedRow();
        localConstPoolTableModel.setValueAt(this.prevConstPoolEntry, i, 0);
        this.tblConstPool.setModel(localConstPoolTableModel);
        this.tblConstPool.changeSelection(i, 1, false, false);
    }

    private void btnAddNewActionPerformed(ActionEvent paramActionEvent) {
        if (null == this.currClassFile) {
            return;
        }
        ConstantPoolInfo localConstantPoolInfo = new ConstantPoolInfo();
        localConstantPoolInfo.setConstPool(this.currClassFile.constantPool);
        switch (this.cmbPoolEntryType.getSelectedIndex()) {
            case 0:
                return;
            case 1:
                localConstantPoolInfo.iTag = 7;
                localConstantPoolInfo.setNameIndex(Integer.parseInt(this.txtNameIndex.getText()));
                break;
            case 2:
                localConstantPoolInfo.iTag = 6;
                localConstantPoolInfo.dDoubleVal = Double.valueOf(this.txtValue.getText()).doubleValue();
                break;
            case 3:
                localConstantPoolInfo.iTag = 9;
                localConstantPoolInfo.setClassIndex(Integer.parseInt(this.txtClassIndex.getText()));
                localConstantPoolInfo.setNameAndTypeIndex(Integer.parseInt(this.txtNameAndTypeIndex.getText()));
                break;
            case 4:
                localConstantPoolInfo.iTag = 4;
                localConstantPoolInfo.fFloatVal = Float.valueOf(this.txtValue.getText()).floatValue();
                break;
            case 5:
                localConstantPoolInfo.iTag = 3;
                localConstantPoolInfo.iIntValue = Integer.parseInt(this.txtValue.getText());
                break;
            case 6:
                localConstantPoolInfo.iTag = 11;
                localConstantPoolInfo.setClassIndex(Integer.parseInt(this.txtClassIndex.getText()));
                localConstantPoolInfo.setNameAndTypeIndex(Integer.parseInt(this.txtNameAndTypeIndex.getText()));
                break;
            case 7:
                localConstantPoolInfo.iTag = 5;
                localConstantPoolInfo.lLongVal = Long.parseLong(this.txtValue.getText());
                break;
            case 8:
                localConstantPoolInfo.iTag = 10;
                localConstantPoolInfo.setClassIndex(Integer.parseInt(this.txtClassIndex.getText()));
                localConstantPoolInfo.setNameAndTypeIndex(Integer.parseInt(this.txtNameAndTypeIndex.getText()));
                break;
            case 9:
                localConstantPoolInfo.iTag = 12;
                localConstantPoolInfo.setNameIndex(Integer.parseInt(this.txtNameIndex.getText()));
                localConstantPoolInfo.setDescriptorIndex(Integer.parseInt(this.txtDescriptorIndex.getText()));
                break;
            case 10:
                localConstantPoolInfo.iTag = 8;
                localConstantPoolInfo.setStringIndex(Integer.parseInt(this.txtStringIndex.getText()));
                break;
            case 11:
                localConstantPoolInfo.iTag = 1;
                localConstantPoolInfo.sUTFStr = this.txtValue.getText();
                break;
            default:
                return;
        }
        this.currClassFile.constantPool.addNewPoolInfo(localConstantPoolInfo);
        refresh();
        this.tblConstPool.changeSelection(this.tblConstPool.getModel().getRowCount() - 1, 1, false, false);
    }

    private void btnDeleteActionPerformed(ActionEvent paramActionEvent) {
        if (null != this.prevConstPoolEntry) {
            int i;
            String str;
            if (0 == (i = this.prevConstPoolEntry.getRef())) {
                str = "There are no resolved references to this entry.\n";
                str = str + "However it might be getting used in the code.\n";
                str = str + "Leaving this intact will not affect the functionality of the class.\n";
                str = str + "\nAre you sure you want to delete this?";
            } else {
                str = "There are " + i + " place(s) where this entry is being used.\n";
                str = str + "Deleting this entry will result in an error in the class file.\n";
                str = str + "\nAre you sure you want to delete this?";
            }
            int j = JOptionPane.showConfirmDialog(null, str, "Confirm Delete", 0);
            if (0 == j) {
                this.currClassFile.constantPool.removePoolInfo(this.prevConstPoolEntry);
                refresh();
            }
        }
    }

    private void btnJumpToActionPerformed(ActionEvent paramActionEvent) {
        if (null == this.currClassFile) {
            return;
        }
        String str1 = (String) this.cmbJumpTo.getSelectedItem();
        if (null == str1) {
            return;
        }
        String str2 = str1.trim();
        if (str2.length() == 0) {
            return;
        }
        try {
            int i = Integer.parseInt(str2);
            i = ((ConstPoolTableModel) this.tblConstPool.getModel()).getModelIndex(i);
            if (i < 0) {
                return;
            }
            this.tblConstPool.changeSelection(i, 1, false, false);
        } catch (NumberFormatException localNumberFormatException) {
        }
    }

    private void cmbPoolEntryTypeItemStateChanged(ItemEvent paramItemEvent) {
        this.txtStringIndex.setEnabled(false);
        this.txtClassIndex.setEnabled(false);
        this.txtNameAndTypeIndex.setEnabled(false);
        this.txtNameIndex.setEnabled(false);
        this.txtDescriptorIndex.setEnabled(false);
        this.txtValue.setEnabled(false);
        switch (this.cmbPoolEntryType.getSelectedIndex()) {
            case 1:
                this.txtPoolTypeDesc.setText(sClassDesc);
                if (this.bEditable) {
                    this.txtNameIndex.setEnabled(true);
                }
                break;
            case 2:
                this.txtPoolTypeDesc.setText(sLongDoubleDesc);
                if (this.bEditable) {
                    this.txtValue.setEnabled(true);
                }
                break;
            case 3:
                this.txtPoolTypeDesc.setText(sRefDesc);
                if (this.bEditable) {
                    this.txtClassIndex.setEnabled(true);
                }
                if (this.bEditable) {
                    this.txtNameAndTypeIndex.setEnabled(true);
                }
                break;
            case 4:
                this.txtPoolTypeDesc.setText(sIntFltDesc);
                if (this.bEditable) {
                    this.txtValue.setEnabled(true);
                }
                break;
            case 5:
                this.txtPoolTypeDesc.setText(sIntFltDesc);
                if (this.bEditable) {
                    this.txtValue.setEnabled(true);
                }
                break;
            case 6:
                this.txtPoolTypeDesc.setText(sRefDesc);
                if (this.bEditable) {
                    this.txtClassIndex.setEnabled(true);
                }
                if (this.bEditable) {
                    this.txtNameAndTypeIndex.setEnabled(true);
                }
                break;
            case 7:
                this.txtPoolTypeDesc.setText(sLongDoubleDesc);
                if (this.bEditable) {
                    this.txtValue.setEnabled(true);
                }
                break;
            case 8:
                this.txtPoolTypeDesc.setText(sRefDesc);
                if (this.bEditable) {
                    this.txtClassIndex.setEnabled(true);
                }
                if (this.bEditable) {
                    this.txtNameAndTypeIndex.setEnabled(true);
                }
                break;
            case 9:
                this.txtPoolTypeDesc.setText(sNameAndTypeDesc);
                if (this.bEditable) {
                    this.txtNameIndex.setEnabled(true);
                }
                if (this.bEditable) {
                    this.txtDescriptorIndex.setEnabled(true);
                }
                break;
            case 10:
                this.txtPoolTypeDesc.setText(sStringDesc);
                if (this.bEditable) {
                    this.txtStringIndex.setEnabled(true);
                }
                break;
            case 11:
                this.txtPoolTypeDesc.setText(sUtfDesc);
                if (this.bEditable) {
                    this.txtValue.setEnabled(true);
                }
                break;
        }
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/ConstantPoolPropPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */