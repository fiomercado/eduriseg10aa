package window;

import component.PanelLoginAndRegister;
import model.MainApp;

import javax.swing.*;

public class LoginWindow extends JFrame {

    private PanelLoginAndRegister loginAndRegister;

    public LoginWindow() {
        setTitle("EduRise Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loginAndRegister = new PanelLoginAndRegister();

        // Assume PanelLoginAndRegister has this method signature:
        // void setLoginSuccessCallback(LoginSuccessListener listener);
        loginAndRegister.setLoginSuccessCallback((email, userName) -> { // Corrected lambda
            // Close login window
            this.dispose();

            // Open MainApp window with username and email
            MainApp mainApp = new MainApp(userName, email);
            mainApp.showApp();
        });

        add(loginAndRegister);
        loginAndRegister.showRegister(false);  // default to Sign In
        setVisible(true);
    }

    public PanelLoginAndRegister getLoginAndRegisterPanel() {
        return loginAndRegister;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginWindow::new);
    }
}
