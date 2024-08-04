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

class AddOutletFrame extends JFrame implements ActionListener {
	
    JLabel timeLabel, dateLabel,heading, existingOutletsLabel, outletName,ownerName,contact,address;
    
    JTextField outletNameTextField,ownerNameTextField,contactTextField,addressTextField;
    
    Timer timer;
    
    JMenuBar menu_bar;
    
    JMenu Home, Analysis, Products, Inlets, Outlets, Bills;
    
    JPanel home_top_panel, availableOutletsPanel;
    
    DefaultTableModel availableOutletsTableModel;
    
    JTable availableOutletsTable;
    
    JScrollPane availableOutletsScrollPane;
    
    JButton add;
    
    AddOutletFrame(){
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
                AddOutletFrame.this.dispose();
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
                AddOutletFrame.this.dispose();
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
                AddOutletFrame.this.dispose();
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
                AddOutletFrame.this.dispose();
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
                AddOutletFrame.this.dispose();
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
                AddOutletFrame.this.dispose();
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
        heading=new JLabel("ADD NEW OUTLETS");
        heading.setBounds(30,90,900,100);
        heading.setFont(headingFont);
        c.add(heading);

//labels for inputs
//outlet Name
        outletName =new JLabel("Outlet Name :");
        outletName.setBounds(30,280,300,50);
        outletName.setFont(mainFont);
        c.add(outletName);
//outlet text field
        outletNameTextField =new JTextField();
        outletNameTextField.setFont(mainFont);
        outletNameTextField.setBounds(250,280,300,50);
        c.add(outletNameTextField);
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


//adding add button
        add=new JButton("ADD");
        add.setBounds(160,670,150,50);
        add.setFont(mainFont);
        add.addActionListener(e->{
        	boolean check=true;
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
        		if(outletNameTextField.getText().equals("") || ownerNameTextField.getText().equals("") || addressTextField.getText().equals("")) {
        			throw new NullPointerException();
        		}
        		System.out.println(outletNameTextField.getText());
				DataBase.addOutletsDetails(outletNameTextField.getText(), ownerNameTextField.getText(),addressTextField.getText(),contactTextField.getText());
				AddOutletFrame.this.dispose();
				new AddOutletFrame();
        	} catch(NullPointerException e1) {
    	        JOptionPane.showMessageDialog(null, "Dont leave any field empty", "Warning", JOptionPane.WARNING_MESSAGE);

        	}catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	}
        });
        c.add(add);



//lable for table
        existingOutletsLabel = new JLabel("Existing Outlets Table");
        existingOutletsLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        existingOutletsLabel.setBounds(800,100,900,70);
        c.add(existingOutletsLabel);

//adding available inlets table
        String[] outlets = {"OUTLET", "OWNER","ADDRESS","CONTACT"};
        Object[][] outletsDetails = {{}};
        Connection con;
        	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "1234");
			Statement stmt = con.createStatement();
			int i=0;
			
			ArrayList<String> outletnames=new ArrayList<>();
			ArrayList<String> ownernames=new ArrayList<>();
			ArrayList<String> address=new ArrayList<>();
			ArrayList<String> contact=new ArrayList<>();
			ResultSet res= stmt.executeQuery("select shopname,ownername,address,mobilenumber from outlets;");			
			while(res.next()) {
				outletnames.add(res.getString(1));
				ownernames.add(res.getString(2));
				address.add(res.getString(3));
				contact.add(res.getString(4));
				//i++;
			}
			
			outletsDetails = new Object[outletnames.size()][4];
			for(i=0;i<outletnames.size();i++) {
				outletsDetails[i][0]=outletnames.get(i);
				outletsDetails[i][1]=ownernames.get(i);
				outletsDetails[i][2]=address.get(i);
				outletsDetails[i][3]=contact.get(i);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Create the table model of today buyers.
        availableOutletsTableModel = new DefaultTableModel(outletsDetails, outlets);

        // Create the JTable
        availableOutletsTable = new JTable(availableOutletsTableModel);
        availableOutletsTable.setRowHeight(30);
        // Get the column at index 1 (second column)
        TableColumn column = availableOutletsTable.getColumnModel().getColumn(1);

        // Set the preferred width for the column
        column.setPreferredWidth(150);
        column.setMaxWidth(500);
        // Adjusting font and size.
        availableOutletsTable.setFont(table_font);

        // Create the scroll pane with vertical and horizontal scrollbars as needed
        availableOutletsScrollPane = new JScrollPane(availableOutletsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Adjust table height to fit into the panel that I will be creating.
        availableOutletsScrollPane.setPreferredSize(new Dimension(800, 530));
        // Creating a new panel for adding Today_Buyer_Table_Panel.
        availableOutletsPanel = new JPanel();

        availableOutletsPanel.setBounds(700, 180, 800, 550);
        availableOutletsPanel.add(availableOutletsScrollPane, BorderLayout.CENTER);
        c.add(availableOutletsPanel);

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
public class outlet {
    public static void main(String[] args) {
        new AddOutletFrame();
    }

}
