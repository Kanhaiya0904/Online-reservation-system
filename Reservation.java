import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame {
    // Dummy users and ticket database
    Map<String, String> users = new HashMap<>();
    Map<String, String> reservations = new HashMap<>();

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    public Main() {
        users.put("user", "pass"); // Dummy login

        // Frame settings
        setTitle("Online Reservation System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panels
        mainPanel.add(loginPanel(), "login");
        mainPanel.add(reservationPanel(), "reserve");
        mainPanel.add(cancellationPanel(), "cancel");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
        setVisible(true);
    }

    // Login Panel
    JPanel loginPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());
            if (users.containsKey(u) && users.get(u).equals(p)) {
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        });

        return panel;
    }

    // Main Menu Panel
    void showMainMenu() {
        JFrame menu = new JFrame("Main Menu");
        menu.setSize(300, 200);
        menu.setLocationRelativeTo(null);
        menu.setLayout(new GridLayout(3, 1, 10, 10));
        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton reserveBtn = new JButton("Reservation");
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton exitBtn = new JButton("Logout");

        reserveBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "reserve");
            menu.dispose();
        });
        cancelBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "cancel");
            menu.dispose();
        });
        exitBtn.addActionListener(e -> {
            menu.dispose();
            cardLayout.show(mainPanel, "login");
        });

        menu.add(reserveBtn);
        menu.add(cancelBtn);
        menu.add(exitBtn);
        menu.setVisible(true);
    }

    // Reservation Panel
    JPanel reservationPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();

        String[] trains = {
            "12345 - Rajdhani Express",
            "23456 - Shatabdi Express",
            "34567 - Duronto Express",
            "45678 - Garib Rath Express",
            "56789 - Humsafar Express",
            "67890 - Vande Bharat Express",
            "78901 - Tejas Express",
            "89012 - Gatimaan Express",
            "90123 - Suvidha Express",
            "11223 - Sampark Kranti Express",
            "22334 - Jan Shatabdi Express",
            "33445 - Antyodaya Express",
            "44556 - Uday Express",
            "55667 - Intercity Express",
            "66778 - Double Decker Express",
            "77889 - Mahamana Express",
            "88990 - Kavi Guru Express",
            "99001 - Yuva Express",
            "10001 - Holiday Special",
            "11002 - Festival Special",
            "12003 - Mail Express",
            "13004 - MEMU Express",
            "14005 - Passenger Train"
        };
        JComboBox<String> trainBox = new JComboBox<>(trains);

        String[] classes = {"Sleeper", "AC", "First Class"};
        JComboBox<String> classBox = new JComboBox<>(classes);

        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
        JTextField dateField = new JTextField();

        JButton submitBtn = new JButton("Reserve");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Age:")); panel.add(ageField);
        panel.add(new JLabel("Train:")); panel.add(trainBox);
        panel.add(new JLabel("Class:")); panel.add(classBox);
        panel.add(new JLabel("From:")); panel.add(fromField);
        panel.add(new JLabel("To:")); panel.add(toField);
        panel.add(new JLabel("Date of Journey:")); panel.add(dateField);
        panel.add(submitBtn); panel.add(backBtn);

        submitBtn.addActionListener(e -> {
            String pnr = "PNR" + (10000 + new Random().nextInt(90000));
            reservations.put(pnr, nameField.getText() + " | " + trainBox.getSelectedItem() + " | " + classBox.getSelectedItem()
                    + " | From: " + fromField.getText() + " To: " + toField.getText() + " Date: " + dateField.getText());
            JOptionPane.showMessageDialog(this, "Ticket Reserved!\nPNR: " + pnr);
        });

        backBtn.addActionListener(e -> showMainMenu());

        return panel;
    }

    // Cancellation Panel
    JPanel cancellationPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField pnrField = new JTextField();
        JButton searchBtn = new JButton("Search");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Enter PNR Number:"));
        panel.add(pnrField);
        panel.add(searchBtn);
        panel.add(backBtn);

        searchBtn.addActionListener(e -> {
            String pnr = pnrField.getText().trim();
            if (reservations.containsKey(pnr)) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Ticket Details:\n" + reservations.get(pnr) + "\nDo you want to cancel this ticket?",
                        "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    reservations.remove(pnr);
                    JOptionPane.showMessageDialog(this, "Ticket Cancelled Successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "PNR Not Found.");
            }
        });

        backBtn.addActionListener(e -> showMainMenu());

        return panel;
    }

    public static void main(String[] args) {
        new Main();
    }
}
