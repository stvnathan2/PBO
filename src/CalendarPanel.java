import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class CalendarPanel extends JPanel {
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private JPanel daysPanel;
    private TransactionDetailsTablePanel detailsPanel;
    private String username;
    private Map<Integer, List<Transaction>> transactionsMap;
    private final Color backgroundColor = Color.decode("#FFFFFFF");
    private final Font dayButtonFont = new Font("Metropolis", Font.BOLD, 18);
    private final Font controlPanelFont = new Font("Metropolis", Font.PLAIN, 16);
    private int selectedMonth;
    private int selectedYear;
    private int currentMonth;
    private int currentYear;

    public CalendarPanel(String username) {
        this.username = username;
        this.transactionsMap = new HashMap<>();
        this.selectedMonth = YearMonth.now().getMonthValue();
        this.selectedYear = Year.now().getValue();

        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBackground(backgroundColor);

        JLabel monthLabel = new JLabel("Bulan:");
        monthLabel.setFont(controlPanelFont);
        JLabel yearLabel = new JLabel("Tahun:");
        yearLabel.setFont(controlPanelFont);
        monthComboBox = new JComboBox<>(getMonths());
        monthComboBox.setFont(controlPanelFont);
        yearComboBox = new JComboBox<>(getYears());
        yearComboBox.setFont(controlPanelFont);
        JButton loadButton = new JButton("Proses");
        loadButton.setFont(controlPanelFont);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMonth = monthComboBox.getSelectedIndex() + 1;
                selectedYear = Integer.parseInt((String) yearComboBox.getSelectedItem());
                loadTransactions();
                generateCalendar();
            }
        });

        controlPanel.add(monthLabel);
        controlPanel.add(monthComboBox);
        controlPanel.add(yearLabel);
        controlPanel.add(yearComboBox);
        controlPanel.add(loadButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        daysPanel = new JPanel();
        daysPanel.setLayout(new BorderLayout());
        daysPanel.setBackground(backgroundColor);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.setBackground(backgroundColor);
        String[] daysOfWeek = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(dayButtonFont);
            headerPanel.add(label);
        }
        daysPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel calendarGridPanel = new JPanel();
        calendarGridPanel.setLayout(new GridBagLayout());
        calendarGridPanel.setBackground(backgroundColor);
        daysPanel.add(calendarGridPanel, BorderLayout.CENTER);

        mainPanel.add(daysPanel, BorderLayout.CENTER);

        detailsPanel = new TransactionDetailsTablePanel(this);
        add(mainPanel, BorderLayout.CENTER);

        java.time.LocalDate now = java.time.LocalDate.now();
        currentMonth = now.getMonthValue();
        currentYear = now.getYear();
        monthComboBox.setSelectedIndex(currentMonth - 1);
        yearComboBox.setSelectedItem(String.valueOf(currentYear));
        loadTransactions();
        generateCalendar();
    }

    private void loadTransactions() {
        transactionsMap.clear();
        try (Connection conn = DatabaseUtils.getConnection()) {
            String query = "SELECT date, type, amount, category, description FROM daily_expenses WHERE username = ? AND MONTH(date) = ? AND YEAR(date) = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setInt(2, selectedMonth);
                stmt.setInt(3, selectedYear);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Date date = rs.getDate("date");
                        int day = date.toLocalDate().getDayOfMonth();
                        Transaction transaction = new Transaction(
                                date,
                                rs.getString("type"),
                                rs.getDouble("amount"),
                                rs.getString("category"),
                                rs.getString("description")
                        );
                        transactionsMap.computeIfAbsent(day, k -> new ArrayList<>()).add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateCalendar() {
        JPanel calendarGridPanel = (JPanel) daysPanel.getComponent(1);
        calendarGridPanel.removeAll();

        int daysInMonth = YearMonth.of(selectedYear, selectedMonth).lengthOfMonth();
        java.time.LocalDate firstOfMonth = java.time.LocalDate.of(selectedYear, selectedMonth, 1);
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2); 
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        for (int i = 0; i < firstDayOfWeek; i++) {
            gbc.gridx = i;
            gbc.gridy = 0;
            calendarGridPanel.add(new JLabel(""), gbc);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            final int currentDay = day;
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(dayButtonFont);
            final Color[] originalColor = new Color[1];
            dayButton.setOpaque(true);
            dayButton.setBorderPainted(false);

            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showTransactions(currentDay);
                }
            });

            dayButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    originalColor[0] = dayButton.getBackground();
                    dayButton.setBackground(Color.decode("#7DBFF8"));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    dayButton.setBackground(originalColor[0]);
                }
            });

            if (transactionsMap.containsKey(day)) {
                boolean hasIncome = false;
                boolean hasExpense = false;
                for (Transaction transaction : transactionsMap.get(day)) {
                    if (transaction.getType().equalsIgnoreCase("pemasukan")) {
                        hasIncome = true;
                    } else if (transaction.getType().equalsIgnoreCase("pengeluaran")) {
                        hasExpense = true;
                    }
                }
                if (hasIncome) {
                    dayButton.setBackground(Color.decode("#65E437"));
                }
                if (hasExpense) {
                    dayButton.setBackground(Color.decode("#E43737"));
                }
            } else {
                dayButton.setBackground(Color.WHITE);
            }

            gbc.gridx = (firstDayOfWeek + day - 1) % 7;
            gbc.gridy = (firstDayOfWeek + day - 1) / 7;
            calendarGridPanel.add(dayButton, gbc);
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void showTransactions(int day) {
        List<Transaction> transactions = transactionsMap.getOrDefault(day, new ArrayList<>());
        detailsPanel.setTransactions(transactions);
        removeAll();
        add(detailsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showCalendar() {
        removeAll();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBackground(backgroundColor);

        JLabel monthLabel = new JLabel("Bulan:");
        monthLabel.setFont(controlPanelFont);
        JLabel yearLabel = new JLabel("Tahun:");
        yearLabel.setFont(controlPanelFont);
        monthComboBox = new JComboBox<>(getMonths());
        monthComboBox.setFont(controlPanelFont);
        monthComboBox.setSelectedIndex(selectedMonth - 1);
        yearComboBox = new JComboBox<>(getYears());
        yearComboBox.setFont(controlPanelFont);
        yearComboBox.setSelectedItem(String.valueOf(selectedYear));
        JButton loadButton = new JButton("Proses");
        loadButton.setFont(controlPanelFont);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedMonth = monthComboBox.getSelectedIndex() + 1;
                selectedYear = Integer.parseInt((String) yearComboBox.getSelectedItem());
                loadTransactions();
                generateCalendar();
            }
        });

        controlPanel.add(monthLabel);
        controlPanel.add(monthComboBox);
        controlPanel.add(yearLabel);
        controlPanel.add(yearComboBox);
        controlPanel.add(loadButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        daysPanel = new JPanel();
        daysPanel.setLayout(new BorderLayout());
        daysPanel.setBackground(backgroundColor);

        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.setBackground(backgroundColor);
        String[] daysOfWeek = {"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(dayButtonFont);
            headerPanel.add(label);
        }
        daysPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel calendarGridPanel = new JPanel();
        calendarGridPanel.setLayout(new GridBagLayout());
        calendarGridPanel.setBackground(backgroundColor);
        daysPanel.add(calendarGridPanel, BorderLayout.CENTER);

        mainPanel.add(daysPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        loadTransactions();
        generateCalendar();
        revalidate();
        repaint();
    }

    private String[] getMonths() {
        return new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    }

    private String[] getYears() {
        int currentYear = Year.now().getValue();
        int startYear = 2020;
        String[] years = new String[currentYear - startYear + 1];
        for (int i = startYear; i <= currentYear; i++) {
            years[i - startYear] = String.valueOf(i);
        }
        return years;
    }

    public static class Transaction {
        private Date date;
        private String type;
        private double amount;
        private String category;
        private String description;

        public Transaction(Date date, String type, double amount, String category, String description) {
            this.date = date;
            this.type = type;
            this.amount = amount;
            this.category = category;
            this.description = description;
        }

        public Date getDate() {
            return date;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }
    }
}
