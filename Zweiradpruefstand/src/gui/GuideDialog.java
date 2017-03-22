/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Levin
 */
public class GuideDialog extends javax.swing.JDialog
{

  /**
   * Creates new form GuideDialog
   */
  public GuideDialog(java.awt.Frame parent, boolean modal)
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

    jPanMain = new javax.swing.JPanel();
    jLabelGuideMeasure = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setMinimumSize(new java.awt.Dimension(500, 400));
    setPreferredSize(new java.awt.Dimension(500, 400));
    setResizable(false);
    setSize(new java.awt.Dimension(500, 400));

    jPanMain.setLayout(new java.awt.GridLayout(1, 0));

    jLabelGuideMeasure.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabelGuideMeasure.setText("<html><h2>   1. Motorrad auf den Prüfstand stellen und befestigen <br><br> 2. Prüfstand mit USB verbinden <br><br> 3. Klicken Sie auf <i>Verbinden</i> <br><br>  4. Starten Sie das Motorrad <br><br> 5. Klicken Sie auf <i>Start</i> <br><br> 6. Beschleunigen Sie mit Vollgas <br><br> 7. Bei maximaler Motordrehzahl klicken Sie auf <i>Stop</i><br> <br>  </h2>"); // NOI18N
    jPanMain.add(jLabelGuideMeasure);

    getContentPane().add(jPanMain, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

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
      java.util.logging.Logger.getLogger(GuideDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex)
    {
      java.util.logging.Logger.getLogger(GuideDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex)
    {
      java.util.logging.Logger.getLogger(GuideDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(GuideDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(new Runnable()
    {

      public void run()
      {
        GuideDialog dialog = new GuideDialog(new javax.swing.JFrame(), true);
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
  private javax.swing.JLabel jLabelGuideMeasure;
  private javax.swing.JPanel jPanMain;
  // End of variables declaration//GEN-END:variables
}
