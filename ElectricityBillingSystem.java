package bill;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ElectricityBillingSystem extends Frame implements ActionListener {
	private ArrayList<User> users;
    TextField txtInput, txtMeterReading, txtAmount;
    Label lblInput, lblChoice, lblResult, lblMeterReading, lblAmount;
    CheckboxGroup choiceGroup;
    Checkbox postpaid, prepaid;
    Button validateButton, calculateButton;

    public ElectricityBillingSystem() {
        setLayout(new GridLayout(10, 2));
        setBackground(Color.yellow);
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font textFont = new Font("Arial", Font.PLAIN, 20);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        users = new ArrayList<User>();
        users.add(new User("Mythili", "9025930150", "EB001", "123 Main St"));
        users.add(new User("Jegan", "8883139860", "EB002", "456 Elm St"));
        users.add(new User("Koushi", "8807748192", "EB003", "789 Oak St"));
        users.add(new User("Swati", "9344876996", "EB004", "101 Pine St"));
        users.add(new User("Abi", "8678901234", "EB005", "202 Maple St"));

        lblInput = new Label("Enter Username/Phone/EB Number:", Label.LEFT);
        lblInput.setFont(labelFont);
        txtInput = new TextField();
        txtInput.setFont(textFont);

        lblChoice = new Label("Select Billing Type:", Label.LEFT);
        lblChoice.setFont(labelFont);

        choiceGroup = new CheckboxGroup();
        postpaid = new Checkbox("Postpaid", choiceGroup, true);
        prepaid = new Checkbox("Prepaid", choiceGroup, false);
        postpaid.setFont(labelFont);
        prepaid.setFont(labelFont);

        lblMeterReading = new Label("Enter Meter Reading (kWh):", Label.LEFT);
        lblMeterReading.setFont(labelFont);
        txtMeterReading = new TextField();
        txtMeterReading.setFont(textFont);

        lblAmount = new Label("Enter Amount to Recharge:", Label.LEFT);
        lblAmount.setFont(labelFont);
        txtAmount = new TextField();
        txtAmount.setFont(textFont);

        validateButton = new Button("Validate User");
        validateButton.setFont(buttonFont);
        validateButton.addActionListener(this);

        calculateButton = new Button("Calculate");
        calculateButton.setFont(buttonFont);
        calculateButton.addActionListener(this);

        lblResult = new Label("--");
        lblResult.setFont(labelFont);

        add(lblInput);
        add(txtInput);
        add(lblChoice);
        add(new Label(""));
        add(postpaid);
        add(prepaid);
        add(lblMeterReading);
        add(txtMeterReading);
        add(lblAmount);
        add(txtAmount);
        add(validateButton);
        add(calculateButton);
        add(lblResult);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent exitState) {
                System.exit(0);
            }
        });
    }

    private User findUser(String input) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(input) || user.getPhoneNumber().equals(input) || user.getEbNumber().equalsIgnoreCase(input)) {
                return user;
            }
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == validateButton) {
            String input = txtInput.getText();
            User user = findUser(input);
            if (user == null) {
                lblResult.setText("Error: User not found!");
            } else {
                lblResult.setText("User validated: " + user.getUsername());
            }
        } else if (e.getSource() == calculateButton) {
            User user = findUser(txtInput.getText());
            if (user == null) {
                lblResult.setText("Error: User not found!");
                return;
            }

            String choice = choiceGroup.getSelectedCheckbox().getLabel();
            if (choice.equals("Postpaid")) {
                try {
                    double meterReading = Double.parseDouble(txtMeterReading.getText());
                    double amount = calculatePostpaidBill(meterReading);
                    lblResult.setText("Total amount: $" + amount);
                } catch (NumberFormatException ex) {
                    lblResult.setText("Please enter a valid meter reading.");
                }
            } else if (choice.equals("Prepaid")) {
                try {
                    double amount = Double.parseDouble(txtAmount.getText());
                    double addedKW = calculatePrepaidRecharge(amount);
                    lblResult.setText("Recharge: $" + amount + " adds " + addedKW + " kWh.");
                } catch (NumberFormatException ex) {
                    lblResult.setText("Please enter a valid amount.");
                }
            } else {
                lblResult.setText("Invalid choice!");
            }
        }
    }

    private double calculatePostpaidBill(double meterReading) {
        double ratePerKWh = 0.12; // example rate
        return meterReading * ratePerKWh;
    }

    private double calculatePrepaidRecharge(double amount) {
        double ratePerKWh = 0.12; // example rate
        return amount / ratePerKWh;
    }

    public static void main(String[] args) {
        ElectricityBillingSystem app = new ElectricityBillingSystem();
        app.setSize(new Dimension(600, 600));
        app.setTitle("Electricity Billing System");
        app.setVisible(true);
    }
    
    class User {
        private String username;
        private String phoneNumber;
        private String ebNumber;
        private String address;

        public User(String username, String phoneNumber, String ebNumber, String address) {
            this.username = username;
            this.phoneNumber = phoneNumber;
            this.ebNumber = ebNumber;
            this.address = address;
        }

        public String getUsername() {
            return username;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getEbNumber() {
            return ebNumber;
        }

        public String getAddress() {
            return address;
        }
    }
}
