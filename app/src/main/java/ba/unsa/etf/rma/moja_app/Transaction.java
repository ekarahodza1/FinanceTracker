package ba.unsa.etf.rma.moja_app;


import java.util.Date;

public class Transaction {

    private Date date;
    private String title;
    private double amount;
    private Type type;
    private String itemDescription;
    private int transactionInterval;
    private Date endDate;

    public Transaction(Date date, String title, double amount, Type type, String itemDescription, int transactionInterval, Date endDate) {
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.itemDescription = itemDescription;
        this.transactionInterval = transactionInterval;
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        if (type == Type.INDIVIDUALINCOME || type == Type.REGULARINCOME) this.itemDescription = null;
        else this.itemDescription = itemDescription;
    }

    public int getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(int transactionInterval) {
        if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) this.transactionInterval = transactionInterval;
        else return;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (type == Type.REGULARINCOME || type == Type.REGULARPAYMENT) this.endDate = endDate;
        else this.endDate = null;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
