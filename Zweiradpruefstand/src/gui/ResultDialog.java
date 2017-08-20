/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;

/**
 *
 * @author Messing Levin <meslem12@htl-kaindorf.ac.at>
 */
public class ResultDialog extends javax.swing.JDialog
{

  private double kw;
  private double ps;
  private double rpm;
  
  /**
   * Creates new form ResultDialog
   */
  public ResultDialog(java.awt.Frame parent, boolean modal)
  {
    
    super(parent, modal);
    setTitle("Maximalwerte");
    setMinimumSize(new Dimension(350, 300));
    setLocationRelativeTo(parent);
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

    jPanelSouth = new javax.swing.JPanel();
    jbutConfirm = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);

    jbutConfirm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
    jbutConfirm.setText("OK");
    jbutConfirm.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbutConfirmActionPerformed(evt);
      }
    });
    jPanelSouth.add(jbutConfirm);

    getContentPane().add(jPanelSouth, java.awt.BorderLayout.SOUTH);

    jPanel1.setLayout(new java.awt.GridBagLayout());

    jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("ERROR");
    jLabel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jbutConfirmActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutConfirmActionPerformed
  {//GEN-HEADEREND:event_jbutConfirmActionPerformed
    this.dispose();
  }//GEN-LAST:event_jbutConfirmActionPerformed

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
      java.util.logging.Logger.getLogger(ResultDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex)
    {
      java.util.logging.Logger.getLogger(ResultDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex)
    {
      java.util.logging.Logger.getLogger(ResultDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(ResultDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the dialog */
    java.awt.EventQueue.invokeLater(new Runnable()
    {

      public void run()
      {
        ResultDialog dialog = new ResultDialog(new javax.swing.JFrame(), true);
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

  public void setPS(double ps)
  {
    this.ps = ps;
  }
  
  public void setRPM(double rpm)
  {
    this.rpm = rpm;
  }
  
  public void setKW(double kw)
  {
    this.kw = kw;
  }
  
  public void update()
  {
    if(rpm == 0)
    {
      if(kw == 0)
        jLabel1.setText(ps + "PS");
      else
        jLabel1.setText(kw + "kW");
    }
    else
    {
      if(kw == 0)
        jLabel1.setText(String.format("<html>%.2f PS<br/>%.0f U/min</html>", ps, rpm));
      else
        jLabel1.setText(String.format("<html>%.2f kW<br/>%.0f U/min</html>", kw, rpm));
    }
    
  }
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanelSouth;
  private javax.swing.JButton jbutConfirm;
  // End of variables declaration//GEN-END:variables
}
