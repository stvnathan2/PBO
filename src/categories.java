import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.event.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Categories extends JPanel {
    private JTable table;
    private JComboBox<String> categoryComboBox1;
    private JComboBox<String> categoryComboBox2;
    private JSpinner dateSpinner;

    public Categories() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Menambahkan padding ke panel

        // Panel bagian atas untuk dropdown dan tombol
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Layout FlowLayout dengan alignment tengah
        topPanel.setBackground(Color.WHITE); // Mengatur warna latar belakang panel atas

        // Panel dropdown pertama
        JPanel categoryPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Layout FlowLayout dengan alignment
                                                                               // tengah
        JLabel categoryLabel1 = new JLabel("Masukan Tipe"); // Label untuk dropdown kategori 1
        categoryPanel1.add(categoryLabel1);
        categoryComboBox1 = new JComboBox<>();
        categoryComboBox1.addItem("Keseluruhan");
        categoryComboBox1.addItem("pemasukan");
        categoryComboBox1.addItem("pengeluaran");
        categoryComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeSelectQuery();
            }
        });
        categoryPanel1.add(categoryComboBox1);
        topPanel.add(categoryPanel1);

        // Panel dropdown kedua
        JPanel categoryPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Layout FlowLayout dengan alignment
                                                                               // tengah
        JLabel categoryLabel2 = new JLabel("Select Category 2:"); // Label untuk dropdown kategori 2
        categoryPanel2.add(categoryLabel2);
        categoryComboBox2 = new JComboBox<>();
        categoryComboBox2.addItem("Keseluruhan");
        categoryComboBox2.addItem("gaji");
        categoryComboBox2.addItem("hiburan");
        categoryComboBox2.addItem("transportasi");
        categoryComboBox2.addItem("makanan");
        categoryComboBox2.addItem("Kesehatan");
        categoryComboBox2.addItem("lainnya");
        categoryComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeSelectQuery();
            }
        });
        categoryPanel2.add(categoryComboBox2);
        topPanel.add(categoryPanel2);

        // Panel untuk spinner tanggal
        // Tombol untuk menjalankan query

        add(topPanel, BorderLayout.NORTH);

        // JTable untuk menampilkan hasil query
        table = new JTable();
        table.setBackground(Color.WHITE); // Mengatur warna latar belakang tabel

        // Mengatur style header tabel
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLUE); // Mengatur warna latar belakang header
        header.setForeground(Color.WHITE); // Mengatur warna teks header
        header.setFont(new Font("SansSerif", Font.BOLD, 14)); // Mengatur font header
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Mengatur border header

        // Mengatur style sel di dalam tabel
        table.setRowHeight(30); // Menentukan tinggi baris
        table.setForeground(Color.BLACK); // Mengatur warna teks
        table.setSelectionBackground(Color.LIGHT_GRAY); // Mengatur warna latar belakang saat dipilih
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Mengatur border tabel

        // Mengatur alignment kolom
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // JScrollPane untuk JTable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Mengatur border JScrollPane

        add(scrollPane, BorderLayout.CENTER);

        // Query semua data saat panel dibuat
        executeSelectQuery();
    }

    private void executeSelectQuery() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtils.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            statement = connection.createStatement();

            // Mendapatkan nilai dari ketiga dropdown
            String category1 = (String) categoryComboBox1.getSelectedItem();
            String category2 = (String) categoryComboBox2.getSelectedItem();


            // Membangun query berdasarkan nilai dropdown
            String sql = "SELECT username, type, category, amount, date, payment_method, account, description FROM daily_expenses WHERE ";

            if ("Keseluruhan".equals(category1) && "Keseluruhan".equals(category2)) {
                // No filtering needed, select all data
                sql = "SELECT username, type, category, amount, date, payment_method, account, description FROM daily_expenses";
            } else {
                boolean hasConditions = false; // Flag to track if any filter conditions are added

                // Add filter conditions based on selected categories
                if (!"Keseluruhan".equals(category1)) {
                    sql += "type='" + category1 + "'";
                    hasConditions = true;
                }
                if (!"Keseluruhan".equals(category2)) {
                    if (hasConditions) {
                        sql += " AND ";
                    }
                    sql += "category='" + category2 + "'";
                    hasConditions = true;
                }
                
            }

            resultSet = statement.executeQuery(sql);

            // Mengambil metadata untuk mendapatkan nama kolom
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Menyiapkan vektor untuk kolom dan data
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(resultSet.getObject(i));
                }
                data.add(vector);
            }

            // Mengatur model untuk JTable
            table.setModel(new DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error executing query: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Categories");
            frame.getContentPane().add(new Categories());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}


