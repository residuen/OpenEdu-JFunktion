package de.openedu.jfunktion.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// Benutztes Beispiel: http://www.java2s.com/Code/Java/Chart/JFreeChartLineChartDemo1.htm
public class EquationPlotPanel extends JPanel  
{
	public static int EMPTY = 0;
	public static int EQUATION = 1;
	public static int DERIVATE = 2;	
	
	private XYPlot plot = null;
	private XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	private XYSeries series1 = null;
	private XYSeries series2 = null;
	private XYDataset xyDataset = null;
    private JFreeChart chart = null;
    private ChartPanel chartPanel = null;

 	public EquationPlotPanel()
	{
 		createChart(EMPTY, "x^2");
    }
	
	private void initChartPanel()
	{
		setBackground(Color.WHITE);
		setLayout(new GridLayout(1, 1));
	}
    
    /**
     * Creates the dataset.
     * 
     * @return a sample dataset.
     */
    private XYDataset createDataset(int seriesId, String legendTxt) {

		series1 = new XYSeries("f(x)"); // legendTxt);
		series2 = new XYSeries("f(x)'"); //dy/dx "+legendTxt);
    	
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        
        return dataset;        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private void createChart(int seriesId, String legendTxt) {
        
    	xyDataset = createDataset(EMPTY, legendTxt);
    	
        // create the chart...
        chart = ChartFactory.createXYLineChart(
            "",      // chart title	"Line Chart Demo 6"
            "X-Achse",					// x axis label
            "Y-Achse",					// y axis label
            xyDataset,                  // data
            PlotOrientation.VERTICAL,
            true,						// include legend
            true,						// tooltips
            false						// urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
//        final XYPlot plot = chart.getXYPlot();
        plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
//        plot.getDomainAxis().setLowerMargin(0.0);
//        plot.getDomainAxis().setUpperMargin(0.0);

        
//        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);	// renderer.setSeriesLinesVisible(1, false);
//        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        removeAll();
        
        chartPanel = new ChartPanel(chart);
        
        initChartPanel();
        
        add(chartPanel);
        
        revalidate();        
    }
    
    public XYSeries getSeries1() {
		return series1;
	}
    
    public XYSeries getSeries2() {
		return series2;
	}
    
    public void updateSeries(int seriesId, double[] x, double[] y,double[] x_Derivate, double[] y_Derivate, String description)
    {
//    	createChart(seriesId, description);
     	
//		System.out.println("x.length="+x.length + " - y.length=" + y.length);
    	
    	series1.clear();
    	series2.clear();
    	
    	double fx;
//    	boolean edge = false;
    	
    	if(y != null) {
    		
    		for(int i=0, n=Math.min(x.length, y.length); i<n; i++)
				series1.add(x[i], y[i]);
	
	    	if(seriesId == DERIVATE)
		    	for(int i=0, n=Math.min(x_Derivate.length, y_Derivate.length); i<n; i++) 
					series2.add(x_Derivate[i], (double)y_Derivate[i]);
    	}
    }

}