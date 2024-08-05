package com.sathwik.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.category.DefaultCategoryDataset;

class Frame extends JFrame implements ActionListener {
    JMenuBar menu_bar;
    
    JTextField Today_Total_Orders_Count,Today_Total_Orders_Worth_Value;
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    
    JLabel Today_Total_Orders,Today_Total_Orders_Worth, Today_Buyer, timeLabel, dateLabel;
    
    DefaultTableModel Today_Buyer_tableModel, Today_Product_tableModel;
    
    JTable Today_Buyer_table, Today_Product_table;
    
    JPanel Today_Buyer_Table_Panel, Today_Product_Table_Panel, home_top_panel;
    
    ChartPanel home_graph_panel;
    
    JScrollPane Today_Buyer_Table_scrollPane, Today_Product_Table_scrollPane;
    
    //graphs data sets like arraylist
    DefaultCategoryDataset last_7_days_data;
    
    JFreeChart home_chart;
    
    Timer timer;

    Frame() {
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
        Home.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Frame.this.dispose();
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
                Frame.this.dispose();
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
                Frame.this.dispose();
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
                Frame.this.dispose();
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
                Frame.this.dispose();
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
                Frame.this.dispose();
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

        setJMenuBar(menu_bar);

        // HomeScreen
        //top panel for data and time
        home_top_panel = new JPanel(new BorderLayout()); // Use BorderLayout
        home_top_panel.setBounds((int)(sw*0),(int)(sh*0),(int)(sw*1),(int)(sh*0.06));
        Color lightRed = new Color(255, 95, 85);
        home_top_panel.setBackground(lightRed);
        c.add(home_top_panel);

        // Creating
        Today_Total_Orders = new JLabel("Today's total orders");
        Today_Total_Orders.setBounds((int)(sw*0.03),(int)(sh*0.15),(int)(sw*0.20),(int)(sh*0.05));
        Today_Total_Orders.setFont(mainFont);
        c.add(Today_Total_Orders);
        
        Today_Total_Orders_Count = new JTextField("NULL");
        Today_Total_Orders_Count.setBounds((int)(sw*0.07),(int)(sh*0.22),(int)(sw*0.08),(int)(sh*0.05));
        Today_Total_Orders_Count.setFont(mainFont);
			
			Today_Total_Orders_Count.setText(DataBase.countTodayOrders());
			Today_Total_Orders_Count.setEditable(false);
			
        c.add(Today_Total_Orders_Count);
        
        Today_Total_Orders_Worth = new JLabel("Worth");
        Today_Total_Orders_Worth.setBounds((int)(sw*0.07),(int)(sh*0.29),(int)(sw*0.1),(int)(sh*0.05));
        Today_Total_Orders_Worth.setFont(mainFont);
        c.add(Today_Total_Orders_Worth);
        
        Today_Total_Orders_Worth_Value = new JTextField("NULL");
        Today_Total_Orders_Worth_Value.setBounds((int)(sw*0.03),(int)(sh*0.36),(int)(sw*0.15),(int)(sh*0.05));
        Today_Total_Orders_Worth_Value.setFont(mainFont);

		Today_Total_Orders_Worth_Value.setText(DataBase.todayOrdersValue());
		Today_Total_Orders_Worth_Value.setEditable(false);
			

        c.add(Today_Total_Orders_Worth_Value);


        Today_Buyer = new JLabel("Today's Buyer's Details");
        Today_Buyer.setFont(mainFont);
        Today_Buyer.setBounds((int)(sw*0.27),(int)(sh*0.08),(int)(sw*2),(int)(sh*0.05));
        c.add(Today_Buyer);

        // Define table data (adjust column names and data types as needed)
        String[] Today_Buyers = {"Name", "Total"};      
        // Create the table model of today buyers.
        Today_Buyer_tableModel = new DefaultTableModel(DataBase.todayBuyerDetails(), Today_Buyers);

        // Create the JTable
        Today_Buyer_table = new JTable(Today_Buyer_tableModel);
        Today_Buyer_table.setRowHeight(30);
        // Get the column at index 1 (second column)
        TableColumn column = Today_Buyer_table.getColumnModel().getColumn(1);

        // Set the preferred width for the column
        column.setPreferredWidth(150);
        column.setMaxWidth(500);
        // Adjusting font and size.
        Today_Buyer_table.setFont(table_font);

        // Create the scroll pane with vertical and horizontal scrollbars as needed
        Today_Buyer_Table_scrollPane = new JScrollPane(Today_Buyer_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adjust table height to fit into the panel that I will be creating.
        Today_Buyer_Table_scrollPane.setPreferredSize(new Dimension(450, 290));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        Today_Buyer_Table_Panel = new JPanel();

        Today_Buyer_Table_Panel.setBounds((int)(sw*0.25),(int)(sh*0.15),(int)(sw*0.3),(int)(sh*0.35));
        Today_Buyer_Table_Panel.add(Today_Buyer_Table_scrollPane, BorderLayout.CENTER);

        // Adding Today_Buyer_Table_Panel to the container.
        c.add(Today_Buyer_Table_Panel);


        // Creating product table
        String[] Today_Products = {"Inlet", "Outlet", "Product", "Amount"};
        // Create the table model of today buyers.
        Today_Product_tableModel = new DefaultTableModel(DataBase.todaySalesDetails(), Today_Products);

        // Create the JTable
        Today_Product_table = new JTable(Today_Product_tableModel);
        Today_Product_table.setRowHeight(30);
        Today_Product_table.setFont(table_font);

        // Create the scroll pane with vertical and horizontal scrollbars as needed
        Today_Product_Table_scrollPane = new JScrollPane(Today_Product_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adjust table height to fit into the panel that I will be creating.
        Today_Product_Table_scrollPane.setPreferredSize(new Dimension(610, 660));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        Today_Product_Table_Panel = new JPanel();

        Today_Product_Table_Panel.setBounds((int)(sw*0.57),(int)(sh*0.06),(int)(sw*0.4),(int)(sh*0.8));
        Today_Product_Table_Panel.add(Today_Product_Table_scrollPane, BorderLayout.CENTER);
        Today_Product_Table_Panel.setBackground(Color.WHITE);

        // Adding Today_Buyer_Table_Panel to the container.
        c.add(Today_Product_Table_Panel);


        // Creating day wise graph.

        // Creating data set for graph
        last_7_days_data = new DefaultCategoryDataset();
        last_7_days_data=DataBase.last7DaysAnalysis();        
//        last_7_days_data.addValue(1.0, "Series 1", "Category 1");
//        last_7_days_data.addValue(2.0, "Series 1", "Category 2");
//        last_7_days_data.addValue(3.0, "Series 1", "Category 3");
//        last_7_days_data.addValue(4.0, "Series 1", "Category 4");
//        last_7_days_data.addValue(5.0, "Series 1", "Category 5");
//        last_7_days_data.addValue(6.0, "Series 1", "Category 6");
//        last_7_days_data.addValue(7.0, "Series 1", "Category 7");
        

        // Creating chart
        home_chart = ChartFactory.createBarChart(
                "Last 7 Days Analysis",
                "Days",
                "Amount",
                last_7_days_data
        );

        Plot plot = home_chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        // Creating panel
        home_graph_panel = new ChartPanel(home_chart);
        home_graph_panel.setBounds((int)(sw*0.01),(int)(sh*0.5),(int)(sw*0.55),(int)(sh*0.4));

        // Adding bar chart panel
        c.add(home_graph_panel);



//time and dates
        // Creating date and time labels
        // Adjusting the layout of home_top_panel
        home_top_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

// Creating date and time labels
        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(mainFont);
        home_top_panel.add(timeLabel); // Add without specifying the position

// Add empty space between time label and date label
        JLabel emptyLabel = new JLabel();
        emptyLabel.setPreferredSize(new Dimension((int)(sw*0.8), 1));// Adjust width as needed
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
        // Update the time label with current time
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

public class Main_Page {
    public static void main(String[] args) {
    	DataBase.setUpDataBase();
        new Frame();
    }
}
