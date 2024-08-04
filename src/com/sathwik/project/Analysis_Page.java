package com.sathwik.project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class analysis_frame extends JFrame implements ActionListener {

    JLabel timeLabel, dateLabel, emptyLabel;
    
    Timer timer;
    
    JMenuBar menu_bar;
    
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    
    JPanel home_top_panel;
    
    ChartPanel Panel1,Panel2,Panel3,Panel4,Panel5;
    
    JFreeChart barchart1,barchart2,piechart1,piechart2,linechart;
    
    DefaultCategoryDataset barchart1_dataset,barchart2_dataset,linechart_dataset;
    
    DefaultPieDataset piechart1_dataset,piechart2_dataset;
    
    analysis_frame(){
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        
        setBounds(0, 0, sw, sh);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        Font mainFont = new Font("SansSerif", Font.PLAIN, 30);
        Font table_font = new Font("SansSerif", Font.PLAIN, 15);
        c.setBackground(Color.WHITE);
        
        

        // menu bar creation
        menu_bar = new JMenuBar();
        Font menuFont = new Font("SansSerif", Font.PLAIN, 20); // Adjust font size as needed
        UIManager.put("Menu.font", menuFont);

        // menu bar elements
        Home = new JMenu("Home  ");
        Analysis = new JMenu("Analysis  ");
        Products = new JMenu("Products  ");
        Inlets = new JMenu("Inlets  ");
        Outlets = new JMenu("Outlets  ");
        Bills = new JMenu("Bills  ");

// adding menu items to menu bar
        menu_bar.add(Home);
        menu_bar.add(Analysis);
        menu_bar.add(Products);
        menu_bar.add(Inlets);
        menu_bar.add(Outlets);
        menu_bar.add(Bills);
        setJMenuBar(menu_bar);
        Home.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analysis_frame.this.dispose();
                new Frame();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        Inlets.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analysis_frame.this.dispose();
                new AddInletsFrame();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        Outlets.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analysis_frame.this.dispose();
                new AddOutletFrame();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        Products.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analysis_frame.this.dispose();
                new ExistingProductsFrame();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        Analysis.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analysis_frame.this.dispose();
                new analysis_frame();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        Bills.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                analysis_frame.this.dispose();
                new NewBillFrame();
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

//Adding red colour to top
        home_top_panel = new JPanel(new BorderLayout()); // Use BorderLayout
        home_top_panel.setBounds(0, 0, sw, (int)(sh*0.06));
        Color lightRed = new Color(255, 95, 85);
        home_top_panel.setBackground(lightRed);
        c.add(home_top_panel);

        home_top_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

//Barchart1
        barchart1_dataset = new DefaultCategoryDataset();
        barchart1_dataset=DataBase.top10OutletsSales();
//        barchart1_dataset.addValue(1.0, "Series 1", "Category 1");
//        barchart1_dataset.addValue(4.0, "Series 1", "Category 2");
//        barchart1_dataset.addValue(3.0, "Series 1", "Category 3");
//        barchart1_dataset.addValue(5.0, "Series 1", "Category 4");

        barchart1=ChartFactory.createBarChart(
                "Top 10 outlets by sales of this month",    // chart title
                "Category",            // category axis label
                "Value",               // value axis label
                barchart1_dataset,               // data
                PlotOrientation.VERTICAL,
                true,                  // include legend
                true,
                false);
        barchart1.getPlot().setBackgroundPaint(Color.WHITE);
//panel for barchart1;
        Panel1=new ChartPanel(barchart1);
        Panel1.setBackground(Color.WHITE);
        Panel1.setBounds((int)(sw*0.01),(int)(sh*0.06),(int)(sw*0.45),(int)(sh*0.42));
        //Panel1.add(barchart1);
        c.add(Panel1);


//Barchart 2
        barchart2_dataset = new DefaultCategoryDataset();
        barchart2_dataset=DataBase.last7DaysAnalysis();
//        barchart2_dataset.addValue(1.0, "Series 1", "Category 1");
//        barchart2_dataset.addValue(4.0, "Series 1", "Category 2");
//        barchart2_dataset.addValue(3.0, "Series 1", "Category 3");
//        barchart2_dataset.addValue(5.0, "Series 1", "Category 4");

        barchart2=ChartFactory.createBarChart(
                "Last 7 Days Sales",    // chart title
                "Category",            // category axis label
                "Value",               // value axis label
                barchart2_dataset,               // data
                PlotOrientation.VERTICAL,
                true,                  // include legend
                true,
                false);
        barchart2.getPlot().setBackgroundPaint(Color.WHITE);
//panel for barchart 2;
        Panel2=new ChartPanel(barchart2);
        Panel2.setBackground(Color.WHITE);
        Panel2.setBounds((int)(sw*0.01),(int)(sh*0.48),(int)(sw*0.45),(int)(sh*0.42));
        //Panel1.add(barchart1);
        c.add(Panel2);


//pie chart 1
        piechart1_dataset = new DefaultPieDataset();
        piechart1_dataset = DataBase.last6MonthsSales();
//        piechart1_dataset.setValue("Category 1", 30);
//        piechart1_dataset.setValue("Category 2", 40);
//        piechart1_dataset.setValue("Category 3", 20);
//        piechart1_dataset.setValue("Category 4", 10);


        piechart1=ChartFactory.createPieChart(
                "Monthly sales",
                piechart1_dataset,
                true,   // Legend
                true,   // Tooltips
                false   // URLs
                );

        piechart1.getPlot().setBackgroundPaint(Color.WHITE);
//panel for pie chart 1;
        Panel3=new ChartPanel(piechart1);
        Panel3.setBackground(Color.WHITE);
        Panel3.setBounds((int)(sw*0.5),(int)(sh*0.06),(int)(sw*0.23),(int)(sh*0.4));
        //Panel1.add(barchart1);
        c.add(Panel3);


//pie chart 2
        piechart2_dataset = new DefaultPieDataset();
        Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "1234");
			Statement stmt = con.createStatement();
			ResultSet res1= stmt.executeQuery("select count(shopname) from outlets");
			while(res1.next()) {
				int noOutlets=res1.getInt(1);
				piechart2_dataset.setValue("Outlets", noOutlets);
			}
			ResultSet res2= stmt.executeQuery("select count(inletname) from inlets");
			while(res2.next()) {
				int noInlets=res2.getInt(1);
				piechart2_dataset.setValue("Inlets", noInlets);
			}
			
			
			
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //piechart2_dataset = DataBase.addressWiseSales();
        



        piechart2=ChartFactory.createPieChart(
                "Inlets VS Outlets",
                piechart2_dataset,
                true,   // Legend
                true,   // Tooltips
                false   // URLs
        );

        piechart2.getPlot().setBackgroundPaint(Color.WHITE);
//panel for pie chart 2;
        Panel4=new ChartPanel(piechart2);
        Panel4.setBackground(Color.WHITE);
        Panel4.setBounds((int)(sw*0.75),(int)(sh*0.06),(int)(sw*0.23),(int)(sh*0.4));
        //Panel1.add(barchart1);
        c.add(Panel4);

//line chart
        DefaultCategoryDataset linechart_dataset = new DefaultCategoryDataset();
        linechart_dataset = DataBase.addressWiseSales();

        // Create chart
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Address wise sales", // Chart title
                "Person",      // X-Axis label
                "Value",       // Y-Axis label
                linechart_dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Customize plot (change background color)
        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE); // Set plot background color to white
        plot.setDomainGridlinesVisible(true); // Show domain gridlines (vertical)
        plot.setRangeGridlinesVisible(true);  // Show range gridlines (horizontal)

        // Customize renderer to show lines for each point on the y-axis
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true); // Show lines for series 0 (the only series in this case)
        plot.setRenderer(renderer);

//panel for line chart
        Panel5=new ChartPanel(lineChart);
        Panel5.setBackground(Color.WHITE);
        Panel5.setBounds((int)(sw*0.5),(int)(sh*0.47),(int)(sw*0.48),(int)(sh*0.42));
        //Panel1.add(barchart1);
        
        c.add(Panel5);


//Adding time	
        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(mainFont);
        home_top_panel.add(timeLabel); // Add without specifying the position

// Add empty space between time label and date label
        emptyLabel = new JLabel();
        emptyLabel.setPreferredSize(new Dimension((int)(sw*0.8), 1)); // Adjust width as needed
        home_top_panel.add(emptyLabel);

        dateLabel = new JLabel("00/00/00");
        dateLabel.setFont(mainFont);
        home_top_panel.add(dateLabel); // Add without specifying the position


        // Create and start timer to update time label
        timer = new Timer(1000, this);
        timer.start();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    updateTime();
    }
    private void updateTime() {
        // Get current time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String time = timeFormat.format(cal.getTime());

        // Get current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(cal.getTime());

        // Update labels
        timeLabel.setText(time);
        dateLabel.setText(date);
    }
}
public class Analysis_Page {
    public static void main(String[] args) {
        new analysis_frame();
    }
}
