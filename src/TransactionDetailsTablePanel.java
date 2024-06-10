import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransactionDetailsTablePanel extends JPanel {
    private JTable transactionTable;
    private JButton backButton;
    private CalendarPanel calendarPanel;

    public TransactionDetailsTablePanel(CalendarPanel calendarPanel) {
        this.calendarPanel = calendarPanel;
        setLayout(new BorderLayout());

        transactionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Kembali");
        backButton.setFont(new Font("Metropolis", Font.PLAIN, 16));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendarPanel.showCalendar();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setTransactions(List<CalendarPanel.Transaction> transactions) {
        String[] columnNames = {"Tanggal", "Tipe", "Jumlah", "Kategori", "Deskripsi"};
        Object[][] data = new Object[transactions.size()][5];

        for (int i = 0; i < transactions.size(); i++) {
            CalendarPanel.Transaction transaction = transactions.get(i);
            data[i][0] = transaction.getDate();
            data[i][1] = transaction.getType();
            data[i][2] = transaction.getAmount();
            data[i][3] = transaction.getCategory();
            data[i][4] = transaction.getDescription();
        }

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
}
