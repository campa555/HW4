import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {

    // define the main frame
    static JFrame frame = new JFrame("Calculator");
    // define number and operator buttons
    static JButton[] numberButtons = new JButton[12];
    static JButton[] operatorButtons = new JButton[7];
    // define calculator's text field
    static JTextField textField = new JTextField();
    // define key variables
    static double num1 = 0, num2 = 0, result = 0, ans = 0;
    static char operator;
    static boolean calculated = false;


    public void main(String[] args) {
        // configure the main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(440, 330);
        frame.setResizable(false);
        frame.setLayout(null);

        // initiate the numberButtons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(Integer.toString(i));
        }
        numberButtons[10] = new JButton(".");
        numberButtons[11] = new JButton("Ans");

        // initiate operator buttons
        operatorButtons[0] = new JButton("+");
        operatorButtons[1] = new JButton("-");
        operatorButtons[2] = new JButton("×");
        operatorButtons[3] = new JButton("/");
        operatorButtons[4] = new JButton("=");
        operatorButtons[5] = new JButton("DEL");
        operatorButtons[6] = new JButton("AC");

        // add action listener for every button
        for (JButton numberButton : numberButtons) {
            numberButton.addActionListener(this);
        }
        for (JButton operatorButton : operatorButtons) {
            operatorButton.addActionListener(this);
        }

        // create a new panel for numbers
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 3));
        numberPanel.setBounds(10, 80, 240, 200);

        // create a new panel for operators
        JPanel operatorPanel = new JPanel();
        operatorPanel.setLayout(new GridLayout(3, 2));
        operatorPanel.setBounds(260, 80, 160, 150);

        // create a dedicated panel for equal sign
        JPanel equalPanel = new JPanel();
        equalPanel.setLayout(new GridLayout(1, 1));
        equalPanel.setBounds(260, 80+150, 160, 50);

        // add numbers to numberPanel
        for (int i = 9; i > 0; i--) {
            numberPanel.add(numberButtons[i]);
        }
        numberPanel.add(numberButtons[10]);
        numberPanel.add(numberButtons[0]);
        numberPanel.add(numberButtons[11]);

        // add buttons to operatorPanel
        operatorPanel.add(operatorButtons[5]);
        operatorPanel.add(operatorButtons[6]);
        operatorPanel.add(operatorButtons[2]);
        operatorPanel.add(operatorButtons[3]);
        operatorPanel.add(operatorButtons[0]);
        operatorPanel.add(operatorButtons[1]);

        // add = sign to equation panel
        equalPanel.add(operatorButtons[4]);

        // add panels to frame
        frame.add(numberPanel);
        frame.add(operatorPanel);
        frame.add(equalPanel);

        // configure textField
        textField.setFont(new Font("monospaced", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setBounds(10, 15, 410, 50);

        frame.add(textField);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // check if number button is pressed
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (calculated) {
                    textField.setText(String.valueOf(i));
                }
                else {
                    textField.setText(textField.getText().concat(String.valueOf(i)));
                }
            }
        }

        // check for decimal button
        if (e.getSource() == numberButtons[10]) {
            // prevent adding more than one decimal point
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText().concat("."));
            }
        }

        // check if Ans button is pressed
        if (e.getSource() == numberButtons[11]) {
            if (textField.getText().equals("-")) {
                textField.setText(textField.getText().concat(String.valueOf(ans)));
            }
            else  {
                textField.setText(String.valueOf(ans));
            }
        }

        // operator buttons (+, ×, /)
        if (e.getSource() == operatorButtons[0]) {
            performOperation('+');
            calculated = false;
        }
        if (e.getSource() == operatorButtons[2]) {
            performOperation('×');
            calculated = false;
        }
        if (e.getSource() == operatorButtons[3]) {
            performOperation('/');
            calculated = false;
        }

        // negative or subtract button (-)
        if (e.getSource() == operatorButtons[1]) {
            // if textField is empty, (-) means negative sign variable
            if (textField.getText().isEmpty()) {
                textField.setText("-");
            }
            // if text already has a number, we have subtraction operation
            else {
                performOperation('-');
            }
            calculated = false;
        }

        // equals button (=)
        if (e.getSource() == operatorButtons[4]) {
            try {
                num2 = Double.parseDouble(textField.getText());

                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '×':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0) result = num1 / num2;
                        else result = 0; // prevent division by zero
                        break;
                }

                // remove the decimal point if it's a whole number
                if(result % 1 == 0) {
                    textField.setText(String.format("%.0f", result));
                } else {
                    textField.setText(String.valueOf(result));
                }

                num1 = result; // add chane property
                ans = result;
                calculated = true;
            } catch (Exception ex) {
                // if user did not put any value in the field before pressing (=)
                textField.setText(String.valueOf(num1));
            }
        }

        // clear button (AC)
        if (e.getSource() == operatorButtons[6]) {
            textField.setText("");
            num1 = 0;
            num2 = 0;
            calculated = false;
        }

        // delete button (DEL)
        if (e.getSource() == operatorButtons[5]) {
            if (!textField.getText().isEmpty()) {
                textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            }
        }
    }

    // Helper method to save the number and operator
    public void performOperation(char op) {
        try {
            if (!textField.getText().isEmpty() && !textField.getText().equals("-")) {
                num1 = Double.parseDouble(textField.getText());
                operator = op;
                textField.setText("");
            }
        } catch (NumberFormatException ex) {
            // by leaving this empty, program will ignore user pushing operator button
        }
    }
}