package assignment2;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.management.modelmbean.ModelMBean;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.*;

import java.util.Date;


public class ScuCalendar extends JFrame {
 
  
  private DefaultTableModel table_model,table_view;
  private JLabel label;
  private JLabel label1 = new JLabel("");
  private JTextField textfield1 = new JTextField(12);
  private JTextField textfield2 = new JTextField(12);
  private DefaultListModel model;
  private JList list;
  private String [] columns = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"}; // Array of Days which serve as Column Headers for Calendar
  private final int num_of_holidays = 10;
  private String [] holiday = new String [num_of_holidays]; //Array holds list of 2019 Year Holidays
  private String [] holiday_msg = new String [num_of_holidays]; //Array holds string values for message to be displayed on Calendar for the 2019 Year Holidays
  private JTable table,view_table;
  Calendar cal = new GregorianCalendar(2019, 00, 01); //Creates a Gregorian Calender
  SimpleDateFormat format1 = new SimpleDateFormat("MMMMMMM dd");
  SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
  ArrayList<String> event_list = new ArrayList<String>();
  ArrayList<String> event_data = new ArrayList<String>();
  boolean year_view = true;
  
  	//Constructor for the Class
   		ScuCalendar() {
   			prepareGUI();
   		}
   	
  
   		public void prepareGUI() {
		
   			this.setLayout(new BorderLayout());
   			ImageIcon imageIcon = new ImageIcon("src/images/sculogo.png");
   			label = new JLabel("Calendar 2019",imageIcon,SwingConstants.CENTER);
   			label.setPreferredSize(new Dimension(100,100));
   			label.setOpaque(true);
   			label.setBackground(Color.white);
   			JPanel panel = new JPanel();
   			panel.setLayout(new BorderLayout());
   			
   			JRadioButton year, month, week, day;
   			
   		    ButtonGroup buttonGroup = new ButtonGroup();
   			year = new JRadioButton("Year");
   			month = new JRadioButton("Month");
   			week = new JRadioButton("Week");
   			day = new JRadioButton("Day");
   			
   			year.setSize(50, 50);
   			month.setSize(50, 50);
   			//test.setBackground(Color.WHITE);
   			buttonGroup.add(year);
   			buttonGroup.add(month);
   			buttonGroup.add(week);
   			buttonGroup.add(day);
   			
   			//side panel layout
   			JPanel sidepanel = new JPanel();
   			JPanel subsidepanel =  new JPanel();
   			JPanel subsidepanel1 = new JPanel();
   			JPanel subsidePanel2 = new JPanel();
   			JPanel subsidePanel3 = new JPanel();
   			JPanel subsidePanel4 = new JPanel();
   			
   			JLabel labeltextfield1 = new JLabel("Event Name");
   			JLabel labeltextfield2 = new JLabel("Event Date");
   			
   			labeltextfield1.setLabelFor(textfield1);
   			labeltextfield2.setLabelFor(textfield2);
   			
   			JButton addeventbutton = new JButton("Add Event");
   			addeventbutton.addActionListener(new AddEventButtonActionListener());
   			JLabel customizecalendar = new JLabel("Customize Calendar");
   			customizecalendar.setSize(120, 50);
   			customizecalendar.setBackground(Color.MAGENTA);
   			textfield1.add(labeltextfield1,BorderLayout.NORTH);
   			
   			subsidepanel.add(textfield1);
   			subsidepanel.add(textfield2);
   			subsidepanel.add(addeventbutton);
   			subsidepanel.add(year);
   			subsidepanel.add(month);
   			subsidepanel.add(week);
   			subsidepanel.add(day); 
   			
	        subsidepanel.setLayout(new GridLayout(3,3));
   			JLabel changetextcolor = new JLabel("Change Text Color: ");
   			String color[] = {"White","Blue","Gray","Brown"};
   	        JComboBox combo = new JComboBox(color);

   	        subsidePanel2.setLayout(new GridLayout(2,1,10,10));
   	        subsidePanel2.add(changetextcolor);
   	        subsidePanel2.add(combo);
   	        
   	        
   			JLabel history = new JLabel("History");
   			model = new DefaultListModel();
   			model.setSize(50);
   			
   			list = new JList(model);
   			list.setBackground(Color.WHITE);
   			list.setPreferredSize(new Dimension(50,50));
   			JButton deleteeventbutton = new JButton("Delete Event");
   			deleteeventbutton.addActionListener(new DeleteEventButtonActionListener());
   			subsidepanel1.add(history);
   			subsidepanel1.add(list);
   			
   			subsidePanel3.add(list);
   			//subsidepanel1.add(deleteeventbutton);
   			
   			subsidePanel4.add(deleteeventbutton);
   			subsidepanel1.setLayout(new GridLayout(3,1));
   			subsidePanel3.setLayout(new GridLayout(2,1));
   			subsidePanel4.setLayout(new GridLayout(0,2));
   			sidepanel.setLayout(new GridLayout(7,0)); 
   			
   			sidepanel.add(customizecalendar, BorderLayout.CENTER);
   			sidepanel.add(subsidepanel);
   			sidepanel.add(subsidePanel2);
   			sidepanel.add(subsidepanel1);
   			sidepanel.add(subsidePanel3);
   			sidepanel.add(subsidePanel4);
   			this.add(sidepanel);

   			//Action listener label
   			this.add(label1, BorderLayout.SOUTH);
	     
   			table_model = new DefaultTableModel(null,columns);
   			table_view = new DefaultTableModel(null,columns);
   			table = new JTable(table_model);
   			view_table = new JTable(table_model);
	      
   			JPanel tablepanel = new JPanel();
	     
   			JScrollPane pane = new JScrollPane(table);
   			tablepanel.add(pane);
	      
   			table.setRowHeight(100);
   			table.setOpaque(false);
   			table.setFillsViewportHeight(true);
   			table.setBackground(Color.blue);
   			table.setForeground(Color.black);
   			table.setPreferredScrollableViewportSize(new Dimension(800,700));
	      
   			sidepanel.setPreferredSize(new Dimension(150,500));
   			sidepanel.setSize(150,800);
   			table.setCellSelectionEnabled(false); //Disable table cell selection
   			table.setFocusable(false); //Set table focus to false  
	      
   			this.add(panel,BorderLayout.NORTH);
   			
   			panel.add(label,BorderLayout.CENTER);
   			
   			
   			JSplitPane sl = new JSplitPane(SwingConstants.VERTICAL, tablepanel, sidepanel); 
   			sl.setOrientation(SwingConstants.VERTICAL); 
   			this.add(sl);
   			
	      
   			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   			this.setLayout(BorderLayout.CENTER);
   			this.setTitle("SCU Calendar");
   			this.setSize(1200,1200);
   			this.setVisible(true);
   			this.setholidays(); //Calls the function for setting holidays in the holiday array
   			this.updateCalendar(); //Calls the funtion to update the Calender in form of table - adding rows and updating cell according to  
   			table.setDefaultRenderer(Object.class, new CalenderRenderer()); //Set Renderer for Table which highlights the first day of every month
   			year.addActionListener(new sliceYearActionListener());
   			month.addActionListener(new sliceMonthActionListener());
   			week.addActionListener(new sliceWeekActionListener());
   			day.addActionListener(new sliceDayActionListener());
		
   		}
   
   		private void setLayout(String center) {
   			// TODO Auto-generated method stub
	
   		}
  
     
     
   		public void updateCalendar() {
   			
   			Calendar cal = new GregorianCalendar(2019, 00, 01);
   			int startDay = cal.get(Calendar.DAY_OF_WEEK); //Gets the day of the week the first day of month starts at
   			int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR); //Gets total days of year in 2019
         
   			ArrayList<String> ar = new ArrayList<String>(); //Dynamic Array of String to add calendar date to table
   				for (int day = 0; day < numberOfDays; day++) {
   					String date = format1.format(cal.getTime());
   					ar.add(date);
   					cal.add(Calendar.DATE, 1);
   				}
        
   				int i = startDay-1; //Gets the start day position of the Calendar
   				table_model.setRowCount(1); //Initialize RowCount to 1 and then add as per logic 
        
   				//For loop which runs from 0 till array size containing days (365) in year
   				for (int day = 0; day < ar.size(); day++) {
   					//The code checks if the days of week are complete, then add a new row to the table
   					if(i % 7 == 0) {
  					table_model.addRow(new Object[] {columns});
  				}
  	 
   				//The code is the principle code which adds the content inside the JTable cells which represent the days of the Calender, along with displaying the Holiday with message string
   				//The code runs through loop of number of holidays, in this case 10, and compares the day with the holiday. If a matching holiday is found with the day selected, then it adds the 
   				//content of the date to the Calendar Cell and also add the Holiday message and breaks from the loop. If the matching holiday is not found, then it adds the content of the date to the Calendar Cell
   					try { for(int j = 0 ; j < num_of_holidays ; j++) {
   							if(holiday[j].equals(ar.get(day))) {
   								table_model.setValueAt(ar.get(day) + "\n" + holiday_msg[j], i/7, i%7 );
   								break;
   							}
   							else {
   								table_model.setValueAt(ar.get(day), i/7, i%7 );
   							}
   					}
   					} catch (ArrayIndexOutOfBoundsException e) {
   						System.out.println("Array out of bound exception");
   					}
   						i = i+1;
   				}
  
   		}
  
   		//Load holiday array with list of holidays and string array for holiday message to be printed on Calendar
   		public void setholidays() {
  	
   			for(int i = 0 ; i < num_of_holidays ; i++) {
   				try { switch(i) {
   				case 0:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 00); // 00 = January
   					cal.set(Calendar.DAY_OF_MONTH, 01); //New Year�s Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n"+ "New Year's Day";
   					continue;
   				case 1:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 00); // 00 = January
   					cal.set(Calendar.DAY_OF_MONTH, 24); //Birthday of Martin Luther King, Jr.
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n" + "Birthday of Martin" + "\n" + "Luther King, Jr.";
   					continue;
   				case 2:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 01); // 01 = February
   					cal.set(Calendar.DAY_OF_MONTH, 18); //Washington�s Birthday
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n" + "Washington's Birthday";
   					continue;
   				case 3:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 04); // 04 = May 
   					cal.set(Calendar.DAY_OF_MONTH, 27); // Memorial Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n" + "Memorial Day";
   					continue;
   				case 4:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 6); // 06 = July
   					cal.set(Calendar.DAY_OF_MONTH, 04); // Independence Day 
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n"+ "Independence Day";
   					continue;
   				case 5:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 8); // 08 = September 
   					cal.set(Calendar.DAY_OF_MONTH, 02); // Labor Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n"+ "\n" + "Labor Day";
   					continue;
   				case 6:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 9); // 09 = October 
   					cal.set(Calendar.DAY_OF_MONTH, 14); // Columbus Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n" + "Columbus Day";
   					continue;
   				case 7:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 10); // 10 = November
   					cal.set(Calendar.DAY_OF_MONTH, 11); // Veteran's Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n"+ "\n" + "Veteran's Day";
   					continue;
   				case 8:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 10); // 10 = November
   					cal.set(Calendar.DAY_OF_MONTH, 28); // Thanksgiving Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n" + "\n"+ "Thanksgiving Day";
   					continue;
   				case 9:
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 11); // 11 = December
   					cal.set(Calendar.DAY_OF_MONTH, 25); // Christmas Day
   					holiday[i] = format1.format(cal.getTime());
   					holiday_msg[i] = "\n"+ "\n" + "Christmas Day";
   					continue;
   				default: 
   					holiday[i] = "";
   					holiday_msg[i] = "";  
   					cal.set(Calendar.YEAR, 2019);
   					cal.set(Calendar.MONTH, 00); // 00 = January
   					cal.set(Calendar.DAY_OF_MONTH, 01); 
   					continue;
   				}
      	 }
      	catch (ArrayIndexOutOfBoundsException e) {
 	       System.out.println("Array out of bound exception");
 	    }
      	 
      	 
      	 
      }
      cal.set(Calendar.YEAR, 2019);
      cal.set(Calendar.MONTH, 00); // 00 = January
      cal.set(Calendar.DAY_OF_MONTH, 01); 
      
  }
  		
   		//---- Start of Action Listener ---//
  
  		//DeleteEvent Button Action Listener
  		class DeleteEventButtonActionListener implements ActionListener {
	   
  			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
  				if(list != null) {
				 int selectedIndex = list.getSelectedIndex();
				 if(selectedIndex != -1) {
					 model.remove(selectedIndex);
					 event_list.remove(selectedIndex);
					 event_data.remove(selectedIndex);
				 }
  				}
  			}
  		}
  		//End of DeleteEvent Button Action Listener
 
 
  		//AddEvent Button Action Listener
  		class AddEventButtonActionListener implements ActionListener {

  			@Override
			public void actionPerformed(ActionEvent e) {
  				Date date1 = null;
  				String textField1Value = textfield1.getText();
  				String textField2Value = textfield2.getText();
	   		
  				if(!textField2Value.equals(null)) {
  					try {
  						date1 = format2.parse(textField2Value);
  					} 	catch (ParseException ex) {
  								ex.printStackTrace();
  					}
	   	   			String current_date = format1.format(date1);
	   	   			event_list.add(current_date);
	   	   			event_data.add(textField1Value);
  				}
  				
  				int total_row = 0;
  				int total_col = 0;

  				total_row = table_model.getRowCount();
  				total_col = table_model.getColumnCount();
	        
  				for(int row = 0; row < total_row; row++ ) {
  					for(int col = 0 ; col < total_col ; col++) {
  						if(event_list != null) {
  							for(int event_i = 0 ; event_i < event_list.size(); event_i++) {
  								if(table_model.getValueAt(row,col) != null) {
  									if(table_model.getValueAt(row,col).equals(event_list.get(event_i))) {
  										table_model.setValueAt(table_model.getValueAt(row,col) + "\n" + event_data.get(event_i), row, col);
  										model.addElement(event_data.get(event_i));
  										model.addElement("\n");
  									}
  								} 
  							}
  						}
  					}
  				}
	          
  				if(table_view != null) {
  					total_row = table_view.getRowCount();
  					total_col = table_view.getColumnCount();
  						for(int row = 0; row < total_row; row++ ) {
  							for(int col = 0 ; col < total_col ; col++) {
  								if(event_list != null) {
  									for(int event_i = 0 ; event_i < event_list.size(); event_i++) {
  										if(table_view.getValueAt(row,col) != null) {
  											if(table_view.getValueAt(row,col).equals(event_list.get(event_i))) {
  												table_view.setValueAt(table_view.getValueAt(row,col) + "\n" + event_data.get(event_i), row, col);
  											}
  										} 
  									}
  								}
  							}
  						}
  				}
			
  			}

  		}	
  		//End of AddEvent Button Listener
  		
  		//Slice Month ActionListener
  		
  		class sliceMonthActionListener implements ActionListener{
  			
  			 public void actionPerformed(ActionEvent actionEvent) {
  		        year_view = false;
  		        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
  		        System.out.println("Selected: " + aButton.getText());
  		        int Row_Value = 0;
  		        int Next_Row_Value = 0;
  		        int total_row = table_model.getRowCount();
  		        int total_col = table_model.getColumnCount();
  		        
  		        Calendar cal1 = new GregorianCalendar(2019, 00, 01);
  		        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault());
  		        Date date = new Date();
  		        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  		        
  		        Date date1 = calendar1.getTime();
  		        String current_date = format1.format(date1);
  		        int month = localDate.getMonthValue() - 1;
  		       
  		        cal1.set(Calendar.YEAR, 2019);
  		        cal1.set(Calendar.MONTH, month+1); 
  		        cal1.set(Calendar.DAY_OF_MONTH, 01);
  		        Date date2 = cal1.getTime();
  		        String next_date = format1.format(date2);
  		        table_view.setRowCount(0);
  		             
  		        int row_cnt = 0;

  		        for (int row = 0; row < total_row ; row++) {
  		    	  for (int col = 0; col < total_col ; col++) {
  		    		if(table_model.getValueAt(row,col) != null) {
  		        	   if(table_model.getValueAt(row,col).equals(current_date)) {
  		        	     Row_Value = row;
  		        	   }
  		    	  }
  		         }	
  		        }
  		     
  		         for (int row = 0; row < total_row ; row++) {
  		    	   for (int col = 0; col < total_col ; col++) {
  		    		 if(table_model.getValueAt(row,col) != null) {
  		        	   if(table_model.getValueAt(row,col).equals(next_date)) {
  		        	     Next_Row_Value = row;
  		        	   }
  		    		 }
  		    	   }
  		         }

  		        row_cnt = 0;
  		        for (int row = 0; row < total_row ; row++) {
  		   	      if(row >= Row_Value-1 && row < Next_Row_Value) {
  		   	        table_view.addRow(new Object[] {columns});
  		   	        for (int col = 0 ; col < table_model.getColumnCount(); col++) {
  		   	          table_view.setValueAt(table_model.getValueAt(row, col), row_cnt, col);
  		            }
  		   	          row_cnt = row_cnt + 1;
  		   	      }
  		        }
  		        table.setModel(table_view);
  		   	  }
  		}
  		//End of Slice Month Action Listener
  		
  		
  		//Start of Slice Year Action Listener
  		
  		class sliceYearActionListener implements ActionListener {

  			public void actionPerformed(ActionEvent actionEvent) {
            year_view = true;
            AbstractButton aButton = (AbstractButton) actionEvent.getSource();
            System.out.println("Selected: " + aButton.getText());
            
            table.setModel(table_model);
       	  }
  		}
  		
  		//End of Slice Year Action Listener
  		
  		//Slice Week Action Listener
  		class sliceWeekActionListener implements ActionListener {
  			public void actionPerformed(ActionEvent actionEvent) {
    		    year_view = false;
    	        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
    	        System.out.println("Selected: " + aButton.getText());
    	        int Row_Value = 0;
    	        int total_row = table_model.getRowCount();
    	        int total_col = table_model.getColumnCount();
    	        System.out.println(total_row);
    	        System.out.println(total_col);
    	        
    	        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault());
    	        Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                Date date1 = calendar1.getTime();
                String current_date = format1.format(date1);
                System.out.println(current_date);
                table_view.setRowCount(0);
    	        
    	       
             int row_cnt = 0;
             
             table_view.addRow(new Object[] {columns});

             for (int row = 0; row < total_row ; row++) {
            	 for (int col = 0; col < total_col ; col++) {
            		 if(table_model.getValueAt(row,col) != null)
                	   if(table_model.getValueAt(row,col).equals(current_date)) {
                	    Row_Value = row;
                	   }
            	     }
                 }	 
             
             
    	        for (int row = 0; row < total_row ; row++) {
    	   	         if(row == Row_Value) {
    	   	          for (int col = 0 ; col < table_model.getColumnCount(); col++) {
    	   	             table_view.setValueAt(table_model.getValueAt(row, col), row_cnt, col);
                      }
    	   	         }
    	        }
    	        
    	        table.setModel(table_view);
    	        
    	  }
  		}
  		//End of Slice Week Action Listener
  		
  		//Start of Slice Day Action Listener
  		class sliceDayActionListener implements ActionListener {
  	   	    
  	    	 public void actionPerformed(ActionEvent actionEvent) {
  	    		    year_view = false;
  	    	        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
  	    	        System.out.println("Selected: " + aButton.getText());
  	    	        int Row_Value = 0;
  	    	        int Col_Value = 0;
  	    	        int total_row = table_model.getRowCount();
  	    	        int total_col = table_model.getColumnCount();
  	    	        
  	    	        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault());
  	                
  	                Date date1 = calendar1.getTime();
  	                String current_date = format1.format(date1);
  	                table_view.setRowCount(0);
  	    	        
  	    	       
  	             int row_cnt = 0;
  	             
  	             table_view.addRow(new Object[] {columns});

  	             for (int row = 0; row < total_row ; row++) {
  	            	 for (int col = 0; col < total_col ; col++) {
  	            		 if(table_model.getValueAt(row,col) != null)
  	                	   if(table_model.getValueAt(row,col).equals(current_date)) {
  	                	    Row_Value = row;
  	                	    Col_Value = col;
  	                	   }
  	            	     }
  	                 }	 
  	             
  	             
  	    	        for (int row = 0; row < total_row ; row++) {
  	    	   	         if(row == Row_Value) {
  	    	   	          for (int col = 0 ; col < table_model.getColumnCount(); col++) {
  	    	   	        	 if(col == Col_Value) {
  	    	   	               table_view.setValueAt(table_model.getValueAt(row, col), row_cnt, col);
  	    	   	        	 }
  	    	   	        	 else {
  	    	   	        		table_view.setValueAt(null, row_cnt, col);
  	    	   	        	 }
  	                      }
  	    	   	         }
  	    	        }
  	    	        
  	    	        table.setModel(table_view);
  	    	        
  	    	  }

  	     } 
  		//End of Slice Week Action Listener
  		
  		//-- End of Action Listener --//
}
