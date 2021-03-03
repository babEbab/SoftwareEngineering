package BankingSystem;

public class Account {

    private String accountNum;
    private String name;
    private String password;
    private int balance;

    public String getAccountNum() {
        return accountNum;
    }

    public int getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    Account(String name, String password, String accountNum) {

        this.name = name;
        this.password = password;
        this.balance = 0;
        this.accountNum = accountNum;
    }

    public boolean changeBalance(int money) {

        if (this.balance + money >= 0) {
            this.balance += money;
            return true;
        }
        else {
            return false;
        }
    }


    public String toString() {

        String result = "계좌번호: " + accountNum +
                        "\n이름: " + name +
                        "\n잔액: " + balance;
        return result;
    }
}