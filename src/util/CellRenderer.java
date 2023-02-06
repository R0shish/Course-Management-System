package util;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import models.course.Course;
import models.course.Module;
import models.user.SystemUser;
import models.user.Teacher;

public class CellRenderer extends JPanel implements ListCellRenderer<Object> {
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
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        if (value instanceof SystemUser) {
            SystemUser user = (SystemUser) value;
            titleLabel.setText(user.getName());
            removeAll();
            add(titleLabel);
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                for (Module module : teacher.getModules()) {
                    JLabel moduleLabel = new JLabel("   " + module.getName());
                    moduleLabel.setFont(new Font("Futura", Font.ITALIC, 10));
                    add(moduleLabel);
                }
            }
        } else if (value instanceof Course) {
            Course course = (Course) value;
            titleLabel.setText("📌  " + course.getName());
            removeAll();
            add(titleLabel);
            for (Module module : course.getModules()) {
                JLabel moduleLabel = new JLabel("           " + module.getName());
                moduleLabel.setFont(new Font("Futura", Font.ITALIC, 10));
                add(moduleLabel);
            }
        }
        titleLabel.setFont(new Font("Futura", Font.BOLD, 15));
        subtitleLabel.setFont(new Font("Futura", Font.ITALIC, 10));
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        return this;
    }
}
