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
import java.util.ArrayList;
import java.util.Calendar;

class AddInletsFrame extends JFrame implements ActionListener {
    JLabel timeLabel, dateLabel,heading,existingInletsLable,inletName,ownerName,contact,address,addProduct;
    JTextField inletNameTextField,ownerNameTextField,contactTextField,addressTextField,addProductTextField;
    Timer timer;
    JMenuBar menu_bar;
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    JPanel home_top_panel,availableInletsPanel;
    DefaultTableModel availableInletsTableModel;
    JTable availableInletsTable;
    JScrollPane availableInletsScrollPane;
    JButton add;
    AddInletsFrame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        
        setBounds((int)(sw*0),(int)(sh*0),(int)(sw*1),(int)(sh*1));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        Font mainFont = new Font("SansSerif", Font.PLAIN, 30);
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
                AddInletsFrame.this.dispose();
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
        Analysis.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddInletsFrame.this.dispose();
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
        Products.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddInletsFrame.this.dispose();
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
                AddInletsFrame.this.dispose();
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
                AddInletsFrame.this.dispose();
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
                AddInletsFrame.this.dispose();
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
        heading=new JLabel("ADD NEW INLETS");
        heading.setBounds(30,90,900,100);
        heading.setFont(headingFont);
        c.add(heading);

//lables for inputs
//inletName
        inletName=new JLabel("Inlet Name :");
        inletName.setBounds(30,280,300,50);
        inletName.setFont(mainFont);
        c.add(inletName);
//inlet text field
        inletNameTextField=new JTextField();
        inletNameTextField.setFont(mainFont);
        inletNameTextField.setBounds(250,280,300,50);
        c.add(inletNameTextField);
//owner name
        ownerName=new JLabel("Owner Name :");
        ownerName.setBounds(30,370,300,50);
        ownerName.setFont(mainFont);
        c.add(ownerName);
//owner text field
        ownerNameTextField=new JTextField();
        ownerNameTextField.setBounds(250,370,300,50);
        ownerNameTextField.setFont(mainFont);
        c.add(ownerNameTextField);

//address
        address=new JLabel("Address :");
        address.setBounds(30,470,300,50);
        address.setFont(mainFont);
        c.add(address);
//address text field
        addressTextField=new JTextField();
        addressTextField.setBounds(250,470,300,50);
        addressTextField.setFont(mainFont);
        c.add(addressTextField);

//contact
        contact=new JLabel("Contact :");
        contact.setBounds(30,570,300,50);
        contact.setFont(mainFont);
        c.add(contact);
//contact text field
        contactTextField=new JTextField();
        contactTextField.setBounds(250,570,300,50);
        contactTextField.setFont(mainFont);
        c.add(contactTextField);
////add product
//        addProduct=new JLabel("Product Name :");
//        addProduct.setBounds(30,660,300,50);
//        addProduct.setFont(mainFont);
//        c.add(addProduct);
////product text field
//        addProductTextField=new JTextField();
//        addProductTextField.setBounds(250,660,300,50);
//        addProductTextField.setFont(mainFont);
//        c.add(addProductTextField);

//adding add button
        add=new JButton("ADD");
        add.setBounds(180,680,150,50);
        add.setFont(mainFont);
        add.addActionListener(e->{
        	boolean check=true;
        	try {
        		String checkInletName=inletNameTextField.getText();
        		for(int i=0;i<checkInletName.length();i++) {
        			if(checkInletName.charAt(i)==' ') {
        				check=false;
        				throw new NumberFormatException();
        			}
        		}
        	}
        	catch(NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, "Inlet name should not contain space and special charecters", "warning", JOptionPane.WARNING_MESSAGE);

        	}
        	try {
        		long checkContact = Long.parseLong(contactTextField.getText());
        		String checkString=contactTextField.getText();
        		if(checkString.length()!=10) {
        			throw new NumberFormatException();
        		}
        	}
        	catch(NumberFormatException e1) {
        		check=false;
                JOptionPane.showMessageDialog(null, "contact number is invalid", "warning", JOptionPane.WARNING_MESSAGE);

        	}
        	if(check) {
        	try {
        		if(inletNameTextField.getText().equals("") || ownerNameTextField.getText().equals("") ||contactTextField.getText().equals("") || addressTextField.getText().equals("")) {
        			throw new NullPointerException();
        		}
				DataBase.addInletsDetails(inletNameTextField.getText(), ownerNameTextField.getText(),contactTextField.getText(),addressTextField.getText());
				AddInletsFrame.this.dispose();
				new AddInletsFrame();
        	} catch(NullPointerException e1) {
    	        JOptionPane.showMessageDialog(null, "Dont leave any field empty", "Warning", JOptionPane.WARNING_MESSAGE);

        	}
        	}
        });
        c.add(add);



//lable for table
        existingInletsLable = new JLabel("Existing Inlets Table");
        existingInletsLable.setFont(new Font("SansSerif", Font.PLAIN, 30));
        existingInletsLable.setBounds(800,100,900,70);
        c.add(existingInletsLable);

//adding available inlets table
        String[] inlets = {"INLET", "OWNER","ADDRESS","CONTACT","PRODUCTS"};

        // Create the table model of inlets information
        availableInletsTableModel = new DefaultTableModel(DataBase.inletsDetails(), inlets);

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
        availableInletsScrollPane.setPreferredSize(new Dimension(800, 530));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        availableInletsPanel = new JPanel();

        availableInletsPanel.setBounds(700, 180, 800, 550);
        availableInletsPanel.add(availableInletsScrollPane, BorderLayout.CENTER);
        c.add(availableInletsPanel);

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
public class Inlets {
    public static void main(String[] args) {
        new AddInletsFrame();
    }
}
