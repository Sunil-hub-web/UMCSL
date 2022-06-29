package in.co.modelclass;

public class AssignCustomer_ModelClass {

    String date,accountNo,name,mobileNo,statues;

    public AssignCustomer_ModelClass(String date, String accountNo, String name, String mobileNo, String statues) {
        this.date = date;
        this.accountNo = accountNo;
        this.name = name;
        this.mobileNo = mobileNo;
        this.statues = statues;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }
}
