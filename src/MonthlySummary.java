import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class MonthlySummary extends JPanel {
    private DefaultPieDataset dataset;
    private JLabel totalIncomeLabel;
    private JLabel totalIncomeAmountLabel;
    private JLabel totalExpensesLabel;
    private JLabel totalExpensesAmountLabel;
    private JLabel differenceLabel;
    private JLabel differenceAmountLabel;
    private JLabel averageDailyExpensesLabel;
    private JLabel averageDailyExpensesAmountLabel;
    private JLabel highestExpenseCategoryLabel;
    private JLabel highestExpenseCategoryAmountLabel;
    private JLabel lowestExpenseCategoryLabel;
    private JLabel lowestExpenseCategoryAmountLabel;
    private JPanel chartPanel;
    private JPanel summaryPanel;
    private ChartPanel chartPanelComponent;
    private int currentMonth;
    private int currentYear;

    public MonthlySummary(String username) {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel monthLabel = new JLabel("Bulan: ");
        JComboBox<String> monthComboBox = new JComboBox<>(getMonths());
        JLabel yearLabel = new JLabel("Tahun: ");
        JComboBox<String> yearComboBox = new JComboBox<>(getYears());
        JButton showButton = new JButton("Tampilkan");

        selectionPanel.add(monthLabel);
        selectionPanel.add(monthComboBox);
        selectionPanel.add(yearLabel);
        selectionPanel.add(yearComboBox);
        selectionPanel.add(showButton);
        panel.add(selectionPanel);

        chartPanel = new JPanel(new BorderLayout());
        panel.add(chartPanel);

        dataset = new DefaultPieDataset();
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);
        chartPanelComponent = new ChartPanel(chart);
        chartPanelComponent.setPreferredSize(new Dimension(500, 400));
        chartPanel.add(chartPanelComponent, BorderLayout.CENTER);

        summaryPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        panel.add(summaryPanel);

        Font font = new Font("Arial", Font.PLAIN, 18);

        totalIncomeLabel = createLabel("Pemasukan: ", font);
        totalIncomeAmountLabel = createLabel("", font);
        totalExpensesLabel = createLabel("Pengeluaran: ", font);
        totalExpensesAmountLabel = createLabel("", font);
        differenceLabel = createLabel("Selisih: ", font);
        differenceAmountLabel = createLabel("", font);
        averageDailyExpensesLabel = createLabel("Rata-rata Pengeluaran Harian: ", font);
        averageDailyExpensesAmountLabel = createLabel("", font);
        highestExpenseCategoryLabel = createLabel("Kategori Pengeluaran Terbesar: ", font);
        highestExpenseCategoryAmountLabel = createLabel("", font);
        lowestExpenseCategoryLabel = createLabel("Kategori Pengeluaran Terkecil: ", font);
        lowestExpenseCategoryAmountLabel = createLabel("", font);

        addToSummaryPanel(summaryPanel, totalIncomeLabel, totalIncomeAmountLabel);
        addToSummaryPanel(summaryPanel, totalExpensesLabel, totalExpensesAmountLabel);
        addToSummaryPanel(summaryPanel, differenceLabel, differenceAmountLabel);
        addToSummaryPanel(summaryPanel, averageDailyExpensesLabel, averageDailyExpensesAmountLabel);
        addToSummaryPanel(summaryPanel, highestExpenseCategoryLabel, highestExpenseCategoryAmountLabel);
        addToSummaryPanel(summaryPanel, lowestExpenseCategoryLabel, lowestExpenseCategoryAmountLabel);

        java.time.LocalDate now = java.time.LocalDate.now();
        currentMonth = now.getMonthValue();
        currentYear = now.getYear();
        monthComboBox.setSelectedIndex(currentMonth - 1);
        yearComboBox.setSelectedItem(String.valueOf(currentYear));

        showButton.addActionListener(e -> {
            String selectedMonth = (String) monthComboBox.getSelectedItem();
            String selectedYear = (String) yearComboBox.getSelectedItem();
            fetchDataFromDatabase(selectedMonth, selectedYear);
            chartPanel.setVisible(true);
            summaryPanel.setVisible(true);
        });
    }

    private String[] getMonths() {
        return new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    }

    private String[] getYears() {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        String[] years = new String[currentYear - 1999];
        for (int i = 0; i <= currentYear - 2000; i++) {
            years[i] = String.valueOf(currentYear - i);
        }
        return years;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void addToSummaryPanel(JPanel panel, JLabel label1, JLabel label2) {
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.add(label1);
        subPanel.add(label2);
        panel.add(subPanel);
    }

    private String convertMonthToNumber(String month) {
        switch (month) {
            case "Januari": return "1";
            case "Februari": return "2";
            case "Maret": return "3";
            case "April": return "4";
            case "Mei": return "5";
            case "Juni": return "6";
            case "Juli": return "7";
            case "Agustus": return "8";
            case "September": return "9";
            case "Oktober": return "10";
            case "November": return "11";
            case "Desember": return "12";
            default: return "0";
        }
    }

    private void fetchDataFromDatabase(String month, String year) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            String sql = "SELECT SUM(amount) as total FROM daily_expenses WHERE type = 'pemasukan' AND MONTH(date) = ? AND YEAR(date) = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, convertMonthToNumber(month));
            stmt.setString(2, year);
            ResultSet rs = stmt.executeQuery();

            DecimalFormat df = new DecimalFormat("#,###.##");
            double totalIncome = 0;
            if (rs.next()) {
                totalIncome = rs.getDouble("total");
            }
            String formattedIncome = df.format(totalIncome);
            totalIncomeAmountLabel.setText("Rp" + formattedIncome);

            sql = "SELECT SUM(amount) as total FROM daily_expenses WHERE type = 'pengeluaran' AND MONTH(date) = ? AND YEAR(date) = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, convertMonthToNumber(month));
            stmt.setString(2, year);
            rs = stmt.executeQuery();

            double totalExpenses = 0;
            if (rs.next()) {
                totalExpenses = rs.getDouble("total");
            }
            String formattedExpenses = df.format(totalExpenses);
            totalExpensesAmountLabel.setText("Rp" + formattedExpenses);

            double difference = totalIncome - totalExpenses;
            String formattedDifference = df.format(difference);
            differenceAmountLabel.setText("Rp" + formattedDifference);

            double averageDailyExpenses = totalExpenses/30;
            String formattedAverage = df.format(averageDailyExpenses);
            averageDailyExpensesAmountLabel.setText("Rp" + formattedAverage);

            String highestExpenseCategory = "";
            double highestExpenseAmount = 0;

            sql = "SELECT category, SUM(amount) AS total_amount " +
                  "FROM daily_expenses " +
                  "WHERE type = 'pengeluaran' AND MONTH(date) = ? AND YEAR(date) = ? " +
                  "GROUP BY category " +
                  "ORDER BY total_amount DESC " +
                  "LIMIT 1";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, convertMonthToNumber(month));
            stmt.setString(2, year);
            rs = stmt.executeQuery();

            if (rs.next()) {
                highestExpenseCategory = rs.getString("category");
                highestExpenseAmount = rs.getDouble("total_amount");
            }
            String formattedHighest = df.format(highestExpenseAmount);
            highestExpenseCategoryAmountLabel.setText(highestExpenseCategory + " (Rp" + formattedHighest + ")");

            String lowestExpenseCategory = "";
            double lowestExpenseAmount = 0;

            sql = "SELECT category, SUM(amount) AS total_amount " +
                  "FROM daily_expenses " +
                  "WHERE type = 'pengeluaran' AND MONTH(date) = ? AND YEAR(date) = ? " +
                  "GROUP BY category " +
                  "ORDER BY total_amount ASC " +
                  "LIMIT 1";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, convertMonthToNumber(month));
            stmt.setString(2, year);
            rs = stmt.executeQuery();

            if (rs.next()) {
                lowestExpenseCategory = rs.getString("category");
                lowestExpenseAmount = rs.getDouble("total_amount");
            }
            String formattedLowest = df.format(lowestExpenseAmount);
            lowestExpenseCategoryAmountLabel.setText(lowestExpenseCategory + " (Rp" + formattedLowest + ")");

            dataset.clear();
            sql = "SELECT category, SUM(amount) as total FROM daily_expenses WHERE type = 'pengeluaran' AND MONTH(date) = ? AND YEAR(date) = ? GROUP BY category";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, convertMonthToNumber(month));
            stmt.setString(2, year);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                double total = rs.getDouble("total");
                dataset.setValue(category, total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
