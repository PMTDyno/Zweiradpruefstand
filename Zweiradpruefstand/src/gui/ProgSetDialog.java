package gui;

import data.Data;
import java.awt.Dimension;
import logging.Logger;
import javax.swing.JOptionPane;

/**
 * Displays a Dialog to enter several settings
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 */
public class ProgSetDialog extends javax.swing.JDialog
{
    private final Data data = Data.getInstance();
    private static final Logger LOG = Logger.getLogger(ProgSetDialog.class.getName());

    private boolean settingsChanged = false;
    //PNG RESOLUTION
    private int resIndex = 1; //800x600 standart
    private boolean customRes = false;
    private int customWidth = 800;
    private int customHeight = 600;
    //POWERUNIT
    private boolean powerunitPS = true;
    //CORRECTION
    private double correctionPower = 1.0;
    private double correctionTorque = 1.0;
    private double inertia = 3.7017;
    private int periodTimeMs = 40;
    private int startMdz = 2000;

    /**
     * Creates the frame with the given values
     *
     * @param parent The parent Frame
     * @param modal  specifies whether dialog blocks user input to other
     *               top-level windows when shown.
     */
    public ProgSetDialog(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();

        setTitle("Programmeinstellungen");
        setSize(new Dimension(500, 500));
        setResizable(false);

        this.inertia = data.getInertia();

        powerunitPS = data.getPowerunit().equals("PS");

        jButRadioUnitPS.setSelected(powerunitPS);

        this.correctionPower = data.getCorrectionPower();
        this.correctionTorque = data.getCorrectionTorque();
        this.periodTimeMs = data.getPeriodTimeMs();
        this.startMdz = data.getStartMdz();
        
        switch (data.getPngWidth())
        {
            case 640:
                if(data.getPngHeight() == 480)
                {
                    customRes = false;
                    resIndex = 0;
                }
                else
                    customRes = true;
                break;
                
            case 800:
                if(data.getPngHeight() == 600)
                {
                    customRes = false;
                    resIndex = 1;
                }
                else
                    customRes = true;
                break;
                
            case 1280:
                if(data.getPngHeight() == 720)
                {
                    customRes = false;
                    resIndex = 2;
                }
                else
                    customRes = true;
                break;
                
            case 1920:
                if(data.getPngHeight() == 1080)
                {
                    customRes = false;
                    resIndex = 3;
                }
                else
                    customRes = true;
                break;
                
            default:
                customRes = true;
                break;
        }

        if(data.getPngWidth() < 400)
            data.setPngWidth(400);

        if(data.getPngHeight() < 200)
            data.setPngHeight(200);

        this.customWidth = data.getPngWidth();
        this.customHeight = data.getPngHeight();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    resolutionGroup = new javax.swing.ButtonGroup();
    powerunitGroup = new javax.swing.ButtonGroup();
    jPanel1 = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jButRadioPng = new javax.swing.JRadioButton();
    jPNGResolutionCombo = new javax.swing.JComboBox();
    jButRadioPngCustom = new javax.swing.JRadioButton();
    jPanel9 = new javax.swing.JPanel();
    jWidth = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jHeight = new javax.swing.JTextField();
    jPanel10 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jButRadioUnitPS = new javax.swing.JRadioButton();
    jButRadioUnitkW = new javax.swing.JRadioButton();
    jPanel5 = new javax.swing.JPanel();
    jPanel7 = new javax.swing.JPanel();
    jLabel4 = new javax.swing.JLabel();
    jSpinCorrectPower = new javax.swing.JSpinner();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jSpinCorrectTorque = new javax.swing.JSpinner();
    jLabel7 = new javax.swing.JLabel();
    jTextInertia = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    jPanel8 = new javax.swing.JPanel();
    jPanel6 = new javax.swing.JPanel();
    jLabel9 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jSpinPeriod = new javax.swing.JSpinner();
    jLabel14 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    jSpinMdz = new javax.swing.JSpinner();
    jLabel13 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    jButCancel = new javax.swing.JButton();
    jButConfirm = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setLocation(new java.awt.Point(0, 0));
    setLocationByPlatform(true);

    jPanel1.setLayout(new java.awt.GridLayout(2, 2));

    jPanel4.setLayout(new java.awt.GridBagLayout());

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("PNG Auflösung");
    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    jPanel4.add(jLabel1, gridBagConstraints);

    resolutionGroup.add(jButRadioPng);
    jButRadioPng.setSelected(true);
    jButRadioPng.setToolTipText("Vordefinierte Auflösung");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    jPanel4.add(jButRadioPng, gridBagConstraints);

    jPNGResolutionCombo.setMaximumRowCount(4);
    jPNGResolutionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VGA (640x480)", "SVGA (800x600)", "HD720 (1280x720)", "HD1080 (1920x1080)" }));
    jPNGResolutionCombo.setName(""); // NOI18N
    jPNGResolutionCombo.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jPNGResolutionComboActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel4.add(jPNGResolutionCombo, gridBagConstraints);

    resolutionGroup.add(jButRadioPngCustom);
    jButRadioPngCustom.setToolTipText("Benutzerdefinierte Auflösung");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    jPanel4.add(jButRadioPngCustom, gridBagConstraints);

    jWidth.setColumns(5);
    jWidth.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jWidth.setText("0000");
    jWidth.setToolTipText("Breite");
    jWidth.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
    jWidth.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jWidthActionPerformed(evt);
      }
    });
    jPanel9.add(jWidth);

    jLabel2.setText("x");
    jPanel9.add(jLabel2);

    jHeight.setColumns(5);
    jHeight.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jHeight.setText("0000");
    jHeight.setToolTipText("Höhe");
    jHeight.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jHeightActionPerformed(evt);
      }
    });
    jPanel9.add(jHeight);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    jPanel4.add(jPanel9, gridBagConstraints);

    jPanel1.add(jPanel4);

    jPanel10.setLayout(new java.awt.GridBagLayout());

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel3.setText("Leistungseinheit");
    jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jPanel10.add(jLabel3, new java.awt.GridBagConstraints());

    powerunitGroup.add(jButRadioUnitPS);
    jButRadioUnitPS.setSelected(true);
    jButRadioUnitPS.setText("PS");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel10.add(jButRadioUnitPS, gridBagConstraints);

    powerunitGroup.add(jButRadioUnitkW);
    jButRadioUnitkW.setText("kW");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel10.add(jButRadioUnitkW, gridBagConstraints);

    jPanel1.add(jPanel10);

    jPanel7.setLayout(new java.awt.GridBagLayout());

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel4.setText("Korrekturfaktoren");
    jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    jPanel7.add(jLabel4, gridBagConstraints);

    jSpinCorrectPower.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.5d, 2.0d, 0.1d));
    jSpinCorrectPower.setName(""); // NOI18N
    jSpinCorrectPower.setValue(1);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 2, 3);
    jPanel7.add(jSpinCorrectPower, gridBagConstraints);

    jLabel5.setText("Leistung");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel7.add(jLabel5, gridBagConstraints);

    jLabel6.setText("Drehmoment");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel7.add(jLabel6, gridBagConstraints);

    jSpinCorrectTorque.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.5d, 2.0d, 0.1d));
    jSpinCorrectTorque.setName(""); // NOI18N
    jSpinCorrectTorque.setValue(1);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 3);
    jPanel7.add(jSpinCorrectTorque, gridBagConstraints);

    jLabel7.setText("Trägheitsmoment");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
    jPanel7.add(jLabel7, gridBagConstraints);

    jTextInertia.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jTextInertia.setText("3,7017");
    jTextInertia.setToolTipText("Trägheitsmoment der Welle");
    jTextInertia.setName(""); // NOI18N
    jTextInertia.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jTextInertiaActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipady = 9;
    gridBagConstraints.insets = new java.awt.Insets(2, 5, 0, 3);
    jPanel7.add(jTextInertia, gridBagConstraints);

    jLabel8.setText("kgm²");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
    jPanel7.add(jLabel8, gridBagConstraints);

    jPanel5.add(jPanel7);

    jPanel1.add(jPanel5);

    jPanel6.setLayout(new java.awt.GridBagLayout());

    jLabel9.setText("Serieller Port");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    jPanel6.add(jLabel9, gridBagConstraints);

    jLabel11.setText("Zeitintervall");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel6.add(jLabel11, gridBagConstraints);

    jSpinPeriod.setModel(new javax.swing.SpinnerNumberModel(10, 5, 100, 1));
    jSpinPeriod.setToolTipText("Der Zeitabstand zwischen einzelne Pakete");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(4, 5, 6, 0);
    jPanel6.add(jSpinPeriod, gridBagConstraints);

    jLabel14.setText("ms");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 2, 0);
    jPanel6.add(jLabel14, gridBagConstraints);

    jLabel10.setText("Startmotordrehzahl");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel6.add(jLabel10, gridBagConstraints);

    jSpinMdz.setModel(new javax.swing.SpinnerNumberModel(2000, 100, 20000, 100));
    jSpinMdz.setToolTipText("Die Motordrehzahl ab der gestartet werden soll");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 2, 0);
    jPanel6.add(jSpinMdz, gridBagConstraints);

    jLabel13.setText("U/min");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    gridBagConstraints.insets = new java.awt.Insets(0, 3, 2, 0);
    jPanel6.add(jLabel13, gridBagConstraints);

    jPanel8.add(jPanel6);

    jPanel1.add(jPanel8);

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    jPanel2.setLayout(new java.awt.GridLayout(1, 2, 40, 0));

    jButCancel.setText("Abbrechen");
    jButCancel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButCancelActionPerformed(evt);
      }
    });
    jPanel2.add(jButCancel);

    jButConfirm.setText("Übernehmen");
    jButConfirm.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButConfirmActionPerformed(evt);
      }
    });
    jPanel2.add(jButConfirm);

    jPanel3.add(jPanel2);

    getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);
  }// </editor-fold>//GEN-END:initComponents

    private void jButCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButCancelActionPerformed
    {//GEN-HEADEREND:event_jButCancelActionPerformed
        settingsChanged = false;
        dispose();
    }//GEN-LAST:event_jButCancelActionPerformed

    private void jPNGResolutionComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jPNGResolutionComboActionPerformed
    {//GEN-HEADEREND:event_jPNGResolutionComboActionPerformed

    }//GEN-LAST:event_jPNGResolutionComboActionPerformed

    private void jButConfirmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButConfirmActionPerformed
    {//GEN-HEADEREND:event_jButConfirmActionPerformed
        confirm();
    }//GEN-LAST:event_jButConfirmActionPerformed

    private void jWidthActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jWidthActionPerformed
    {//GEN-HEADEREND:event_jWidthActionPerformed

    }//GEN-LAST:event_jWidthActionPerformed

    private void jHeightActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jHeightActionPerformed
    {//GEN-HEADEREND:event_jHeightActionPerformed

    }//GEN-LAST:event_jHeightActionPerformed

  private void jTextInertiaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextInertiaActionPerformed
  {//GEN-HEADEREND:event_jTextInertiaActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jTextInertiaActionPerformed

    /*---PUBLIC METHODS----------------------------*/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ProgSetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(()
                -> 
                {
                    ProgSetDialog dialog = new ProgSetDialog(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter()
                    {

                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e)
                        {
                            System.exit(0);
                        }

                    });
                    dialog.setVisible(true);
        });
    }

    /**
     * Sets the given/default values.<br>
     * Then calls the super method.
     *
     * @param b
     */
    @Override
    public void setVisible(boolean b)
    {
        if(b)
        {
            settingsChanged = false;
            //PNG RESOLUTION

            jButRadioPng.setSelected(!customRes);
            jButRadioPngCustom.setSelected(customRes);

            jWidth.setEnabled(customRes);
            jHeight.setEnabled(customRes);

            if(resIndex == -1)
                jPNGResolutionCombo.setSelectedIndex(1);

            jPNGResolutionCombo.setEnabled(!customRes);

            jWidth.setText(String.valueOf(customWidth));
            jHeight.setText(String.valueOf(customHeight));

            //POWERUNIT
            jButRadioUnitPS.setSelected(powerunitPS);
            jButRadioUnitkW.setSelected(!powerunitPS);

            //CORRECTION
            jSpinCorrectPower.setValue(correctionPower);
            jSpinCorrectTorque.setValue(correctionTorque);

            //INERTIA
            jTextInertia.setText(String.format("%.4f", inertia));

            //SERIALPORT
            jSpinPeriod.setValue(periodTimeMs);
            jSpinMdz.setValue(startMdz);

        }

        super.setVisible(b);
        LOG.finest("ProgSetDialog visible");
    }

    /**
     * Checks the selected dimension and returns it.
     *
     * @return The Dimension
     */
    public Dimension getPNGResolution()
    {
        Dimension dimension = new Dimension();

        switch (resIndex)
        {
            case -1:
                dimension.width = customWidth;
                dimension.height = customHeight;
                break;
            case 0:
                dimension.width = 640;
                dimension.height = 480;
                break;
            case 1:
                dimension.width = 800;
                dimension.height = 600;
                break;
            case 2:
                dimension.width = 1280;
                dimension.height = 720;
                break;
            case 3:
                dimension.width = 1920;
                dimension.height = 1080;
                break;
            default:
                JOptionPane.showMessageDialog(this,
                                              "Fehler bei Auflösung!",
                                              "Fehler",
                                              JOptionPane.ERROR_MESSAGE);
        }

        return dimension;
    }

    /**
     * @return the powerunit
     */
    public String getPowerunit()
    {
        if(powerunitPS)
            return "PS";
        else
            return "kW";
    }

    /**
     * @return the correction factor for the power series
     */
    public double getCorrectionPower()
    {
        return correctionPower;
    }

    /**
     * @return the correction factor for the torque series
     */
    public double getCorrectionTorque()
    {
        return correctionTorque;
    }

    /**
     * @return true if the setting is changed
     */
    public boolean isSettingsChanged()
    {
        return settingsChanged;
    }

    /**
     * @return the inertia
     */
    public double getInertia()
    {
        return inertia;
    }

    /**
     * @return the period between datapoint captures
     */
    public int getPeriodTimeMs()
    {
        return periodTimeMs;
    }

    
    
    /*---PRIVATE METHODS-------------------------------------*/
    /**
     * Checks if all the values are correct and saves the changed values. Then
     * disposes the frame.
     */
    private void confirm()
    {
        //PNG RESOLUTION
        customRes = jButRadioPngCustom.isSelected();
        boolean error = false;

        int tempW;
        int tempH;
        if(customRes)
        {
            resIndex = -1;
            try
            {
                tempW = Integer.parseInt(jWidth.getText());
                tempH = Integer.parseInt(jHeight.getText());
                if(tempW < 400 || tempH < 200)
                    throw new IllegalArgumentException();
                else
                {
                    customWidth = tempW;
                    customHeight = tempH;
                }
            }
            catch (NumberFormatException ex)
            {
                error = true;
                LOG.warning("Invalid Resolution", ex);
                JOptionPane.showMessageDialog(this, jWidth.getText()
                                              + " x "
                                              + jHeight.getText()
                                              + " ist keine gültige Auflösung!",
                                              "Keine gültige Auflösung!", JOptionPane.ERROR_MESSAGE);
            }
            catch (IllegalArgumentException ex)
            {
                error = true;
                LOG.warning("Resolution too small(min 400x200)", ex);
                JOptionPane.showMessageDialog(this, jWidth.getText()
                                              + " x "
                                              + jHeight.getText()
                                              + " ist keine gültige Auflösung! \n"
                                              + " Auflösung mindestens 400x200!",
                                              "Keine gültige Auflösung!",
                                              JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex)
            {
                error = true;
                LOG.severe("Unsupported Exception", ex);
                JOptionPane.showMessageDialog(this,
                                              "Unbekannter Fehler aufgetreten.\n" + ex.toString(),
                                              "Unbekannter Fehler",
                                              JOptionPane.ERROR_MESSAGE);
            }

        }
        else
            resIndex = jPNGResolutionCombo.getSelectedIndex();

        //POWERUNIT
        if(jButRadioUnitPS.isSelected())
            powerunitPS = true;
        else if(jButRadioUnitkW.isSelected())
            powerunitPS = false;
        else
        {
            error = true;
            JOptionPane.showMessageDialog(this,
                                          "Fehler bei der Leistungseinheit!",
                                          "Fehler Leistungseinheit!",
                                          JOptionPane.ERROR_MESSAGE);
        }

        //CORRECTION
        correctionPower = (double) jSpinCorrectPower.getValue();
        correctionTorque = (double) jSpinCorrectTorque.getValue();

        //INERTIA
        try
        {
            double tmp = Double.parseDouble(jTextInertia.getText().replaceAll(",", "."));
            if(tmp < 0.5 || tmp > 5.0)
                throw new NumberFormatException();
            inertia = tmp;
        }
        catch (NumberFormatException | NullPointerException ex)
        {
            error = true;
            LOG.warning("Inertia too small/big (0.5 - 5.0)", ex);
            JOptionPane.showMessageDialog(this,
                                          "Fehler bei Trägheitsmoment! \n"
                                          + "Wert mussen zwischen 0,5 und 5,0 liegen.",
                                          "Fehler Trägheitsmoment!",
                                          JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            error = true;
            LOG.severe("Unsupported Exception", ex);
            JOptionPane.showMessageDialog(this,
                                          "Unbekannter Fehler aufgetreten\n" + ex.toString(),
                                          "Unbekannter Fehler",
                                          JOptionPane.ERROR_MESSAGE);
        }

        //SERIALPORT
        periodTimeMs = (int) jSpinPeriod.getValue();
        startMdz = (int) jSpinMdz.getValue();
        
        if(!error)
        {
            settingsChanged = true;
            dispose();
        }
    }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButCancel;
  private javax.swing.JButton jButConfirm;
  private javax.swing.JRadioButton jButRadioPng;
  private javax.swing.JRadioButton jButRadioPngCustom;
  private javax.swing.JRadioButton jButRadioUnitPS;
  private javax.swing.JRadioButton jButRadioUnitkW;
  private javax.swing.JTextField jHeight;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JComboBox jPNGResolutionCombo;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel10;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JSpinner jSpinCorrectPower;
  private javax.swing.JSpinner jSpinCorrectTorque;
  private javax.swing.JSpinner jSpinMdz;
  private javax.swing.JSpinner jSpinPeriod;
  private javax.swing.JTextField jTextInertia;
  private javax.swing.JTextField jWidth;
  private javax.swing.ButtonGroup powerunitGroup;
  private javax.swing.ButtonGroup resolutionGroup;
  // End of variables declaration//GEN-END:variables

}
