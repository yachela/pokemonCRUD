package ar.edu.davinci.UI;
import ar.edu.davinci.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserTableFrame extends JFrame {

    public UserTableFrame(List<User> users) {
        setTitle("Listado de Usuarios");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");


        for (User user : users) {
            Object[] row = {user.getId(), user.getName()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);

        setVisible(true);
    }
}
