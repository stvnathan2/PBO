import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private static ExpenseManager instance;

    private ExpenseManager() {
        try (Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS daily_expenses (" +
                         "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                         "username TEXT NOT NULL," +
                         "amount REAL NOT NULL," +
                         "type TEXT NOT NULL," +
                         "category TEXT NOT NULL," +
                         "date TEXT NOT NULL," +
                         "description TEXT," +
                         "payment_method TEXT," +
                         "account TEXT)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    public void addExpense(Expense expense) {
        String sql = "INSERT INTO daily_expenses(username, amount, type, category, date, description, payment_method, account) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, expense.getUsername());
            pstmt.setDouble(2, expense.getAmount());
            pstmt.setString(3, expense.getType());
            pstmt.setString(4, expense.getCategory());
            pstmt.setString(5, expense.getDate());
            pstmt.setString(6, expense.getDescription());
            pstmt.setString(7, expense.getPaymentMethod());
            pstmt.setString(8, expense.getAccount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getExpensesByUser(String username) {
        List<Expense> daily_expenses = new ArrayList<>();
        String sql = "SELECT * FROM daily_expenses WHERE username = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Expense expense = new Expense(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getDouble("amount"),
                    rs.getString("type"),
                    rs.getString("category"),
                    rs.getString("date"),
                    rs.getString("description"),
                    rs.getString("payment_method"),
                    rs.getString("account")
                );
                daily_expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daily_expenses;
    }

    public void updateExpense(Expense expense) {
        String sql = "UPDATE daily_expenses SET amount = ?, type = ?, category = ?, date = ?, description = ?, payment_method = ?, account = ? WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, expense.getAmount());
            pstmt.setString(2, expense.getType());
            pstmt.setString(3, expense.getCategory());
            pstmt.setString(4, expense.getDate());
            pstmt.setString(5, expense.getDescription());
            pstmt.setString(6, expense.getPaymentMethod());
            pstmt.setString(7, expense.getAccount());
            pstmt.setInt(8, expense.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExpense(int expenseId) {
        String sql = "DELETE FROM daily_expenses WHERE id = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expenseId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
