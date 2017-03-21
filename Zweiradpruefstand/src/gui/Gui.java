package gui;

import data.Config;
import data.Data;
import data.RawDatapoint;
import data.ReadPMT;
import measure.MeasurementWorker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import measure.Communication;
import measure.CommunicationException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.TextAnchor;

/**
 * This shows the general user interface and also includes various functions
 *
 * @author Levin Messing (meslem12@htl-kaindorf.ac.at)
 * @version 0.9.8
 */
public class Gui extends javax.swing.JFrame
{

  private static final String VERSION = "0.9.9";

  private static final Logger LOGP = Logger.getParentLogger();
  private static final Logger LOG = Logger.getLogger(Gui.class.getName());
  private static final java.util.logging.Level DEBUGLEVEL = java.util.logging.Level.ALL;

  private final Communication com = new Communication();

  private final ProgSetDialog progset;
  private final VehicleSetDialog vehicleset;
  private final AboutDialog about;
  private final GuideDialog guide;

  private final Data data = Data.getInstance();

  private final Font font = new Font("sansserif", Font.BOLD, 15);
  private final ValueMarker maxPowerMarker = new ValueMarker(data.getMaxpower());
  private final ValueMarker maxTorqueMarker = new ValueMarker(data.getMaxtorque());

  private ChartPanel chartPanel;
  private XYSeries seriesTorque = new XYSeries("Drehmoment");
  private XYSeries seriesPower = new XYSeries("Leistung");
  private XYSeries series1 = new XYSeries("tempLeistung series");
  private XYSeries series2 = new XYSeries("tempDrehmoment series");
  private XYSeriesCollection dataset1 = new XYSeriesCollection();
  private XYSeriesCollection dataset2 = new XYSeriesCollection();

  private JFreeChart chart;

  /**
   * Creates the GUI
   */
  public Gui()
  {

    initComponents();
    try
    {
      Config.getInstance().load(); //settings taken over config file
    }
    catch (Exception ex)
    {
      LOG.warning("Error reading Config file", ex);
      showErrorMessage("Fehler aufgetreten!", ex.getMessage() + "\n\n" + ex.getCause().toString());
    }

    about = new AboutDialog(this, true);
    guide = new GuideDialog(this, true);

    setIconImage(new ImageIcon(getClass().getResource("/icons/logo128.png")).getImage());

    setTitle("PMTDyno v" + VERSION);
    setMinimumSize(new Dimension(800, 450));
    setSize(data.getWindowWidth(), data.getWindowHeight());
    setLocation(data.getWindowRelativeX(), data.getWindowRelativeY());

    progset = new ProgSetDialog(this, true);
    vehicleset = new VehicleSetDialog(this, true);
    refreshPorts();

    initChart();

    jComboBoxPort.requestFocusInWindow();

  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    jToolBar = new javax.swing.JToolBar();
    jStart = new javax.swing.JButton();
    jRefresh = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    jProgSet = new javax.swing.JButton();
    jSeparator2 = new javax.swing.JToolBar.Separator();
    jPrint = new javax.swing.JButton();
    jSave = new javax.swing.JButton();
    jSeparator3 = new javax.swing.JToolBar.Separator();
    jPanSerial = new javax.swing.JPanel();
    jpanEast = new javax.swing.JPanel();
    jComboBoxPort = new javax.swing.JComboBox<>();
    jpanSerialButtons = new javax.swing.JPanel();
    jConnect = new javax.swing.JButton();
    jDisconnect = new javax.swing.JButton();
    jRefreshDevice = new javax.swing.JButton();
    jLabelStatus = new javax.swing.JLabel();
    jChartPanel = new javax.swing.JPanel();
    jMenuBar = new javax.swing.JMenuBar();
    jFile = new javax.swing.JMenu();
    jMenuOpen = new javax.swing.JMenuItem();
    jSeparator6 = new javax.swing.JPopupMenu.Separator();
    jMenuSave = new javax.swing.JMenuItem();
    jMenuExport = new javax.swing.JMenuItem();
    jMenuPrint = new javax.swing.JMenuItem();
    jSeparator4 = new javax.swing.JPopupMenu.Separator();
    jMenuSettings = new javax.swing.JMenuItem();
    jSeparator5 = new javax.swing.JPopupMenu.Separator();
    jMenuClose = new javax.swing.JMenuItem();
    jHelp = new javax.swing.JMenu();
    jMenuGuide = new javax.swing.JMenuItem();
    jMenuAbout = new javax.swing.JMenuItem();
    jMenuItem1 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setIconImages(null);

    jToolBar.setRollover(true);

    jStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start48.png"))); // NOI18N
    jStart.setToolTipText("Messung starten");
    jStart.setEnabled(false);
    jStart.setFocusable(false);
    jStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jStart.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jStartActionPerformed(evt);
      }
    });
    jToolBar.add(jStart);

    jRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/refresh48.png"))); // NOI18N
    jRefresh.setToolTipText("Umgebungswerte aktualisieren");
    jRefresh.setEnabled(false);
    jRefresh.setFocusable(false);
    jRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jRefresh.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jRefreshActionPerformed(evt);
      }
    });
    jToolBar.add(jRefresh);
    jToolBar.add(jSeparator1);

    jProgSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/settings48.png"))); // NOI18N
    jProgSet.setToolTipText("Programmeinstellungen");
    jProgSet.setFocusable(false);
    jProgSet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jProgSet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jProgSet.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jProgSetActionPerformed(evt);
      }
    });
    jToolBar.add(jProgSet);
    jToolBar.add(jSeparator2);

    jPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer48.png"))); // NOI18N
    jPrint.setToolTipText("Drucken...");
    jPrint.setEnabled(false);
    jPrint.setFocusable(false);
    jPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jPrint.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jPrintActionPerformed(evt);
      }
    });
    jToolBar.add(jPrint);

    jSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save48.png"))); // NOI18N
    jSave.setToolTipText("Als Bild speichern");
    jSave.setEnabled(false);
    jSave.setFocusable(false);
    jSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jSave.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jSaveActionPerformed(evt);
      }
    });
    jToolBar.add(jSave);
    jToolBar.add(jSeparator3);

    jPanSerial.setLayout(new java.awt.BorderLayout());

    jpanEast.setLayout(new java.awt.GridBagLayout());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.ipadx = 20;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
    jpanEast.add(jComboBoxPort, gridBagConstraints);

    jpanSerialButtons.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

    jConnect.setText("Verbinden");
    jConnect.setMargin(new java.awt.Insets(3, 3, 3, 3));
    jConnect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jConnectActionPerformed(evt);
      }
    });
    jpanSerialButtons.add(jConnect);

    jDisconnect.setText("Trennen");
    jDisconnect.setEnabled(false);
    jDisconnect.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jDisconnectActionPerformed(evt);
      }
    });
    jpanSerialButtons.add(jDisconnect);

    jRefreshDevice.setText("Aktualisieren");
    jRefreshDevice.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jRefreshDeviceActionPerformed(evt);
      }
    });
    jpanSerialButtons.add(jRefreshDevice);

    jpanEast.add(jpanSerialButtons, new java.awt.GridBagConstraints());

    jLabelStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabelStatus.setForeground(java.awt.Color.gray);
    jLabelStatus.setText("getrennt");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    jpanEast.add(jLabelStatus, gridBagConstraints);

    jPanSerial.add(jpanEast, java.awt.BorderLayout.WEST);

    jToolBar.add(jPanSerial);

    getContentPane().add(jToolBar, java.awt.BorderLayout.PAGE_START);

    jChartPanel.setLayout(new java.awt.GridLayout(1, 0));
    getContentPane().add(jChartPanel, java.awt.BorderLayout.CENTER);

    jFile.setText("Datei");

    jMenuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
    jMenuOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/open16.png"))); // NOI18N
    jMenuOpen.setText("Öffnen...");
    jMenuOpen.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuOpenActionPerformed(evt);
      }
    });
    jFile.add(jMenuOpen);
    jFile.add(jSeparator6);

    jMenuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
    jMenuSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save16.png"))); // NOI18N
    jMenuSave.setText("Als Bild speichern...");
    jMenuSave.setEnabled(false);
    jMenuSave.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuSaveActionPerformed(evt);
      }
    });
    jFile.add(jMenuSave);

    jMenuExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
    jMenuExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/export16.png"))); // NOI18N
    jMenuExport.setText("Exportieren...");
    jMenuExport.setEnabled(false);
    jMenuExport.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuExportActionPerformed(evt);
      }
    });
    jFile.add(jMenuExport);

    jMenuPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
    jMenuPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer16.png"))); // NOI18N
    jMenuPrint.setText("Drucken...");
    jMenuPrint.setEnabled(false);
    jMenuPrint.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuPrintActionPerformed(evt);
      }
    });
    jFile.add(jMenuPrint);
    jFile.add(jSeparator4);

    jMenuSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/settings16.png"))); // NOI18N
    jMenuSettings.setText("Einstellungen");
    jMenuSettings.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuSettingsActionPerformed(evt);
      }
    });
    jFile.add(jMenuSettings);
    jFile.add(jSeparator5);

    jMenuClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
    jMenuClose.setText("Beenden");
    jMenuClose.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuCloseActionPerformed(evt);
      }
    });
    jFile.add(jMenuClose);

    jMenuBar.add(jFile);

    jHelp.setText("Hilfe");

    jMenuGuide.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
    jMenuGuide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/manual16.png"))); // NOI18N
    jMenuGuide.setText("Anleitung");
    jMenuGuide.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuGuideActionPerformed(evt);
      }
    });
    jHelp.add(jMenuGuide);

    jMenuAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo16.png"))); // NOI18N
    jMenuAbout.setText("Über");
    jMenuAbout.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuAboutActionPerformed(evt);
      }
    });
    jHelp.add(jMenuAbout);

    jMenuItem1.setText("StartWorker");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jHelp.add(jMenuItem1);

    jMenuBar.add(jHelp);

    setJMenuBar(jMenuBar);

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void jStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jStartActionPerformed
    {//GEN-HEADEREND:event_jStartActionPerformed
      start();
    }//GEN-LAST:event_jStartActionPerformed

    private void jPrintActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jPrintActionPerformed
    {//GEN-HEADEREND:event_jPrintActionPerformed
      print();
    }//GEN-LAST:event_jPrintActionPerformed

    private void jSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveActionPerformed
      savePng();
    }//GEN-LAST:event_jSaveActionPerformed

    private void jProgSetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jProgSetActionPerformed
    {//GEN-HEADEREND:event_jProgSetActionPerformed
      startProgSet();
    }//GEN-LAST:event_jProgSetActionPerformed

    private void jConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jConnectActionPerformed
    {//GEN-HEADEREND:event_jConnectActionPerformed
      connectDevice();
    }//GEN-LAST:event_jConnectActionPerformed

    private void jDisconnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jDisconnectActionPerformed
    {//GEN-HEADEREND:event_jDisconnectActionPerformed
      try
      {
        com.disconnect();
        jLabelStatus.setText("getrennt");
        jLabelStatus.setForeground(Color.GRAY);
      }
      catch (CommunicationException ex)
      {
        showErrorMessage("Fehler beim trennen", "Gerät konnte nicht getrennt werden!");
      }
      finally
      {
        jStart.setEnabled(com.isConnected());
        jRefresh.setEnabled(com.isConnected());
        jConnect.setEnabled(!com.isConnected());
        jDisconnect.setEnabled(com.isConnected());
        jRefreshDevice.setEnabled(!com.isConnected());
        jComboBoxPort.setEnabled(!com.isConnected());
      }
    }//GEN-LAST:event_jDisconnectActionPerformed

    private void jRefreshDeviceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRefreshDeviceActionPerformed
    {//GEN-HEADEREND:event_jRefreshDeviceActionPerformed
      refreshPorts();
    }//GEN-LAST:event_jRefreshDeviceActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuAboutActionPerformed
    {//GEN-HEADEREND:event_jMenuAboutActionPerformed
      about.setLocationRelativeTo(this);
      about.setVisible(true);

//      jFrameAbout.setLocationRelativeTo(this);
//      jFrameAbout.setVisible(true);
    }//GEN-LAST:event_jMenuAboutActionPerformed

    private void jMenuGuideActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuGuideActionPerformed
    {//GEN-HEADEREND:event_jMenuGuideActionPerformed
      guide.setLocationRelativeTo(this);
      guide.setVisible(true);
    }//GEN-LAST:event_jMenuGuideActionPerformed

    private void jRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshActionPerformed
      refreshEco();
    }//GEN-LAST:event_jRefreshActionPerformed

  private int easterEgg = 0;
  private void jMenuCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuCloseActionPerformed
  {//GEN-HEADEREND:event_jMenuCloseActionPerformed
    dispose();
  }//GEN-LAST:event_jMenuCloseActionPerformed

  private void jMenuSettingsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuSettingsActionPerformed
  {//GEN-HEADEREND:event_jMenuSettingsActionPerformed
    startProgSet();
  }//GEN-LAST:event_jMenuSettingsActionPerformed

  private void jMenuPrintActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuPrintActionPerformed
  {//GEN-HEADEREND:event_jMenuPrintActionPerformed
    print();
  }//GEN-LAST:event_jMenuPrintActionPerformed

  private void jMenuExportActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuExportActionPerformed
  {//GEN-HEADEREND:event_jMenuExportActionPerformed
    exportFile();
  }//GEN-LAST:event_jMenuExportActionPerformed

  private void jMenuSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuSaveActionPerformed
  {//GEN-HEADEREND:event_jMenuSaveActionPerformed
    savePng();
  }//GEN-LAST:event_jMenuSaveActionPerformed

  private void jMenuOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuOpenActionPerformed
  {//GEN-HEADEREND:event_jMenuOpenActionPerformed
    openMeasureFile();
  }//GEN-LAST:event_jMenuOpenActionPerformed

  private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
  {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
    start();
  }//GEN-LAST:event_jMenuItem1ActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
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
//      for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
//      {
//        if("Nimbus".equals(info.getName()))
//        {
//          javax.swing.UIManager.setLookAndFeel(info.getClassName());
//          
//          break;
//        }
//      }

      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
    }
    catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    //</editor-fold>
    /*
     * Create and display the form
     */
    LOGP.addHandler(new logging.LogOutputStreamHandler(System.out));
    LOG.setLevel(DEBUGLEVEL);
    LOGP.setLevel(DEBUGLEVEL);
    try
    {
      String opt = System.getProperty("DebugLevel");
      if(opt != null)
      {

        LOG.setLevel(Level.parse(opt));
      }
    }
    catch (IllegalArgumentException | SecurityException ex)
    {
      System.out.println("Invalid debug level");
    }
    catch (Exception ex)
    {
      LOG.severe("Unsupported Exception", ex);
    }

    LOG.info("Start of Application");

    java.awt.EventQueue.invokeLater(()
            ->
    {
      new Gui().setVisible(true);
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel jChartPanel;
  private javax.swing.JComboBox<String> jComboBoxPort;
  private javax.swing.JButton jConnect;
  private javax.swing.JButton jDisconnect;
  private javax.swing.JMenu jFile;
  private javax.swing.JMenu jHelp;
  private javax.swing.JLabel jLabelStatus;
  private javax.swing.JMenuItem jMenuAbout;
  private javax.swing.JMenuBar jMenuBar;
  private javax.swing.JMenuItem jMenuClose;
  private javax.swing.JMenuItem jMenuExport;
  private javax.swing.JMenuItem jMenuGuide;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuOpen;
  private javax.swing.JMenuItem jMenuPrint;
  private javax.swing.JMenuItem jMenuSave;
  private javax.swing.JMenuItem jMenuSettings;
  private javax.swing.JPanel jPanSerial;
  private javax.swing.JButton jPrint;
  private javax.swing.JButton jProgSet;
  private javax.swing.JButton jRefresh;
  private javax.swing.JButton jRefreshDevice;
  private javax.swing.JButton jSave;
  private javax.swing.JToolBar.Separator jSeparator1;
  private javax.swing.JToolBar.Separator jSeparator2;
  private javax.swing.JToolBar.Separator jSeparator3;
  private javax.swing.JPopupMenu.Separator jSeparator4;
  private javax.swing.JPopupMenu.Separator jSeparator5;
  private javax.swing.JPopupMenu.Separator jSeparator6;
  private javax.swing.JButton jStart;
  private javax.swing.JToolBar jToolBar;
  private javax.swing.JPanel jpanEast;
  private javax.swing.JPanel jpanSerialButtons;
  // End of variables declaration//GEN-END:variables


  /*
   * ---PUBLIC METHODS------------------------------------------
   */
  /**
   * Saves the config. Then shuts down the program.
   */
  @Override
  public void dispose()
  {
    data.setWindowWidth(getWidth());
    data.setWindowHeight(getHeight());
    data.setWindowRelativeX(getLocationOnScreen().x);
    data.setWindowRelativeY(getLocationOnScreen().y);

    try
    {
      Config.getInstance().save();
      com.disconnect();
    }
    catch (CommunicationException ex)
    {
      LOG.severe("Port could not be closed");
    }
    catch (Exception ex)
    {
      LOG.severe(ex.getMessage());
      showErrorMessage("Fehler aufgetreten!", ex.getMessage() + "\n\n" + ex.getCause().toString());
    }
    finally
    {
      LOG.info("End of Application");
      System.exit(0);
    }
  }


  /*
   * ---PRIVATE METHODS------------------------------------------
   */
  /**
   * disables:
   * <ul>
   * <li>jStart
   * <li>jRefresh
   * </ul>
   *
   */
  private void enableCancelling()
  {
    jStart.setEnabled(false);
    jRefresh.setEnabled(false);
  }

  /**
   * enables:
   * <ul>
   * <li>jStart
   * <li>jRefresh
   * </ul>
   *
   */
  private void enableStarting()
  {
    jStart.setEnabled(true);
    jRefresh.setEnabled(true);

  }

  /**
   * Shows an error message relative to the main GUI
   *
   * @param title   The Title of the Frame
   * @param message The displayed message
   */
  private void showErrorMessage(String title, String message)
  {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
  }


  /*
   * ---PRIVATE METHODS----------------------------------------
   */
  /**
   * Creates the Chart and sets the datasets/series
   */
  private void initChart()
  {
    chart = ChartFactory.createXYLineChart(data.getVehicle(),
                                           "Motordrehzahl [U/min]",
                                           "Leistung [" + data.getPowerunit() + "]",
                                           dataset1,
                                           PlotOrientation.VERTICAL,
                                           true,
                                           true,
                                           false);

    ValueAxis torqueAxis = new NumberAxis("Drehmoment [Nm]");
    torqueAxis.setLabelFont(chart.getXYPlot().getDomainAxis().getLabelFont());

    seriesPower.setKey("Leistung");

    chart.getXYPlot().setDataset(1, dataset2);
    chart.getXYPlot().setRangeAxis(1, torqueAxis);
    chart.getXYPlot().mapDatasetToRangeAxis(0, 0);//1st dataset to 1st y-axis
    chart.getXYPlot().mapDatasetToRangeAxis(1, 1); //2nd dataset to 2nd y-axis

    // Hinzufuegen von series zu der Datenmenge dataset
    dataset1.addSeries(seriesPower);
    dataset2.addSeries(seriesTorque);

    maxPowerMarker.setPaint(Color.darkGray);
    maxPowerMarker.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                                             1.0f, new float[]
                                             {
                                               6.0f, 7.0f
            }, 0.0f));

    maxPowerMarker.setLabelTextAnchor(TextAnchor.BASELINE_LEFT);
    maxPowerMarker.setLabelFont(font);

    maxTorqueMarker.setPaint(Color.darkGray);
    maxTorqueMarker.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                                              1.0f, new float[]
                                              {
                                                6.0f, 7.0f
            }, 0.0f));

    maxTorqueMarker.setLabelTextAnchor(TextAnchor.TOP_LEFT);
    maxTorqueMarker.setLabelFont(font);

    XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
    r1.setSeriesPaint(0, Color.blue);
    r1.setSeriesShapesVisible(0, false);
    r1.setSeriesStroke(0, new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    XYLineAndShapeRenderer r2 = new XYLineAndShapeRenderer();
    r2.setSeriesPaint(0, Color.red);
    r2.setSeriesShapesVisible(0, false);
    r2.setSeriesStroke(0, new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    chart.getXYPlot().setRenderer(0, r1);
    chart.getXYPlot().setRenderer(1, r2);

    chart.getXYPlot().addRangeMarker(0, maxPowerMarker, Layer.BACKGROUND);
    chart.getXYPlot().addRangeMarker(1, maxTorqueMarker, Layer.BACKGROUND);

    chartPanel = new ChartPanel(chart);

    TextTitle eco = new TextTitle("Temperatur: NaN "
            + "Luftfeuchtigkeit: NaN "
            + "Luftdruck: NaN "
            + "vmax: NaN");

    chart.addSubtitle(eco);

    jChartPanel.add(chartPanel);

    chart.fireChartChanged();

  }

  /**
   * Starts the measurement
   */
  private void start()
  {

    if(!startVehicleSet())
      return;

    MeasureDialog loading = new MeasureDialog(this, true);
    loading.init(this, com);

    enableCancelling();

    loading.startMeasurement();
    loading.setVisible(true);

    enableStarting();

  }

  private void openMeasureFile()
  {
    try
    {

      if(!startVehicleSet())
        return;

      File file;

      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "PMTDyno (*.pmt)", "pmt");
      chooser.setFileFilter(filter);

      int rv = chooser.showOpenDialog(this);
      if(rv == JFileChooser.APPROVE_OPTION)
      {
        file = chooser.getSelectedFile();

        //ReadCSV fr = new ReadCSV("/home/levin/Desktop/measure.csv");
        //ReadCSV fr = new ReadCSV("/home/robert/Schreibtisch/measure.csv");
        ReadPMT fr = new ReadPMT(file);

        data.setMeasureList(fr.read());

        series1.clear();
        series2.clear();

        calculate();
      }
    }
    catch (NumberFormatException ex)
    {
//      ex.printStackTrace(System.err);
      LOG.severe("Wrong number format!" + ex.getMessage(), ex);
      showErrorMessage("Fehler", "Datei hat falsches Format. \n"
                       + "Es dürfen keine Kommazahlen angegeben werden!");
    }
    catch (Exception ex)
    {
//      ex.printStackTrace(System.err);
      LOG.severe("Error", ex);
      showErrorMessage("Error", ex.getMessage());
    }

  }

  private void exportFile()
  {
    if(data.getRawDataList().size() < 1)
    {
      showErrorMessage("Keine Messung vorhanden", "Keine Messdaten vorhanden. Bitte zuerst Messung durchführen");
      return;
    }

    File file;

    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "PMTDyno (*.pmt)", "pmt");
    chooser.setFileFilter(filter);
    int rv = chooser.showSaveDialog(this);
    if(rv == JFileChooser.APPROVE_OPTION)
    {
      file = chooser.getSelectedFile();

      if(!file.getName().endsWith(".pmt") && !file.getName().contains("."))
      {
        file = new File(file.getPath() + ".pmt");
      }

      try (FileWriter writer = new FileWriter(file);)
      {
        //time - rpm - wss
        for(RawDatapoint rawDatapoint : data.getRawDataList())
        {
          String time = String.valueOf(rawDatapoint.getTime());
          String rpm = String.valueOf(rawDatapoint.getRpm());
          String wss = String.valueOf(rawDatapoint.getWss());

          String line = time + ':' + rpm + ':' + wss + '\n';
          writer.write(line);
        }
        writer.close();
      }
      catch (IOException ex) //Fehler beim Speichern
      {
        LOG.warning("Error saving .pmt", ex);
        showErrorMessage("Fehler beim Exportieren", "Fehler beim Exportieren aufgetreten");
      }
      catch (NullPointerException ex)//Fehler beim Pfad
      {
        LOG.warning("Error with path", ex);
        showErrorMessage("Fehler beim Pfad", "Fehler beim Pfad aufgetreten");
      }
      catch (Exception ex)
      {
        LOG.severe("Unsupported Exception", ex);
        showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
      }
    }
  }

  /**
   * Prints the Chart Frame
   */
  private void print()
  {
    chartPanel.createChartPrintJob();
  }

  /**
   * Saves the Chart as PNG file
   */
  private void savePng()
  {

    File file;

    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Portable Network Graphic (*.png)", "png");
    chooser.setFileFilter(filter);
    int rv = chooser.showSaveDialog(this);
    if(rv == JFileChooser.APPROVE_OPTION)
    {
      file = chooser.getSelectedFile();
      if(!file.getName().endsWith(".png"))
      {
        file = new File(file.getPath() + ".png");
      }

      try
      {
        ChartUtilities.saveChartAsPNG(file, chart, data.getPngWidth(), data.getPngHeight());
      }
      catch (IOException ex)//Fehler beim Speichern
      {
        LOG.warning("Error saving PNG", ex);
        showErrorMessage("Fehler beim Speichern", "Fehler beim Speichern aufgetreten");
      }
      catch (NullPointerException ex)//Fehler beim Pfad
      {
        LOG.warning("Error with path", ex);
        showErrorMessage("Fehler beim Pfad", "Fehler beim Pfad aufgetreten");
      }
      catch (Exception ex)
      {
        LOG.severe("Unsupported Exception", ex);
        showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
      }
    }
  }

  /**
   * Opens up the ProgSetDialog and saves the changes values. If the measurement
   * is already done the datasets will be updated.
   */
  private void startProgSet()
  {
    progset.setLocationRelativeTo(this);
    progset.setVisible(true);

    int updateLevel = 0;  //0-noUpdate 1-correct 2-convert 3-calculate
    //Einstellungen übernehmen
    if(progset.isSettingsChanged())
    {

      //CORRECTION  --OPTIMIZATION POSSIBLE
      if(Double.compare(data.getCorrectionPower(), progset.getCorrectionPower()) != 0)
      {
        data.setCorrectionPower(progset.getCorrectionPower());
        updateLevel = 1;
      }
      if(Double.compare(data.getCorrectionTorque(), progset.getCorrectionTorque()) != 0)
      {
        data.setCorrectionTorque(progset.getCorrectionTorque());
        updateLevel = 1;
      }

      //POWERUNIT
      if(!data.getPowerunit().equals(progset.getPowerunit()))
      {
        data.setPowerunit(progset.getPowerunit());
        updateLevel = 2;
      }

      //INERTIA
      if(Double.compare(data.getInertia(), progset.getInertia()) != 0)
      {
        data.setInertia(progset.getInertia());
        updateLevel = 3;
      }

      //SERIALPORT
      if(data.getPeriodTimeMs() != progset.getPeriodTimeMs())
      {
        data.setPeriodTimeMs(progset.getPeriodTimeMs());
        updateLevel = 3;
      }

      //update
      if(data.getMeasureList() != null)
      {
        switch (updateLevel) //don't insert break!
        {
          case 3:
            LOG.finest("entering level 3");

            series1.clear();
            series2.clear();

            calculate();

          case 2:
            LOG.finest("entering level 2");
            if(updateLevel == 3 && data.getPowerunit().equals("PS"))
            {
              convertToPs(series1);
            }
            else if(updateLevel == 2 && data.getPowerunit().equals("kW"))
            {
              convertToKw(series1);
            }
            else if(data.getPowerunit().endsWith("PS"))
            {
              convertToPs(series1);
            }

          case 1:
            LOG.finest("entering level 1");
            dataset1.removeSeries(seriesPower);
            seriesPower = correctByFactor(series1, "Leistung", data.getCorrectionPower());
            dataset1.addSeries(seriesPower);

            dataset2.removeSeries(seriesTorque);
            seriesTorque = correctByFactor(series2, "Drehmoment", data.getCorrectionTorque());
            dataset2.addSeries(seriesTorque);

            updateChartLabels();
            break;

          case 0:
            LOG.finest("no update needed");
            break;

          default:
            LOG.severe("Something went completly wrong");
            showErrorMessage("Fehler beim uebernehmen", "Unerwarteter Fehler beim uebernehmen der Daten aufgetreten");
        }
      }
      else if(updateLevel == 2)
      {
        updateChartLabels();
      }

      //PNG RESOLUTION
      data.setPngWidth(progset.getPNGResolution().width);
      data.setPngHeight(progset.getPNGResolution().height);

      try
      {
        //safe config File
        Config.getInstance().save();
      }
      catch (Exception ex)
      {
        LOG.warning("Error saving Config File", ex);
        showErrorMessage("Fehler aufgetreten!", ex.getMessage() + "\n\n" + ex.getCause().toString());
      }

    }

  }

  /**
   * Opens up the VehicleSetDialog and saves the values. If the measurement is
   * already done the datasets will be updated.
   *
   * @return true if the dialog is confirmed
   */
  private boolean startVehicleSet()
  {
    vehicleset.setLocationRelativeTo(this);
    vehicleset.setVisible(true);

    //Einstellungen übernehmen
    if(vehicleset.isConfirmed())
    {
      //TAKT
      if(data.isTwoStroke() != vehicleset.isTwoStroke())
        data.setTwoStroke(vehicleset.isTwoStroke());

      //TRANSMISSION
      if(data.isAutomatic() != vehicleset.isAutomatic())
        data.setAutomatic(vehicleset.isAutomatic());

      //MEASrpm
      if(data.isMeasRPM() != vehicleset.isMeasRpm())
        data.setMeasRPM(vehicleset.isMeasRpm());

      //VEHICLENAME
      data.setVehicle(vehicleset.getVehicleName());

      chart.setTitle(data.getVehicle());

      chart.fireChartChanged();

      try
      {
        //save config File
        Config.getInstance().save();
      }
      catch (Exception ex)
      {
        LOG.warning("Error saving Config file");
        showErrorMessage("Fehler aufgetreten!", ex.getMessage() + "\n\n" + ex.getCause().toString());
      }
      return true;
    }
    return false;
  }

  /**
   * refreshes the availablePorts Array and puts them into the ComboBox
   */
  private void refreshPorts()
  {
    if(com.getAvailablePorts() == null || com.getAvailablePorts().length == 0)
    {
      jConnect.setEnabled(false);
    }
    else
    {
      jConnect.setEnabled(true);
    }

    jComboBoxPort.removeAllItems();
    for(String availablePort : com.getAvailablePorts())
    {
      jComboBoxPort.addItem(availablePort);
    }
  }

  /**
   * Converts the given series to PS
   *
   * @param series the series which should be converted
   */
  private void convertToPs(XYSeries series)
  {
    int max = series.getItemCount();

    for(int i = 0; i < max; i++)
    {
      double tmp = (double) series.getY(i);
      series.updateByIndex(i, tmp * 1.35962);
    }
  }

  /**
   * Converts the given series to kW
   *
   * @param series the series which should be converted
   */
  private void convertToKw(XYSeries series)
  {
    int max = series.getItemCount();

    for(int i = 0; i < max; i++)
    {
      double tmp = (double) series.getY(i);
      series.updateByIndex(i, tmp / 1.35962);
    }
  }

  /**
   * Corrects the series by the given factor and returns the result.
   *
   * @param series The series that should be copied and converted, but not
   *               changed!
   * @param factor The factor the series should be converted with.
   * @return The changed series or <code>null</code> if an error occured.
   */
  private XYSeries correctByFactor(XYSeries series, String key, double factor)
  {

    try
    {
      if(factor <= 0)
      {
        LOG.severe(new InputMismatchException("factor smaller or equal to 0!"));
        showErrorMessage("Fehler bei Konvertierung", "Interner Fehler aufgetreten\n"
                         + "Bitte sicherstellen dass ein Korrekturfaktor zwischen 0,5 und 2,0 eingestellt ist.\n");
      }
      XYSeries target = new XYSeries(key);

      for(int i = 0; i < series.getItemCount(); i++)
      {
        double x = (double) series.getX(i);
        double y = (double) series.getY(i) * factor;
        target.add(x, y);
      }

      return target;
    }
    catch (Exception ex)
    {
      LOG.severe("Unsupported Exception", ex);
      showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
    }
    return null;
  }

  /**
   * <b>Updates:</b>
   * <ul>
   * <li>maxPowerMarker</li>
   * <li>maxTorqueMarker</li>
   * <li>Subtitle(vehicle, temperature, humidity)</li>
   * <li>powerunit</li>
   * </ul>
   */
  private void updateChartLabels()
  {
    data.setMaxpower(seriesPower.getMaxY());
    data.setMaxtorque(seriesTorque.getMaxY());

    maxPowerMarker.setValue(data.getMaxpower());
    maxTorqueMarker.setValue(data.getMaxtorque());

    String strEco = String.format("Temperatur: %.1f° Luftfeuchtigkeit: %d%% Luftdruck: %.1fmbar vmax: %.1fkm/h",
                                  data.getTemperature(),
                                  data.getHumidity(),
                                  data.getPressure(),
                                  data.getVmax());

    TextTitle eco = new TextTitle(strEco);
    chart.removeSubtitle(chart.getSubtitle(1));
    chart.addSubtitle(eco);

    String strMaxPower = String.format("Maximale Leistung: %.2f %s ", data.getMaxpower(), data.getPowerunit());
    String strMaxTorque = String.format("Maximales Drehmoment: %.2f Nm", data.getMaxtorque());
    maxPowerMarker.setLabel(strMaxPower);
    maxTorqueMarker.setLabel(strMaxTorque);
    chart.getXYPlot().getRangeAxis().setLabel("Leistung [" + data.getPowerunit() + "]");
    seriesPower.setKey("Leistung");

    chart.fireChartChanged();
  }

  private int getValMaxIndex(ArrayList<Double> aL)
  {
    int valMax = 0;
    int i;
    for(i = 0; i < aL.size(); i++)
    {
      if(aL.get(i) > aL.get(valMax))
      {
        valMax = i;
      }
    }
    return valMax;
  }

  private ArrayList<Double> filterValuesOrder(ArrayList<Double> aL, double smoothing, int order)
  {
    for(int i = 0; i < order; i++)
    {
      aL = filterValues(aL, smoothing);
    }

    return aL;
  }

  private ArrayList<Double> filterValues(ArrayList<Double> aL, double smoothing) //higher smoothingvalue means more smoothing
  {
    ArrayList<Double> smoothedValues = new ArrayList<>();
    smoothedValues.add(aL.get(0));
    for(int i = 0; i < aL.size() - 1; i++)
    {
//      System.out.println(i);
      smoothedValues.add((1 - smoothing) * smoothedValues.get(i) + smoothing * aL.get(i + 1));
    }

    return smoothedValues;

  }

  /**
   * calculates Power and Torque and updates the Chart
   *
   * @author Robert Tinauer
   */
  private void calculate()
  {

    //Einzufuegen:Druck und Temperaturwerte, Einstellung ob Darstellung über Geschwindigkeit
    //oder RPM, richtige X-Achsenbeschriftung für Roller-Modus (km/h),  
    LOG.fine("calculating...");

    double inertia = data.getInertia();
    double n; //uebersetzungsverhaeltnis rolle zu motor

    ArrayList<Double> trq = new ArrayList<>();
    ArrayList<Double> trqSchl = new ArrayList<>(); //schleppmoment
    ArrayList<Double> pwr = new ArrayList<>();
    ArrayList<Double> rpm = new ArrayList<>(); //in U/min
    ArrayList<Double> time = new ArrayList<>(); //in s
    ArrayList<Double> alpha = new ArrayList<>();
    ArrayList<Double> omega = new ArrayList<>();
    ArrayList<Double> omegaSchl = new ArrayList<>();

    boolean schleppEnable = true;

    double tempFactor = (1013 / data.getPressure()) * Math.sqrt((273 + data.getTemperature()) / 293); //Korrekturfaktor temp

    for(int i = 0; i < data.getMeasureList().size() - 1; i++)
    {
      omega.add(data.getMeasureList().get(i).getWss());
      rpm.add(data.getMeasureList().get(i).getRpm());
      time.add(data.getMeasureList().get(i).getTime());
    }
    omega = filterValuesOrder(omega, 0.5, 2);

    //VMAX ermitteln
    data.setVmax(omega.get(getValMaxIndex(omega)) * 0.175 * 3.6);

    //alpha berechnen
    for(int i = 0; i < omega.size() - 1; i++)
    {
      alpha.add((omega.get(i + 1) - omega.get(i)) / (time.get(i + 1) - time.get(i)));
    }
    alpha = filterValuesOrder(alpha, 0.1, 2);

    //faktor fuer berechnungseinheit
    double factor;

    if(data.getPowerunit().contains("PS"))
    {
      factor = 1.36;
    }
    else
    {
      factor = 1;
    }

    //drehmoment roller
    if(!data.isMeasRPM())
    {

      for(int i = 0; i < alpha.size(); i++)
      {
        trq.add(alpha.get(i) * inertia);  //M=dOmega/dt * J

      }
    }
    else//drehmoment kein roller
    {

      rpm = filterValuesOrder(rpm, 0.09, 3);
      //uebersetzungsverhaeltnis:

      for(int i = 0; i < alpha.size(); i++)
      {
        //moment
        try
        {
          trq.add(alpha.get(i) * inertia * ((omega.get(i) / (rpm.get(i) / 60 * 2 * 3.14)) + (omega.get(i + 1) / (rpm.get(i + 1) / 60 * 2 * Math.PI))) / 2);  //M=dOmega/dt * J
        }
        catch (IndexOutOfBoundsException ex)
        {
          showErrorMessage("Fehler", "Zu wenig Messwerte! (" + omega.size() + ')');
          return;
        }
      }
    }

    //index ermitteln, ab dem moment negativ ist und somit schleppmoment vorhanden ist
    int limitSchl = 0;
    try
    {
      for(limitSchl = getValMaxIndex(omega); true; limitSchl++)
      {
        if(trq.get(limitSchl) < 0)
        {
          break;
        }
      }
    }
    catch (IndexOutOfBoundsException ex)
    {
      schleppEnable = false;
      LOG.info("No Towing Torque");
      JOptionPane.showMessageDialog(this, "Berechnung erfolgt ohne Berücksichtigung des Schleppmoments", "Kein Schleppmoment", JOptionPane.INFORMATION_MESSAGE);
    }

    //schleppmoment zu motormoment addieren, falls schleppmoment vorhanden ist
    if(schleppEnable)
    {
      trqSchl = new ArrayList<>(trq.subList(limitSchl, trq.size()));
      omegaSchl = new ArrayList<>(omega.subList(limitSchl, omega.size()));
//      ArrayList<Double> timeSchl = new ArrayList<>(time.subList(limitSchl, time.size()));

      for(int i2 = 0; i2 < limitSchl; i2++)
      {
        for(int i = 0; i < trqSchl.size(); i++)
        {
          if(omega.get(i2) - (omegaSchl.get(i)) < 2 && omega.get(i2) - (omegaSchl.get(i)) > 0)
          {
            if(trqSchl.get(i) > 0)
            {
              break;
            }
            trq.set(i2, trq.get(i2) + trqSchl.get(i) * -1);
            i2++;
          }
        }
      }

      trq = filterValuesOrder(trq, 0.13, 2);
    }

    //leistung berechnen
    if(!data.isMeasRPM())
    {
      for(int i = 0; i < trq.size(); i++)
      {
        pwr.add((trq.get(i) * omega.get(i) / 1000) * factor * tempFactor);
      }
    }
    else
    {
      for(int i = 0; i < trq.size(); i++)
      {
        pwr.add((trq.get(i) * ((rpm.get(i) / 60) * (2 * Math.PI)) / 1000) * factor * tempFactor);
      }
    }

    series1.clear();
    series2.clear();

    if(!data.isMeasRPM() || data.isAutomatic())
    {
      for(int i = 0; i < trq.size(); i++)
      {
//        System.out.println(i + " Leistung: " + pwr.get(i) + " Drehmoment: " + trq.get(i) + " Geschwindigkeit: " + (omega.get(i) * 0.175 * 3.6));
        if(i == getValMaxIndex(omega))
        {
          break;
        }
        series1.add(omega.get(i) * 0.175 * 3.6, pwr.get(i));
        series2.add(omega.get(i) * 0.175 * 3.6, trq.get(i));

      }
    }
    else
    {
      for(int i = 0; i < trq.size(); i++)
      {
//        System.out.println(i + " Leistung: " + pwr.get(i) + " Drehmoment: " + trq.get(i) + " Motordrehzahl: " + rpm.get(i));
        if(i == getValMaxIndex(rpm))
        {
          break;
        }

        series1.add(rpm.get(i), pwr.get(i));
        series2.add(rpm.get(i), trq.get(i));

      }
    }

    dataset1.removeSeries(seriesPower);
    seriesPower = correctByFactor(series1, "Leistung", data.getCorrectionPower());
    dataset1.addSeries(seriesPower);

    dataset2.removeSeries(seriesTorque);
    seriesTorque = correctByFactor(series2, "Drehmoment", data.getCorrectionTorque());
    dataset2.addSeries(seriesTorque);
    seriesTorque.setKey("Drehmoment [Nm]");

    if(!data.isMeasRPM())
      chart.getXYPlot().getDomainAxis().setLabel("Geschwindigkeit [km/h]");
    else
      chart.getXYPlot().getDomainAxis().setLabel("Motordrehzahl [U/m]");

    chart.fireChartChanged();
    updateChartLabels();

    jSave.setEnabled(true);
    jPrint.setEnabled(true);
    jMenuSave.setEnabled(true);
    jMenuPrint.setEnabled(true);
    jMenuExport.setEnabled(true);

    LOG.fine("done calculating");

  }

  /**
   * Sets the Port and tries to connect to the Device.<br>
   * Then updates the Ecosystem.
   */
  private void connectDevice()
  {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try
    {
      com.connect(jComboBoxPort.getSelectedItem().toString());

      LOG.fine(String.format("Connected to Port: %s", com.getPort()));

      updateChartLabels();
    }
    catch (CommunicationException ex)
    {
//      ex.printStackTrace(System.err);
      LOG.warning("could not connect", ex);
      showErrorMessage("Fehler beim verbinden",
                       "Fehler beim Verbinden.\n"
                       + "Möglicherweise falsches Gerät. Erneut mit anderem Gerät versuchen.");
    }
    catch (TimeoutException ex)
    {
      //LOG.severe("Timeout");
      showErrorMessage("Fehler beim verbinden",
                       "Fehler beim Verbinden.\n"
                       + "Keine Antwort vom Gerät bekommen (Timeout)!");
    }
    catch (Exception ex)
    {
      LOG.severe("Unsupported Exception", ex);
      showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
      ex.printStackTrace(System.err);
    }
    finally
    {
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    jDisconnect.setEnabled(com.isConnected());
    jConnect.setEnabled(!com.isConnected());
    jRefreshDevice.setEnabled(!com.isConnected());
    jRefresh.setEnabled(com.isConnected());
    jComboBoxPort.setEnabled(!com.isConnected());
    jStart.setEnabled(com.isConnected());

    if(com.isConnected())
    {
      jLabelStatus.setText("verbunden");
      jLabelStatus.setForeground(new Color(0, 130, 0));
    }
  }

  /**
   * Communicates with the Device and refreshes the Ecosystem.
   */
  private void refreshEco()
  {
    Runnable runRefresh = () ->
    {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      jRefresh.setEnabled(false);

      try
      {
        com.refreshEco();

        LOG.fine("got ecosystem");
        updateChartLabels();

      }
      catch (CommunicationException ex)
      {
        LOG.warning("could not refresh", ex);
        showErrorMessage("Fehler beim aktualisieren",
                         "Fehler beim aktualisieren.\n"
                         + "Möglicherweise falsches Gerät. Erneut oder mit anderem Gerät versuchen.\n\n"
                         + ex.getMessage());
      }
      catch (TimeoutException ex)
      {
        LOG.warning("Timeout");
        showErrorMessage("Fehler beim aktualisieren",
                         "Fehler beim aktualisieren.\n"
                         + "Keine Antwort vom Gerät bekommen (Timeout)!");
      }
      catch (IllegalStateException ex)
      {
        LOG.info(ex.getMessage());
      }
      catch (Exception ex)
      {
        LOG.severe("Unsupported Exception", ex);
        showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
      }
      finally
      {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if(com.isConnected())
          jRefresh.setEnabled(true);
      }

    };

    new Thread(runRefresh).start();

  }

  public void done(MeasurementWorker worker)
  {
    try
    {
      enableCancelling();
      if(worker.get() == null)
      {
        throw new CancellationException();
      }

      data.setMeasureList(worker.get());
      calculate();
      enableStarting();
    }
    catch (ExecutionException ex)
    {
      Throwable cause = ex.getCause();
      if(cause instanceof CommunicationException)
      {
        LOG.severe(cause.getMessage());
        showErrorMessage("Kommunikationsfehler", "Folgender Kommunikationsfehler ist aufgetreten: " + cause.getMessage());
      }
      else if(cause instanceof TimeoutException)
      {
        LOG.severe(cause.getMessage());
        showErrorMessage("Timeout", "µC antwortet nicht - Timeout");
      }
      else
      {
        LOG.severe("Unknown Exception: " + cause.getMessage());
        showErrorMessage("Unbekannter Fehler", "Ein unbekannter Fehler ist aufgetreten! " + cause.getMessage());
      }
    }
    catch (InterruptedException ex)
    {
    }
    catch (CancellationException ex)
    {
      LOG.info("Measurement aborted");
    }
    catch (Exception ex)
    {
      ex.printStackTrace(System.err);
      LOG.severe("Unknown Exception: " + ex);
      showErrorMessage("Unbekannter Fehler", "Ein unbekannter Fehler ist aufgetreten! " + ex);
    }

  }

  public static String getVERSION()
  {
    return VERSION;
  }

}
