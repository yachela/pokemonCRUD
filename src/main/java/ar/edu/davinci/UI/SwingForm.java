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

        private JTextField nameField;
        private JTextField phoneField;

        public SwingForm() {
            setTitle("Form");
            setSize(300, 200);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));

            JLabel nameLabel = new JLabel("Nombre:");
            nameField = new JTextField();
            JLabel phoneLabel = new JLabel("Telefono:");
            phoneField = new JTextField();

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(phoneLabel);
            panel.add(phoneField);


            JButton sendButton = new JButton("Guardar");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendData();
                }
            });

            panel.add(sendButton);

            add(panel);

            JButton cancelButton = new JButton("Cancelar");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });


            JButton listAllButton = new JButton("Ver lista");
            listAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listAllPersons();
                }
            });
            panel.add(listAllButton);

            add(panel);
        }

        private void listAllPersons() {
            UserDAO userDAO = new UserDAOImplH2();
            List<User> users = userDAO.getAllUsers();
            for (User user : users) {
                System.out.println(user);
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    UserTableFrame tableFrame = new UserTableFrame(users);
                    tableFrame.setVisible(true);
                }
            });
        }


        private void sendData() {
            String name = nameField.getText();
            String phone = phoneField.getText();


            System.out.println("Name: " + name + ", Phone: " + phone);

            UserDAO userDAO = new UserDAOImplH2();
            User user = new User(name, phone);
            userDAO.insertUser(user);
        }

        public static void main(String[] args) {
            SwingForm form = new SwingForm();
            form.setVisible(true);
        }
    }
