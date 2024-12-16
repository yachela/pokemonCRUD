package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.*;
import ar.edu.davinci.Model.User;

import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SwingForm extends JFrame {
    private JPanel panel;
    private JTextField nameField;
    private JTextField phoneField;

    public SwingForm() {
        setTitle("Formulario de Registro");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Nombre:");
        nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Teléfono:");
        phoneField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneLabel);
        panel.add(phoneField);

        JButton sendButton = new JButton("Guardar");
        sendButton.addActionListener(e -> sendData());
        panel.add(sendButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);
    }

    public JPanel getContentPane() {
        return panel;
    }

    private void sendData() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        System.out.println("Name: " + name + ", Phone: " + phone);

        UserDAOImplH2 userDAO = new UserDAOImplH2();
        User user = new User(name, phone);
        userDAO.insertUser(user);
    }
}