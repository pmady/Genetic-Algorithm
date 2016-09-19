package src;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;

import java.io.File;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.PublicCloneable;


public class Graph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String sequence;
	public File imageFile = new File("XYGraph.png");
	public JFreeChart chart = null;
	public String chartTitle = "Genetic Algorithm";
	int[] HPModel;
	int txtProteinLength;
	GeneType p;
	public Graph(GeneType p,int[] HPModel,int proteinLength,String proteinStructure,String title) {
		super("Graph");		
		sequence = proteinStructure;
		chartTitle = title;		
		this.p = p;
		this.txtProteinLength = proteinLength;
		this.HPModel = HPModel;
		JPanel chartPanel = createChartPanel();
		add(chartPanel, BorderLayout.CENTER);		
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private JPanel createChartPanel() {
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		XYDataset dataset = createDataset();		
		chart = ChartFactory.createXYLineChart(chartTitle,xAxisLabel, yAxisLabel, dataset);
		
		XYPlot plot = chart.getXYPlot();
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		xAxis.setTickUnit(new NumberTickUnit(1));

		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setTickUnit(new NumberTickUnit(1));
		
		customizeChart(chart);	
		return new ChartPanel(chart);
	}

	private XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Fold", false);
		for(int i=1; i<=HPModel.length; i++){
			series1.add(p.X[i], p.Y[i]);
		}
		dataset.addSeries(series1);
		return dataset;
	}
	
	public class LegendXYItemLabelGenerator extends StandardXYItemLabelGenerator
    implements XYItemLabelGenerator, Cloneable, PublicCloneable,
    Serializable {
		private LegendItemCollection legendItems;
		
		public LegendXYItemLabelGenerator(LegendItemCollection legendItems) {
		    super();
		    this.legendItems = legendItems;
		}
		
		@Override
		public String generateLabel(XYDataset dataset, int series, int item) {
		    LegendItem legendItem = legendItems.get(series);
		    return ""+sequence.charAt(item);
		}
	}
	private void customizeChart(JFreeChart chart) {
		
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelGenerator(new LegendXYItemLabelGenerator(plot.getLegendItems()));
		plot.setRenderer(renderer);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelPaint(Color.BLACK);
		renderer.setSeriesPaint(0, Color.GREEN);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		plot.setOutlinePaint(null);
		plot.setOutlineStroke(new BasicStroke(2.0f));		
		plot.setRenderer(renderer);		
		plot.setBackgroundPaint(Color.WHITE);		
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.RED);		
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLUE);
		
	}

    /**
     * @param p
     * @param HPModel
     * @param txtProteinLength
     * @param txtProteinStructure
     * @param title
     */
    public void refresh(final GeneType p,final int[] HPModel,final int txtProteinLength,final String txtProteinStructure, String title) {
		sequence = txtProteinStructure;
		chart.getXYPlot().setDataset(createDataset());
		chart.setTitle(title);
	}
}