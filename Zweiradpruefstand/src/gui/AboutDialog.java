package gui;

import org.jfree.JCommonInfo;

/**
 *
 * @author Levin
 */
public class AboutDialog extends javax.swing.JDialog
{

  /**
   * Creates new form jDialogAbout
   */
  public AboutDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
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

    jPanLogo = new javax.swing.JPanel();
    jLabelLogo = new javax.swing.JLabel();
    jLabelVersion = new javax.swing.JLabel();
    jPanInfo = new javax.swing.JPanel();
    jLabelDevelopers = new javax.swing.JLabel();
    jLabelInfo = new javax.swing.JLabel();
    jLabelWarning = new javax.swing.JLabel();
    jPanInfo2 = new javax.swing.JPanel();
    jLabelDate = new javax.swing.JLabel();
    jLabelAuthor = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Über");
    setMinimumSize(new java.awt.Dimension(500, 500));
    setPreferredSize(new java.awt.Dimension(500, 500));
    setResizable(false);
    setSize(new java.awt.Dimension(500, 500));

    jPanLogo.setLayout(new java.awt.GridBagLayout());

    jLabelLogo.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
    jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo128.png"))); // NOI18N
    jLabelLogo.setText(" PMT Dyno");
    jLabelLogo.setVerifyInputWhenFocusTarget(false);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
    jPanLogo.add(jLabelLogo, gridBagConstraints);

    jLabelVersion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabelVersion.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabelVersion.setText('v' + gui.Gui.getVERSION());
    jLabelVersion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
    jLabelVersion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
    gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
    jPanLogo.add(jLabelVersion, gridBagConstraints);

    getContentPane().add(jPanLogo, java.awt.BorderLayout.NORTH);

    jPanInfo.setLayout(new java.awt.GridBagLayout());

    jLabelDevelopers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jLabelDevelopers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabelDevelopers.setText("<html>  <center> <b>Diplomanden: </b> <br>  Primus Christoph - Elektrotechnik<br>  Messing Levin - Programm<br>  Tinauer Robert - Mechanik<br>  </center>");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
    jPanInfo.add(jLabelDevelopers, gridBagConstraints);

    jLabelInfo.setBackground(new java.awt.Color(255, 255, 255));
    jLabelInfo.setText("<html>"
      + "<b>Version: </b>PMT Dyno v" + gui.Gui.getVERSION() + "<br>"
      + "<b>Config Datei: </b>" + data.Data.getInstance().getFilePath() + "<br>"
      + "<b>System: </b>" + System.getProperty("os.name") + "<br>"
      + "<b>Benutzer: </b>" + System.getProperty("user.name") + "<br>"
      + "<b>Java: </b>" + System.getProperty("java.version") + "<br>"
      + "<b>JSSC: </b>" + jssc.SerialNativeInterface.getLibraryVersion() + "<br>"
      + "<b>JCommon: </b>" + JCommonInfo.getInstance().getVersion() + "<br>"
      + "<b>JFreeChart: </b>" + org.jfree.chart.JFreeChart.INFO.getVersion() + "<br></html>"
    );
    jLabelInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jLabelInfo.setOpaque(true);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.ipadx = 5;
    gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
    jPanInfo.add(jLabelInfo, gridBagConstraints);

    jLabelWarning.setText("Die Nutzung des Prüfstandes erfolgt auf eigene Gefahr!");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
    jPanInfo.add(jLabelWarning, gridBagConstraints);

    getContentPane().add(jPanInfo, java.awt.BorderLayout.CENTER);

    jPanInfo2.setLayout(new java.awt.GridLayout(1, 0));

    jLabelDate.setText("2016-2017");
    jLabelDate.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        jLabelDateMouseClicked(evt);
      }
    });
    jPanInfo2.add(jLabelDate);

    jLabelAuthor.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabelAuthor.setText("Autor: Messing Levin");
    jPanInfo2.add(jLabelAuthor);

    getContentPane().add(jPanInfo2, java.awt.BorderLayout.SOUTH);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  
  private void jLabelDateMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelDateMouseClicked
  {//GEN-HEADEREND:event_jLabelDateMouseClicked

  }//GEN-LAST:event_jLabelDateMouseClicked

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
    catch (ClassNotFoundException ex)
    {
      java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex)
    {
      java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex)
    {
      java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>
    //</editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(new Runnable()
    {

      public void run()
      {
        AboutDialog dialog = new AboutDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter()
        {

          @Override
          public void windowClosing(java.awt.event.WindowEvent e)
          {
            System.exit(0);
          }

        });
        dialog.setVisible(true);
      }

    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabelAuthor;
  private javax.swing.JLabel jLabelDate;
  private javax.swing.JLabel jLabelDevelopers;
  private javax.swing.JLabel jLabelInfo;
  private javax.swing.JLabel jLabelLogo;
  private javax.swing.JLabel jLabelVersion;
  private javax.swing.JLabel jLabelWarning;
  private javax.swing.JPanel jPanInfo;
  private javax.swing.JPanel jPanInfo2;
  private javax.swing.JPanel jPanLogo;
  // End of variables declaration//GEN-END:variables
}
