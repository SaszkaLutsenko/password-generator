package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class PasswordGenerator extends JFrame {
    private JTextField numberField;
    private JCheckBox checkbox;
    private JLabel passwordLabel;
    private String generatedPassword = "";

    public PasswordGenerator() {
        super("Password Generator");
        setBounds(400, 200, 500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = getContentPane();
        container.setLayout(new BorderLayout(10, 10));
        container.setBackground(new Color(240, 240, 240));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel numberLabel = new JLabel("How many characters should the password have?");
        numberField = new JTextField("", 10);
        numberField.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(numberLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(numberField, gbc);

        checkbox = new JCheckBox("Include special characters", true);
        checkbox.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(checkbox, gbc);

        passwordLabel = new JLabel("Generated password: ");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(0, 102, 204));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(passwordLabel, gbc);

        container.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(100, 100, 100));

        JButton generateButton = new JButton("Generate");
        generateButton.setPreferredSize(new Dimension(120, 30));
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setBackground(new Color(70, 130, 180));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        generateButton.addActionListener(new ButtonEventManager());

        JButton copyButton = new JButton("Copy");
        copyButton.setPreferredSize(new Dimension(100, 30));
        copyButton.setFont(new Font("Arial", Font.BOLD, 14));
        copyButton.setBackground(new Color(34, 139, 34));
        copyButton.setForeground(Color.WHITE);
        copyButton.setFocusPainted(false);
        copyButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        copyButton.addActionListener(e -> copyToClipboard());

        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);

        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    private String generatePassword(int length, boolean includeSpecial) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_+=<>?";

        String chars = letters + digits;
        if (includeSpecial) {
            chars += special;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();
    }

    private void copyToClipboard() {
        if (!generatedPassword.isEmpty()) {
            StringSelection selection = new StringSelection(generatedPassword);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            JOptionPane.showMessageDialog(null, "Password copied to clipboard!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No password generated yet!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ButtonEventManager implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int length = Integer.parseInt(numberField.getText());
                boolean includeSpecial = checkbox.isSelected();
                generatedPassword = generatePassword(length, includeSpecial);
                passwordLabel.setText("Generated password: " + generatedPassword);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        PasswordGenerator app = new PasswordGenerator();
        app.setVisible(true);
    }
}
