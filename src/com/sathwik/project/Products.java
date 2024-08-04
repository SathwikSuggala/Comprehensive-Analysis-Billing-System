package com.sathwik.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//import javax.xml.crypto.Data;

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


class ExistingProductsFrame extends JFrame implements ActionListener {
	
    JLabel timeLabel, dateLabel,heading,inletName,productName,quantity;
    
    Timer timer;
    
    JMenuBar menu_bar;
    
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    
    JPanel home_top_panel,availableInletsPanel,availableProductsPanel;
    
    JComboBox inletsComboBox,productsComboBox,change;
    
    JTextField quantityInput;
    
    DefaultTableModel availableProductsTableModel,availableInletsTableModel;
    
    JTable availableInletsTable,availableProductsTable;
    
    JScrollPane availableInletsScrollPane,availableProductsScrollPane;
    
    JButton add;


    ExistingProductsFrame(){
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
    	
        setBounds((int)(sw*0),(int)(sh*0),(int)(sw*1),(int)(sh*1));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        
        Font mainFont = new Font("SansSerif", Font.PLAIN, 30);
        Font headingFont=new Font("SansSerif", Font.PLAIN, 50);
        Font table_font = new Font("SansSerif", Font.PLAIN, 20);
        
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
                ExistingProductsFrame.this.dispose();
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
        Products.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExistingProductsFrame.this.dispose();
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
                ExistingProductsFrame.this.dispose();
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
        Inlets.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExistingProductsFrame.this.dispose();
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
                ExistingProductsFrame.this.dispose();
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
        Bills.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExistingProductsFrame.this.dispose();
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

//adding heading to " Add existing products to the inventory "
        heading=new JLabel("ADD EXISTING PRODUCTS TO THE INVENTORY");
        heading.setBounds(30,90,1300,100);
        heading.setFont(headingFont);
        c.add(heading);

//adding label for inlet name
        inletName=new JLabel("Inlet Name: ");
        inletName.setFont(mainFont);
        inletName.setBounds(80,300,200,50);
        c.add(inletName);

        c.add(inletsTable());
//adding inlet combo box
        String availableInlets[]={};
        inletsComboBox=new JComboBox(DataBase.availableInletsNames());
        inletsComboBox.setFont(mainFont);
        inletsComboBox.setBounds(320,300,300,50);
        inletsComboBox.setSelectedItem(null);
        String[] availableProducts ={};
        productsComboBox=new JComboBox(availableProducts);
        
        //creating function for inlets table
        
        inletsComboBox.addActionListener(e->{
        	String selected=inletsComboBox.getSelectedItem().toString();
            System.out.println(selected);
            productsComboBox.removeAllItems();
        	try {
        		String items[]=DataBase.availableProductsNames(selected);
        		if(items.length==0) {
        			throw new SQLException();
        			
        		}
                for (String item : items) {
                    productsComboBox.addItem(item);
                }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				inletsComboBox.setSelectedItem(null);
    	        JOptionPane.showMessageDialog(null, "please select a inlet that have products", "Warning", JOptionPane.WARNING_MESSAGE);

			}
            //int selected=inletsComboBox.getSelectedIndex();
            productsComboBox.setSelectedItem(null);

//
//            
//            String[] products = {"PRODUCTS", "QUANTITY"};
//            Object[][] productsDetails = {{"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}
//                    , {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}
//                    , {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}, {"Alice", 30}, {"Bob", 25}};
//
//            // Create the table model of today buyers.
//            availableProductsTableModel = new DefaultTableModel(productsDetails, products);
//
//            // Create the JTable
//            availableProductsTable = new JTable(availableProductsTableModel);
//            availableProductsTable.setRowHeight(30);
//            // Get the column at index 1 (second column)
//            TableColumn column = availableProductsTable.getColumnModel().getColumn(1);
//
//            // Set the preferred width for the column
//            column.setPreferredWidth(150);
//            column.setMaxWidth(500);
//            // Adjusting font and size.
//            availableProductsTable.setFont(table_font);
//
//            // Create the scroll pane with vertical and horizontal scrollbars as needed
//            availableProductsScrollPane = new JScrollPane(availableProductsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//            // Adjust table height to fit into the panel that I will be creating.
//            availableProductsScrollPane.setPreferredSize(new Dimension(480, 530));
//            // Creating a new panel for adding Today_Buyer_Table_Panel.
//            availableProductsPanel = new JPanel();
//
//            availableProductsPanel.setBounds(800, 220, 500, 550);
//            availableProductsPanel.add(availableProductsScrollPane, BorderLayout.CENTER);
//            c.add(availableProductsPanel);
        });
        c.add(inletsComboBox);

//adding label product name
        productName=new JLabel("Product Name:");
        productName.setFont(mainFont);
        productName.setBounds(80,380,240,50);
        c.add(productName);
//adding products combo box
        
		
        productsComboBox.setFont(mainFont);
        productsComboBox.setBounds(320,380,300,50);
        productsComboBox.setSelectedItem(null);

        c.add(productsComboBox);

//adding quantity label
        quantity=new JLabel("Quantity: ");
        quantity.setBounds(80,460,200,50);
        quantity.setFont(mainFont);
        c.add(quantity);
//adding quantity input text area
        quantityInput=new JTextField();
        quantityInput.setBounds(320,460,300,50);
        quantityInput.setFont(mainFont);
        c.add(quantityInput);

//adding button for changing layout
        String Change[]={"New Product","Existing Product"};
        change=new JComboBox(Change);
        change.setBounds(1250,120,250,50);
        change.setFont(mainFont);
        change.setSelectedIndex(1);
        change.addActionListener(e->{
            int selected=change.getSelectedIndex();
            if(selected==0){
            	ExistingProductsFrame.this.dispose();
                new NewProductsFrame();
            }
            if(selected==1){
            	ExistingProductsFrame.this.dispose();
                new ExistingProductsFrame();
            }
        });

        c.add(change);

//adding add button
        add=new JButton("Save");
        add.setFont(mainFont);
        add.setBounds(220,600,200,50);
        add.addActionListener(e->{
        	boolean check=true;
        	try {
        			int quanitycheck=Integer.parseInt(quantityInput.getText());
        			check=true;
        		}catch(NumberFormatException e2) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "quantity is invalid", "Error", JOptionPane.ERROR_MESSAGE);

        		}
        	if(check) {
        	try {
        		
        		if(quantityInput.getText().equals("")) {
        			throw new NullPointerException();
        		}
        	DataBase.addExistingProducts(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString(),quantityInput.getText());
            JOptionPane.showMessageDialog(null, "successfully added", "Information", JOptionPane.INFORMATION_MESSAGE);
            ExistingProductsFrame.this.dispose();
            new ExistingProductsFrame();
        	}
        	catch(NullPointerException e1) {
                JOptionPane.showMessageDialog(null, "please select something before adding items", "Error", JOptionPane.ERROR_MESSAGE);
//                ExistingProductsFrame.this.dispose();
//                new ExistingProductsFrame();
        	}
        	catch(Exception e1) {
    	        JOptionPane.showMessageDialog(null, "Dont leave any field empty", "Warning", JOptionPane.WARNING_MESSAGE);

        	}
        	}
        });
        c.add(add);



//date and time
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

        setVisible(true);}

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
    public JPanel inletsTable(){
        Font table_font = new Font("SansSerif", Font.PLAIN, 20);
        String[] inlets = {"INLETS", "PRODUCTS"};

        // Create the table model of today buyers.
        availableInletsTableModel = new DefaultTableModel(DataBase.inletsProductCount(), inlets);

        // Create the JTable
        availableInletsTable = new JTable(availableInletsTableModel);
        availableInletsTable.setRowHeight(30);
        // Get the column at index 1 (second column)
        TableColumn column = availableInletsTable.getColumnModel().getColumn(1);

        // Set the preferred width for the column
        column.setPreferredWidth(150);
        column.setMaxWidth(500);
        // Adjusting font and size.
        availableInletsTable.setFont(table_font);

        // Create the scroll pane with vertical and horizontal scrollbars as needed
        availableInletsScrollPane = new JScrollPane(availableInletsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adjust table height to fit into the panel that I will be creating.
        availableInletsScrollPane.setPreferredSize(new Dimension(480, 530));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        availableInletsPanel = new JPanel();

        availableInletsPanel.setBounds(800, 220, 500, 550);
        availableInletsPanel.add(availableInletsScrollPane, BorderLayout.CENTER);
        return availableInletsPanel;
    }
    

}



class NewProductsFrame extends JFrame implements ActionListener{
    JLabel timeLabel, dateLabel,heading,inletName,productName,quantity, costPriceLabel,sellingPriceLabel;
    Timer timer;
    JMenuBar menu_bar;
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    JPanel home_top_panel,availableInletsPanel;
    JComboBox inletsComboBox,change;
    JTextField quantityInput,productInput,costPriceTextField,sellingPriceTextField;
    DefaultTableModel availableInletsTableModel;
    JTable availableInletsTable;
    JScrollPane availableInletsScrollPane;
    JButton add;
    NewProductsFrame() {

    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
    	
        setBounds((int)(sw*0),(int)(sh*0),(int)(sw*1),(int)(sh*1));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            Container c = getContentPane();
            c.setLayout(null);
            Font mainFont = new Font("SansSerif", Font.PLAIN, 30);
            Font headingFont=new Font("SansSerif", Font.PLAIN, 50);
            Font table_font = new Font("SansSerif", Font.PLAIN, 20);
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
                    NewProductsFrame.this.dispose();
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
            Products.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    NewProductsFrame.this.dispose();
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
        Inlets.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewProductsFrame.this.dispose();
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
            Analysis.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    NewProductsFrame.this.dispose();
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
                NewProductsFrame.this.dispose();
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
        Outlets.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewProductsFrame.this.dispose();
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

            setJMenuBar(menu_bar);

            // HomeScreen
            //top panel for data and time
            home_top_panel = new JPanel(new BorderLayout()); // Use BorderLayout
            home_top_panel.setBounds((int)(sw*0),(int)(sh*0),(int)(sw*1),(int)(sh*0.06));
            Color lightRed = new Color(255, 95, 85);
            home_top_panel.setBackground(lightRed);
            c.add(home_top_panel);

//adding heading to " Add existing products to the inventory "
            heading=new JLabel("ADD NEW PRODUCTS TO THE INVENTORY");
            heading.setBounds(30,90,1300,100);
            heading.setFont(headingFont);
            c.add(heading);

//adding label for inlet name
            inletName=new JLabel("Inlet Name: ");
            inletName.setFont(mainFont);
            inletName.setBounds(100,300,200,50);
            c.add(inletName);
//adding inlet input text field
        String availableInlets[]= {};
		availableInlets = DataBase.availableInletsNames();
        inletsComboBox=new JComboBox(availableInlets);
        inletsComboBox.setFont(mainFont);
        inletsComboBox.setBounds(320,300,300,50);
        inletsComboBox.setSelectedItem(null);
        c.add(inletsComboBox);

//adding label product name
            productName=new JLabel("Product Name:");
            productName.setFont(mainFont);
            productName.setBounds(100,380,250,50);
            c.add(productName);
//adding product input text field
            productInput=new JTextField();
            productInput.setBounds(320,380,300,50);
            productInput.setFont(mainFont);
            c.add(productInput);

//adding quantity label
            quantity=new JLabel("Quantity: ");
            quantity.setBounds(100,460,200,50);
            quantity.setFont(mainFont);
            c.add(quantity);
//adding quantity input text area
            quantityInput=new JTextField();
            quantityInput.setBounds(320,460,300,50);
            quantityInput.setFont(mainFont);

            c.add(quantityInput);
//adding cost field
            costPriceLabel =new JLabel("Cost Price :");
            costPriceLabel.setBounds(100,540,300,50);
            costPriceLabel.setFont(mainFont);
            c.add(costPriceLabel);

            costPriceTextField=new JTextField();
            costPriceTextField.setFont(mainFont);
            costPriceTextField.setBounds(320,540,300,50);
            c.add(costPriceTextField);

//adding selling price field
            sellingPriceLabel=new JLabel("Selling Price :");
            sellingPriceLabel.setFont(mainFont);
            sellingPriceLabel.setBounds(100,620,300,50);
            c.add(sellingPriceLabel);

            sellingPriceTextField=new JTextField();
            sellingPriceTextField.setBounds(320,620,300,50);
            sellingPriceTextField.setFont(mainFont);
            c.add(sellingPriceTextField);

//adding button for changing layout
            String Change[]={"New Product","Existing Product"};
            change=new JComboBox(Change);
            change.setBounds(1250,120,250,50);
            change.setFont(mainFont);
            change.setSelectedIndex(0);
            change.addActionListener(e->{
                int selected=change.getSelectedIndex();
                if(selected==0){
                	NewProductsFrame.this.dispose();
                    new NewProductsFrame();
                }
                if(selected==1){
                	NewProductsFrame.this.dispose();
                    new ExistingProductsFrame();
                }
            });

            c.add(change);



//adding add button
            add=new JButton("ADD");
            add.setFont(mainFont);
            add.setBounds(220,700,200,50);
            add.addActionListener(e->{
            	boolean check=true;
        		try {
        			int checkQuantity=Integer.parseInt( quantityInput.getText());
        		}
        		catch(NumberFormatException e1) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "quantity is invalid", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		try {
        			float checkcp=Float.parseFloat( costPriceTextField.getText());
        		}
        		catch(NumberFormatException e1) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "cost price is invalid", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		try {
        			int checksp=Integer.parseInt( sellingPriceTextField.getText());
        		}
        		catch(NumberFormatException e1) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "selling price is invalid", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		if(check) {
            	try {
            		

            		if(inletsComboBox.getSelectedItem().toString().equals("") || productInput.getText().equals("") || quantityInput.getText().equals("") || costPriceTextField.getText().equals("") || sellingPriceTextField.getText().equals("")) {
            			throw new NullPointerException();
            		}
            	DataBase.addNewProduct(inletsComboBox.getSelectedItem().toString(),productInput.getText(),quantityInput.getText(),costPriceTextField.getText(),sellingPriceTextField.getText());
            	NewProductsFrame.this.dispose();
            	new NewProductsFrame();
            	}catch(NullPointerException e1) {
        	        JOptionPane.showMessageDialog(null, "Dont leave any field empty", "Warning", JOptionPane.WARNING_MESSAGE);

            	}
        		}
            });
            c.add(add);

//adding available inlets table
        String[] inlets = {"INLETS", "PRODUCTS"};
        // Create the table model of today buyers.
        availableInletsTableModel = new DefaultTableModel(DataBase.inletsProductCount(), inlets);

        // Create the JTable
        availableInletsTable = new JTable(availableInletsTableModel);
        availableInletsTable.setRowHeight(30);
        // Get the column at index 1 (second column)
        TableColumn column = availableInletsTable.getColumnModel().getColumn(1);

        // Set the preferred width for the column
        column.setPreferredWidth(150);
        column.setMaxWidth(500);
        // Adjusting font and size.
        availableInletsTable.setFont(table_font);

        // Create the scroll pane with vertical and horizontal scrollbars as needed
        availableInletsScrollPane = new JScrollPane(availableInletsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adjust table height to fit into the panel that I will be creating.
        availableInletsScrollPane.setPreferredSize(new Dimension(480, 490));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        availableInletsPanel = new JPanel();

        availableInletsPanel.setBounds(800, 220, 500, 500);
        availableInletsPanel.add(availableInletsScrollPane, BorderLayout.CENTER);
        c.add(availableInletsPanel);






//date and time
            home_top_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
// Creating date and time labels
            timeLabel = new JLabel("00:00:00");
            timeLabel.setFont(mainFont);
            home_top_panel.add(timeLabel); // Add without specifying the position

// Add empty space between time label and date label
            JLabel emptyLabel = new JLabel();
            emptyLabel.setPreferredSize(new Dimension((int)(sw*0.8), 1)); // Adjust width as needed
            home_top_panel.add(emptyLabel);

            dateLabel = new JLabel("00/00/00");
            dateLabel.setFont(mainFont);
            home_top_panel.add(dateLabel); // Add without specifying the position


            // Create and start timer to update time label
            timer = new Timer(1000, this);
            timer.start();

            setVisible(true);}

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
public class Products {
    public static void main(String[] args) {
        new ExistingProductsFrame();
    }
}
