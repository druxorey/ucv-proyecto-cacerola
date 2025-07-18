package Model.Entities;

import java.util.Date;

public class WalletMovement {
    private Date date;
    private String description;
    private double amount;

    public WalletMovement(Date date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
