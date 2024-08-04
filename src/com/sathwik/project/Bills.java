package com.sathwik.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

class NewBillFrame extends JFrame implements ActionListener {
    JLabel timeLabel, dateLabel,inletLabel,outletLabel,quantityLabel,cgstLabel,sgstLabel,totalLabel,heading,productsLabel;
    JComboBox<String> inletsComboBox,outletsComboBox,productsComboBox;
    JTextField quantityTextField,cgstTextField,sgstTextField,totalTextField;

    Timer timer;
    JMenuBar menu_bar;
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    JPanel home_top_panel;
    Container c;
    Font tableFont;
    DefaultTableModel displayTableModel;
    JButton add,save,print;
    JPanel displayPanel;

    NewBillFrame(){
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        	
        

        	setBounds((int)(sw*0),(int)(sh*0),(int)(sw*1),(int)(sh*1));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            c = getContentPane();
            c.setLayout(null);
            Font mainFont = new Font("SansSerif", Font.PLAIN, 30);
            Font table_font = new Font("SansSerif", Font.PLAIN, 15);
            c.setBackground(Color.WHITE);
            table_font = new Font("SansSerif", Font.PLAIN, 15);

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
                NewBillFrame.this.dispose();
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
                NewBillFrame.this.dispose();
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
                NewBillFrame.this.dispose();
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
                NewBillFrame.this.dispose();
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
                NewBillFrame.this.dispose();
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
                NewBillFrame.this.dispose();
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

//main heading
        Font headingFont=new Font("SansSerif", Font.PLAIN, 50);
        heading=new JLabel("NEW BILL");
        heading.setBounds((int)(sw*0.03),(int)(sh*0.03),(int)(sw*0.2),(int)(sh*0.2));
        heading.setFont(headingFont);
        c.add(heading);
//labels and text fields
//inlet
            inletLabel=new JLabel("Inlet Name :");
            inletLabel.setFont(mainFont);
            inletLabel.setBounds((int)(sw*0.03),(int)(sh*0.25),(int)(sw*0.12),(int)(sh*0.05));
            c.add(inletLabel);

            //String inlets[]={};
            inletsComboBox=new JComboBox<>(DataBase.availableInletsNames());
            inletsComboBox.setSelectedItem(null);
            String[] availableProducts ={};
            productsComboBox=new JComboBox<String>(availableProducts);
            
            //creating function for inlets table
            JLabel availableProductsLabel1=new JLabel("Available :");
            availableProductsLabel1.setBounds((int)(sw*0.4),(int)(sh*0.8),(int)(sw*0.3),(int)(sh*0.05));
            availableProductsLabel1.setFont(mainFont);
            c.add(availableProductsLabel1);
            inletsComboBox.addActionListener(e->{
            	//productsComboBox.setSelectedItem(null);
            	if(inletsComboBox.getSelectedItem()!=null) {
            	String selected=inletsComboBox.getSelectedItem().toString();
                System.out.println(selected);
                productsComboBox.removeAllItems();
            	String items[]=DataBase.availableProductsNames(selected);
				if(items.length==0) {
				    JOptionPane.showMessageDialog(null, "please select a inlet that have products", "Warning", JOptionPane.WARNING_MESSAGE);
				    inletsComboBox.setSelectedItem(null);
				}
				for (String item : items) {
				    productsComboBox.addItem(item);
				}
                //int selected=inletsComboBox.getSelectedIndex();
            	productsComboBox.setSelectedItem(null);
            	availableProductsLabel1.setText("Available :");
            	}
            	
                
            });
            
            inletsComboBox.setFont(mainFont);
            inletsComboBox.setBounds((int)(sw*0.18),(int)(sh*0.25),(int)(sw*0.15),(int)(sh*0.05));
            c.add(inletsComboBox);

//products
            productsLabel=new JLabel("Product Name :");
            productsLabel.setFont(mainFont);
            productsLabel.setBounds((int)(sw*0.03),(int)(sh*0.35),(int)(sw*0.15),(int)(sh*0.05));
            c.add(productsLabel);
            
          //available products lable


            //String products[]={"NULL1","NULL2","NULL3","NULL4","NULL5"};
            //productsComboBox=new JComboBox<>(products);
            productsComboBox.setSelectedItem(null);
            productsComboBox.setFont(mainFont);
            productsComboBox.setBounds((int)(sw*0.18),(int)(sh*0.35),(int)(sw*0.15),(int)(sh*0.05));
            productsComboBox.addActionListener(e->{
            	if(productsComboBox.getSelectedItem()!=null && inletsComboBox.getSelectedItem()!=null) {
            		try {
            		String availableQuantity=DataBase.availableQuantityOfProduct(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString());
                	
            		availableProductsLabel1.setText("Available : "+ availableQuantity);

            		}
            		catch(Exception tableNotFound) {
            	        JOptionPane.showMessageDialog(null, "please select a inlet that have products", "Warning", JOptionPane.WARNING_MESSAGE);

            			
            		}
            	}
            });
            c.add(productsComboBox);

//outlets
            outletLabel=new JLabel("Outlet Name :");
            outletLabel.setFont(mainFont);
            outletLabel.setBounds((int)(sw*0.03),(int)(sh*0.45),(int)(sw*0.15),(int)(sh*0.05));
            c.add(outletLabel);

            String outletsItems[]= {};
			outletsItems = DataBase.availableOutletsNames();
            outletsComboBox=new JComboBox<>(outletsItems);
            outletsComboBox.setSelectedItem(null);
            outletsComboBox.setFont(mainFont);
            outletsComboBox.setBounds((int)(sw*0.18),(int)(sh*0.45),(int)(sw*0.15),(int)(sh*0.05));
            
            
            final String[] outletsComboBoxSelectedItem = new String[1];
            outletsComboBox.addActionListener(e->{
                outletsComboBoxSelectedItem[0] =outletsComboBox.getSelectedItem().toString();
                outletsComboBox.setEnabled(false);
                System.out.println(outletsComboBoxSelectedItem[0]);
            });

            c.add(outletsComboBox);

//quantity
            quantityLabel=new JLabel("Quantity :");
            quantityLabel.setFont(mainFont);
            quantityLabel.setBounds((int)(sw*0.03),(int)(sh*0.55),(int)(sw*0.15),(int)(sh*0.05));
            c.add(quantityLabel);

            quantityTextField=new JTextField();
            quantityTextField.setBounds((int)(sw*0.18),(int)(sh*0.55),(int)(sw*0.15),(int)(sh*0.05));
            quantityTextField.setFont(mainFont);
            c.add(quantityTextField);

//cgst
            cgstLabel=new JLabel("C.G.S.T :");
            cgstLabel.setFont(mainFont);
            cgstLabel.setBounds((int)(sw*0.03),(int)(sh*0.65),(int)(sw*0.15),(int)(sh*0.05));
            c.add(cgstLabel);

            cgstTextField=new JTextField();
            cgstTextField.setBounds((int)(sw*0.18),(int)(sh*0.65),(int)(sw*0.15),(int)(sh*0.05));
            cgstTextField.setFont(mainFont);
            c.add(cgstTextField);

//sgst
            sgstLabel=new JLabel("S.G.S.T :");
            sgstLabel.setFont(mainFont);
            sgstLabel.setBounds((int)(sw*0.03),(int)(sh*0.75),(int)(sw*0.15),(int)(sh*0.05));
            c.add(sgstLabel);

            sgstTextField=new JTextField();
            sgstTextField.setBounds((int)(sw*0.18),(int)(sh*0.75),(int)(sw*0.15),(int)(sh*0.05));
            sgstTextField.setFont(mainFont);
            c.add(sgstTextField);

//total amount
//            totalLabel=new JLabel("Total Amount :");
//            totalLabel.setFont(mainFont);
//            totalLabel.setBounds((int)(sw*0.5),(int)(sh*0.06),(int)(sw*0.15),(int)(sh*0.05));
//            c.add(totalLabel);
            
            totalTextField=new JTextField();
            totalTextField.setBounds((int)(sw*0.18),(int)(sh*0.82),(int)(sw*0.15),(int)(sh*0.05));
            totalTextField.setFont(mainFont);
            totalTextField.setEditable(false);
            c.add(totalTextField);
            


//display table

            Display();

//add button
            add=new JButton("ADD");
            add.setFont(mainFont);
            add.setBounds((int)(sw*0.5),(int)(sh*0.08),(int)(sw*0.10),(int)(sh*0.05));
            add.addActionListener(e->{
            	boolean check=true;
            	try {
        			int checkQuantity=Integer.parseInt( quantityTextField.getText());
        		}
        		catch(NumberFormatException e1) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "quantity is invalid", "Error", JOptionPane.ERROR_MESSAGE);
        		}
            	try {
        			float checkQuantity=Float.parseFloat( sgstTextField.getText());
        		}
        		catch(NumberFormatException e1) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "quantity is invalid", "Error", JOptionPane.ERROR_MESSAGE);
        		}
            	try {
        			float checkQuantity=Float.parseFloat( cgstTextField.getText());
        		}
        		catch(NumberFormatException e1) {
        			check=false;
                    JOptionPane.showMessageDialog(null, "quantity is invalid", "Error", JOptionPane.ERROR_MESSAGE);
        		}
            	try {
            		
            	String availableQuantity=DataBase.availableQuantityOfProduct(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString());            	
        		int availableProductsint=Integer.parseInt(availableQuantity);
        		int quantitycheck=Integer.parseInt(quantityTextField.getText());
        		if(quantitycheck>availableProductsint) {
        			throw new Exception();
        		}
        		
            	}
            	catch(Exception e1) {
            		check=false;
            				
                    JOptionPane.showMessageDialog(null, "Entered quantity is more than available", "Error", JOptionPane.ERROR_MESSAGE);

            	}
            	if(check) {
            	try {
            	 String sellingPrice=DataBase.sellingPriceOfProduct(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString());
                 int sp=Integer.parseInt(sellingPrice); 
                 String inlet_Name=inletsComboBox.getSelectedItem().toString();
                 String outlet_Nmae=outletsComboBox.getSelectedItem().toString();
                 String product_name=productsComboBox.getSelectedItem().toString();
                 int quantity=Integer.parseInt(quantityTextField.getText());
                 float cgst=Float.parseFloat(cgstTextField.getText());
                 float sgst=Float.parseFloat(sgstTextField.getText());
                 float total=quantity*sp+(quantity*sp*((float)cgst/100))+(quantity*sp*((float)sgst/100));
                 System.out.println(sp);
                 totalTextField.setEditable(true);
                 totalTextField.setText(""+total);
                 totalTextField.setEditable(false);
                 Object[] addToDisplay={inlet_Name,product_name,outlet_Nmae,quantity,cgst,sgst,total};
                 displayTableModel.addRow(addToDisplay);
                 
                 //updating data base
                 int existingQuantity=Integer.parseInt(DataBase.availableQuantityOfProduct(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString()));
                 String newQuantity = ""+(existingQuantity-quantity);
                 DataBase.reduceQuantityOfProduct(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString(),newQuantity);
                 
                 
                 //adding to bills table
                 DataBase.addbill(inletsComboBox.getSelectedItem().toString(),productsComboBox.getSelectedItem().toString(),outletsComboBoxSelectedItem[0],quantityTextField.getText(),cgstTextField.getText(),sgstTextField.getText(),""+total);
                 inletsComboBox.setSelectedItem(null);
                 productsComboBox.setSelectedItem(null);
                 quantityTextField.setText(null);
                 cgstTextField.setText(null);
                 sgstTextField.setText(null);
            	}
            	catch(NullPointerException e1) {
        	        JOptionPane.showMessageDialog(null, "Dont leave any field empty", "Warning", JOptionPane.WARNING_MESSAGE);

            	}
            	}
            });
            c.add(add);



//print button
            print=new JButton("PRINT");
            print.setBounds((int)(sw*0.8),(int)(sh*0.08),(int)(sw*0.10),(int)(sh*0.05));
            print.setFont(mainFont);
            print.addActionListener(e->{
            	PanelPrinter.printPanel(displayPanel);
            	NewBillFrame.this.dispose();
            	new NewBillFrame();
            });
            c.add(print);




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
    //function for display;
    private void Display(){

    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        String[] inletsColumn = {"INLET","PRODUCT","OUTLET", "QUANTITY","C.G.S.T","S.G.S.T","AMOUNT"};
        Object[][] inletsRow = {};

        // Create the table model of today buyers.
        displayTableModel = new DefaultTableModel(inletsRow, inletsColumn);

        // Create the JTable
        JTable displayTable = new JTable(displayTableModel);
        displayTable.setRowHeight(30);
        // Get the column at index 1 (second column)
        TableColumn column = displayTable.getColumnModel().getColumn(1);

        // Set the preferred width for the column
        column.setPreferredWidth(150);
        column.setMaxWidth(500);
        // Adjusting font and size.
        displayTable.setFont(tableFont);

        // Create the scroll pane with vertical and horizontal scrollbars as needed
        JScrollPane displayScrollPane = new JScrollPane(displayTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adjust table height to fit into the panel that I will be creating.
        displayScrollPane.setPreferredSize(new Dimension(900, 500));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        displayPanel = new JPanel();

        displayPanel.setBounds((int)(sw*0.38),(int)(sh*0.18),(int)(sw*0.6),(int)(sh*0.6));
        displayPanel.add(displayScrollPane, BorderLayout.CENTER);
        c.add(displayPanel);
    }


}



public class Bills {
    public static void main(String[] args) {
        new NewBillFrame();
    }
}
