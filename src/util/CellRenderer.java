package util;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import models.SystemUser;

public class CellRenderer extends JPanel implements ListCellRenderer<SystemUser> {
    private static final long serialVersionUID = 6422050714652017814L;
    private JLabel titleLabel;
    private JLabel subtitleLabel;

    public CellRenderer() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel();
        subtitleLabel = new JLabel();
        add(titleLabel);
        add(subtitleLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends SystemUser> list, SystemUser value, int index,
            boolean isSelected, boolean cellHasFocus) {
        titleLabel.setText(value.getName());
        titleLabel.setFont(new Font("Futura", Font.BOLD, 15));
        subtitleLabel.setText("Module Name");
        subtitleLabel.setFont(new Font("Futura", Font.ITALIC, 10));
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        return this;
    }
}
