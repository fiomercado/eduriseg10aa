package panels;

import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ProfileInfoPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField nameField, emailField, phoneField, addressField;
    private JComboBox<String> statusCombo;
    private JTextArea notesArea;
    private boolean isEditMode = false;
    private boolean isAdmin = false;

    private JLabel nameLabel, emailLabel, appIdLabel, profilePicLabel;
    private BufferedImage profileImage = null;

    private String userName;
    private String userEmail;
    private String userPhone = "+63 912 345 6789";
    private String userAddress = "123 Main Street, Quezon City, Metro Manila";
    private String applicationStatus = "Pending";
    private String applicationId = generateApplicationId();

    private JButton editButton, saveButton, deleteButton;

    public ProfileInfoPanel(CardLayout cardLayout, JPanel mainPanel, String userName, String userEmail) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.userName = userName;
        this.userEmail = userEmail;

        setupLayout();
        loadProfileData();
        setFieldsEditable(false);
        updateButtons();
    }

    private String generateApplicationId() {
        return "APP-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    private BufferedImage createDefaultProfileImage() {
        int size = 60;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.decode("#E8B23B"));
        g2d.fillRoundRect(0, 0, size, size, 12, 12);

        g2d.setColor(Color.WHITE);
        g2d.fillOval(20, 10, 20, 20);
        g2d.fillRoundRect(18, 30, 24, 15, 8, 8);

        g2d.dispose();
        return img;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(123, 8, 40), getWidth(), 0, new Color(82, 43, 71));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        int picSize = 60;
        JPanel profilePicContainer = new JPanel(new BorderLayout());
        profilePicContainer.setOpaque(false);
        profilePicContainer.setPreferredSize(new Dimension(picSize, picSize));

        BufferedImage defaultProfileImg = createDefaultProfileImage();
        ImageIcon defaultIcon = new ImageIcon(defaultProfileImg.getScaledInstance(picSize, picSize, Image.SCALE_SMOOTH));

        profilePicLabel = new JLabel(defaultIcon, SwingConstants.CENTER);
        profilePicLabel.setPreferredSize(new Dimension(picSize, picSize));
        profilePicLabel.setOpaque(true);
        profilePicLabel.setBackground(new Color(255, 255, 255, 30));
        profilePicLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        profilePicLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        profilePicLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isEditMode) showPhotoOptions();
                else JOptionPane.showMessageDialog(ProfileInfoPanel.this,
                        "Click 'Edit Profile' first to change your photo.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        profilePicContainer.add(profilePicLabel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setOpaque(false);

        nameLabel = new JLabel(userName);
        nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
        nameLabel.setForeground(new Color(232, 178, 59));
        emailLabel = new JLabel(userEmail);
        emailLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        emailLabel.setForeground(Color.WHITE);
        appIdLabel = new JLabel("App ID: " + applicationId);
        appIdLabel.setFont(new Font("Century Gothic", Font.BOLD, 11));
        appIdLabel.setForeground(new Color(220, 220, 220));

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(emailLabel);
        userInfoPanel.add(appIdLabel);

        rightPanel.add(userInfoPanel, BorderLayout.EAST);

        headerPanel.add(profilePicContainer, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.add(createPersonalInfoSection());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createApplicationSection());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createAccountSettingsSection());

        JScrollPane scrollPane = new JScrollPane(contentPanel);

        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(new Color(240, 240, 240));

        JButton backButton = new JButton("\u2190 Back to Home");
        saveButton = new JButton("Save Changes");
        editButton = new JButton("Edit Profile");
        deleteButton = new JButton("Delete Account");
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        saveButton.addActionListener(e -> saveProfileChanges());
        editButton.addActionListener(e -> toggleEditMode());
        deleteButton.addActionListener(e -> deleteAccount());

        footerPanel.add(backButton);
        footerPanel.add(editButton);
        footerPanel.add(saveButton);
        footerPanel.add(deleteButton);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void setFieldsEditable(boolean editable) {
        nameField.setEditable(editable);
        emailField.setEditable(editable);
        phoneField.setEditable(editable);
        addressField.setEditable(editable);
        notesArea.setEditable(editable);
        statusCombo.setEnabled(isAdmin); // Only admins can change status
        isEditMode = editable;
        updateButtons();
    }

    private void updateButtons() {
        saveButton.setEnabled(isEditMode);
        editButton.setEnabled(!isEditMode);
    }

    private void toggleEditMode() {
        setFieldsEditable(true);
    }

    private void saveProfileChanges() {
        if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userName = nameField.getText().trim();
        userEmail = emailField.getText().trim();
        userPhone = phoneField.getText().trim();
        userAddress = addressField.getText().trim();

        if (isAdmin) {
            applicationStatus = (String) statusCombo.getSelectedItem();
        }

        updateHeaderLabels();
        setFieldsEditable(false);
        JOptionPane.showMessageDialog(this, "Profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateHeaderLabels() {
        nameLabel.setText(userName);
        emailLabel.setText(userEmail);
        appIdLabel.setText("App ID: " + applicationId);
    }

    private JPanel createPersonalInfoSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Personal Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        addressField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Name:"), gbc); gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Email:"), gbc); gbc.gridx = 1; panel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Phone:"), gbc); gbc.gridx = 1; panel.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(new JLabel("Address:"), gbc); gbc.gridx = 1; panel.add(addressField, gbc);

        return panel;
    }

    private JPanel createApplicationSection() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Application"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        statusCombo = new JComboBox<>(new String[]{"Pending", "Approved", "Rejected"});
        statusCombo.setSelectedItem(applicationStatus);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; panel.add(statusCombo, gbc);

        return panel;
    }

    private JPanel createAccountSettingsSection() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Notes"));

        notesArea = new JTextArea(5, 40);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
        return panel;
    }

    private void showPhotoOptions() {
        String[] options = {"Change Photo", "Remove Photo", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
                "What would you like to do with your profile photo?",
                "Profile Photo Options",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        switch (choice) {
            case 0 -> changeProfilePhoto();
            case 1 -> removeProfilePicture();
        }
    }

    private void changeProfilePhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Photo");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                profileImage = ImageIO.read(file);
                Image scaledImage = profileImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                profilePicLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeProfilePicture() {
        profileImage = null;
        BufferedImage defaultProfileImg = createDefaultProfileImage();
        profilePicLabel.setIcon(new ImageIcon(defaultProfileImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
    }

    private void deleteAccount() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Delete Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            userName = "";
            userEmail = "";
            userPhone = "";
            userAddress = "";
            applicationStatus = "Pending";
            applicationId = generateApplicationId();
            profileImage = null;

            loadProfileData();
            removeProfilePicture();
            setFieldsEditable(false);

            JOptionPane.showMessageDialog(this,
                    "Your account has been deleted.",
                    "Account Deleted",
                    JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Home");
        }
    }

    private void loadProfileData() {
        nameField.setText(userName);
        emailField.setText(userEmail);
        phoneField.setText(userPhone);
        addressField.setText(userAddress);
        statusCombo.setSelectedItem(applicationStatus);
        notesArea.setText("");
        updateHeaderLabels();
    }
}
