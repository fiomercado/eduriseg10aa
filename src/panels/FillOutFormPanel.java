package panels;

import model.FormData;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class FillOutFormPanel extends JPanel {

    // Personal Information fields
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField suffixField;
    private JComboBox<String> birthMonthField;
    private JTextField birthDayField;
    private JTextField birthYearField;
    private JTextField ageField;
    private JComboBox<String> sexField;
    private JComboBox<String> civilStatusField;
    private JTextField religionField;
    private JTextField citizenshipField;
    private JTextField addressField;
    private JTextField permanentAddressField;
    private JComboBox<String> houseTypeField;
    private JTextField incomeField;
    private JTextField contactField;
    private JTextField emailField;
    private JTextField cellphoneField;
    private JTextField gwaField;
    private JTextField degreeProgramField;
    private JTextField schoolNameField;
    private JTextField schoolAddressField;
    private JTextField schoolEmailField;
    private JTextField schoolIdField;

    // Parent info fields
    private JComboBox<String> parentTypeField;
    private JTextField parentFirstNameField;
    private JTextField parentMiddleNameField;
    private JTextField parentLastNameField;
    private JTextField parentSuffixField;
    private JComboBox<String> parentBirthMonthField;
    private JTextField parentBirthDayField;
    private JTextField parentBirthYearField;
    private JTextField parentAgeField;
    private JComboBox<String> parentSexField;
    private JTextField parentCitizenshipField;
    private JComboBox<String> parentCivilStatusField;
    private JTextField parentOccupationField;
    private JTextField parentEmailField;
    private JTextField parentMonthlyIncomeField;
    private JTextField parentEducationalAttainmentField;

    // Child info fields
    private JTextField childFirstNameField;
    private JTextField childMiddleNameField;
    private JTextField childLastNameField;
    private JTextField childSuffixField;
    private JComboBox<String> childBirthMonthField;
    private JTextField childBirthDayField;
    private JTextField childBirthYearField;
    private JTextField childAgeField;
    private JComboBox<String> childSexField;
    private JTextField childGradeLevelField;
    private JTextField childSchoolNameField;
    private JTextField childSchoolAddressField;

    // Navigation components
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Consumer<FormData> onFormSubmit;

    public FillOutFormPanel(CardLayout cardLayout, JPanel mainPanel, Consumer<FormData> onFormSubmit) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.onFormSubmit = onFormSubmit;

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Initialize personal information fields with smaller width
        firstNameField = new JTextField(15);
        middleNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        suffixField = new JTextField(10);
        birthDayField = new JTextField(8);
        birthYearField = new JTextField(8);
        ageField = new JTextField(8);
        religionField = new JTextField(15);
        citizenshipField = new JTextField(15);
        addressField = new JTextField(25);
        permanentAddressField = new JTextField(25);
        incomeField = new JTextField(15);
        contactField = new JTextField(15);
        emailField = new JTextField(20);
        cellphoneField = new JTextField(15);
        gwaField = new JTextField(10);
        degreeProgramField = new JTextField(20);
        schoolNameField = new JTextField(20);
        schoolAddressField = new JTextField(25);
        schoolEmailField = new JTextField(20);
        schoolIdField = new JTextField(15);

        // Initialize parent fields with smaller width
        parentFirstNameField = new JTextField(15);
        parentMiddleNameField = new JTextField(15);
        parentLastNameField = new JTextField(15);
        parentSuffixField = new JTextField(10);
        parentBirthDayField = new JTextField(8);
        parentBirthYearField = new JTextField(8);
        parentAgeField = new JTextField(8);
        parentCitizenshipField = new JTextField(15);
        parentOccupationField = new JTextField(15);
        parentEmailField = new JTextField(20);
        parentMonthlyIncomeField = new JTextField(15);
        parentEducationalAttainmentField = new JTextField(25);

        // Initialize child fields with smaller width
        childFirstNameField = new JTextField(15);
        childMiddleNameField = new JTextField(15);
        childLastNameField = new JTextField(15);
        childSuffixField = new JTextField(10);
        childBirthDayField = new JTextField(8);
        childBirthYearField = new JTextField(8);
        childAgeField = new JTextField(8);
        childGradeLevelField = new JTextField(10);
        childSchoolNameField = new JTextField(25);
        childSchoolAddressField = new JTextField(25);

        // Initialize combo boxes
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        birthMonthField = new JComboBox<>(months);
        parentBirthMonthField = new JComboBox<>(months);
        childBirthMonthField = new JComboBox<>(months);

        String[] sexOptions = {"Male", "Female", "Other"};
        sexField = new JComboBox<>(sexOptions);
        parentSexField = new JComboBox<>(sexOptions);
        childSexField = new JComboBox<>(sexOptions);

        String[] civilStatusOptions = {"Single", "Married", "Divorced", "Widowed"};
        civilStatusField = new JComboBox<>(civilStatusOptions);
        parentCivilStatusField = new JComboBox<>(civilStatusOptions);

        String[] houseTypeOptions = {"Owned", "Rented", "Shared"};
        houseTypeField = new JComboBox<>(houseTypeOptions);

        String[] parentTypeOptions = {"Father", "Mother", "Guardian", "Other"};
        parentTypeField = new JComboBox<>(parentTypeOptions);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create main scroll pane for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Scholarship Application Form");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(20));

        // Personal Information Section
        formPanel.add(createSectionPanel("Personal Information", createPersonalInfoPanel()));
        formPanel.add(Box.createVerticalStrut(15));

        // Parent Information Section
        formPanel.add(createSectionPanel("Parent/Guardian Information", createParentPanel()));
        formPanel.add(Box.createVerticalStrut(15));

        // Children Information Section
        formPanel.add(createSectionPanel("Children Information", createChildPanel()));

        // Create scroll pane with horizontal scrolling enabled
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setPreferredSize(new Dimension(800, 600));

        // Add navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton backButton = new JButton("Back");
        JButton submitButton = new JButton("Submit");

        backButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        submitButton.setFont(new Font("Century Gothic", Font.BOLD, 14));

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        submitButton.addActionListener(e -> {
            FormData data = collectFormData();
            if (data != null && onFormSubmit != null) {
                onFormSubmit.accept(data);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(submitButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createSectionPanel(String title, JPanel contentPanel) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title,
                        javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                        new Font("Century Gothic", Font.BOLD, 16)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        sectionPanel.add(contentPanel, BorderLayout.CENTER);
        return sectionPanel;
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
        // Basic validation for required fields
        if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (First Name, Last Name, Email).",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        FormData data = new FormData();

        // Personal info
        data.setFirstName(firstNameField.getText().trim());
        data.setMiddleName(middleNameField.getText().trim());
        data.setLastName(lastNameField.getText().trim());
        data.setSuffix(suffixField.getText().trim());
        data.setBirthMonth((String) birthMonthField.getSelectedItem());
        data.setBirthDay(birthDayField.getText().trim());
        data.setBirthYear(birthYearField.getText().trim());
        data.setAge(ageField.getText().trim());
        data.setSex((String) sexField.getSelectedItem());
        data.setCivilStatus((String) civilStatusField.getSelectedItem());
        data.setReligion(religionField.getText().trim());
        data.setCitizenship(citizenshipField.getText().trim());
        data.setAddress(addressField.getText().trim());
        data.setPermanentAddress(permanentAddressField.getText().trim());
        data.setHouseType((String) houseTypeField.getSelectedItem());
        data.setIncome(incomeField.getText().trim());
        data.setContact(contactField.getText().trim());
        data.setEmail(emailField.getText().trim());
        data.setCellphone(cellphoneField.getText().trim());
        data.setGwa(gwaField.getText().trim());
        data.setDegreeProgram(degreeProgramField.getText().trim());
        data.setSchoolName(schoolNameField.getText().trim());
        data.setSchoolAddress(schoolAddressField.getText().trim());
        data.setSchoolEmail(schoolEmailField.getText().trim());
        data.setSchoolId(schoolIdField.getText().trim());

        // Parent info
        data.setParentType((String) parentTypeField.getSelectedItem());
        data.setParentFirstName(parentFirstNameField.getText().trim());
        data.setParentMiddleName(parentMiddleNameField.getText().trim());
        data.setParentLastName(parentLastNameField.getText().trim());
        data.setParentLastName(parentLastNameField.getText().trim());
        data.setParentSuffix(parentSuffixField.getText().trim());
        data.setParentBirthMonth((String) parentBirthMonthField.getSelectedItem());
        data.setParentBirthDay(parentBirthDayField.getText().trim());
        data.setParentBirthYear(parentBirthYearField.getText().trim());
        data.setParentAge(parentAgeField.getText().trim());
        data.setParentSex((String) parentSexField.getSelectedItem());
        data.setParentCitizenship(parentCitizenshipField.getText().trim());
        data.setParentCivilStatus((String) parentCivilStatusField.getSelectedItem());
        data.setParentOccupation(parentOccupationField.getText().trim());
        data.setParentEmail(parentEmailField.getText().trim());
        data.setParentMonthlyIncome(parentMonthlyIncomeField.getText().trim());
        data.setParentEducationalAttainment(parentEducationalAttainmentField.getText().trim());

        // Child info
        data.setChildFirstName(childFirstNameField.getText().trim());
        data.setChildMiddleName(childMiddleNameField.getText().trim());
        data.setChildLastName(childLastNameField.getText().trim());
        data.setChildSuffix(childSuffixField.getText().trim());
        data.setChildBirthMonth((String) childBirthMonthField.getSelectedItem());
        data.setChildBirthDay(childBirthDayField.getText().trim());
        data.setChildBirthYear(childBirthYearField.getText().trim());
        data.setChildAge(childAgeField.getText().trim());
        data.setChildSex((String) childSexField.getSelectedItem());
        data.setChildGradeLevel(childGradeLevelField.getText().trim());
        data.setChildSchoolName(childSchoolNameField.getText().trim());
        data.setChildSchoolAddress(childSchoolAddressField.getText().trim());

        return data;
    }
    public void prefillForm(FormData data) {
        if (data == null) return;

        // Personal Information
        if (data.getFirstName() != null) firstNameField.setText(data.getFirstName());
        if (data.getMiddleName() != null) middleNameField.setText(data.getMiddleName());
        if (data.getLastName() != null) lastNameField.setText(data.getLastName());
        if (data.getSuffix() != null) suffixField.setText(data.getSuffix());
        if (data.getBirthMonth() != null) birthMonthField.setSelectedItem(data.getBirthMonth());
        if (data.getBirthDay() != null) birthDayField.setText(data.getBirthDay());
        if (data.getBirthYear() != null) birthYearField.setText(data.getBirthYear());
        if (data.getAge() != null) ageField.setText(data.getAge());
        if (data.getSex() != null) sexField.setSelectedItem(data.getSex());
        if (data.getCivilStatus() != null) civilStatusField.setSelectedItem(data.getCivilStatus());
        if (data.getReligion() != null) religionField.setText(data.getReligion());
        if (data.getCitizenship() != null) citizenshipField.setText(data.getCitizenship());
        if (data.getAddress() != null) addressField.setText(data.getAddress());
        if (data.getPermanentAddress() != null) permanentAddressField.setText(data.getPermanentAddress());
        if (data.getHouseType() != null) houseTypeField.setSelectedItem(data.getHouseType());
        if (data.getIncome() != null) incomeField.setText(data.getIncome());
        if (data.getContact() != null) contactField.setText(data.getContact());
        if (data.getCellphone() != null) cellphoneField.setText(data.getCellphone());
        if (data.getEmail() != null) emailField.setText(data.getEmail());
        if (data.getGwa() != null) gwaField.setText(data.getGwa());
        if (data.getDegreeProgram() != null) degreeProgramField.setText(data.getDegreeProgram());
        if (data.getSchoolName() != null) schoolNameField.setText(data.getSchoolName());
        if (data.getSchoolAddress() != null) schoolAddressField.setText(data.getSchoolAddress());
        if (data.getSchoolEmail() != null) schoolEmailField.setText(data.getSchoolEmail());
        if (data.getSchoolId() != null) schoolIdField.setText(data.getSchoolId());

        // Parent Information
        if (data.getParentType() != null) parentTypeField.setSelectedItem(data.getParentType());
        if (data.getParentFirstName() != null) parentFirstNameField.setText(data.getParentFirstName());
        if (data.getParentMiddleName() != null) parentMiddleNameField.setText(data.getParentMiddleName());
        if (data.getParentLastName() != null) parentLastNameField.setText(data.getParentLastName());
        if (data.getParentSuffix() != null) parentSuffixField.setText(data.getParentSuffix());
        if (data.getParentBirthMonth() != null) parentBirthMonthField.setSelectedItem(data.getParentBirthMonth());
        if (data.getParentBirthDay() != null) parentBirthDayField.setText(data.getParentBirthDay());
        if (data.getParentBirthYear() != null) parentBirthYearField.setText(data.getParentBirthYear());
        if (data.getParentAge() != null) parentAgeField.setText(data.getParentAge());
        if (data.getParentSex() != null) parentSexField.setSelectedItem(data.getParentSex());
        if (data.getParentCitizenship() != null) parentCitizenshipField.setText(data.getParentCitizenship());
        if (data.getParentCivilStatus() != null) parentCivilStatusField.setSelectedItem(data.getParentCivilStatus());
        if (data.getParentOccupation() != null) parentOccupationField.setText(data.getParentOccupation());
        if (data.getParentEmail() != null) parentEmailField.setText(data.getParentEmail());
        if (data.getParentMonthlyIncome() != null) parentMonthlyIncomeField.setText(data.getParentMonthlyIncome());
        if (data.getParentEducationalAttainment() != null) parentEducationalAttainmentField.setText(data.getParentEducationalAttainment());

        // Child Information
        if (data.getChildFirstName() != null) childFirstNameField.setText(data.getChildFirstName());
        if (data.getChildMiddleName() != null) childMiddleNameField.setText(data.getChildMiddleName());
        if (data.getChildLastName() != null) childLastNameField.setText(data.getChildLastName());
        if (data.getChildSuffix() != null) childSuffixField.setText(data.getChildSuffix());
        if (data.getChildBirthMonth() != null) childBirthMonthField.setSelectedItem(data.getChildBirthMonth());
        if (data.getChildBirthDay() != null) childBirthDayField.setText(data.getChildBirthDay());
        if (data.getChildBirthYear() != null) childBirthYearField.setText(data.getChildBirthYear());
        if (data.getChildAge() != null) childAgeField.setText(data.getChildAge());
        if (data.getChildSex() != null) childSexField.setSelectedItem(data.getChildSex());
        if (data.getChildGradeLevel() != null) childGradeLevelField.setText(data.getChildGradeLevel());
        if (data.getChildSchoolName() != null) childSchoolNameField.setText(data.getChildSchoolName());
        if (data.getChildSchoolAddress() != null) childSchoolAddressField.setText(data.getChildSchoolAddress());
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
        birthMonthField.setSelectedIndex(0);
        birthDayField.setText("");
        birthYearField.setText("");
        ageField.setText("");
        sexField.setSelectedIndex(0);
        civilStatusField.setSelectedIndex(0);
        religionField.setText("");
        citizenshipField.setText("");
        addressField.setText("");
        permanentAddressField.setText("");
        houseTypeField.setSelectedIndex(0);
        incomeField.setText("");
        contactField.setText("");
        emailField.setText("");
        cellphoneField.setText("");
        gwaField.setText("");
        degreeProgramField.setText("");
        schoolNameField.setText("");
        schoolAddressField.setText("");
        schoolEmailField.setText("");
        schoolIdField.setText("");

        // Clear parent information fields
        parentTypeField.setSelectedIndex(0);
        parentFirstNameField.setText("");
        parentMiddleNameField.setText("");
        parentLastNameField.setText("");
        parentSuffixField.setText("");
        parentBirthMonthField.setSelectedIndex(0);
        parentBirthDayField.setText("");
        parentBirthYearField.setText("");
        parentAgeField.setText("");
        parentSexField.setSelectedIndex(0);
        parentCitizenshipField.setText("");
        parentCivilStatusField.setSelectedIndex(0);
        parentOccupationField.setText("");
        parentEmailField.setText("");
        parentMonthlyIncomeField.setText("");
        parentEducationalAttainmentField.setText("");

        // Clear child information fields
        childFirstNameField.setText("");
        childMiddleNameField.setText("");
        childLastNameField.setText("");
        childSuffixField.setText("");
        childBirthMonthField.setSelectedIndex(0);
        childBirthDayField.setText("");
        childBirthYearField.setText("");
        childAgeField.setText("");
        childSexField.setSelectedIndex(0);
        childGradeLevelField.setText("");
        childSchoolNameField.setText("");
        childSchoolAddressField.setText("");
    }

    /**
     * Validates the form data
     * @return true if all required fields are filled, false otherwise
     */
    public boolean validateForm() {
        StringBuilder errorMessage = new StringBuilder();

        // Check required personal information fields
        if (firstNameField.getText().trim().isEmpty()) {
            errorMessage.append("- First Name is required\n");
        }
        if (lastNameField.getText().trim().isEmpty()) {
            errorMessage.append("- Last Name is required\n");
        }
        if (emailField.getText().trim().isEmpty()) {
            errorMessage.append("- Email Address is required\n");
        }

        // Basic email validation
        String email = emailField.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorMessage.append("- Please enter a valid email address\n");
        }

        // Validate numeric fields if they're not empty
        if (!birthYearField.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(birthYearField.getText().trim());
            } catch (NumberFormatException e) {
                errorMessage.append("- Birth Year must be a valid number\n");
            }
        }

        if (!ageField.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(ageField.getText().trim());
            } catch (NumberFormatException e) {
                errorMessage.append("- Age must be a valid number\n");
            }
        }

        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Please correct the following errors:\n\n" + errorMessage.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}