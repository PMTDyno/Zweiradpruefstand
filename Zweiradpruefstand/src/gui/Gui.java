package gui;

import data.Config;
import data.Data;
import measure.MeasurementWorker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
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
 * @version 0.8.5
 */
public class Gui extends javax.swing.JFrame
{

    private final ProgSetDialog progset;
    private final VehicleSetDialog vehicleset;
    private Communication com;
    private ChartPanel chartPanel;
    private final Data data = Data.getInstance();

    private static final Logger LOGP = Logger.getParentLogger();
    private static final Logger LOG = Logger.getLogger(Gui.class.getName());
    private static final java.util.logging.Level DEBUGLEVEL = java.util.logging.Level.ALL;

    private static final String VERSION = "0.9";

    private final Font font = new Font("sansserif", Font.BOLD, 15);
    private final ValueMarker maxPowerMarker = new ValueMarker(data.getMaxpower());
    private final ValueMarker maxTorqueMarker = new ValueMarker(data.getMaxtorque());

    private XYSeries seriesTorque = new XYSeries("Drehmoment [Nm]");
    private XYSeries seriesPower = new XYSeries("Leistung [PS]");
    private XYSeries series1 = new XYSeries("temp1 series");
    private XYSeries series2 = new XYSeries("temp2 series");
    private XYSeriesCollection dataset1 = new XYSeriesCollection();
    private XYSeriesCollection dataset2 = new XYSeriesCollection();

    private JFreeChart chart;

    /**
     * Creates the main GUI
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
        jLabelInfo.setText("<html>"
                + "<b>Version: </b>PMT Dyno v" + VERSION + "<br>"
                + "<b>Config Datei: </b>" + data.getFilePath() + "<br>"
                + "<b>System: </b>" + System.getProperty("os.name") + "<br>"
                + "<b>Benutzer: </b>" + System.getProperty("user.name") + "<br>"
                + "<b>Java: </b>" + System.getProperty("java.version") + "<br>"
                + "<b>JSSC: </b>" + jssc.SerialNativeInterface.getLibraryVersion() + "<br>"
                + "<b>JFreeChart: </b>" + org.jfree.chart.JFreeChart.INFO.getVersion() + "<br>"
        );
        jLabelVersion.setText("v" + VERSION);

        setIconImage(new ImageIcon(getClass().getResource("/icons/logo128.png")).getImage());

        setTitle("PMT-DYNO v" + VERSION);
        setMinimumSize(new Dimension(1000, 450));
        setSize(data.getWindowWidth(), data.getWindowHeight());
        setLocation(data.getWindowRelativeX(), data.getWindowRelativeY());

        progset = new ProgSetDialog(this, true);
        vehicleset = new VehicleSetDialog(this, true);

        //com = new PortSim();
        refreshPorts();

        initChart();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jFrameAbout = new javax.swing.JFrame();
        jPanelLogo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();
        jPanelInfo = new javax.swing.JPanel();
        jLabelDevelopers = new javax.swing.JLabel();
        jLabelInfo = new javax.swing.JLabel();
        jLabelWarning = new javax.swing.JLabel();
        jPanelInfo2 = new javax.swing.JPanel();
        jLabelDate = new javax.swing.JLabel();
        jLabelAuthor = new javax.swing.JLabel();
        jFrameGuide = new javax.swing.JFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMeasure = new javax.swing.JPanel();
        jLabelGuideMeasure = new javax.swing.JLabel();
        jPanelSettings = new javax.swing.JPanel();
        jLabelGuideSettings = new javax.swing.JLabel();
        jToolBar = new javax.swing.JToolBar();
        jStart = new javax.swing.JButton();
        jStop = new javax.swing.JButton();
        jCancel = new javax.swing.JButton();
        jRefresh = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jProgSet = new javax.swing.JButton();
        jVehicleSet = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jPrint = new javax.swing.JButton();
        jSave = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jPanSerial = new javax.swing.JPanel();
        jpanDevice = new javax.swing.JPanel();
        jComboBoxPort = new javax.swing.JComboBox<>();
        jpanEast = new javax.swing.JPanel();
        jpanSerialButtons = new javax.swing.JPanel();
        jbutConnect = new javax.swing.JButton();
        jbutDisconnect = new javax.swing.JButton();
        jbutRefreshDevice = new javax.swing.JButton();
        jLabelStatus = new javax.swing.JLabel();
        jChartPanel = new javax.swing.JPanel();
        jMenuBar = new javax.swing.JMenuBar();
        jFile = new javax.swing.JMenu();
        jMenuSave = new javax.swing.JMenuItem();
        jMenuPrint = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuSettings = new javax.swing.JMenuItem();
        jMenuVehicle = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuClose = new javax.swing.JMenuItem();
        jHelp = new javax.swing.JMenu();
        jMenuGuide = new javax.swing.JMenuItem();
        jMenuAbout = new javax.swing.JMenuItem();

        jFrameAbout.setTitle("Über...");
        jFrameAbout.setLocation(new java.awt.Point(0, 0));
        jFrameAbout.setMinimumSize(new java.awt.Dimension(500, 500));
        jFrameAbout.setResizable(false);

        jPanelLogo.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo128.png"))); // NOI18N
        jLabel2.setText(" PMT Dyno");
        jLabel2.setVerifyInputWhenFocusTarget(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanelLogo.add(jLabel2, gridBagConstraints);

        jLabelVersion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelVersion.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelVersion.setText("v");
        jLabelVersion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabelVersion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        jPanelLogo.add(jLabelVersion, gridBagConstraints);

        jFrameAbout.getContentPane().add(jPanelLogo, java.awt.BorderLayout.NORTH);

        jPanelInfo.setLayout(new java.awt.GridBagLayout());

        jLabelDevelopers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelDevelopers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDevelopers.setText("<html> \n<center> <b>Diplomanten: </b> <br>\n Primus Christoph - Elektrotechnik<br>\n Messing Levin - Programm<br> \nTinauer Robert - Mechanik<br> \n</center>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        jPanelInfo.add(jLabelDevelopers, gridBagConstraints);

        jLabelInfo.setBackground(new java.awt.Color(255, 255, 255));
        jLabelInfo.setText("<html> <b>Version:</b> PMT Dyno 0.5 <br> <b>Config Datei:</b> Benutzerpfad\\.PMTDyno\\PMTDyno.config <br> <b>JSSC:</b> 2.8.0 <br> <b>JFreeChart:</b> 1.0.19 <br> "); // NOI18N
        jLabelInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelInfo.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanelInfo.add(jLabelInfo, gridBagConstraints);

        jLabelWarning.setText("Die Nutzung der Anlage des Prüfstandes erfolgt auf eigene Gefahr!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanelInfo.add(jLabelWarning, gridBagConstraints);

        jFrameAbout.getContentPane().add(jPanelInfo, java.awt.BorderLayout.CENTER);

        jPanelInfo2.setLayout(new java.awt.GridLayout(1, 0));

        jLabelDate.setText("2016-2017");
        jLabelDate.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabelDateMouseClicked(evt);
            }
        });
        jPanelInfo2.add(jLabelDate);

        jLabelAuthor.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelAuthor.setText("Autor: Messing Levin");
        jPanelInfo2.add(jLabelAuthor);

        jFrameAbout.getContentPane().add(jPanelInfo2, java.awt.BorderLayout.SOUTH);

        jFrameGuide.setTitle("Anleitung");
        jFrameGuide.setMinimumSize(new java.awt.Dimension(700, 600));
        jFrameGuide.setResizable(false);

        jPanelMeasure.setLayout(new java.awt.GridLayout(1, 0));

        jLabelGuideMeasure.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGuideMeasure.setText("<html> <h2>   1. Motorrad auf den Prüfstand stellen und befestigen <br><br> 2. Prüfstand mit USB verbinden <br><br> 3. Klicken Sie auf <i>Verbinden</i> <br><br>  4. Starten Sie das Motorrad <br><br> 5. Klicken Sie auf <i>Start</i> <br><br> 6. Beschleunigen Sie mit Vollgas <br><br> 7. Bei maximaler Motordrehzahl klicken Sie auf <i>Stop</i><br> <br>  </h2>"); // NOI18N
        jPanelMeasure.add(jLabelGuideMeasure);

        jTabbedPane1.addTab("Messung", jPanelMeasure);

        jPanelSettings.setLayout(new java.awt.GridLayout(1, 0));

        jLabelGuideSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGuideSettings.setText("todo"); // NOI18N
        jPanelSettings.add(jLabelGuideSettings);

        jTabbedPane1.addTab("Einstellungen", jPanelSettings);

        jFrameGuide.getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(null);

        jToolBar.setRollover(true);

        jStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start48.png"))); // NOI18N
        jStart.setMnemonic('s');
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

        jStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop48.png"))); // NOI18N
        jStop.setToolTipText("Messung stoppen");
        jStop.setEnabled(false);
        jStop.setFocusable(false);
        jStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jStop.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jStopActionPerformed(evt);
            }
        });
        jToolBar.add(jStop);

        jCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel48.png"))); // NOI18N
        jCancel.setToolTipText("Messung abbrechen");
        jCancel.setEnabled(false);
        jCancel.setFocusable(false);
        jCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCancel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCancelActionPerformed(evt);
            }
        });
        jToolBar.add(jCancel);

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

        jVehicleSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/motorbike48.png"))); // NOI18N
        jVehicleSet.setToolTipText("Fahrzeugeinstellungen");
        jVehicleSet.setFocusable(false);
        jVehicleSet.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jVehicleSet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jVehicleSet.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jVehicleSetActionPerformed(evt);
            }
        });
        jToolBar.add(jVehicleSet);
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

        jpanDevice.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 5, 7, 1));
        jpanDevice.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jpanDevice.add(jComboBoxPort, gridBagConstraints);

        jPanSerial.add(jpanDevice, java.awt.BorderLayout.CENTER);

        jpanEast.setLayout(new java.awt.GridBagLayout());

        jpanSerialButtons.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        jbutConnect.setText("Verbinden");
        jbutConnect.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jbutConnect.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbutConnectActionPerformed(evt);
            }
        });
        jpanSerialButtons.add(jbutConnect);

        jbutDisconnect.setText("Trennen");
        jbutDisconnect.setEnabled(false);
        jbutDisconnect.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbutDisconnectActionPerformed(evt);
            }
        });
        jpanSerialButtons.add(jbutDisconnect);

        jbutRefreshDevice.setText("Aktualisieren");
        jbutRefreshDevice.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jbutRefreshDeviceActionPerformed(evt);
            }
        });
        jpanSerialButtons.add(jbutRefreshDevice);

        jpanEast.add(jpanSerialButtons, new java.awt.GridBagConstraints());

        jLabelStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelStatus.setForeground(java.awt.Color.gray);
        jLabelStatus.setText("getrennt");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jpanEast.add(jLabelStatus, gridBagConstraints);

        jPanSerial.add(jpanEast, java.awt.BorderLayout.EAST);

        jToolBar.add(jPanSerial);

        getContentPane().add(jToolBar, java.awt.BorderLayout.PAGE_START);

        jChartPanel.setLayout(new java.awt.GridLayout(1, 0));
        getContentPane().add(jChartPanel, java.awt.BorderLayout.CENTER);

        jFile.setText("Datei");

        jMenuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save16.png"))); // NOI18N
        jMenuSave.setText("Speichern...");
        jMenuSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuSaveActionPerformed(evt);
            }
        });
        jFile.add(jMenuSave);

        jMenuPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer16.png"))); // NOI18N
        jMenuPrint.setText("Drucken...");
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

        jMenuVehicle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/motorbike16.png"))); // NOI18N
        jMenuVehicle.setText("Fahrzeug...");
        jMenuVehicle.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuVehicleActionPerformed(evt);
            }
        });
        jFile.add(jMenuVehicle);
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

        jMenuGuide.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
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
        jMenuAbout.setText("Über...");
        jMenuAbout.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuAboutActionPerformed(evt);
            }
        });
        jHelp.add(jMenuAbout);

        jMenuBar.add(jHelp);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPrintActionPerformed
        print();
    }//GEN-LAST:event_jMenuPrintActionPerformed

    private void jStartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jStartActionPerformed
    {//GEN-HEADEREND:event_jStartActionPerformed
        start();
        jStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/loading48.gif")));
        jStart.setEnabled(false);
        jStop.setEnabled(true);
        jCancel.setEnabled(true);
        jRefresh.setEnabled(false);
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

    private void jVehicleSetActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jVehicleSetActionPerformed
    {//GEN-HEADEREND:event_jVehicleSetActionPerformed
        startVehicleSet();
    }//GEN-LAST:event_jVehicleSetActionPerformed

    private void jbutConnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutConnectActionPerformed
    {//GEN-HEADEREND:event_jbutConnectActionPerformed
        connectDevice();
    }//GEN-LAST:event_jbutConnectActionPerformed

    private void jbutDisconnectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutDisconnectActionPerformed
    {//GEN-HEADEREND:event_jbutDisconnectActionPerformed
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
            jStop.setEnabled(false);
            jCancel.setEnabled(false);
            jRefresh.setEnabled(com.isConnected());
            jbutConnect.setEnabled(!com.isConnected());
            jbutDisconnect.setEnabled(com.isConnected());
            jbutRefreshDevice.setEnabled(!com.isConnected());
            jComboBoxPort.setEnabled(!com.isConnected());
        }
    }//GEN-LAST:event_jbutDisconnectActionPerformed

    private void jbutRefreshDeviceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbutRefreshDeviceActionPerformed
    {//GEN-HEADEREND:event_jbutRefreshDeviceActionPerformed
        refreshPorts();
    }//GEN-LAST:event_jbutRefreshDeviceActionPerformed

    private void jStopActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jStopActionPerformed
    {//GEN-HEADEREND:event_jStopActionPerformed
        jStop.setEnabled(false);
        jCancel.setEnabled(false);
        com.stopWorker();
    }//GEN-LAST:event_jStopActionPerformed

    private void jCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCancelActionPerformed
    {//GEN-HEADEREND:event_jCancelActionPerformed
        if(JOptionPane.showConfirmDialog(this, "Sind Sie sicher?", "Messung abbrechen", JOptionPane.YES_NO_OPTION) == 0)
        {
            com.cancelWorker();
            jStop.setEnabled(false);
            jCancel.setEnabled(false);
        }
    }//GEN-LAST:event_jCancelActionPerformed

    private void jMenuSettingsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuSettingsActionPerformed
    {//GEN-HEADEREND:event_jMenuSettingsActionPerformed
        startProgSet();
    }//GEN-LAST:event_jMenuSettingsActionPerformed

    private void jMenuSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuSaveActionPerformed
    {//GEN-HEADEREND:event_jMenuSaveActionPerformed
        savePng();
    }//GEN-LAST:event_jMenuSaveActionPerformed

    private void jMenuVehicleActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuVehicleActionPerformed
    {//GEN-HEADEREND:event_jMenuVehicleActionPerformed
        startVehicleSet();
    }//GEN-LAST:event_jMenuVehicleActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuAboutActionPerformed
    {//GEN-HEADEREND:event_jMenuAboutActionPerformed
        jFrameAbout.setLocationRelativeTo(this);
        jFrameAbout.setVisible(true);
    }//GEN-LAST:event_jMenuAboutActionPerformed

    private void jMenuGuideActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuGuideActionPerformed
    {//GEN-HEADEREND:event_jMenuGuideActionPerformed
        jFrameGuide.setLocationRelativeTo(this);
        jFrameGuide.setVisible(true);
    }//GEN-LAST:event_jMenuGuideActionPerformed

    private void jRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRefreshActionPerformed
        refreshEco();
    }//GEN-LAST:event_jRefreshActionPerformed

    private void jMenuCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuCloseActionPerformed
    {//GEN-HEADEREND:event_jMenuCloseActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuCloseActionPerformed

    int easterEgg = 0;
    private void jLabelDateMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelDateMouseClicked
    {//GEN-HEADEREND:event_jLabelDateMouseClicked
        easterEgg++;
        if(easterEgg >= 6)
        {
            System.out.println("TRIGGERED");
            System.out.println("TRIGGERED");
            System.out.println("TRIGGERED");
            LOG.info("TRIGGERED");
            LOG.info("TRIGGERED");
            LOG.info("TRIGGERED");
            this.showErrorMessage("TRIGGERED", "PRIMUS IS THE ABSOLUTE WORST!!!!111!!elf!!1eins");
            this.showErrorMessage("TRIGGERED", "PRIMUS IS THE ABSOLUTE WORST!!!!111!!elf!!1eins");
            this.showErrorMessage("TRIGGERED", "PRIMUS IS THE ABSOLUTE WORST!!!!111!!elf!!1eins");
            this.
            easterEgg = 0;
        }
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
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        /* Create and display the form */
        LOGP.addHandler(new logging.LogOutputStreamHandler(System.out));
        LOG.setLevel(DEBUGLEVEL);
        try
        {
            String opt = System.getProperty("DebugLevel");
            if(opt != null)
                LOG.setLevel(Level.parse(opt));
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
    private javax.swing.JButton jCancel;
    private javax.swing.JPanel jChartPanel;
    private javax.swing.JComboBox<String> jComboBoxPort;
    private javax.swing.JMenu jFile;
    private javax.swing.JFrame jFrameAbout;
    private javax.swing.JFrame jFrameGuide;
    private javax.swing.JMenu jHelp;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelAuthor;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelDevelopers;
    private javax.swing.JLabel jLabelGuideMeasure;
    private javax.swing.JLabel jLabelGuideSettings;
    private javax.swing.JLabel jLabelInfo;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JLabel jLabelWarning;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuClose;
    private javax.swing.JMenuItem jMenuGuide;
    private javax.swing.JMenuItem jMenuPrint;
    private javax.swing.JMenuItem jMenuSave;
    private javax.swing.JMenuItem jMenuSettings;
    private javax.swing.JMenuItem jMenuVehicle;
    private javax.swing.JPanel jPanSerial;
    private javax.swing.JPanel jPanelInfo;
    private javax.swing.JPanel jPanelInfo2;
    private javax.swing.JPanel jPanelLogo;
    private javax.swing.JPanel jPanelMeasure;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JButton jPrint;
    private javax.swing.JButton jProgSet;
    private javax.swing.JButton jRefresh;
    private javax.swing.JButton jSave;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JButton jStart;
    private javax.swing.JButton jStop;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar;
    private javax.swing.JButton jVehicleSet;
    private javax.swing.JButton jbutConnect;
    private javax.swing.JButton jbutDisconnect;
    private javax.swing.JButton jbutRefreshDevice;
    private javax.swing.JPanel jpanDevice;
    private javax.swing.JPanel jpanEast;
    private javax.swing.JPanel jpanSerialButtons;
    // End of variables declaration//GEN-END:variables

    /*---PUBLIC METHODS------------------------------------------*/
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
        }
        catch (Exception ex)
        {
            LOG.warning("Error saving Config file");
            showErrorMessage("Fehler aufgetreten!", ex.getMessage() + "\n\n" + ex.getCause().toString());
        }
        finally
        {
            LOG.info("End of Application");
            System.exit(0);
        }
    }

    /**
     * Checks if the worker is done and if an error occured.<br>
     * Then sets the worker to <code>null</code> <br>
     * Calculates the measurement and updates the chart.
     *
     * @param w The Worker
     */
    public void measurementDone(MeasurementWorker w)
    {
        try
        {
            if(w != null)
                w.get(); //warten bis worker fertig ist
        }
        catch (InterruptedException | ExecutionException ex)
        {
            LOG.severe("Measurement failed", ex);
            showErrorMessage("Fehler bei Messung", "Fehler bei Messung aufgetreten.\n"
                             + "Messung wiederholen.");
        }
        catch (CancellationException ex)
        {
        }
        catch (Exception ex)
        {
            LOG.severe("Unsupported Exception", ex);
            showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
        }
        finally
        {
            com.setWorker(null);
            if(com.isSuccess())
            {
                calculate();
                updateSeriesPower();
                updateSeriesTorque();

                updateChartLabels();

                jStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start48.png")));
                jPrint.setEnabled(true);
                jSave.setEnabled(true);
                jStart.setEnabled(true);
                jRefresh.setEnabled(true);
                LOG.finer("powerchart updated");
            }
            else
            {

                jStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/start48.png")));
                jStop.setEnabled(false);
                jCancel.setEnabled(false);
                jStart.setEnabled(true);
                jRefresh.setEnabled(true);
                LOG.warning("measurement aborted!");
            }
        }
    }

    /**
     *
     * @return the maximum elements of measure data
     */
    public int getMaxElements()
    {
        return (data.getMaxMeasureTimeSec() * 1000) / (data.getPeriodTimeMs());
    }

    /**
     * Enables the Cancelling Buttons if the boolean is true
     *
     * @param b boolean
     */
    public void setCancellingEnabled(boolean b)
    {
        jStop.setEnabled(b);
        jCancel.setEnabled(b);
    }

    /**
     * Shows an error message relative to the main GUI
     *
     * @param title   The Title of the Frame
     * @param message The displayed message
     */
    public final void showErrorMessage(String title, String message)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /*---PRIVATE METHODS----------------------------------------*/
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

        seriesPower.setKey("Leistung " + "[" + data.getPowerunit() + "]");

        chart.getXYPlot().setDataset(1, dataset2);
        chart.getXYPlot().setRangeAxis(1, torqueAxis);
        chart.getXYPlot().mapDatasetToRangeAxis(0, 0);//1st dataset to 1st y-axis
        chart.getXYPlot().mapDatasetToRangeAxis(1, 1); //2nd dataset to 2nd y-axis

        // Hinzufuegen von series1 zu der Datenmenge dataset
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

        //jchartframe.setContentPane(chartPanel);
        //jchartframe.pack();
        //jchartframe.setVisible(true);
    }

    /**
     * Starts the measurement
     */
    private void start()
    {
        com.start(this);
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
     * Opens up the ProgSetDialog and saves the changes values. If the
     * measurement is already done the datasets will be updated.
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
            if(data.getMaxMeasureTimeSec() != progset.getMaxMeasureTimeSec())
            {
                data.setMaxMeasureTimeSec(progset.getMaxMeasureTimeSec());
            }

            //update
            if(com.isSuccess())
            {
                switch (updateLevel) //don't insert break!
                {
                    case 3:
                        LOG.finest("entering level 3");
                        data.setPower(null);
                        data.setTorque(null);
                        calculate();
                        series1.clear();
                        series2.clear();
                        for(int i = 0; i < data.getPower().length; i++)
                        {
                            series1.add(data.getMotorRpm()[i], data.getPower()[i]);
                            series2.add(data.getMotorRpm()[i], data.getTorque()[i]);
                        }

                    case 2:
                        LOG.finest("entering level 2");
                        if(updateLevel == 3 && data.getPowerunit().equals("PS"))
                            convertToPs(series1);
                        else if(updateLevel == 2 && data.getPowerunit().equals("kW"))
                            convertToKw(series1);
                        else if(data.getPowerunit().endsWith("PS"))
                            convertToPs(series1);

                    case 1:
                        LOG.finest("entering level 1");
                        dataset1.removeSeries(seriesPower);
                        seriesPower = correctByFactor(series1, data.getCorrectionPower());
                        dataset1.addSeries(seriesPower);

                        dataset2.removeSeries(seriesTorque);
                        seriesTorque = correctByFactor(series2, data.getCorrectionTorque());
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
     */
    private void startVehicleSet()
    {
        vehicleset.setLocationRelativeTo(this);
        vehicleset.setVisible(true);

        //Einstellungen übernehmen
        if(vehicleset.isSettingsChanged())
        {
            //TAKT
            if(data.isTwoStroke() != vehicleset.isTwoStroke())
            {
                data.setTwoStroke(vehicleset.isTwoStroke());
            }

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

        }

    }

    /**
     * refreshes the availablePorts Array and puts them into the ComboBox
     */
    private void refreshPorts()
    {
        if(Communication.getAvailablePorts() == null || Communication.getAvailablePorts().length == 0)
        {
            jbutConnect.setEnabled(false);
        }
        else
            jbutConnect.setEnabled(true);

        jComboBoxPort.removeAllItems();
        for(String availablePort : Communication.getAvailablePorts())
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
    private XYSeries correctByFactor(XYSeries series, double factor)
    {

        try
        {
            if(factor <= 0)
            {
                LOG.severe(new InputMismatchException("factor smaller or equal to 0!"));
                showErrorMessage("Fehler bei Konvertierung", "Interner Fehler aufgetreten\n"
                                 + "Bitte sicherstellen dass ein Korrekturfaktor zwischen 0,5 und 2,0 eingestellt ist.\n"
                                 + "Ist dies der Fall bitte Kontakt mit dem Programmierer(Levin Messing) aufnehmen.");
            }
            XYSeries target = series;

            int max = target.getItemCount();

            for(int i = 0; i < max; i++)
            {
                double tmp = (double) series.getY(i);
                target.updateByIndex(i, tmp * factor);
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
        seriesPower.setKey("Leistung [" + data.getPowerunit() + "]");
    }

    /**
     * converts motorRpm field to two stroke. divides by 2
     */
    private void convertToTwoStroke()
    {
        for(int i = 0; i < data.getMotorRpm().length; i++)
        {
            data.getMotorRpm()[i] = data.getMotorRpm()[i] / 2;
        }
    }

    /**
     * converts motorRpm field to four stroke. multiplies with 2
     */
    private void convertToFourStroke()
    {
        for(int i = 0; i < data.getMotorRpm().length; i++)
        {
            data.getMotorRpm()[i] = data.getMotorRpm()[i] * 2;
        }
    }

    /**
     * calculates torque and power with wheelRpm
     */
    private void calculate()
    {
        //Winkelgeschw. = (Pi/180) * Umdr.
        //Winkelbeschl. = Winkelgeschw. / Zeit
        //Drehmoment = Winkelbeschl. * J(kgm^2)
        //Leistung = 2*Pi*Drehmoment*Umdr.
        int length = data.getWheelRpm().length;

        double angularspeed[] = new double[length];
        double diff[] = new double[length - 1];
        double angularacc[] = new double[length - 1];
        double inertia = data.getInertia();
        double[] torque = new double[length - 1];
        double[] power = new double[length - 1];
        double vmax=0;
        int periodTimeMs = data.getPeriodTimeMs();
        int[] wheelRpm = data.getWheelRpm();

//        if(!data.isTwoStroke()) //if 4 stroke
//        {
//            convertToFourStroke();
//        }
        for(int i = 0; i < length; i++)
        {
            angularspeed[i] = (wheelRpm[i]/60) * (2.0*Math.PI);
        }
        
        for(int i = 0; i < length - 1; i++)
        {
            diff[i] = (angularspeed[i + 1]) - angularspeed[i];
            if (angularspeed[i+1]>angularspeed[i])
            {
                vmax=angularspeed[i+1]*0.175*3.6;
            }
        }

        for(int i = 0; i < length - 1; i++)
        {
            angularacc[i] = diff[i] / (periodTimeMs / 1000.0);
        }

        for(int i = 0; i < length - 1; i++)
        {
            torque[i] = angularacc[i] * inertia;
        }

        for(int i = 0; i < length - 1; i++)
        {
            power[i] = (angularspeed[i] * torque[i]);
            //System.out.println(i + " " + power[i]);
        }

        data.setTorque(torque);
        data.setPower(power);
        
        data.setVmax(vmax);
    }

    /**
     * Clears series1 <br>
     * Writes series1 <br>
     * Checks and converts to PS/kW Corrects by factor and refreshes diagram<br>
     * Sets the seriesPower Key(for powerunit changes)
     */
    private void updateSeriesPower()
    {
        series1.clear();
        for(int i = 0; i < data.getPower().length; i++)
        {
            series1.add(data.getMotorRpm()[i], data.getPower()[i]);
        }
        if(data.getPowerunit().equals("PS"))
            convertToPs(series1);

        dataset1.removeSeries(seriesPower);
        seriesPower = correctByFactor(series1, data.getCorrectionPower());
        dataset1.addSeries(seriesPower);
        seriesPower.setKey("Leistung [" + data.getPowerunit() + "]");
        chart.getXYPlot().getRangeAxis().setLabel("Leistung [" + data.getPowerunit() + "]");

    }

    /**
     * Clears seriesTorque <br>
     * Writes seriesTorque <br>
     * Corrects by factor and refreshes diagram
     */
    private void updateSeriesTorque()
    {
        series2.clear();
        for(int i = 0; i < data.getTorque().length; i++)
        {
            series2.add(data.getMotorRpm()[i], data.getTorque()[i]);
        }
        dataset2.removeSeries(seriesTorque);
        seriesTorque = correctByFactor(series2, data.getCorrectionTorque());
        seriesTorque.setKey("Drehmoment [Nm]");
        dataset2.addSeries(seriesTorque);
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
            com = new Communication(jComboBoxPort.getSelectedItem().toString());schueler.conf

            LOG.fine(String.format("Connected to Port: %s", com.getPort()));

            updateChartLabels();
        }
        catch (CommunicationException ex)
        {
            //ex.printStackTrace(System.err);
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

        jbutDisconnect.setEnabled(Communication.isConnected());
        jbutConnect.setEnabled(!Communication.isConnected());
        jbutRefreshDevice.setEnabled(!Communication.isConnected());
        jRefresh.setEnabled(Communication.isConnected());
        jComboBoxPort.setEnabled(!Communication.isConnected());
        jStart.setEnabled(Communication.isConnected());

        if(Communication.isConnected())
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
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
                             + "Möglicherweise falsches Gerät. Erneut oder mit anderem Gerät versuchen.");
        }
        catch (TimeoutException ex)
        {
            LOG.warning("timeout");
            showErrorMessage("Fehler beim aktualisieren",
                             "Fehler beim aktualisieren.\n"
                             + "Keine Antwort vom Gerät bekommen (Timeout)!");
        }
        catch (Exception ex)
        {
            LOG.severe("Unsupported Exception", ex);
            showErrorMessage("Unbekannter Fehler", "Es ist ein unbekannter Fehler aufgetreten.\n" + ex.toString());
        }
        finally
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

}