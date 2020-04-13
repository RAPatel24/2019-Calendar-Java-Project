package assignment2;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

class CalenderRenderer implements TableCellRenderer  {
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

    	
    	JTextArea textarea = new JTextArea();
    	textarea.setBackground(new Color(135,206,250));
    	textarea.setForeground(Color.black);
    	
       	
    	if (value != null) {
       		textarea.setText(value.toString());
         }
        
        int Row_Value = 0; //Initialize Row Value
        GregorianCalendar cal = new GregorianCalendar(2019, 00, 1); //Creating a new Gregorian Calendar for calculating first day of month
        
	  for(int i = 0; i < 12 ; i++) {
        	
            cal.set(Calendar.MONTH, i);
        	
            int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
             
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    
	      //Logic checks if the row selected matches the row which contains 1st day of month and column matchs first day of the week of the month
	      //If matched, then the cell selected by row and column represents the 1st day of month and highlight the cell as per requirement
            if (row == Row_Value && column == (dayOfWeek - 1)) {
            	textarea.setForeground(Color.BLUE);
            }
            
	      //The logic sets which should be the next row which contains the first day of next month
	      //If the first day of current month falls within the first half of the week, then the row which contains the next month's first date would be apart by 5 rows, else it would be apart by 4 rows
	      //Special case for exception to this logic would be when the last day of month falls on saturday, which means that the first day of next month would need to add 1 more row - hence to add that special case
	      //the logic was added to OR the previous logic with (dayOfMonth + dayOfWeek == 36) which will be only case if the last day of month was Saturday
            if ((((dayOfMonth + dayOfWeek -1)%7) == 1) || (dayOfMonth + dayOfWeek == 36) ) {
            	Row_Value = Row_Value + 5;
            }
            else { 
            	Row_Value = Row_Value + 4;
            }
        } 
       
        
	  
	return textarea;
    }
}
