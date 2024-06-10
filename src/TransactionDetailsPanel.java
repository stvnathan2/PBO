import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class TransactionDetailsPanel extends JPanel {
    private JTextArea detailsTextArea;

    public TransactionDetailsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color to white
        setBorder(new EmptyBorder(0, 10, 0, 10)); // Add left margin of 10 pixels
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setFont(new Font("Metropolis Bold", Font.PLAIN, 22));

        // Set border of JScrollPane to null to remove the border
        JScrollPane scrollPane = new JScrollPane(detailsTextArea);
        scrollPane.setBorder(null); // Remove the border

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setTransactions(List<CalendarPanel.Transaction> transactions) {
        StringBuilder details = new StringBuilder();
        if (transactions.isEmpty()) {
            details.append("Tidak ada transaksi untuk tanggal ini.");
        } else {
            for (CalendarPanel.Transaction transaction : transactions) {
                details.append(String.format("DETAIL TRANSAKSI \n\nTanggal: %s\nJumlah: %.2f\nTipe: %s\nKategori: %s\nDeskripsi: %s\n\n",
                        transaction.getDate().toString(), transaction.getAmount(), transaction.getType(), transaction.getCategory(), transaction.getDescription()));
            }
        }
        detailsTextArea.setText(details.toString());
    }
}
