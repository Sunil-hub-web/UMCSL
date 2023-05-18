package in.co.modelclass;

public class Report_ModelClass {

    String date,time,accountNo,collectAmount,balanceAmount,VocherNumber,UName;

    public Report_ModelClass(String date, String time, String accountNo, String collectAmount, String balanceAmount,
                             String VocherNumber, String UName) {
        this.date = date;
        this.time = time;
        this.accountNo = accountNo;
        this.collectAmount = collectAmount;
        this.balanceAmount = balanceAmount;
        this.VocherNumber = VocherNumber;
        this.UName = UName;
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

    public String getVocherNumber() {
        return VocherNumber;
    }

    public void setVocherNumber(String vocherNumber) {
        VocherNumber = vocherNumber;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    @Override
    public String toString() {

        return  "Date   " + date + "\n" +
                "Name   " + UName + "\n" +
                "Ac/no. " + accountNo + "\n" +
                "Collect Amount  " + collectAmount + "\n" +
                "Balance Amount  " + balanceAmount + "\n"+
                " ================================ \n";
    }
}
