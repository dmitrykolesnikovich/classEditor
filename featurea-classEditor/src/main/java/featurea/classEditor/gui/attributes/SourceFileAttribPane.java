/*
 * SourceFileAttribPane.java
 *
 * Created on January 27, 2002, 3:05 PM
 *
 * Modification Log:
 * 1.00   27th Jan 2002   Tanmay   Original version.
 *-----------------------------------------------------------------------------------------
 *       10th Sep 2003   Tanmay   Moved to SourceForge (http://classeditor.sourceforge.net)
 *-----------------------------------------------------------------------------------------
 */

package featurea.classEditor.gui.attributes;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.classfile.attributes.Attribute;
import featurea.classEditor.classfile.attributes.SourceFileAttribute;
import featurea.classEditor.guihelper.JavaFileFilter;

import javax.swing.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Copyright (C) 2002-2003  Tanmay K. Mohapatra
 * <br>
 *
 * @author Tanmay K. Mohapatra
 * @version 1.00, 27th January, 2002
 */
public class SourceFileAttribPane extends javax.swing.JPanel implements AttribDisplay {

    private SourceFileAttribute attribute;
    private ConstantPool constPool;
    private boolean bModifyMode;
    private Hashtable hashUTF;
    private String[] strUTF;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnOpen;
    private javax.swing.JComboBox cmbSourceFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txtSourcePane;
    private javax.swing.JTextField txtSourcePath;


    /**
     * Creates new form SourceFileAttribPane
     */
    public SourceFileAttribPane(boolean bModifyMode) {
        this.bModifyMode = bModifyMode;
        initComponents();

        cmbSourceFile.setEnabled(bModifyMode);
        btnModify.setEnabled(bModifyMode);
    }

    public void setInput(Attribute attribute, ConstantPool constPool) {
        this.attribute = (SourceFileAttribute) attribute;
        this.constPool = constPool;

        if (bModifyMode) {
            extractConstPoolInfo();
        } else {
            strUTF = new String[1];
            strUTF[0] = this.attribute.cpSourceFile.sUTFStr;
        }
        cmbSourceFile.setModel(new DefaultComboBoxModel(strUTF));
        cmbSourceFile.setSelectedItem(this.attribute.cpSourceFile.sUTFStr);
    }

    private void extractConstPoolInfo() {
        int iMaxPoolLen = constPool.getPoolInfoCount();
        int iIndex;
        hashUTF = new Hashtable();

        for (iIndex = 0; iIndex < iMaxPoolLen; iIndex++) {
            ConstantPoolInfo thisInfo = (ConstantPoolInfo) constPool.getPoolInfo(iIndex + 1);
            if (ConstantPoolInfo.CONSTANT_Utf8 == thisInfo.iTag) {
                hashUTF.put(thisInfo.sUTFStr, new Integer(iIndex + 1));
            }
        }

        Enumeration allKeys;
        strUTF = new String[hashUTF.size()];
        allKeys = hashUTF.keys();
        for (iIndex = 0; allKeys.hasMoreElements(); iIndex++) {
            strUTF[iIndex] = (String) allKeys.nextElement();
        }
    }

    private ConstantPoolInfo addSourceNameInConstPool(String sName) {
        ConstantPoolInfo newPoolInfo = new ConstantPoolInfo();
        newPoolInfo.setConstPool(constPool);
        newPoolInfo.iTag = ConstantPoolInfo.CONSTANT_Utf8;
        newPoolInfo.sUTFStr = sName;
        constPool.addNewPoolInfo(newPoolInfo);
        return newPoolInfo;
    }

    /**
     * Either creates a new UTF8 constant pool entry with sName or modifies the
     * supplied UTF8 constant pool entry prevPoolInfo depending on whether
     * someone else is using the pool entry.
     * It is important to decrement reference count for the current pool entry before
     * calling this method, otherwise this method will always add a new entry to
     * the pool!
     */
    private ConstantPoolInfo addOrModifySourceNameInConstPool(String sName, ConstantPoolInfo prevPoolInfo) {
        Integer poolIndex = (Integer) hashUTF.get(sName);

        if (null == poolIndex) {
            if (prevPoolInfo.getRef() > 0) {
                // the current pool entry is being referred to somebody else
                return addSourceNameInConstPool(sName);
            } else {
                // no one is referring to this, we can modify it for our purpose
                prevPoolInfo.sUTFStr = sName;
                return prevPoolInfo;
            }
        } else {
            // return the existing pool entry
            return constPool.getPoolInfo(poolIndex.intValue());
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        cmbSourceFile = new javax.swing.JComboBox();
        btnModify = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSourcePath = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSourcePane = new javax.swing.JTextPane();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("File");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        add(jLabel1, gridBagConstraints);

        cmbSourceFile.setEditable(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(cmbSourceFile, gridBagConstraints);

        btnModify.setText("Modify");
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        add(btnModify, gridBagConstraints);

        jLabel2.setText("Contents");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(txtSourcePath, gridBagConstraints);

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(btnBrowse, gridBagConstraints);

        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 10);
        add(btnOpen, gridBagConstraints);

        jScrollPane1.setViewportView(txtSourcePane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(jScrollPane1, gridBagConstraints);

    }//GEN-END:initComponents

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        String sNewName = ((String) cmbSourceFile.getSelectedItem()).trim();
        attribute.cpSourceFile.deleteRef();
        attribute.cpSourceFile = addOrModifySourceNameInConstPool(sNewName, attribute.cpSourceFile);
        attribute.cpSourceFile.addRef();
    }//GEN-LAST:event_btnModifyActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        String sName = txtSourcePath.getText().trim();
        if (sName.length() == 0) return;

        try {
            char[] readBytes = new char[1024];
            BufferedReader reader = new BufferedReader(new FileReader(sName));
            Writer strwrtr = new StringWriter();

            for (int iRet = reader.read(readBytes); iRet != -1; iRet = reader.read(readBytes)) {
                strwrtr.write(readBytes, 0, iRet);
            }
            strwrtr.flush();
            strwrtr.close();
            txtSourcePane.setText(strwrtr.toString());
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(this, "File not found.\nFile: " + sName, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file.\nFile: " + sName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        String sFileName;
        javax.swing.JFileChooser newFileChooser = new javax.swing.JFileChooser();
        newFileChooser.addChoosableFileFilter(new JavaFileFilter("java", "Java Source Files"));
        int returnVal = newFileChooser.showOpenDialog(this);

        if (returnVal != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }

        sFileName = newFileChooser.getSelectedFile().getAbsolutePath();
        txtSourcePath.setText(sFileName);
    }//GEN-LAST:event_btnBrowseActionPerformed
    // End of variables declaration//GEN-END:variables

}