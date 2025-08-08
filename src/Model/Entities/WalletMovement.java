package Model.Entities;

import java.time.LocalDate;
import java.util.Date;

public class WalletMovement {
    private Date date;
    private String description;
    private double amount;
    private LocalDate menuDate;
    private String shift;
    private String concept;


    public WalletMovement(Date date, String description, double amount, LocalDate menuDate, String shift, String concept) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.menuDate = menuDate;
        this.shift = shift;
        this.concept = concept;
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

    public LocalDate getMenuDate() {
        return menuDate;
    }
    public String getShift() {
        return shift;
    }
    public String getConcept() {
        return concept;
    }
}
