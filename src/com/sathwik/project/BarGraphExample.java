package com.sathwik.project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class BarGraphExample {
    public static void main(String[] args) {
        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "Series 1", "Category 1");
        dataset.addValue(4.0, "Series 1", "Category 2");
        dataset.addValue(3.0, "Series 1", "Category 3");
        dataset.addValue(5.0, "Series 1", "Category 4");

        // Create a bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Bar Chart Example",    // chart title
                "Category",            // category axis label
                "Value",               // value axis label
                dataset,               // data
                PlotOrientation.VERTICAL,
                true,                  // include legend
                true,
                false);

        // Set plot background color to light gray
        barChart.getPlot().setBackgroundPaint(Color.LIGHT_GRAY);

        // Customize plot appearance
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.WHITE); // Set gridline color
        plot.setRangeGridlinesVisible(true);     // Show gridlines
        plot.getRenderer().setSeriesPaint(0, new Color(79, 129, 189)); // Set bar color

        // Add reference lines
        ValueMarker marker = new ValueMarker(3.5); // Example reference line at y=3.5
        marker.setPaint(Color.RED);
        marker.setStroke(new BasicStroke(2));
        plot.addRangeMarker(marker);

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        // Create a frame to hold the panel
        JFrame frame = new JFrame("Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
