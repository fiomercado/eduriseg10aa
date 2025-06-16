package panels;

import javax.swing.*;
import java.awt.*;

public class SignOutPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public SignOutPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        ImageIcon icon = new ImageIcon("");
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Sign Out");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(123, 8, 40));

        JLabel messageLabel = new JLabel("Are you sure you want to sign out?");
        messageLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signOutButton = new JButton("Yes, Sign Out");
        JButton cancelButton = new JButton("Cancel");

        signOutButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signOutButton.setBackground(new Color(220, 53, 69));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setFocusPainted(false);
        signOutButton.setPreferredSize(new Dimension(160, 35));

        cancelButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(120, 35));

        signOutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "You will be logged out of the application.\nAny unsaved changes will be lost.",
                    "Confirm Sign Out",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(
                        this,
                        "You have been successfully signed out.\nThank you for using EduRise Scholarship Program!",
                        "Signed Out",
                        JOptionPane.INFORMATION_MESSAGE
                );

                System.exit(0);
            }
        });

        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        buttonPanel.add(signOutButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(cancelButton);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(buttonPanel);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(contentPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(new Color(240, 240, 240));

        JButton backButton = new JButton("← Back to Home");
        backButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        footerPanel.add(backButton);
        add(footerPanel, BorderLayout.SOUTH);
    }
}