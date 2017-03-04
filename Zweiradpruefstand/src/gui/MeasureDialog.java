package gui;

import data.Data;
import java.awt.Dimension;
import java.util.List;
import java.util.logging.Level;
import logging.Logger;
import javax.swing.JOptionPane;
import measure.Communication;
import measure.MeasurementWorker;

/**
 *
 * @author Messing Levin (meslem12@htl-kaindorf.ac.at)
 */
public class MeasureDialog extends javax.swing.JDialog
{

  private static final Logger LOG = Logger.getLogger(MeasureDialog.class.getName());

  private final Data data = Data.getInstance();
  private Gui gui;
  private Measure worker;

  /**
   * Creates new form LoadingFrame
   *
   * @param parent
   * @param modal
   */
  public MeasureDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);

    LOG.setLevel(Level.ALL);
    setTitle("Messung läuft...");
    setResizable(false);
    setMinimumSize(new Dimension(250, 150));

    initComponents();

  }

  public void init(Gui gui, Communication com)
  {
    this.gui = gui;
    worker = new Measure(com);
    setLocationRelativeTo(gui);
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

    jPanelButtons = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jPanelInfo = new javax.swing.JPanel();
    jProgressBar = new javax.swing.JProgressBar();
    jLabelStatus = new javax.swing.JLabel();
    jLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);

    jButton1.setText("Abbrechen");
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton1ActionPerformed(evt);
      }
    });
    jPanelButtons.add(jButton1);

    jButton3.setText("Messung fertigstellen");
    jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton3.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton3ActionPerformed(evt);
      }
    });
    jPanelButtons.add(jButton3);

    getContentPane().add(jPanelButtons, java.awt.BorderLayout.SOUTH);

    jPanelInfo.setLayout(new java.awt.GridBagLayout());

    jProgressBar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jProgressBar.setPreferredSize(new java.awt.Dimension(150, 25));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    jPanelInfo.add(jProgressBar, gridBagConstraints);

    jLabelStatus.setText("0");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    jPanelInfo.add(jLabelStatus, gridBagConstraints);

    jLabel.setText("Anzahl der Messpunkte: ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanelInfo.add(jLabel, gridBagConstraints);

    getContentPane().add(jPanelInfo, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
  {//GEN-HEADEREND:event_jButton1ActionPerformed
    abort();
  }//GEN-LAST:event_jButton1ActionPerformed

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
  {//GEN-HEADEREND:event_jButton3ActionPerformed
    finish();
  }//GEN-LAST:event_jButton3ActionPerformed

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
      java.util.logging.Logger.getLogger(MeasureDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(() ->
    {
      MeasureDialog dialog = new MeasureDialog(new javax.swing.JFrame(), true);
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
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton3;
  private javax.swing.JLabel jLabel;
  private javax.swing.JLabel jLabelStatus;
  private javax.swing.JPanel jPanelButtons;
  private javax.swing.JPanel jPanelInfo;
  private javax.swing.JProgressBar jProgressBar;
  // End of variables declaration//GEN-END:variables

  private void abort()
  {
    if(JOptionPane.showConfirmDialog(this, "Sind Sie sicher?", "Messung abbrechen", JOptionPane.YES_NO_OPTION) == 0)
    {
      worker.cancel(true);
      close();
    }
  }

  private void finish()
  {
    worker.stop();
    close();
  }

  private void close()
  {
    super.dispose();
  }

  @Override
  public void dispose()
  {
    if(JOptionPane.showConfirmDialog(this, "Sind Sie sicher?", "Messung abbrechen", JOptionPane.YES_NO_OPTION) == 0)
    {
      worker.cancel(true);
      close();
    }
  }

  public void startMeasurement()
  {
    worker.execute();
    jProgressBar.setIndeterminate(true);
  }

  /**
   * MeasurementWorker
   */
  private class Measure extends MeasurementWorker
  {

    private Measure(Communication com)
    {
      super(com);
    }

    @Override
    protected void process(List<Integer> chunks)
    {
      for(Integer chunk : chunks)
      {
        jLabelStatus.setText(String.valueOf(chunk));
      }
    }

    @Override
    protected void done()
    {
      gui.done(worker);
      close();
    }

  }

}
