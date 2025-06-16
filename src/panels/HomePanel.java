package panels;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Home Panel", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton fillOutForm = new JButton("Fill Out Form");
        JButton finalizeApp = new JButton("Finalize Application");
        JButton help = new JButton("Help & Support");
        JButton signOut = new JButton("Sign Out");
        JButton profile = new JButton("Profile Info");

        fillOutForm.addActionListener(e -> cardLayout.show(mainPanel, "Form"));
        finalizeApp.addActionListener(e -> cardLayout.show(mainPanel, "Finalize"));
        help.addActionListener(e -> cardLayout.show(mainPanel, "Help"));
        signOut.addActionListener(e -> cardLayout.show(mainPanel, "SignOut"));
        profile.addActionListener(e -> cardLayout.show(mainPanel, "Profile"));

        buttonsPanel.add(fillOutForm);
        buttonsPanel.add(finalizeApp);
        buttonsPanel.add(help);
        buttonsPanel.add(signOut);
        buttonsPanel.add(profile);

        add(buttonsPanel, BorderLayout.CENTER);
    }
}


