package featurea.classEditor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class AboutDialog
        extends Dialog {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextArea jTextArea1;

    public AboutDialog(Frame paramFrame, boolean paramBoolean) {
        super(paramFrame, paramBoolean);
        initComponents();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jTextArea1 = new JTextArea();
        setLayout(new GridBagLayout());
        setBackground(Color.white);
        setResizable(false);
        setTitle("About ClassEditor");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
                AboutDialog.this.closeDialog(paramAnonymousWindowEvent);
            }
        });
        this.jLabel1.setBackground(Color.white);
        this.jLabel1.setIcon(new ImageIcon(getClass().getResource("/res/large/classeditor.gif")));
        this.jLabel1.setText("ClassEditor Version 2.23");
        GridBagConstraints localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.anchor = 17;
        localGridBagConstraints.insets = new Insets(10, 10, 0, 10);
        add(this.jLabel1, localGridBagConstraints);
        this.jLabel2.setBackground(Color.white);
        this.jLabel2.setText("WebSite: http://classeditor.sourceforge.net/");
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.anchor = 17;
        localGridBagConstraints.insets = new Insets(10, 45, 0, 10);
        add(this.jLabel2, localGridBagConstraints);
        this.jTextArea1.setEditable(false);
        this.jTextArea1.setText("Current Version: 21st March, 2004\nOriginal Version: 12th March, 1999\n\nContributors:\nTanmay K. M.");
        this.jTextArea1.setBorder(null);
        this.jTextArea1.setRequestFocusEnabled(false);
        localGridBagConstraints = new GridBagConstraints();
        localGridBagConstraints.gridwidth = 0;
        localGridBagConstraints.gridheight = 0;
        localGridBagConstraints.fill = 1;
        localGridBagConstraints.weighty = 1.0D;
        localGridBagConstraints.insets = new Insets(10, 45, 10, 10);
        add(this.jTextArea1, localGridBagConstraints);
        setBounds(50, 100, 340, 250);
    }

    private void closeDialog(WindowEvent paramWindowEvent) {
        setVisible(false);
        dispose();
    }
}


/* Location:              /home/dmitrykolesnikovich/ce2.23/ce.jar!/gui/AboutDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */