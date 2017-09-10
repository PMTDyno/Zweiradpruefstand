package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Levin
 */
public class Diagram
{

  private JFreeChart chart;
  private ChartPanel chartPanel;
  private XYSeriesCollection dataset;
  
  public Diagram(String title, String xAxis, String yAxis, XYSeriesCollection dataset)
  {
    this.dataset = dataset;
    chart = ChartFactory.createXYLineChart(title,
                                           xAxis,
                                           yAxis,
                                           this.dataset,
                                           PlotOrientation.VERTICAL,
                                           true,
                                           true,
                                           false);
    
    XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
    r1.setSeriesPaint(0, Color.blue);
    r1.setSeriesShapesVisible(0, false);
    r1.setSeriesStroke(0, new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    chart.getXYPlot().setRenderer(r1);
    chartPanel = new ChartPanel(chart);
    
    chart.fireChartChanged();
  }
  
//  public JFreeChart getChart()
//  {
//    return chart;
//  }
  
  public ChartPanel getChartPanel()
  {
    return chartPanel;
  }
  
  public void updateDataset(XYSeries series)
  {
    dataset.removeAllSeries();
    dataset.addSeries(series);
    chart.fireChartChanged();
  }



 
    
    
    
    
    
    
    
    
    
}
