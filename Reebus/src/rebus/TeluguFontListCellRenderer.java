package rebus;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class TeluguFontListCellRenderer extends DefaultListCellRenderer{
	
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setFont(new Font("gautami", Font.PLAIN, 16));
        return this;
    }

}
