package BankManagementSoftware;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Customer {
    private String customerId;
    private String customerName;
    private double accountBalance;
    private String atmStatus;
    private String checkbookStatus;
    private Map<String, Double> transactionHistory;

    public Customer(String customerId, String customerName, double accountBalance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountBalance = accountBalance;
        this.atmStatus = "Not Applied";
        this.checkbookStatus = "Not Applied";
        this.transactionHistory = new HashMap<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public String getAtmStatus() {
        return atmStatus;
    }

    public String getCheckbookStatus() {
        return checkbookStatus;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value for deposit.");
            return;
        }
        accountBalance += amount;
        addToTransactionHistory("Deposit", amount);
        System.out.println("Deposited: " + formatCurrency(amount));
        displayBalance();
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value for withdrawal.");
            return;
        }
        if (amount > accountBalance) {
            System.out.println("Insufficient funds. Withdrawal failed.");
        } else {
            accountBalance -= amount;
            addToTransactionHistory("Withdrawal", amount);
            System.out.println("Withdrawn: " + formatCurrency(amount));
            displayBalance();
        }
    }

    public void applyForATM() {
        if (atmStatus.equals("Not Applied")) {
            atmStatus = "Pending Approval";
            System.out.println("ATM application submitted. It will be processed shortly.");
        } else if (atmStatus.equals("Pending Approval")) {
            System.out.println("ATM application is already pending approval.");
        } else {
            System.out.println("You already have an active ATM.");
        }
    }

    public void applyForCheckbook() {
        if (checkbookStatus.equals("Not Applied")) {
            checkbookStatus = "Pending Approval";
            System.out.println("Checkbook application submitted. It will be processed shortly.");
        } else if (checkbookStatus.equals("Pending Approval")) {
            System.out.println("Checkbook application is already pending approval.");
        } else {
            System.out.println("You already have an active checkbook.");
        }
    }

    public void viewTransactionHistory() {
        System.out.println("------ Transaction History ------");
        for (Map.Entry<String, Double> entry : transactionHistory.entrySet()) {
            System.out.println(entry.getKey() + ": " + formatCurrency(entry.getValue()));
        }
    }

    private void addToTransactionHistory(String transactionType, double amount) {
        String transaction = transactionType + ": " + formatCurrency(amount);
        transactionHistory.put(transaction, amount);
    }

    private String formatCurrency(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        return currencyFormatter.format(amount);
    }

    public void displayBalance() {
        System.out.println("Current Balance: " + formatCurrency(accountBalance));
    }
}

public class BankManagementSystem {
    private static Map<String, Customer> customers = new HashMap<>();
    private static int customerIdCounter = 1000;

    public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBank Management System");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Apply for ATM");
            System.out.println("5. Apply for Checkbook");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");

            System.out.print("Enter choice (1-7): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    performTransaction("deposit");
                    break;
                case 3:
                    performTransaction("withdraw");
                    break;
                case 4:
                    applyForATM();
                    break;
                case 5:
                    applyForCheckbook();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    System.out.println("Exiting Bank Management System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private static void createAccount() {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        String customerId = "A" + customerIdCounter++;
        double initialBalance = 0.0;

        Customer newCustomer = new Customer(customerId, customerName, initialBalance);
        customers.put(customerId, newCustomer);

        System.out.println("Account created successfully.");
        System.out.println("Customer ID: " + customerId);
    }

    private static void performTransaction(String transactionType) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = customers.get(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please enter a valid customer ID.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value for the amount.");
            return;
        }

        if (transactionType.equals("deposit")) {
            customer.deposit(amount);
        } else if (transactionType.equals("withdraw")) {
            customer.withdraw(amount);
        }
    }

    private static void applyForATM() {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = customers.get(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please enter a valid customer ID.");
            return;
        }

        customer.applyForATM();
    }

    private static void applyForCheckbook() {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = customers.get(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please enter a valid customer ID.");
            return;
        }

        customer.applyForCheckbook();
    }

    private static void viewTransactionHistory() {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer = customers.get(customerId);

        if (customer == null) {
            System.out.println("Customer not found. Please enter a valid customer ID.");
            return;
        }

        customer.viewTransactionHistory();
    }
}
