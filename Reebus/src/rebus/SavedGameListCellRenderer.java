package rebus;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class SavedGameListCellRenderer extends DefaultListCellRenderer {
	
	    public Component getListCellRendererComponent(JList<?> list,
	                                 Object value,
	                                 int index,
	                                 boolean isSelected,
	                                 boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	        if (value instanceof SavedGame) {
	            SavedGame savedGame = (SavedGame)value;
	            setText("Solution Word: "+savedGame.getSolutionWord().toString());
	        }
	        setFont(new Font("gautami", Font.PLAIN, 16));
	        return this;
	    }
	
}
