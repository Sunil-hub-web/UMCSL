package in.co.modelclass;

public class Report_ModelClass {

    String date,time,accountNo,collectAmount,balanceAmount;

    public Report_ModelClass(String date, String time, String accountNo, String collectAmount, String balanceAmount) {
        this.date = date;
        this.time = time;
        this.accountNo = accountNo;
        this.collectAmount = collectAmount;
        this.balanceAmount = balanceAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
