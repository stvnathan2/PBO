import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Categories extends JPanel {
    private JTable table;
    private JComboBox<String> categoryComboBox1;
    private JComboBox<String> categoryComboBox2;
    private String username;

    public Categories(String username) {
        this.username = username;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); 

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        topPanel.setBackground(Color.WHITE); 

        JLabel categoryLabel1 = new JLabel("Tipe: "); 
        categoryComboBox1 = new JComboBox<>();
        categoryComboBox1.addItem("Keseluruhan");
        categoryComboBox1.addItem("Pemasukan");
        categoryComboBox1.addItem("Pengeluaran");
        categoryComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeSelectQuery();
            }
        });
        topPanel.add(categoryLabel1);
        topPanel.add(categoryComboBox1);

        JLabel categoryLabel2 = new JLabel("Kategori: "); 
        categoryComboBox2 = new JComboBox<>();
        categoryComboBox2.addItem("Keseluruhan");
        categoryComboBox2.addItem("Gaji");
        categoryComboBox2.addItem("Hiburan");
        categoryComboBox2.addItem("Transportasi");
        categoryComboBox2.addItem("Makan");
        categoryComboBox2.addItem("Kesehatan");
        categoryComboBox2.addItem("Belanja");
        categoryComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeSelectQuery();
            }
        });
        topPanel.add(categoryLabel2);
        topPanel.add(categoryComboBox2);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);

        table = new JTable();
        table.setBackground(Color.WHITE); 

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLUE); 
        header.setForeground(Color.WHITE); 
        header.setFont(new Font("SansSerif", Font.BOLD, 14)); 
        header.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 

        table.setRowHeight(30); 
        table.setForeground(Color.BLACK); 
        table.setSelectionBackground(Color.LIGHT_GRAY); 
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column != -1 && row != -1) {
                    TableModel model = (TableModel) e.getSource();
                    String columnName = model.getColumnName(column);
                    Object data = model.getValueAt(row, column);
                    updateDataToDatabase(row, columnName, data);
                }
            }
        });

        executeSelectQuery();
    }

    private void executeSelectQuery() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtils.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Gagal terhubung ke database.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            statement = connection.createStatement();

            String category1 = (String) categoryComboBox1.getSelectedItem();
            String category2 = (String) categoryComboBox2.getSelectedItem();

            String sql = "SELECT type, category, amount, date, payment_method, account, description FROM daily_expenses WHERE ";

            if ("Keseluruhan".equals(category1) && "Keseluruhan".equals(category2)) {
                sql = "SELECT type, category, amount, date, payment_method, account, description FROM daily_expenses WHERE username='" + username + "'";
            } else {
                boolean hasConditions = false; 

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
                sql += " AND username='" + username + "'";
            }

            resultSet = statement.executeQuery(sql);

            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

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

    private void updateDataToDatabase(int row, String columnName, Object data) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseUtils.getConnection();
            if (connection == null) {
                JOptionPane.showMessageDialog(this, "Gagal terhubung ke database.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            statement = connection.createStatement();

            String type = (String) table.getValueAt(row, 0);
            String category = (String) table.getValueAt(row, 1);
            double amount = (double) table.getValueAt(row, 2);
            String date = (String) table.getValueAt(row, 3);
            String paymentMethod = (String) table.getValueAt(row, 4);
            String account = (String) table.getValueAt(row, 5);
            String description = (String) table.getValueAt(row, 6);

            String sql = "UPDATE daily_expenses SET " +
                         "type='" + type + "', " +
                         "category='" + category + "', " +
                         "amount=" + amount + ", " +
                         "date='" + date + "', " +
                         "payment_method='" + paymentMethod + "', " +
                         "account='" + account + "', " +
                         "description='" + description + "' " +
                         "WHERE username='" + username + "'";

            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error closing resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
