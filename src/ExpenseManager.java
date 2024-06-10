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
        // Kode tambahan untuk menambahkan data transaksi ke database
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
