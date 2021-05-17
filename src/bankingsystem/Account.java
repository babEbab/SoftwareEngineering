package bankingsystem;

public class Account {

    private String accountNum; // 계좌번호
    private String name; // 고객 이름
    private String password; // 비밀번호
    private int balance; // 잔고

    public String getAccountNum() {
        return accountNum;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    Account(String name, String password, String accountNum) { // 생성자에서 계좌의 정보를 등록한다.
        this.name = name;
        this.password = password;
        this.balance = 0;
        this.accountNum = accountNum;
    }

    public boolean changeBalance(int money) {
        if (this.balance + money >= 0) { // 출금 시 계좌 잔액이 0원 미만이 되는 것을 방지한다
            this.balance += money;
            return true;
        }
        else {
            return false;
        }
    }


    public String toString() { // 계좌 정보 출력용 메서드
        String result = "계좌번호: " + accountNum +
                        "\n이름: " + name +
                        "\n잔액: " + balance;
        return result;
    }
}