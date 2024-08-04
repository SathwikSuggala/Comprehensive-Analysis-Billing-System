package com.sathwik.project;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class LineChartExample extends JFrame {

    public LineChartExample(String title) {
        super(title);

        // Create dataset
        DefaultCategoryDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createLineChart(
                "Person Data", // Chart title
                "Person",      // X-Axis label
                "Value",       // Y-Axis label
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Customize plot (change background color)
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE); // Set plot background color to white
        plot.setDomainGridlinesVisible(true); // Show domain gridlines (vertical)
        plot.setRangeGridlinesVisible(true);  // Show range gridlines (horizontal)

        // Create panel to display chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(Color.WHITE); // Set background color to white
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1000, "Value", "Person1");
        dataset.addValue(1500, "Value", "Person2");
        dataset.addValue(1250, "Value", "Person3");
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LineChartExample example = new LineChartExample("Line Chart Example");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
