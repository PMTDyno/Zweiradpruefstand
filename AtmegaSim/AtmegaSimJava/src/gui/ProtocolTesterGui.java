package gui;

import java.awt.Dimension;
import java.util.logging.Level;
import logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import measure.Communication;
import measure.CommunicationException;
import measure.Port;
import measure.PortCom;
import measure.PortSim;


/**
 *
 * @author steiner
 */
public class ProtocolTesterGui extends javax.swing.JFrame
{
  static final Logger LOGP, LOG;

  static 
  {
    //System.setProperty("logging.Logger.printStackTrace", "");
    //System.setProperty("logging.LogOutputStreamHandler.showRecordHashcode", "false");
    //System.setProperty("logging.Logger.printAll", "");
    //System.setProperty("logging.LogRecordDataFormattedText.Terminal","NETBEANS");
    //System.setProperty("logging.LogRecordDataFormattedText.Terminal","LINUX");
    System.setProperty("logging.Logger.Level", "ALL");
    //System.setProperty("logging.LogOutputStreamHandler.timeFormat", "%1$ta/%1$tF/%1$tT.%1$tL #%2$-3d");
    //System.setProperty("logging.LogOutputStreamHandler.colorize", "false");

    //System.setProperty("gui.ProtocolTesterGui.Logger.Level", "FINER");
    System.setProperty("measure.PortSim.Logger.Level", "ALL");
    System.setProperty("measure.PortCom.Logger.Level", "ALL");
    LOGP = Logger.getParentLogger();
    LOG = Logger.getLogger(ProtocolTesterGui.class.getName());
  }
  
  // *******************************************************************************************************
   
  private Port port;
  
  /**
   * Creates new form ProtocolTesterGui
   */
  @SuppressWarnings("OverridableMethodCallInConstructor")
  public ProtocolTesterGui ()
  {
    initComponents();
    setMinimumSize(new Dimension(500,200));
    setTitle("Protocol Tester");
    setLocationRelativeTo(null);
    updateSwingControls();
    jcbPorts.setModel(new DefaultComboBoxModel());
    updatePorts();
  }

  
  private void updateSwingControls ()
  {
    if (port != null && port.isOpened())
    {
      jchkSimulate.setEnabled(false);
      jcbPorts.setEnabled(false);
      jbutUpdate.setEnabled(false);
      jbutOpen.setEnabled(false);
      jbutClose.setEnabled(true);
      
      jbutTestConnect.setEnabled(true);
      jbutTestSend.setEnabled(true);
    }
    else
    {
      jchkSimulate.setEnabled(true);
      jcbPorts.setEnabled(true);
      jbutUpdate.setEnabled(true);
      jbutOpen.setEnabled(true);
      jbutClose.setEnabled(false);

      jbutTestConnect.setEnabled(false);
      jbutTestSend.setEnabled(false);
    }
  }
  
  private void updatePorts()
  {
    port = jchkSimulate.isSelected() ? new PortSim() : new PortCom();    
    String [] ports = port.getPortList();
    DefaultComboBoxModel m = (DefaultComboBoxModel) jcbPorts.getModel();
    Object selected = m.getSelectedItem();
    m.removeAllElements();
    for (String p : ports)
      m.addElement(p);
    for (int i=0; i<m.getSize(); i++)
    {
      String item = (String)m.getElementAt(i);
      if (item.equals(selected))
      {
        m.setSelectedItem(item);
        selected = null;
        break;
      }
    }
    if (selected != null)
    {
      for (int i=0; i<m.getSize(); i++)
      {
        String item = (String)m.getElementAt(i);
        if ( item.contains("USB0") )
        {
          m.setSelectedItem(item);
          break;
        }
      }
    }
  }
  
  private void showThrowable (Throwable th)
  {
    showThrowable(null, th);
  }
  
  private void showThrowable (String msg, Throwable th)
  {
    LOG.warning(th);
    if (msg == null && th != null)
      msg = th.getMessage();
    if (msg == null && th != null)
      msg = th.getClass().getSimpleName();
    if (msg == null)
      msg = "Unbekannter Fehler, siehe Callstack...";
    JOptionPane.showMessageDialog(this, msg, "Error...", JOptionPane.ERROR_MESSAGE);
  }
  

  private void open ()
  {
    String portName = (String)jcbPorts.getSelectedItem();
    if (portName == null || port == null)
    {
      LOG.warning("portName == " + portName + ", port == " + port);
      return;
    }
    try
    {
      port.openPort(portName);
      updateSwingControls();
    }
    catch (Exception ex)
    {
      showThrowable("cannot open port " + portName, ex);
    }
  }
  
  private void close () 
  {
    try
    {
      port.closePort();
    }
    catch (Exception ex)
    {
      showThrowable("cannot close port", ex);
    }
    finally
    {
      port = jchkSimulate.isSelected() ? new PortSim() : new PortCom();
      updateSwingControls();
    }
  }
  
  
  private void testSend ()
  {
    try
    {
      String frame = "hello";
      port.writeBytes(frame.getBytes());
    }
    catch (Exception ex)
    {
      showThrowable(ex);
    }
  }
  
  private void testConnect ()
  {
    try
    {
      Communication comm = new Communication(port);
      comm.connect();
    }
    catch (Exception ex)
    {
      showThrowable(ex);
    }
  }
  
  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
   * content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    jpanNorth = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jcbPorts = new javax.swing.JComboBox<>();
    jpanCommButtons = new javax.swing.JPanel();
    jbutOpen = new javax.swing.JButton();
    jbutClose = new javax.swing.JButton();
    jbutUpdate = new javax.swing.JButton();
    jchkSimulate = new javax.swing.JCheckBox();
    jpanCenter = new javax.swing.JPanel();
    jpanTest = new javax.swing.JPanel();
    jbutTestConnect = new javax.swing.JButton();
    jbutTestSend = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jpanNorth.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
    jpanNorth.setLayout(new java.awt.GridBagLayout());

    jLabel1.setText("Port");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jpanNorth.add(jLabel1, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jpanNorth.add(jcbPorts, gridBagConstraints);

    jpanCommButtons.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

    jbutOpen.setText("Open");
    jbutOpen.setMargin(new java.awt.Insets(4, 4, 4, 4));
    jbutOpen.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutOpenActionPerformed(evt);
      }
    });
    jpanCommButtons.add(jbutOpen);

    jbutClose.setText("Close");
    jbutClose.setMargin(new java.awt.Insets(4, 4, 4, 4));
    jbutClose.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutCloseActionPerformed(evt);
      }
    });
    jpanCommButtons.add(jbutClose);

    jbutUpdate.setText("Update");
    jbutUpdate.setMargin(new java.awt.Insets(4, 4, 4, 4));
    jbutUpdate.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutUpdateActionPerformed(evt);
      }
    });
    jpanCommButtons.add(jbutUpdate);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    jpanNorth.add(jpanCommButtons, gridBagConstraints);

    jchkSimulate.setSelected(true);
    jchkSimulate.setText("Simulation");
    jchkSimulate.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jchkSimulateActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    jpanNorth.add(jchkSimulate, gridBagConstraints);

    getContentPane().add(jpanNorth, java.awt.BorderLayout.PAGE_START);

    jpanCenter.setBorder(javax.swing.BorderFactory.createTitledBorder("Test"));
    jpanCenter.setLayout(new java.awt.BorderLayout());

    jpanTest.setLayout(new java.awt.GridBagLayout());

    jbutTestConnect.setText("Connect");
    jbutTestConnect.setMargin(new java.awt.Insets(4, 4, 4, 4));
    jbutTestConnect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutTestConnectActionPerformed(evt);
      }
    });
    jpanTest.add(jbutTestConnect, new java.awt.GridBagConstraints());

    jbutTestSend.setText("Send");
    jbutTestSend.setMargin(new java.awt.Insets(4, 4, 4, 4));
    jbutTestSend.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutTestSendActionPerformed(evt);
      }
    });
    jpanTest.add(jbutTestSend, new java.awt.GridBagConstraints());

    jpanCenter.add(jpanTest, java.awt.BorderLayout.NORTH);

    getContentPane().add(jpanCenter, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jbutUpdateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutUpdateActionPerformed
  {//GEN-HEADEREND:event_jbutUpdateActionPerformed
    updatePorts();
  }//GEN-LAST:event_jbutUpdateActionPerformed

  private void jbutOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutOpenActionPerformed
  {//GEN-HEADEREND:event_jbutOpenActionPerformed
    open();
  }//GEN-LAST:event_jbutOpenActionPerformed

  private void jbutCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutCloseActionPerformed
  {//GEN-HEADEREND:event_jbutCloseActionPerformed
    close();
  }//GEN-LAST:event_jbutCloseActionPerformed

  private void jchkSimulateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jchkSimulateActionPerformed
  {//GEN-HEADEREND:event_jchkSimulateActionPerformed
    updatePorts();
  }//GEN-LAST:event_jchkSimulateActionPerformed

  private void jbutTestSendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutTestSendActionPerformed
  {//GEN-HEADEREND:event_jbutTestSendActionPerformed
    testSend();
  }//GEN-LAST:event_jbutTestSendActionPerformed

  private void jbutTestConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutTestConnectActionPerformed
  {//GEN-HEADEREND:event_jbutTestConnectActionPerformed
    testConnect();
  }//GEN-LAST:event_jbutTestConnectActionPerformed


  
  
  
  /**
   * @param args the command line arguments
   */
  public static void main (String args[])
  {
    /*
     * Set the Nimbus look and feel
     */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /*
     * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try
    {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
      {
        if ("Nimbus".equals(info.getName()))
        {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (ClassNotFoundException ex)
    {
      java.util.logging.Logger.getLogger(ProtocolTesterGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex)
    {
      java.util.logging.Logger.getLogger(ProtocolTesterGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex)
    {
      java.util.logging.Logger.getLogger(ProtocolTesterGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(ProtocolTesterGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    LOGP.addHandler(new logging.LogOutputStreamHandler(System.out));
    LOG.setLevel(java.util.logging.Level.ALL);
    /*
     * Create and display the form
     */
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run ()
      {
        new ProtocolTesterGui().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JButton jbutClose;
  private javax.swing.JButton jbutOpen;
  private javax.swing.JButton jbutTestConnect;
  private javax.swing.JButton jbutTestSend;
  private javax.swing.JButton jbutUpdate;
  private javax.swing.JComboBox<String> jcbPorts;
  private javax.swing.JCheckBox jchkSimulate;
  private javax.swing.JPanel jpanCenter;
  private javax.swing.JPanel jpanCommButtons;
  private javax.swing.JPanel jpanNorth;
  private javax.swing.JPanel jpanTest;
  // End of variables declaration//GEN-END:variables

}
