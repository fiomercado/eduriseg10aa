package panels;

import swing.UIUtils;
import model.FormData;
import model.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class FillOutFormPanel extends JPanel {

    // Personal Information fields (for applicantinformation table)
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField suffixField;
    private JTextField addressField;
    private JComboBox<String> houseTypeField;
    private JTextField incomeField;
    private JTextField appIDField; // Added for user input AppID

    // Student Information fields (for studentinformation table)
    private JTextField studIDField;
    private JTextField studAgeField;
    private JTextField studBirthdateField;
    private JTextField studCitizenshipField;
    private JComboBox<String> studCivilStatusField;
    private JTextField studReligionField;
    private JTextField studPermanentAddField;
    private JTextField studCellNoField;
    private JTextField studEmailField;
    private JTextField degreeProgramField;
    private JTextField gwaField;
    private JTextField schoolIDField;
    private JTextField schoolNameField;
    private JTextField schoolAddField;
    private JTextField schoolEmailField;
    private JComboBox<String> studSexField; // Added for student's sex

    // Parent info fields (for parentguardianinfo table)
    private JTextField parentIDField; // Added for ParentID
    private JComboBox<String> parentTypeField; // Added for ParentType
    private JTextField parentNameField; // Added for Name
    private JTextField parentBirthdateField; // Added for Birthdate
    private JTextField parentCitizenshipField;
    private JComboBox<String> parentCivilStatusField;
    private JTextField parentOccupationField;
    private JTextField parentMonthlyIncomeField;
    private JTextField parentEducationalAttainmentField;
    private JTextField parentCellNoField;
    private JTextField parentEmailField; // Added for Email
    private JTextField parentPermanentAddField;

    // Child info fields (for childreninfo table)
    private JTextField childFirstNameField;
    private JTextField childMiddleNameField;
    private JTextField childLastNameField;
    private JTextField childSuffixField;
    private JTextField childBirthDateField;
    private JTextField childAgeField;
    private JComboBox<String> childSexField;
    private JTextField childGradeYearLevelField;
    private JTextField childNameOfSchoolField;
    private JTextField childAddressOfSchoolField;
    private JTextField chSchoolIDField;

    // Navigation components
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Consumer<FormData> onFormSubmit;

    // Edit mode tracking
    private boolean editMode = false;
    private String editingStudID = null;
    private String editingAppID = null;

    private String loginEmail;

    public FillOutFormPanel(CardLayout cardLayout, JPanel mainPanel, String loginEmail, Consumer<FormData> onFormSubmit) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.onFormSubmit = onFormSubmit;
        this.loginEmail = loginEmail;

        initializeComponents();
        setupLayout();

        // Prefill App ID if available
        String appId = null;
        model.UserManager.UserData userData = model.UserManager.getInstance().users.get(loginEmail);
        if (userData != null && userData.getAppId() != null && !userData.getAppId().isEmpty()) {
            appId = userData.getAppId();
        } else {
            // fallback: try to get from database
            appId = model.DatabaseManager.getInstance().getAppIDByEmail(loginEmail);
        }
        if (appId != null && !appId.isEmpty()) {
            appIDField.setText(appId);
            appIDField.setEditable(false); // Prevent user from changing their App ID
        }
    }

    private void initializeComponents() {
        // Personal Information fields
        firstNameField = UIUtils.createTextField(15);
        middleNameField = UIUtils.createTextField(15);
        lastNameField = UIUtils.createTextField(15);
        suffixField = UIUtils.createTextField(10);
        addressField = UIUtils.createTextField(25);
        incomeField = UIUtils.createTextField(15);
        appIDField = UIUtils.createTextField(15);

        // Student Information fields
        studIDField = UIUtils.createTextField(15);
        studAgeField = UIUtils.createTextField(8);
        studBirthdateField = UIUtils.createTextField(10);
        studCitizenshipField = UIUtils.createTextField(15);
        studReligionField = UIUtils.createTextField(15);
        studPermanentAddField = UIUtils.createTextField(25);
        studCellNoField = UIUtils.createTextField(15);
        studEmailField = UIUtils.createTextField(20);
        if (loginEmail != null) {
            studEmailField.setText(loginEmail);
            studEmailField.setEditable(false);
        }
        gwaField = UIUtils.createTextField(10);
        degreeProgramField = UIUtils.createTextField(20);
        schoolIDField = UIUtils.createTextField(15);
        schoolNameField = UIUtils.createTextField(20);
        schoolAddField = UIUtils.createTextField(25);
        schoolEmailField = UIUtils.createTextField(20);

        // Parent info fields
        parentIDField = UIUtils.createTextField(15);
        parentTypeField = UIUtils.createComboBox(new String[]{"Father", "Mother", "Guardian"});
        parentNameField = UIUtils.createTextField(20);
        parentBirthdateField = UIUtils.createTextField(10);
        parentCitizenshipField = UIUtils.createTextField(15);
        parentCivilStatusField = UIUtils.createComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        parentOccupationField = UIUtils.createTextField(15);
        parentMonthlyIncomeField = UIUtils.createTextField(15);
        parentEducationalAttainmentField = UIUtils.createTextField(25);
        parentCellNoField = UIUtils.createTextField(15);
        parentEmailField = UIUtils.createTextField(20);
        parentPermanentAddField = UIUtils.createTextField(25);

        // Child info fields
        childFirstNameField = UIUtils.createTextField(15);
        childMiddleNameField = UIUtils.createTextField(15);
        childLastNameField = UIUtils.createTextField(15);
        childSuffixField = UIUtils.createTextField(10);
        childBirthDateField = UIUtils.createTextField(10);
        childAgeField = UIUtils.createTextField(8);
        childGradeYearLevelField = UIUtils.createTextField(10);
        childNameOfSchoolField = UIUtils.createTextField(25);
        childAddressOfSchoolField = UIUtils.createTextField(25);
        chSchoolIDField = UIUtils.createTextField(15);

        // Initialize combo boxes
        String[] sexOptions = {"Male", "Female", "Other"};
        studSexField = UIUtils.createComboBox(sexOptions);
        childSexField = UIUtils.createComboBox(sexOptions);

        parentCivilStatusField = UIUtils.createComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        studCivilStatusField = UIUtils.createComboBox(new String[]{"Single", "Married", "Divorced", "Widowed"});
        String[] houseTypeOptions = {"Owned", "Rented", "Shared"};
        houseTypeField = UIUtils.createComboBox(houseTypeOptions);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);

        // Create main scroll pane for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Scholarship Application Form");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(20));

        // Personal Information Section
        formPanel.add(UIUtils.createSectionPanel("Personal Information", createPersonalInfoPanel()));
        formPanel.add(Box.createVerticalStrut(15));

        // Student Information Section
        formPanel.add(UIUtils.createSectionPanel("Student Information", createStudentInfoPanel()));
        formPanel.add(Box.createVerticalStrut(15));

        // Parent Information Section
        formPanel.add(UIUtils.createSectionPanel("Parent/Guardian Information", createParentPanel()));
        formPanel.add(Box.createVerticalStrut(15));

        // Children Information Section
        formPanel.add(UIUtils.createSectionPanel("Children Information", createChildPanel()));

        // Create scroll pane with horizontal scrolling enabled
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setPreferredSize(new Dimension(800, 600));

        // Adjust scroll speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(160);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setBlockIncrement(160);

        // Add navigation buttons
        JButton backButton = UIUtils.createSecondaryButton("Back");
        JButton nextButton = UIUtils.createPrimaryButton("Next");

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        nextButton.addActionListener(e -> {
            String userInputStudID = studIDField.getText().trim();
            if (!editMode && !isStudentIdUnique(userInputStudID)) {
                JOptionPane.showMessageDialog(this, "Student ID already exists. Please enter a unique ID.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Check if application already exists for this StudID
            if (!editMode && model.DatabaseManager.getInstance().applicationExistsForStudID(userInputStudID)) {
                JOptionPane.showMessageDialog(this, "An application with this Student ID already exists. Only one application per Student ID is allowed.", "Duplicate Application", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (validateForm()) {
                FormData data = collectFormData();
                if (data != null) {
                    // Use user input AppID or generate one if empty
                    String userAppID = appIDField.getText().trim();
                    if (userAppID.isEmpty()) {
                        // Generate AppID in format EDU-XXXX (random 4-digit number)
                        String appId = "EDU-" + String.format("%04d", (int)(Math.random() * 10000));
                        data.setAppID(appId);
                    } else {
                        // Validate user input AppID format
                        if (!userAppID.matches("EDU-\\d{4}")) {
                            JOptionPane.showMessageDialog(this, "App ID must be in format EDU-XXXX (e.g., EDU-1234)", "Invalid App ID Format", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        // Check if AppID already exists
                        if (model.DatabaseManager.getInstance().appIDExists(userAppID)) {
                            JOptionPane.showMessageDialog(this, "App ID " + userAppID + " already exists. Please use a different App ID.", "Duplicate App ID", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        data.setAppID(userAppID);
                    }
                    if (onFormSubmit != null) {
                        onFormSubmit.accept(data);
                    }
                }
            }
        });

        JPanel buttonPanel = UIUtils.createFooterPanel(backButton, nextButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1 - Name fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(firstNameField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(middleNameField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(lastNameField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Suffix:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(suffixField, gbc);

        // Row 3 - Birthdate
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Month:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(birthMonthField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Day:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(birthDayField, gbc);

        // Row 4
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Year:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.5;
        panel.add(birthYearField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(ageField, gbc);

        // Row 5
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(sexField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(civilStatusField, gbc);

        // Row 6
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
        panel.add(new JLabel("Religion:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(religionField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Citizenship:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(citizenshipField, gbc);

        // Row 7 - Full width fields
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.0;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(addressField, gbc);
        gbc.gridwidth = 1;

        // Row 8
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.0;
        panel.add(new JLabel("Permanent Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(permanentAddressField, gbc);
        gbc.gridwidth = 1;

        // Row 9
        gbc.gridx = 0; gbc.gridy = 8; gbc.weightx = 0.0;
        panel.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(emailField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Cellphone:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(cellphoneField, gbc);

        // Row 10
        gbc.gridx = 0; gbc.gridy = 9; gbc.weightx = 0.0;
        panel.add(new JLabel("House Type:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(houseTypeField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("GWA:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(gwaField, gbc);

        // Row 11
        gbc.gridx = 0; gbc.gridy = 10; gbc.weightx = 0.0;
        panel.add(new JLabel("Degree Program:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(degreeProgramField, gbc);
        gbc.gridwidth = 1;

        // Row 12
        gbc.gridx = 0; gbc.gridy = 11; gbc.weightx = 0.0;
        panel.add(new JLabel("School Name:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(schoolNameField, gbc);
        gbc.gridwidth = 1;

        // Row 13
        gbc.gridx = 0; gbc.gridy = 12; gbc.weightx = 0.0;
        panel.add(new JLabel("School Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(schoolAddressField, gbc);
        gbc.gridwidth = 1;

        // Row 14
        gbc.gridx = 0; gbc.gridy = 13; gbc.weightx = 0.0;
        panel.add(new JLabel("School Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(schoolEmailField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("School ID:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(schoolIdField, gbc);

        return panel;
    }

    private JPanel createParentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        panel.add(new JLabel("Parent Type:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentTypeField, gbc);

        // Row 2 - Name fields
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentFirstNameField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(parentMiddleNameField, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentLastNameField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Suffix:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(parentSuffixField, gbc);

        // Row 4 - Birthdate
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Month:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentBirthMonthField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Day:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(parentBirthDayField, gbc);

        // Row 5
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Year:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.5;
        panel.add(parentBirthYearField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(parentAgeField, gbc);

        // Row 6
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentSexField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Citizenship:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(parentCitizenshipField, gbc);

        // Row 7
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.0;
        panel.add(new JLabel("Civil Status:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentCivilStatusField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Occupation:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(parentOccupationField, gbc);

        // Row 8
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 0.0;
        panel.add(new JLabel("Email Address:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(parentEmailField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Monthly Income:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(parentMonthlyIncomeField, gbc);

        // Row 9
        gbc.gridx = 0; gbc.gridy = 8; gbc.weightx = 0.0;
        panel.add(new JLabel("Educational Attainment:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(parentEducationalAttainmentField, gbc);

        return panel;
    }

    private JPanel createChildPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1 - Name fields
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(childFirstNameField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        panel.add(childMiddleNameField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(childLastNameField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Suffix:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(childSuffixField, gbc);

        // Row 3 - Birthdate
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Month:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(childBirthMonthField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Day:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(childBirthDayField, gbc);

        // Row 4
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.0;
        panel.add(new JLabel("Birth Year:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.5;
        panel.add(childBirthYearField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(childAgeField, gbc);

        // Row 5
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.0;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panel.add(childSexField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.0;
        panel.add(new JLabel("Grade Level:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        panel.add(childGradeLevelField, gbc);

        // Row 6
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.0;
        panel.add(new JLabel("School Name:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(childSchoolNameField, gbc);
        gbc.gridwidth = 1;

        // Row 7
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.0;
        panel.add(new JLabel("School Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        panel.add(childSchoolAddressField, gbc);

        return panel;
    }

    public FormData collectFormData() {
        FormData data = new FormData();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Personal Information (for applicantinformation table)
            data.setFirstName(firstNameField.getText().trim());
            data.setMiddleName(middleNameField.getText().trim());
            data.setLastName(lastNameField.getText().trim());
            data.setSuffix(suffixField.getText().trim());
            data.setAddress(addressField.getText().trim());
            data.setHouseType((String) houseTypeField.getSelectedItem());
            data.setIncome(incomeField.getText().trim());
            data.setAppID(appIDField.getText().trim());

            // Student Information (for studentinformation table)
            String studIdStr = studIDField.getText().trim(); // Use the StudID from the form field
            data.setStudID(studIdStr);
            data.setStudAge(Integer.parseInt(studAgeField.getText().trim()));
            data.setStudBirthdate(new java.sql.Date(dateFormat.parse(studBirthdateField.getText().trim()).getTime()));
            data.setStudCitizenship(studCitizenshipField.getText().trim());

            String selectedCivilStatus = (String) studCivilStatusField.getSelectedItem();
            String civilStatusAbbreviation = "";
            switch (selectedCivilStatus) {
                case "Single":
                    civilStatusAbbreviation = "S";
                    break;
                case "Married":
                    civilStatusAbbreviation = "M";
                    break;
                case "Divorced":
                    civilStatusAbbreviation = "D";
                    break;
                case "Widowed":
                    civilStatusAbbreviation = "W";
                    break;
                default:
                    civilStatusAbbreviation = "O"; // Other or unknown
                    break;
            }
            data.setStudCivilStatus(civilStatusAbbreviation);

            data.setStudReligion(studReligionField.getText().trim());
            data.setStudPermanentAdd(studPermanentAddField.getText().trim());
            data.setStudCellNo(studCellNoField.getText().trim());
            data.setStudEmail(studEmailField.getText().trim());
            data.setDegreeProgram(degreeProgramField.getText().trim());
            data.setGwa(Double.parseDouble(gwaField.getText().trim()));
            data.setSchoolID(schoolIDField.getText().trim());
            data.setSchoolName(schoolNameField.getText().trim());
            data.setSchoolAdd(schoolAddField.getText().trim());
            data.setSchoolEmail(schoolEmailField.getText().trim());

            // Convert student sex to database format (single character)
            String studSex = (String) studSexField.getSelectedItem();
            String studSexCode = "";
            if (studSex != null && !studSex.isEmpty()) {
                switch (studSex) {
                    case "Male":
                        studSexCode = "M";
                        break;
                    case "Female":
                        studSexCode = "F";
                        break;
                    case "Other":
                        studSexCode = "O";
                        break;
                    default:
                        studSexCode = "O";
                        break;
                }
            }
            data.setStudSex(studSexCode);

            // Parent Information (for parentguardianinfo table)
            // Generate ParentID if not provided
            String parentID = parentIDField.getText().trim();
            if (parentID.isEmpty()) {
                parentID = "P" + String.format("%04d", (int)(Math.random() * 10000));
            }
            data.setParentID(parentID);

            // Convert parent type to database format
            String selectedParentType = (String) parentTypeField.getSelectedItem();
            String parentTypeAbbreviation = "";
            switch (selectedParentType) {
                case "Father":
                    parentTypeAbbreviation = "F";
                    break;
                case "Mother":
                    parentTypeAbbreviation = "M";
                    break;
                case "Guardian":
                    parentTypeAbbreviation = "G";
                    break;
                default:
                    parentTypeAbbreviation = "G"; // Default to Guardian
                    break;
            }
            data.setParentType(parentTypeAbbreviation);

            data.setParentName(parentNameField.getText().trim());

            // Parse parent birthdate
            String parentBirthdateStr = parentBirthdateField.getText().trim();
            if (!parentBirthdateStr.isEmpty()) {
                try {
                    data.setParentBirthdate(new java.sql.Date(dateFormat.parse(parentBirthdateStr).getTime()));
                } catch (ParseException e) {
                    data.setParentBirthdate(null);
                }
            }

            String selectedParentCivilStatus = (String) parentCivilStatusField.getSelectedItem();
            String parentCivilStatusAbbreviation = "";
            switch (selectedParentCivilStatus) {
                case "Single":
                    parentCivilStatusAbbreviation = "S";
                    break;
                case "Married":
                    parentCivilStatusAbbreviation = "M";
                    break;
                case "Divorced":
                    parentCivilStatusAbbreviation = "D";
                    break;
                case "Widowed":
                    parentCivilStatusAbbreviation = "W";
                    break;
                default:
                    parentCivilStatusAbbreviation = "O"; // Other or unknown
                    break;
            }
            data.setParentCivilStatus(parentCivilStatusAbbreviation);

            data.setParentOccupation(parentOccupationField.getText().trim());
            data.setParentMonthlyIncome(parentMonthlyIncomeField.getText().trim());
            data.setParentEducationalAttainment(parentEducationalAttainmentField.getText().trim());
            data.setParentPermanentAdd(parentPermanentAddField.getText().trim());
            data.setParentCitizenship(parentCitizenshipField.getText().trim()); // This maps to Nationality in DB
            data.setParentCellNo(parentCellNoField.getText().trim());
            data.setParentEmail(parentEmailField.getText().trim());

            // Child Information (for childreninfo table) - make optional
            String childFirstName = childFirstNameField.getText().trim();
            data.setChildFirstName(childFirstName.isEmpty() ? null : childFirstName);

            String childMiddleName = childMiddleNameField.getText().trim();
            data.setChildMiddleName(childMiddleName.isEmpty() ? null : childMiddleName);

            String childLastName = childLastNameField.getText().trim();
            data.setChildLastName(childLastName.isEmpty() ? null : childLastName);

            String childSuffix = childSuffixField.getText().trim();
            data.setChildSuffix(childSuffix.isEmpty() ? null : childSuffix);

            String childBirthDateStr = childBirthDateField.getText().trim();
            data.setChildDateOfBirth(childBirthDateStr.isEmpty() ? null : new java.sql.Date(dateFormat.parse(childBirthDateStr).getTime()));

            String childAgeStr = childAgeField.getText().trim();
            data.setChildAge(childAgeStr.isEmpty() ? null : childAgeStr);

            String childSex = (String) childSexField.getSelectedItem();
            // Convert sex to database format (single character)
            String childSexCode = "";
            if (childSex != null && !childSex.isEmpty() && !childSex.equals("Other")) {
                switch (childSex) {
                    case "Male":
                        childSexCode = "M";
                        break;
                    case "Female":
                        childSexCode = "F";
                        break;
                    case "Other":
                        childSexCode = "O";
                        break;
                    default:
                        childSexCode = "O";
                        break;
                }
            }
            data.setChildSex(childSexCode);

            String childGradeYearLevel = childGradeYearLevelField.getText().trim();
            data.setChildGradeYearLevel(childGradeYearLevel.isEmpty() ? null : childGradeYearLevel);

            String childNameOfSchool = childNameOfSchoolField.getText().trim();
            data.setChildNameOfSchool(childNameOfSchool.isEmpty() ? null : childNameOfSchool);

            String childAddressOfSchool = childAddressOfSchoolField.getText().trim();
            data.setChildAddressOfSchool(childAddressOfSchool.isEmpty() ? null : childAddressOfSchool);

            String chSchoolID = chSchoolIDField.getText().trim();
            data.setChSchoolID(chSchoolID.isEmpty() ? null : chSchoolID);

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Age or GWA.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return data;
    }

    public void saveFormDataToDatabase(FormData data) {
        Connection conn = null;
        PreparedStatement pstmtApplicant = null;
        PreparedStatement pstmtParent = null;
        PreparedStatement pstmtChild = null;
        PreparedStatement pstmtStudent = null;

        try {
            conn = DatabaseManager.getInstance().getConnection();
            conn.setAutoCommit(false); // Start transaction
            if (editMode && editingStudID != null) {
                // UPDATE studentinformation
                String sqlStudent = "UPDATE studentinformation SET StudAge=?, StudBirthdate=?, StudCitizenship=?, StudCivilStatus=?, StudReligion=?, StudPermanentAdd=?, StudCellNo=?, StudEmail=?, DegreeProgram=?, GWA=?, SchoolID=?, SchoolName=?, SchoolAdd=?, SchoolEmail=?, StudSex=? WHERE StudID=?";
                pstmtStudent = conn.prepareStatement(sqlStudent);
                pstmtStudent.setInt(1, data.getStudAge());
                pstmtStudent.setDate(2, data.getStudBirthdate());
                pstmtStudent.setString(3, data.getStudCitizenship());
                pstmtStudent.setString(4, data.getStudCivilStatus());
                pstmtStudent.setString(5, data.getStudReligion());
                pstmtStudent.setString(6, data.getStudPermanentAdd());
                pstmtStudent.setString(7, data.getStudCellNo());
                pstmtStudent.setString(8, data.getStudEmail());
                pstmtStudent.setString(9, data.getDegreeProgram());
                pstmtStudent.setDouble(10, data.getGwa());
                pstmtStudent.setString(11, data.getSchoolID());
                pstmtStudent.setString(12, data.getSchoolName());
                pstmtStudent.setString(13, data.getSchoolAdd());
                pstmtStudent.setString(14, data.getSchoolEmail());
                pstmtStudent.setString(15, data.getStudSex());
                pstmtStudent.setString(16, editingStudID);
                pstmtStudent.executeUpdate();
                // UPDATE applicantinformation
                String sqlApplicant = "UPDATE applicantinformation SET AppDate=?, AppStatus=? WHERE AppID=?";
                pstmtApplicant = conn.prepareStatement(sqlApplicant);
                pstmtApplicant.setDate(1, new java.sql.Date(System.currentTimeMillis()));
                pstmtApplicant.setString(2, "P");
                pstmtApplicant.setString(3, editingAppID);
                pstmtApplicant.executeUpdate();
                // UPDATE parentguardianinfo
                String sqlParent = "UPDATE parentguardianinfo SET ParentID=?, ParentType=?, Name=?, Birthdate=?, CellNo=?, Email=?, PermanentAdd=?, CivilStatus=?, Nationality=?, Occupation=?, EducAttainment=?, MonthlyIncome=? WHERE StudID=?";
                pstmtParent = conn.prepareStatement(sqlParent);
                pstmtParent.setString(1, data.getParentID());
                pstmtParent.setString(2, data.getParentType());
                pstmtParent.setString(3, data.getParentName());
                pstmtParent.setDate(4, data.getParentBirthdate());
                pstmtParent.setString(5, data.getParentCellNo());
                pstmtParent.setString(6, data.getParentEmail());
                pstmtParent.setString(7, data.getParentPermanentAdd());
                pstmtParent.setString(8, data.getParentCivilStatus());
                pstmtParent.setString(9, data.getParentCitizenship()); // This maps to Nationality in DB
                pstmtParent.setString(10, data.getParentOccupation());
                pstmtParent.setString(11, data.getParentEducationalAttainment());
                pstmtParent.setDouble(12, Double.parseDouble(data.getParentMonthlyIncome()));
                pstmtParent.setString(13, editingStudID);
                pstmtParent.executeUpdate();
                // UPDATE or INSERT child info (if provided)
                if (data.getChildFirstName() != null && !data.getChildFirstName().isEmpty()) {
                    // Try update first
                    String sqlChildUpdate = "UPDATE childreninfo SET Name=?, BirthDate=?, Sex=?, Age=?, GradeYearLevel=?, NameofSchool=?, AddressofSchool=?, ChSchoolID=? WHERE AppID=?";
                    pstmtChild = conn.prepareStatement(sqlChildUpdate);
                    pstmtChild.setString(1, data.getChildFirstName() + (data.getChildMiddleName() != null ? " " + data.getChildMiddleName() : "") + (data.getChildLastName() != null ? " " + data.getChildLastName() : ""));
                    pstmtChild.setDate(2, data.getChildDateOfBirth());
                    pstmtChild.setString(3, data.getChildSex());
                    pstmtChild.setInt(4, Integer.parseInt(data.getChildAge()));
                    pstmtChild.setString(5, data.getChildGradeYearLevel());
                    pstmtChild.setString(6, data.getChildNameOfSchool());
                    pstmtChild.setString(7, data.getChildAddressOfSchool());
                    pstmtChild.setString(8, data.getChSchoolID());
                    pstmtChild.setString(9, editingAppID);
                    int updated = pstmtChild.executeUpdate();
                    pstmtChild.close();
                    if (updated == 0) {
                        // If no row updated, insert
                        String sqlChildInsert = "INSERT INTO childreninfo (ChildID, Name, BirthDate, Sex, Age, GradeYearLevel, NameofSchool, AddressofSchool, AppID, ChSchoolID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmtChild = conn.prepareStatement(sqlChildInsert);
                        String childId = java.util.UUID.randomUUID().toString().substring(0, 10);
                        pstmtChild.setString(1, childId);
                        pstmtChild.setString(2, data.getChildFirstName() + (data.getChildMiddleName() != null ? " " + data.getChildMiddleName() : "") + (data.getChildLastName() != null ? " " + data.getChildLastName() : ""));
                        pstmtChild.setDate(3, data.getChildDateOfBirth());
                        pstmtChild.setString(4, data.getChildSex());
                        pstmtChild.setInt(5, Integer.parseInt(data.getChildAge()));
                        pstmtChild.setString(6, data.getChildGradeYearLevel());
                        pstmtChild.setString(7, data.getChildNameOfSchool());
                        pstmtChild.setString(8, data.getChildAddressOfSchool());
                        pstmtChild.setString(9, editingAppID);
                        pstmtChild.setString(10, data.getChSchoolID());
                        pstmtChild.executeUpdate();
                    }
                }
                conn.commit();
                JOptionPane.showMessageDialog(this, "Application data updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                editMode = false;
                editingStudID = null;
                editingAppID = null;
                return;
            }
            // Insert into studentinformation first (since it contains the applicant's personal info)
            String sqlStudent = "INSERT INTO studentinformation (StudID, StudAge, StudBirthdate, StudCitizenship, StudCivilStatus, StudReligion, StudPermanentAdd, StudCellNo, StudEmail, DegreeProgram, GWA, SchoolID, SchoolName, SchoolAdd, SchoolEmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmtStudent = conn.prepareStatement(sqlStudent);
            pstmtStudent.setString(1, data.getStudID());
            pstmtStudent.setInt(2, data.getStudAge());
            pstmtStudent.setDate(3, data.getStudBirthdate());
            pstmtStudent.setString(4, data.getStudCitizenship());
            pstmtStudent.setString(5, data.getStudCivilStatus());
            pstmtStudent.setString(6, data.getStudReligion());
            pstmtStudent.setString(7, data.getStudPermanentAdd());
            pstmtStudent.setString(8, data.getStudCellNo());
            pstmtStudent.setString(9, data.getStudEmail());
            pstmtStudent.setString(10, data.getDegreeProgram());
            pstmtStudent.setDouble(11, data.getGwa());
            pstmtStudent.setString(12, data.getSchoolID());
            pstmtStudent.setString(13, data.getSchoolName());
            pstmtStudent.setString(14, data.getSchoolAdd());
            pstmtStudent.setString(15, data.getSchoolEmail());
            pstmtStudent.executeUpdate();

            // Insert into applicantinformation (using the generated AppID)
            String sqlApplicant = "INSERT INTO applicantinformation (AppID, AppDate, AppStatus, StudID) VALUES (?, ?, ?, ?)";
            pstmtApplicant = conn.prepareStatement(sqlApplicant);
            pstmtApplicant.setString(1, data.getAppID()); // Use the generated AppID instead of StudID
            pstmtApplicant.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Current date
            pstmtApplicant.setString(3, "P"); // P for Pending
            pstmtApplicant.setString(4, data.getStudID());

            // Debug output
            System.out.println("DEBUG: Saving to database - AppID: " + data.getAppID() + ", StudID: " + data.getStudID());

            pstmtApplicant.executeUpdate();

            // Insert into parentguardianinfo
            String sqlParent = "INSERT INTO parentguardianinfo (ParentID, ParentType, Name, Birthdate, CellNo, Email, PermanentAdd, CivilStatus, Nationality, Occupation, EducAttainment, MonthlyIncome, StudID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmtParent = conn.prepareStatement(sqlParent);
            pstmtParent.setString(1, data.getParentID());
            pstmtParent.setString(2, data.getParentType());
            pstmtParent.setString(3, data.getParentName());
            pstmtParent.setDate(4, data.getParentBirthdate());
            pstmtParent.setString(5, data.getParentCellNo());
            pstmtParent.setString(6, data.getParentEmail());
            pstmtParent.setString(7, data.getParentPermanentAdd());
            pstmtParent.setString(8, data.getParentCivilStatus());
            pstmtParent.setString(9, data.getParentCitizenship()); // This maps to Nationality in DB
            pstmtParent.setString(10, data.getParentOccupation());
            pstmtParent.setString(11, data.getParentEducationalAttainment());
            pstmtParent.setDouble(12, Double.parseDouble(data.getParentMonthlyIncome()));
            pstmtParent.setString(13, data.getStudID());
            pstmtParent.executeUpdate();

            // Insert into childreninfo only if child first name is provided (making it optional)
            if (data.getChildFirstName() != null && !data.getChildFirstName().isEmpty()) {
                String sqlChild = "INSERT INTO childreninfo (ChildID, Name, BirthDate, Sex, Age, GradeYearLevel, NameofSchool, AddressofSchool, AppID, ChSchoolID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmtChild = conn.prepareStatement(sqlChild);
                String childId = UUID.randomUUID().toString().substring(0, Math.min(UUID.randomUUID().toString().length(), 10));
                pstmtChild.setString(1, childId);
                pstmtChild.setString(2, data.getChildFirstName() + (data.getChildMiddleName() != null ? " " + data.getChildMiddleName() : "") + (data.getChildLastName() != null ? " " + data.getChildLastName() : ""));
                pstmtChild.setDate(3, data.getChildDateOfBirth());
                pstmtChild.setString(4, data.getChildSex());
                pstmtChild.setInt(5, Integer.parseInt(data.getChildAge()));
                pstmtChild.setString(6, data.getChildGradeYearLevel());
                pstmtChild.setString(7, data.getChildNameOfSchool());
                pstmtChild.setString(8, data.getChildAddressOfSchool());
                pstmtChild.setString(9, data.getAppID()); // Use the generated AppID instead of StudID
                pstmtChild.setString(10, data.getChSchoolID());
                pstmtChild.executeUpdate();
            }

            conn.commit(); // Commit transaction
            JOptionPane.showMessageDialog(this, "Application data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save application data. Database error.", "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmtApplicant != null) pstmtApplicant.close();
                if (pstmtParent != null) pstmtParent.close();
                if (pstmtChild != null) pstmtChild.close();
                if (pstmtStudent != null) pstmtStudent.close();
                if (conn != null) conn.setAutoCommit(true); // Restore auto-commit mode
                // Don't close connection here - keep it open for better performance
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void prefillForm(FormData data) {
        if (data == null) return;
        editMode = true;
        editingStudID = data.getStudID();
        editingAppID = data.getAppID();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Personal Information
        if (data.getFirstName() != null) firstNameField.setText(data.getFirstName());
        if (data.getMiddleName() != null) middleNameField.setText(data.getMiddleName());
        if (data.getLastName() != null) lastNameField.setText(data.getLastName());
        if (data.getSuffix() != null) suffixField.setText(data.getSuffix());
        if (data.getAddress() != null) addressField.setText(data.getAddress());
        if (data.getHouseType() != null) houseTypeField.setSelectedItem(data.getHouseType());
        if (data.getIncome() != null) incomeField.setText(data.getIncome());
        if (data.getAppID() != null) appIDField.setText(data.getAppID());

        // Student Information
        if (data.getStudID() != null) studIDField.setText(data.getStudID());
        if (data.getStudAge() != 0) studAgeField.setText(String.valueOf(data.getStudAge()));
        if (data.getStudBirthdate() != null) studBirthdateField.setText(dateFormat.format(data.getStudBirthdate()));
        if (data.getStudCitizenship() != null) studCitizenshipField.setText(data.getStudCitizenship());
        if (data.getStudCivilStatus() != null) studCivilStatusField.setSelectedItem(data.getStudCivilStatus());
        if (data.getStudReligion() != null) studReligionField.setText(data.getStudReligion());
        if (data.getStudPermanentAdd() != null) studPermanentAddField.setText(data.getStudPermanentAdd());
        if (data.getStudCellNo() != null) studCellNoField.setText(data.getStudCellNo());
        if (data.getStudEmail() != null) studEmailField.setText(data.getStudEmail());
        if (data.getDegreeProgram() != null) degreeProgramField.setText(data.getDegreeProgram());
        if (data.getGwa() != 0.0) gwaField.setText(String.valueOf(data.getGwa()));
        if (data.getSchoolID() != null) schoolIDField.setText(data.getSchoolID());
        if (data.getSchoolName() != null) schoolNameField.setText(data.getSchoolName());
        if (data.getSchoolAdd() != null) schoolAddField.setText(data.getSchoolAdd());
        if (data.getSchoolEmail() != null) schoolEmailField.setText(data.getSchoolEmail());
        if (data.getStudSex() != null) {
            switch (data.getStudSex()) {
                case "M":
                    studSexField.setSelectedItem("Male");
                    break;
                case "F":
                    studSexField.setSelectedItem("Female");
                    break;
                case "O":
                    studSexField.setSelectedItem("Other");
                    break;
                default:
                    studSexField.setSelectedIndex(0);
                    break;
            }
        }

        // Parent Information
        if (data.getParentID() != null) parentIDField.setText(data.getParentID());
        if (data.getParentType() != null) {
            switch (data.getParentType()) {
                case "F":
                    parentTypeField.setSelectedItem("Father");
                    break;
                case "M":
                    parentTypeField.setSelectedItem("Mother");
                    break;
                case "G":
                    parentTypeField.setSelectedItem("Guardian");
                    break;
                default:
                    parentTypeField.setSelectedIndex(0);
                    break;
            }
        }
        if (data.getParentName() != null) parentNameField.setText(data.getParentName());
        if (data.getParentBirthdate() != null) parentBirthdateField.setText(dateFormat.format(data.getParentBirthdate()));
        if (data.getParentCitizenship() != null) parentCitizenshipField.setText(data.getParentCitizenship());
        if (data.getParentEducationalAttainment() != null) parentEducationalAttainmentField.setText(data.getParentEducationalAttainment());
        if (data.getParentCellNo() != null) parentCellNoField.setText(data.getParentCellNo());
        if (data.getParentEmail() != null) parentEmailField.setText(data.getParentEmail());
        if (data.getParentPermanentAdd() != null) parentPermanentAddField.setText(data.getParentPermanentAdd());

        // Child Information
        if (data.getChildFirstName() != null) childFirstNameField.setText(data.getChildFirstName());
        if (data.getChildMiddleName() != null) childMiddleNameField.setText(data.getChildMiddleName());
        if (data.getChildLastName() != null) childLastNameField.setText(data.getChildLastName());
        if (data.getChildSuffix() != null) childSuffixField.setText(data.getChildSuffix());
        if (data.getChildDateOfBirth() != null) childBirthDateField.setText(dateFormat.format(data.getChildDateOfBirth()));
        if (data.getChildAge() != null) childAgeField.setText(data.getChildAge());
        if (data.getChildSex() != null) {
            switch (data.getChildSex()) {
                case "M":
                    childSexField.setSelectedItem("Male");
                    break;
                case "F":
                    childSexField.setSelectedItem("Female");
                    break;
                case "O":
                    childSexField.setSelectedItem("Other");
                    break;
                default:
                    childSexField.setSelectedIndex(0);
                    break;
            }
        }
        if (data.getChildGradeYearLevel() != null) childGradeYearLevelField.setText(data.getChildGradeYearLevel());
        if (data.getChildNameOfSchool() != null) childNameOfSchoolField.setText(data.getChildNameOfSchool());
        if (data.getChildAddressOfSchool() != null) childAddressOfSchoolField.setText(data.getChildAddressOfSchool());
        if (data.getChSchoolID() != null) chSchoolIDField.setText(data.getChSchoolID());
    }

    /**
     * Clears all form fields
     */
    public void clearForm() {
        // Clear personal information fields
        firstNameField.setText("");
        middleNameField.setText("");
        lastNameField.setText("");
        suffixField.setText("");
        addressField.setText("");
        houseTypeField.setSelectedIndex(0);
        incomeField.setText("");
        appIDField.setText("");

        // Clear student information fields
        studIDField.setText("");
        studAgeField.setText("");
        studBirthdateField.setText("");
        studCitizenshipField.setText("");
        studCivilStatusField.setSelectedIndex(0);
        studReligionField.setText("");
        studPermanentAddField.setText("");
        studCellNoField.setText("");
        studEmailField.setText("");
        degreeProgramField.setText("");
        gwaField.setText("");
        schoolIDField.setText("");
        schoolNameField.setText("");
        schoolAddField.setText("");
        schoolEmailField.setText("");
        studSexField.setSelectedIndex(0);

        // Clear parent information fields
        parentIDField.setText("");
        parentTypeField.setSelectedIndex(0);
        parentNameField.setText("");
        parentBirthdateField.setText("");
        parentCitizenshipField.setText("");
        parentCivilStatusField.setSelectedIndex(0);
        parentOccupationField.setText("");
        parentMonthlyIncomeField.setText("");
        parentEducationalAttainmentField.setText("");
        parentCellNoField.setText("");
        parentEmailField.setText("");
        parentPermanentAddField.setText("");

        // Clear child information fields
        childFirstNameField.setText("");
        childMiddleNameField.setText("");
        childLastNameField.setText("");
        childSuffixField.setText("");
        childBirthDateField.setText("");
        childAgeField.setText("");
        childSexField.setSelectedIndex(0);
        childGradeYearLevelField.setText("");
        childNameOfSchoolField.setText("");
        childAddressOfSchoolField.setText("");
        chSchoolIDField.setText("");
    }

    public boolean validateForm() {
        // Basic validation for required fields
        if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                addressField.getText().trim().isEmpty() ||
                houseTypeField.getSelectedItem() == null ||
                incomeField.getText().trim().isEmpty() ||
                studAgeField.getText().trim().isEmpty() ||
                studBirthdateField.getText().trim().isEmpty() ||
                studCitizenshipField.getText().trim().isEmpty() ||
                studCivilStatusField.getSelectedItem() == null ||
                studReligionField.getText().trim().isEmpty() ||
                studPermanentAddField.getText().trim().isEmpty() ||
                studCellNoField.getText().trim().isEmpty() ||
                studEmailField.getText().trim().isEmpty() ||
                degreeProgramField.getText().trim().isEmpty() ||
                gwaField.getText().trim().isEmpty() ||
                schoolIDField.getText().trim().isEmpty() ||
                schoolNameField.getText().trim().isEmpty() ||
                schoolAddField.getText().trim().isEmpty() ||
                schoolEmailField.getText().trim().isEmpty() ||
                studSexField.getSelectedItem() == null || // Added for student sex validation
                parentNameField.getText().trim().isEmpty() || // Added for parent name validation
                parentTypeField.getSelectedItem() == null || // Added for parent type validation
                parentBirthdateField.getText().trim().isEmpty() || // Added for parent birthdate validation
                parentCitizenshipField.getText().trim().isEmpty() ||
                parentCivilStatusField.getSelectedItem() == null ||
                parentOccupationField.getText().trim().isEmpty() ||
                parentMonthlyIncomeField.getText().trim().isEmpty() ||
                parentEducationalAttainmentField.getText().trim().isEmpty() ||
                parentCellNoField.getText().trim().isEmpty() || // Added for parent cell number validation
                parentEmailField.getText().trim().isEmpty() || // Added for parent email validation
                parentPermanentAddField.getText().trim().isEmpty()) {
            // Children info fields are now optional, so they are not included in this basic validation
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate date formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            // Only validate birthDateField if it's not empty, as it's part of student info now and might be empty if the applicant doesn't fill student info separately or if it's implied by app/student info overlap.
            if (!studBirthdateField.getText().trim().isEmpty()) {
                dateFormat.parse(studBirthdateField.getText().trim());
            }
            if (!childBirthDateField.getText().trim().isEmpty()) {
                dateFormat.parse(childBirthDateField.getText().trim());
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate number formats
        try {
            if (!studAgeField.getText().trim().isEmpty()) {
                Integer.parseInt(studAgeField.getText().trim());
            }
            if (!gwaField.getText().trim().isEmpty()) {
                Double.parseDouble(gwaField.getText().trim());
            }
            if (!childAgeField.getText().trim().isEmpty()) {
                Integer.parseInt(childAgeField.getText().trim());
            }
            // Validate income and monthly income fields
            if (!incomeField.getText().trim().isEmpty()) {
                Double.parseDouble(incomeField.getText().trim());
            }
            if (!parentMonthlyIncomeField.getText().trim().isEmpty()) {
                Double.parseDouble(parentMonthlyIncomeField.getText().trim());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Age, GWA, Income, or Monthly Income.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Add this method to check uniqueness of StudentID
    private boolean isStudentIdUnique(String studId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseManager.getInstance().getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM studentinformation WHERE StudID = ?");
            stmt.setString(1, studId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // true if not found
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            // Do not close conn here if you use a shared connection
        }
        return false; // default to not unique on error
    }
}