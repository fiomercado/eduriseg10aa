package model;

import com.formdev.flatlaf.FlatLightLaf;
import panels.FillOutFormPanel;
import panels.FinalizeApplicationPanel;
import panels.HelpSupportPanel;
import panels.SignOutPanel;
import panels.ProfileInfoPanel;
import panels.AboutUsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MainApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private FillOutFormPanel formPanel;
    private FinalizeApplicationPanel finalizePanel;

    private String userName;
    private String userGmail;

    public MainApp(String userName, String userGmail) {
        this.userName = userName;
        this.userGmail = userGmail;

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("defaultFont", new Font("Century Gothic", Font.PLAIN, 14));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        initUI();
    }

    public MainApp(String username) {
    }

    private void initUI() {
        setTitle("EduRise Scholarship Program");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel whiteBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        whiteBar.setBackground(Color.WHITE);
        whiteBar.setPreferredSize(new Dimension(900, 45));

        ImageIcon logoIcon = null;
        try {
            logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/logoremoveprev.png")));
        } catch (Exception e) {
            System.err.println("Logo image not found");
        }
        if (logoIcon != null) {
            Image scaledImage = logoIcon.getImage().getScaledInstance(55, 40, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            whiteBar.add(logoLabel);
        }

        JPanel statusRow = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(123, 8, 40), getWidth(), 0, new Color(82, 43, 71));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        statusRow.setPreferredSize(new Dimension(900, 60));

        JLabel title = new JLabel("Welcome, " + userName + "!");
        title.setFont(new Font("Century Gothic", Font.BOLD, 40));
        title.setForeground(new Color(232, 178, 59));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);

        JPanel leftStatus = new JPanel();
        leftStatus.setLayout(new BoxLayout(leftStatus, BoxLayout.Y_AXIS));
        leftStatus.setOpaque(false);
        leftStatus.add(titlePanel);

        JButton aboutUsBtn = makeFlatLink("About Us");
        aboutUsBtn.addActionListener(e -> cardLayout.show(mainPanel, "AboutUs"));

        ImageIcon profileIcon = null;
        try {
            profileIcon = new ImageIcon(getClass().getResource("/icon/profile.png"));
        } catch (Exception e) {
            System.err.println("Profile icon image not found");
        }
        JLabel profileImageLabel = new JLabel();
        if (profileIcon != null) {
            Image resizedImg = profileIcon.getImage().getScaledInstance(53, 53, Image.SCALE_SMOOTH);
            profileImageLabel.setIcon(new ImageIcon(resizedImg));
        }
        profileImageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileImageLabel.setToolTipText("View Profile");
        profileImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "Profile");
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(aboutUsBtn);
        rightPanel.add(profileImageLabel);

        statusRow.add(leftStatus, BorderLayout.WEST);
        statusRow.add(rightPanel, BorderLayout.EAST);

        JPanel infoBar = new JPanel(new BorderLayout());
        infoBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        infoBar.setBackground(new Color(131, 95, 121));

        JLabel nameLabel = new JLabel("Name: " + userName);
        JLabel emailLabel = new JLabel("Gmail: " + userGmail);
        nameLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        infoBar.add(nameLabel, BorderLayout.WEST);
        infoBar.add(emailLabel, BorderLayout.EAST);

        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BoxLayout(dashboardPanel, BoxLayout.Y_AXIS));
        dashboardPanel.setBackground(Color.WHITE);

        JButton btnForm = makeModernButton("Fill-out Form", "/icon/filloutbg.png");
        JButton btnFinalize = makeModernButton("Finalize Application", "/icon/finalizebg.png");
        btnForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFinalize.setAlignmentX(Component.CENTER_ALIGNMENT);
        dashboardPanel.add(Box.createVerticalStrut(30));
        dashboardPanel.add(btnForm);
        dashboardPanel.add(Box.createVerticalStrut(20));
        dashboardPanel.add(btnFinalize);

        JPanel footer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(123, 8, 40), getWidth(), 0, new Color(82, 43, 71));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        footer.setPreferredSize(new Dimension(900, 30));
        JButton help = makeFlatLink("Help & Support");
        JButton signOut = makeFlatLink("Sign out");
        footer.add(help, BorderLayout.WEST);
        footer.add(signOut, BorderLayout.EAST);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(dashboardPanel, BorderLayout.CENTER);

        finalizePanel = new FinalizeApplicationPanel(cardLayout, mainPanel);
        formPanel = new FillOutFormPanel(cardLayout, mainPanel, formData -> {
            finalizePanel.setFormData(formData);
            cardLayout.show(mainPanel, "Finalize");
        });

        HelpSupportPanel helpPanel = new HelpSupportPanel(cardLayout, mainPanel);
        SignOutPanel signOutPanel = new SignOutPanel(cardLayout, mainPanel);
        ProfileInfoPanel profilePanel = new ProfileInfoPanel(cardLayout, mainPanel, userName, userGmail);
        AboutUsPanel aboutUsPanel = new AboutUsPanel(cardLayout, mainPanel);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(formPanel, "Form");
        mainPanel.add(finalizePanel, "Finalize");
        mainPanel.add(helpPanel, "Help");
        mainPanel.add(signOutPanel, "SignOut");
        mainPanel.add(profilePanel, "Profile");
        mainPanel.add(aboutUsPanel, "AboutUs");

        btnForm.addActionListener(e -> cardLayout.show(mainPanel, "Form"));
        btnFinalize.addActionListener(e -> {
            var data = formPanel.collectFormData();
            if (data != null) {
                finalizePanel.setFormData(data);
                cardLayout.show(mainPanel, "Finalize");
            } else {
                JOptionPane.showMessageDialog(this, "Please complete the form first.", "Incomplete", JOptionPane.WARNING_MESSAGE);
                cardLayout.show(mainPanel, "Form");
            }
        });
        help.addActionListener(e -> cardLayout.show(mainPanel, "Help"));
        setupSignOutButton(signOut);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(whiteBar, BorderLayout.NORTH);
        topContainer.add(statusRow, BorderLayout.CENTER);
        topContainer.add(infoBar, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        cardLayout.show(mainPanel, "Home");
    }

    private void setupSignOutButton(JButton signOutButton) {
        signOutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new window.LoginWindow().setVisible(true)); // reopen login window
        });
    }

    public void showApp() {
        setVisible(true);
    }

    private JLabel makeStatusLabel(String text, Color color, boolean bold) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Century Gothic", bold ? Font.BOLD : Font.PLAIN, 12));
        return label;
    }

    private JButton makeModernButton(String text, String imagePath) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Century Gothic", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(280, 45));
        btn.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left

        ImageIcon icon = null;
        try {
            icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(imagePath)));
            Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scale image if needed
            icon = new ImageIcon(image);
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
        }

        if (icon != null) {
            btn.setIcon(icon);
            btn.setIconTextGap(10);
        }

        return btn;
    }

    private JButton makeFlatLink(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setForeground(new Color(232, 178, 59));
            }

            public void mouseExited(MouseEvent evt) {
                btn.setForeground(Color.WHITE);
            }
        });

        return btn;
    }
}