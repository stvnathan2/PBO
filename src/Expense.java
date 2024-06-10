public class Expense {
    private int id;
    private String username;
    private double amount;
    private String type;
    private String category;
    private java.sql.Date date;
    private String description;
    private String paymentMethod;
    private String account;

    public Expense(String username, double amount, String type, String category, java.sql.Date date, String description, String paymentMethod, String account) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}