package panels;

import model.FormData;
import javax.swing.*;
import java.awt.*;

public class FinalizeApplicationPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private FormData formData;

    private JTextArea summaryArea;

    public FinalizeApplicationPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        summaryArea = new JTextArea(20, 50);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        summaryArea.setBackground(new Color(250, 250, 250));
        summaryArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Application Summary");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Summary panel
        JScrollPane scrollPane = new JScrollPane(summaryArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Application Details"));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton updateButton = new JButton("Update Form");
        JButton submitButton = new JButton("Submit Application");
        JButton homeButton = new JButton("Back to Home");

        updateButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        submitButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        homeButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        // Update button logic
        updateButton.addActionListener(e -> {
            // Assuming "Form" maps to FillOutFormPanel and it's at index 0
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof panels.FillOutFormPanel fillPanel) {
                    fillPanel.prefillForm(formData); // You must implement this in FillOutFormPanel
                    break;
                }
            }
            cardLayout.show(mainPanel, "Form");
        });

        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Application submitted successfully! Thank you for submitting your scholarship application." +
                            " Our team will review your details and contact you for any updates. Please check your email " +
                            "regularly.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "Home");
        });

        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        buttonPanel.add(updateButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(homeButton);

        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
        updateSummary();
    }

    private void updateSummary() {
        if (formData == null) {
            summaryArea.setText("No form data available.");
            return;
        }

        StringBuilder summary = new StringBuilder();
        summary.append("SCHOLARSHIP APPLICATION SUMMARY\n");
        summary.append("=====================================\n\n");

        summary.append("APPLICANT INFORMATION:\n");
        summary.append("Name: ").append(formData.getFirstName()).append(" ");
        if (formData.getMiddleName() != null && !formData.getMiddleName().isEmpty()) {
            summary.append(formData.getMiddleName()).append(" ");
        }
        summary.append(formData.getLastName());
        if (formData.getSuffix() != null && !formData.getSuffix().isEmpty()) {
            summary.append(" ").append(formData.getSuffix());
        }
        summary.append("\n");

        summary.append("Date of Birth: ").append(formData.getBirthMonth()).append(" ")
                .append(formData.getBirthDay()).append(", ").append(formData.getBirthYear()).append("\n");
        summary.append("Age: ").append(formData.getAge()).append("\n");
        summary.append("Sex: ").append(formData.getSex()).append("\n");
        summary.append("Civil Status: ").append(formData.getCivilStatus()).append("\n");
        summary.append("Religion: ").append(formData.getReligion()).append("\n");
        summary.append("Citizenship: ").append(formData.getCitizenship()).append("\n");
        summary.append("Address: ").append(formData.getAddress()).append("\n");
        summary.append("Permanent Address: ").append(formData.getPermanentAddress()).append("\n");
        summary.append("House Type: ").append(formData.getHouseType()).append("\n");
        summary.append("Family Income: ").append(formData.getIncome()).append("\n");
        summary.append("Contact Number: ").append(formData.getCellphone()).append("\n");
        summary.append("Email: ").append(formData.getEmail()).append("\n");
        summary.append("GWA: ").append(formData.getGwa()).append("\n");
        summary.append("Degree Program: ").append(formData.getDegreeProgram()).append("\n");
        summary.append("School Name: ").append(formData.getSchoolName()).append("\n");
        summary.append("School Address: ").append(formData.getSchoolAddress()).append("\n");
        summary.append("School Email: ").append(formData.getSchoolEmail()).append("\n");
        summary.append("School ID: ").append(formData.getSchoolId()).append("\n\n");

        summary.append("PARENT/GUARDIAN INFORMATION:\n");
        summary.append("Parent Type: ").append(formData.getParentType()).append("\n");
        summary.append("Name: ").append(formData.getParentFirstName()).append(" ");
        if (formData.getParentMiddleName() != null && !formData.getParentMiddleName().isEmpty()) {
            summary.append(formData.getParentMiddleName()).append(" ");
        }
        summary.append(formData.getParentLastName());
        if (formData.getParentSuffix() != null && !formData.getParentSuffix().isEmpty()) {
            summary.append(" ").append(formData.getParentSuffix());
        }
        summary.append("\n");
        summary.append("Date of Birth: ").append(formData.getParentBirthMonth()).append(" ")
                .append(formData.getParentBirthDay()).append(", ").append(formData.getParentBirthYear()).append("\n");
        summary.append("Age: ").append(formData.getParentAge()).append("\n");
        summary.append("Sex: ").append(formData.getParentSex()).append("\n");
        summary.append("Citizenship: ").append(formData.getParentCitizenship()).append("\n");
        summary.append("Civil Status: ").append(formData.getParentCivilStatus()).append("\n");
        summary.append("Occupation: ").append(formData.getParentOccupation()).append("\n");
        summary.append("Email: ").append(formData.getParentEmail()).append("\n");
        summary.append("Monthly Income: ").append(formData.getParentMonthlyIncome()).append("\n");
        summary.append("Educational Attainment: ").append(formData.getParentEducationalAttainment()).append("\n\n");

        summary.append("CHILD INFORMATION:\n");
        summary.append("Name: ").append(formData.getChildFirstName()).append(" ");
        if (formData.getChildMiddleName() != null && !formData.getChildMiddleName().isEmpty()) {
            summary.append(formData.getChildMiddleName()).append(" ");
        }
        summary.append(formData.getChildLastName());
        if (formData.getChildSuffix() != null && !formData.getChildSuffix().isEmpty()) {
            summary.append(" ").append(formData.getChildSuffix());
        }
        summary.append("\n");
        summary.append("Date of Birth: ").append(formData.getChildBirthMonth()).append(" ")
                .append(formData.getChildBirthDay()).append(", ").append(formData.getChildBirthYear()).append("\n");
        summary.append("Age: ").append(formData.getChildAge()).append("\n");
        summary.append("Sex: ").append(formData.getChildSex()).append("\n");
        summary.append("Grade Level: ").append(formData.getChildGradeLevel()).append("\n");
        summary.append("School Name: ").append(formData.getChildSchoolName()).append("\n");
        summary.append("School Address: ").append(formData.getChildSchoolAddress()).append("\n");

        summaryArea.setText(summary.toString());
        summaryArea.setCaretPosition(0);
    }
}