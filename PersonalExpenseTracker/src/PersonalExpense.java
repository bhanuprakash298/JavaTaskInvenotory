import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

enum ExpenseCategory 
{
    FOOD,
    TRAVEL,
    SHOPPING,
    BILLS,
    HEALTH,
    EDUCATION,
    ENTERTAINMENT,
    OTHER
}

class Expense {

    private int expenseId;
    private String description;
    private double amount;
    private ExpenseCategory category;
    private LocalDate expenseDate;
    public Expense(int expenseId, String description, double amount,ExpenseCategory category, LocalDate expenseDate) 
    {

        this.expenseId = expenseId;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    @Override
    public String toString() {

        return "ID: " + expenseId +
               " | " + expenseDate +
               " | " + category +
               " | " + description +
               " | Rs " + amount;
    }
}

public class PersonalExpense 
{
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Expense> expenseBook = new ArrayList<>();
    static int nextExpenseId = 1001;
    public static void main(String[] args) 
    {
        boolean running = true;
        while (running) 
        {   
            System.out.println("\n======================================");
            System.out.println("       PERSONAL EXPENSE TRACKER   ");
            System.out.println("======================================");
            System.out.println("1. Add Expense");
            System.out.println("2. Display All Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Calculate Total Expense");
            System.out.println("5. Find Highest Expense");
            System.out.println("6. Category-wise Expenses");
            System.out.println("7. Exit");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) 
            {

                case 1:
                    addExpense();
                    break;

                case 2:
                    displayExpenses();
                    break;

                case 3:
                    deleteExpense();
                    break;

                case 4:
                    calculateTotal();
                    break;

                case 5:
                    findHighestExpense();
                    break;

                case 6:
                    categoryWiseExpense();
                    break;

                case 7:
                    running = false;
                    System.out.println("Thank you for using Expense Tracker!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
    // Add a new expense
    static void addExpense() {

        System.out.println("\n---------- ADD EXPENSE ----------");

        System.out.print("Enter expense description: ");
        String description = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        System.out.println("\nSelect Category:");

        ExpenseCategory[] categories = ExpenseCategory.values();

        for (int i = 0; i < categories.length; i++)
        {
            System.out.println((i + 1) + ". " + categories[i]);
        }

        System.out.print("Enter category number: ");
        int categoryChoice = sc.nextInt();
        sc.nextLine();

        if (categoryChoice < 1 || categoryChoice > categories.length) 
        {
            System.out.println("Invalid category.");
            return;
        }

        ExpenseCategory selectedCategory =categories[categoryChoice - 1];
        LocalDate today = LocalDate.now();
        Expense newExpense = new Expense(
                nextExpenseId,
                description,
                amount,
                selectedCategory,
                today
        );

        expenseBook.add(newExpense);

        System.out.println("\nExpense added successfully!");
        System.out.println("Expense ID: " + nextExpenseId);
        nextExpenseId++;
    }

    // Display every expense
    static void displayExpenses()
    {
        System.out.println("\n---------- ALL EXPENSES ----------");
        if (expenseBook.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        expenseBook.forEach(expense ->
                System.out.println(expense)
        );
    }

    // Delete expense using ID
    static void deleteExpense()
    {
        System.out.println("\n---------- DELETE EXPENSE ----------");

        if (expenseBook.isEmpty()) {
            System.out.println("There are no expenses to delete.");
            return;
        }

        System.out.print("Enter Expense ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean removed = expenseBook.removeIf(expense -> expense.getExpenseId() == id);

        if (removed) {
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Expense ID not found.");
        }
    }

    // Calculate total using Stream
    static void calculateTotal() {

        System.out.println("\n---------- TOTAL EXPENSE ----------");

        if (expenseBook.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        double total = expenseBook
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        System.out.println("Total Expense: ₹" + total);
    }

    // Find highest expense using Stream
    static void findHighestExpense() {

        System.out.println("\n---------- HIGHEST EXPENSE ----------");

        if (expenseBook.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        Expense highest = expenseBook.stream().max((e1, e2) ->Double.compare(e1.getAmount(), e2.getAmount())).orElse(null);
        System.out.println("Highest Expense:");
        System.out.println(highest);
    }

    // Category-wise calculation using HashMap + Stream
    static void categoryWiseExpense() 
    {
        System.out.println("\n---------- CATEGORY SUMMARY ----------");

        if (expenseBook.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        HashMap<ExpenseCategory, Double> categorySummary =new HashMap<>();
        expenseBook.stream().forEach(expense -> 
                {

                    categorySummary.merge(expense.getCategory(),expense.getAmount(), Double::sum);
                });

        for (Map.Entry<ExpenseCategory, Double> entry: categorySummary.entrySet()) 
        {

            System.out.println(entry.getKey() +" : Rs " +entry.getValue());
        }
    }
}