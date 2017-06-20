package featurea.classEditor.gui;

import featurea.classEditor.guihelper.JavaFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SummaryDialog
        extends JDialog {
    private JButton btnClose;
    private JButton btnSaveAs;
    private JScrollPane jScrollPane1;
    private JTextArea txtSumary;

    public SummaryDialog(Frame paramFrame, boolean paramBoolean) {
        super(paramFrame, paramBoolean);
        initComponents();
        setClassFileSummary("Working... Please wait.");
    }

    public void setClassFileSummary(String paramString) {
        this.txtSumary.setText(paramString);
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.txtSumary = new JTextArea();
        this.btnSaveAs = new JButton();
        this.btnClose = new JButton();
        getContentPane().setLayout(new GridBagLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
                SummaryDialog.this.closeDialog(paramAnonymousWindowEvent);
            }
        });
        this.txtSumary.setWrapStyleWord(true);
        this.txtSumary.setEditable(false);
        this.txtSumary.setColumns(40);
        this.txtSumary.setTabSize(2);
        this.txtSumary.setRows(20);
        this.jScrollPane1.setViewportView(this.txtSumary);
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weightx = 1.0D;
        localGridBagConstraints.weighty = 1.0D;
        getContentPane().add(this.jScrollPane1, localGridBagConstraints);
        this.btnSaveAs.setText("Save As");
        this.btnSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                SummaryDialog.this.btnSaveAsActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 17;
        getContentPane().add(this.btnSaveAs, localGridBagConstraints);
        this.btnClose.setText("Close");
        this.btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
                SummaryDialog.this.btnCloseActionPerformed(paramAnonymousActionEvent);
            }
        });
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.anchor = 13;
        getContentPane().add(this.btnClose, localGridBagConstraints);
        pack();
    }

    private void btnSaveAsActionPerformed(ActionEvent paramActionEvent) {
        JFileChooser localJFileChooser = new JFileChooser();
        localJFileChooser.addChoosableFileFilter(new JavaFileFilter("html", "HTML Files"));
        localJFileChooser.addChoosableFileFilter(new JavaFileFilter("doc", "Document Files"));
        localJFileChooser.addChoosableFileFilter(new JavaFileFilter("txt", "Text Files"));
        int i = localJFileChooser.showSaveDialog(this);
        if (i != 0) {
            return;
        }
        String str = localJFileChooser.getSelectedFile().getAbsolutePath();
        try {
            FileWriter localFileWriter = new FileWriter(str, false);
            localFileWriter.write(this.txtSumary.getText());
            localFileWriter.close();
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
            return;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            return;
        }
    }

    private void btnCloseActionPerformed(ActionEvent paramActionEvent) {
        closeDialog(null);
    }

    private void closeDialog(WindowEvent paramWindowEvent) {
        setVisible(false);
        dispose();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/SummaryDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */