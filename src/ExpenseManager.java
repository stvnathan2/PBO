import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private static ExpenseManager instance;

    private ExpenseManager() {
        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS daily_expenses (" +
                         "id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT," +
                         "username VARCHAR(20) NOT NULL," +
                         "amount DECIMAL(10,2) NOT NULL," +
                         "type VARCHAR(15) NOT NULL," +
                         "category VARCHAR(50) NOT NULL," +
                         "date DATE NOT NULL," +
                         "description TEXT," +
                         "payment_method VARCHAR(50)," +
                         "account VARCHAR(50)," +
                         "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                         "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                         "CONSTRAINT daily_expenses_chk_1 CHECK (type IN ('pemasukan', 'pengeluaran'))" +
                         ")";
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
        String sql = "INSERT INTO daily_expenses (username, amount, type, category, date, description, payment_method, account) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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

    public List<Expense> getExpensesByUserAndMonth(String username, int month, int year) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM daily_expenses WHERE username = ? AND MONTH(date) = ? AND YEAR(date) = ?";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);
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
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }
}
