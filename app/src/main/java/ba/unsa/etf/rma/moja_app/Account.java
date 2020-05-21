package ba.unsa.etf.rma.moja_app;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private double budget;
    private double totalLimit;
    private double monthLimit;
    private int id;

    public Account(double budget, double totalLimit, double monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public Account(int id, double budget, double totalLimit, double monthLimit) {
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
        this.id = id;
    }

    protected Account(Parcel in) {
        id = in.readInt();
        budget = in.readDouble();
        totalLimit = in.readDouble();
        monthLimit = in.readDouble();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public Account() {

    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public double getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(double monthLimit) {
        this.monthLimit = monthLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(budget);
        dest.writeDouble(monthLimit);
        dest.writeDouble(totalLimit);

    }
}
