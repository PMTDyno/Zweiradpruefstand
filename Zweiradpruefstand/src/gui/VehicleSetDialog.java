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
public class VehicleSetDialog extends javax.swing.JDialog
{

  private final Data data = Data.getInstance();
  private static final Logger LOG = Logger.getLogger(VehicleSetDialog.class.getName());

  private boolean measRpm = false;
  private boolean confirmed = false;
  private boolean twoStroke;
  private boolean automatic;
  private String vehicleName;

  /**
   * Creates the frame with the given values
   *
   * @param parent The parent Frame
   * @param modal  specifies whether dialog blocks user input to other top-level
   *               windows when shown.
   */
  public VehicleSetDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();

    setTitle("Fahrzeugeinstellungen");
    setSize(new Dimension(300, 250));
    setResizable(false);

    this.vehicleName = data.getVehicle();
    this.twoStroke = data.isTwoStroke();

    jButConfirm.requestFocusInWindow();

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

    taktButGroup = new javax.swing.ButtonGroup();
    transmissionButGroup = new javax.swing.ButtonGroup();
    jPanel5 = new javax.swing.JPanel();
    jPanel6 = new javax.swing.JPanel();
    jVehicleName = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    jMeasRpm = new javax.swing.JCheckBox();
    jPanel4 = new javax.swing.JPanel();
    j2Takt = new javax.swing.JRadioButton();
    j4Takt = new javax.swing.JRadioButton();
    jPanel8 = new javax.swing.JPanel();
    jManual = new javax.swing.JRadioButton();
    jAutomatic = new javax.swing.JRadioButton();
    jPanel7 = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    jButCancel = new javax.swing.JButton();
    jButConfirm = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);

    jPanel5.setLayout(new java.awt.GridBagLayout());

    jPanel6.setLayout(new java.awt.GridBagLayout());

    jVehicleName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jVehicleName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jVehicleName.setText("Honda CWF 125");
    jVehicleName.setMinimumSize(new java.awt.Dimension(60, 20));
    jVehicleName.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jVehicleNameActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.ipadx = 38;
    gridBagConstraints.weightx = 0.3;
    jPanel6.add(jVehicleName, gridBagConstraints);

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel1.setText("Fahrzeugname");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
    jPanel6.add(jLabel1, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    jPanel5.add(jPanel6, gridBagConstraints);

    jMeasRpm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jMeasRpm.setSelected(true);
    jMeasRpm.setText("Motordrehzahl messen");
    jMeasRpm.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMeasRpmActionPerformed(evt);
      }
    });
    jPanel3.add(jMeasRpm);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
    jPanel5.add(jPanel3, gridBagConstraints);

    jPanel4.setLayout(new java.awt.GridBagLayout());

    taktButGroup.add(j2Takt);
    j2Takt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    j2Takt.setText("2 Takt");
    j2Takt.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        j2TaktActionPerformed(evt);
      }
    });
    jPanel4.add(j2Takt, new java.awt.GridBagConstraints());

    taktButGroup.add(j4Takt);
    j4Takt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    j4Takt.setText("4 Takt");
    j4Takt.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        j4TaktActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    jPanel4.add(j4Takt, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    jPanel5.add(jPanel4, gridBagConstraints);

    jPanel8.setLayout(new java.awt.GridBagLayout());

    transmissionButGroup.add(jManual);
    jManual.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jManual.setSelected(true);
    jManual.setText("Schaltgetriebe");
    jManual.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jManualActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel8.add(jManual, gridBagConstraints);

    transmissionButGroup.add(jAutomatic);
    jAutomatic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jAutomatic.setText("Automatik");
    jAutomatic.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jAutomaticActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    jPanel8.add(jAutomatic, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
    jPanel5.add(jPanel8, gridBagConstraints);

    getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

    jPanel1.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

    jButCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jButCancel.setText("Abbrechen");
    jButCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButCancel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButCancelActionPerformed(evt);
      }
    });
    jPanel1.add(jButCancel);

    jButConfirm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jButConfirm.setText("Messung starten");
    jButConfirm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButConfirm.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButConfirmActionPerformed(evt);
      }
    });
    jPanel1.add(jButConfirm);

    jPanel7.add(jPanel1);

    getContentPane().add(jPanel7, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void jButCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButCancelActionPerformed
    {//GEN-HEADEREND:event_jButCancelActionPerformed
      confirmed = false;
      dispose();
    }//GEN-LAST:event_jButCancelActionPerformed

    private void jButConfirmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButConfirmActionPerformed
    {//GEN-HEADEREND:event_jButConfirmActionPerformed
      confirm();
    }//GEN-LAST:event_jButConfirmActionPerformed

  private void j2TaktActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_j2TaktActionPerformed
  {//GEN-HEADEREND:event_j2TaktActionPerformed

  }//GEN-LAST:event_j2TaktActionPerformed

  private void j4TaktActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_j4TaktActionPerformed
  {//GEN-HEADEREND:event_j4TaktActionPerformed

  }//GEN-LAST:event_j4TaktActionPerformed

  private void jMeasRpmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMeasrpmActionPerformed
  {//GEN-HEADEREND:event_jMeasrpmActionPerformed
    if(jMeasRpm.isSelected())
    {
      j2Takt.setEnabled(true);
      j4Takt.setEnabled(true);
      jManual.setEnabled(true);
      jAutomatic.setEnabled(true);
    }
    else
    {
      j2Takt.setEnabled(false);
      j4Takt.setEnabled(false);
      jManual.setEnabled(false);
      jAutomatic.setEnabled(false);
    }
  }//GEN-LAST:event_jMeasrpmActionPerformed

  private void jVehicleNameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jVehicleNameActionPerformed
  {//GEN-HEADEREND:event_jVehicleNameActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jVehicleNameActionPerformed

  private void jManualActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jManualActionPerformed
  {//GEN-HEADEREND:event_jManualActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jManualActionPerformed

  private void jAutomaticActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jAutomaticActionPerformed
  {//GEN-HEADEREND:event_jAutomaticActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jAutomaticActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    /* Set the system look and feel */
    try
    {
      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    }
    catch (ClassNotFoundException
           | InstantiationException
           | IllegalAccessException
           | javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(VehicleSetDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    //</editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(()
            ->
    {
      VehicleSetDialog dialog = new VehicleSetDialog(new javax.swing.JFrame(), true);
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


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JRadioButton j2Takt;
  private javax.swing.JRadioButton j4Takt;
  private javax.swing.JRadioButton jAutomatic;
  private javax.swing.JButton jButCancel;
  private javax.swing.JButton jButConfirm;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JRadioButton jManual;
  private javax.swing.JCheckBox jMeasRpm;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JTextField jVehicleName;
  private javax.swing.ButtonGroup taktButGroup;
  private javax.swing.ButtonGroup transmissionButGroup;
  // End of variables declaration//GEN-END:variables

  /*---PUBLIC METHODS----------------------------------*/
  /**
   * sets the settings and then calls the super method.
   *
   * @param b
   */
  @Override
  public void setVisible(boolean b)
  {
    if(b)
    {
      confirmed = false;

      //TAKT
      j2Takt.setSelected(twoStroke);
      j4Takt.setSelected(!twoStroke);

      //TRANSMISSION
      jAutomatic.setSelected(automatic);
      jManual.setSelected(!automatic);

      //VEHICLENAME
      jVehicleName.setText(vehicleName);

    }

    super.setVisible(b);
    LOG.finest("VehicleSetDialog visible");
  }

  /**
   * @return the vehicle name
   */
  public String getVehicleName()
  {
    return vehicleName;
  }

  /**
   * @return true if the vehicle has a two stroke engine
   */
  public boolean isTwoStroke()
  {
    return twoStroke;
  }

  public boolean isMeasRpm()
  {
    return measRpm;
  }

  public boolean isAutomatic()
  {
    return automatic;
  }

  /**
   * @return true if the settings have changed
   */
  public boolean isConfirmed()
  {
    return confirmed;
  }

  /*---PRIVATE METHODS----------------------------*/
  /**
   * Checks if the settings are correct and saves them. Then disposes the frame.
   */
  private void confirm()
  {
    boolean error = false;

    //TAKT
    twoStroke = j2Takt.isSelected();

    //TRANSMISSION
    automatic = jAutomatic.isSelected();

    //MEASRPM
    measRpm = jMeasRpm.isSelected();

    //VEHICLENAME
    if(jVehicleName.getText().length() >= 25)
    {
      error = true;
      LOG.warning("Vehicle name too long(max 25 characters)");
      JOptionPane.showMessageDialog(this,
                                    "Fahrzeugname darf maximal 25 Zeichen lang sein! \n"
                                    + "Aktuelle Zeichenlänge: "
                                    + jVehicleName.getText().length(),
                                    "Fahrzeugname zu lang!",
                                    JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      vehicleName = jVehicleName.getText();
    }

    if(!error)
    {
      confirmed = true;
      dispose();
    }
  }

}
