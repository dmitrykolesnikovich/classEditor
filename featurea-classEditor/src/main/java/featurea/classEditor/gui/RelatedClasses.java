/*
 * RelatedClasses.java
 *
 * Created on June 9, 1999, 11:56 PM
 *
 * Modification Log:
 * 1.00   09th Jun 1999   Tanmay   Original Version
 * 2.00   16th Dec 2001   Tanmay   Moved over to Java Swing.
 *-----------------------------------------------------------------------------------------
 *       10th Sep 2003   Tanmay   Moved to SourceForge (http://classeditor.sourceforge.net)
 *-----------------------------------------------------------------------------------------
 */

package featurea.classEditor.gui;

import featurea.classEditor.classfile.ConstantPool;
import featurea.classEditor.classfile.ConstantPoolInfo;
import featurea.classEditor.classfile.Utils;
import featurea.classEditor.guihelper.JavaFileFilter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Dialog to show classes referred from this class file.
 * <br><br>
 *
 * @author Tanmay K. Mohapatra
 * @version 2.00, 16th December, 2001
 */
public class RelatedClasses extends javax.swing.JDialog {

    String[] asClassList;
    String[] asDisplayList;
    ClassEditor ce;
    boolean bPopulating;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnOpenClass;
    private javax.swing.JComboBox cmbFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstRelatedClasses;
    private javax.swing.JTextField txtFilePath;

    /**
     * Creates new form RelatedClasses
     */
    public RelatedClasses(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void applyFilter(String sFilter) {
        if (bPopulating) return;

        int iIndex, iDisplayIndex, iNumDisplay;
        DefaultListModel dlm = (DefaultListModel) lstRelatedClasses.getModel();
        dlm.removeAllElements();

        if ((null == sFilter) || (sFilter.length() == 0) || (null == asClassList)) {
            if (null != asClassList) {
                for (iIndex = 0; iIndex < asClassList.length; iIndex++) {
                    dlm.addElement(asClassList[iIndex]);
                }
            }
            lstRelatedClasses.setModel(dlm);
            return;
        }
        iNumDisplay = 0;
        for (iIndex = 0; iIndex < asClassList.length; iIndex++) {
            if (asClassList[iIndex].startsWith(sFilter)) {
                iNumDisplay++;
            }
        }
        asDisplayList = null;
        if (iNumDisplay > 0) {
            asDisplayList = new String[iNumDisplay];
            for (iIndex = iDisplayIndex = 0; iIndex < asClassList.length; iIndex++) {
                if (asClassList[iIndex].startsWith(sFilter)) {
                    asDisplayList[iDisplayIndex++] = asClassList[iIndex];
                    dlm.addElement(asClassList[iIndex]);
                }
            }
        }
        lstRelatedClasses.setModel(dlm);
    }

    private void addFilterString(String sFilter) {
        DefaultComboBoxModel dcm = (DefaultComboBoxModel) cmbFilter.getModel();
        int iNumStrs = dcm.getSize();
        if (0 == iNumStrs) {
            dcm.addElement(sFilter);
            return;
        }
        for (int iIndex = iNumStrs - 1; iIndex >= 0; iIndex--) {
            String sThisItem = (String) dcm.getElementAt(iIndex);
            if (sFilter.equals(sThisItem)) {
                return;
            }
        }
        dcm.addElement(sFilter);
        cmbFilter.setModel(dcm);
    }

    void showRelatedClasses(ClassEditor parent, ConstantPool constPool) {
        int iIndex, iNumClasses, iMaxPoolCount;
        DefaultComboBoxModel dcm = new DefaultComboBoxModel();
        DefaultListModel dlm = new DefaultListModel();

        bPopulating = true;
        ce = parent;
        lstRelatedClasses.setModel(dlm);
        cmbFilter.setModel(dcm);

        iMaxPoolCount = constPool.getPoolInfoCount();
        for (iIndex = iNumClasses = 0; iIndex < iMaxPoolCount; iIndex++) {
            ConstantPoolInfo newInfo = constPool.getPoolInfo(iIndex + 1);
            if (newInfo.iTag == ConstantPoolInfo.CONSTANT_Class) {
                newInfo = constPool.getPoolInfo(newInfo.iNameIndex);
                iNumClasses++;
                dlm.addElement(Utils.convertClassStrToStr(newInfo.sUTFStr));
            }
            if (newInfo.isDoubleSizeConst()) {
                iIndex++;
            }
        }
        lstRelatedClasses.setModel(dlm);
        if (iNumClasses > 0) {
            addFilterString("");
            asClassList = new String[iNumClasses];
            asDisplayList = new String[iNumClasses];
            for (iIndex = 0; iIndex < iNumClasses; iIndex++) {
                asDisplayList[iIndex] = asClassList[iIndex] = (String) dlm.getElementAt(iIndex);
                int iLastDot = asDisplayList[iIndex].lastIndexOf(".");
                if (iLastDot > 0) {
                    String sFilter = asDisplayList[iIndex].substring(0, iLastDot);
                    addFilterString(sFilter);
                }
            }
        }
        bPopulating = false;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        lstRelatedClasses = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        cmbFilter = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtFilePath = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        btnOpenClass = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jScrollPane1.setViewportView(lstRelatedClasses);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jLabel1.setText("Filter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        cmbFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFilterItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        getContentPane().add(cmbFilter, gridBagConstraints);

        jLabel2.setText("Path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        txtFilePath.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        getContentPane().add(txtFilePath, gridBagConstraints);

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        getContentPane().add(btnBrowse, gridBagConstraints);

        btnOpenClass.setText("Open Class");
        btnOpenClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenClassActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 0);
        getContentPane().add(btnOpenClass, gridBagConstraints);

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        getContentPane().add(btnClose, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void btnOpenClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenClassActionPerformed
        String sNewFile = txtFilePath.getText();
        if (sNewFile.length() > 0) {
            try {
                ce.chkNLoadClass(sNewFile);
                closeDialog(null);
            } catch (FileNotFoundException fnfe) {
                JOptionPane.showMessageDialog(this, "File not found:\n" + sNewFile, "File open failed", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, "Error reading file:\n" + sNewFile, "File read failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnOpenClassActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        String sFileName;
        javax.swing.JFileChooser newFileChooser = new javax.swing.JFileChooser();
        newFileChooser.addChoosableFileFilter(new JavaFileFilter("class", "Class Files"));
        int returnVal = newFileChooser.showOpenDialog(this);

        if (returnVal != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }
        sFileName = newFileChooser.getSelectedFile().getAbsolutePath();
        txtFilePath.setText(sFileName);
    }//GEN-LAST:event_btnBrowseActionPerformed

    private void cmbFilterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFilterItemStateChanged
        applyFilter((String) cmbFilter.getModel().getElementAt(cmbFilter.getSelectedIndex()));
    }//GEN-LAST:event_cmbFilterItemStateChanged

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_btnCloseActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    // End of variables declaration//GEN-END:variables

}