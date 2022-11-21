package mainWindow;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
    private mainPanel mainPanel;

    public MainFrame () {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(320, 127, 60, 400);
        this.setResizable(true);
        this.setVisible(true);
        this.mainPanel = new mainPanel(this);
        this.getContentPane().add(mainPanel);
    }
}
