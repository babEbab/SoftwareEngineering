package BankingSystem;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;


public class BankingSystem {

    Scanner sc = new Scanner(System.in);
    public LinkedList<Account> ll = new LinkedList();

    void processCommand() {
        while (true) {
            System.out.println("1. 계좌 개설");
            System.out.println("2. 계좌 입금");
            System.out.println("3. 계좌 출금");
            System.out.println("4. 계좌 잔액조회");
            System.out.println("5. 계좌 이체");
            System.out.println("0. 프로그램 종료");
            System.out.println("원하는 기능을 선택하시오: ");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    handleCreate();
                case 2:
                    handleDeposit();
                case 3:
                    handleWithdraw();
                case 4:
                    handleBalanceInquiry();
                case 5:
                    handleAccountTransfer();
                case 0:
                    break;
            }
        }
    }

    private void handleAccountTransfer() {
        int orderAccount = checkHaveAccountNum();
        if (checkPassword(orderAccount)) {
            System.out.print("상대방의 ");
            int otherAccount = checkHaveAccountNum();
            if (otherAccount != -1) {
                System.out.println("해당하는 계좌가 없습니다.");
                return;
            }
            else {
                System.out.println();
            }

        }
        else {
            System.out.println("틀린 비밀번호입니다.");
            return;
        }

    }

    private void handleBalanceInquiry() {
        int orderAccount = checkHaveAccountNum();
        if (orderAccount != -1) {
            System.out.println(ll.get(orderAccount).toString());
        }
        else {
            System.out.println("해당하는 계좌가 없습니다.");
            return;
        }
    }

    private void handleWithdraw() {
        int orderAccount = checkHaveAccountNum();
        if (orderAccount != -1) {
            System.out.println("얼마를 출금하시겠습니까?");
            int money = sc.nextInt();
            if (ll.get(orderAccount).changeBalance(money)) {
                System.out.println("금액이 정상적으로 출금되었습니다.");
                System.out.println(ll.get(orderAccount).toString());
            }
            else {
                System.out.println("계좌의 잔액이 부족합니다.");
                return;
            }
        }
        else {
            System.out.println("해당하는 계좌가 없습니다.");
            return;
        }
    }

    private void handleDeposit() {
        int orderAccount = checkHaveAccountNum();
        if (orderAccount != -1) {
            System.out.println("얼마를 입금하시겠습니까?");
            int money = sc.nextInt();
            ll.get(orderAccount).changeBalance(money);
            System.out.println("금액이 정상적으로 입금되었습니다.");
            System.out.println(ll.get(orderAccount).toString());
        }
        else {
            System.out.println("해당하는 계좌가 없습니다.");
            return;
        }
    }

    private void handleCreate() {
        System.out.println("고객의 이름을 입력하시오: ");
        String name = sc.next();
        System.out.println("고객의 비밀번호를 설정하세요: ");
        String password = sc.next();
        String accountNum;
        while (true) {
            int randNum = (int) (Math.random() * Integer.MAX_VALUE) % 1000000;
            accountNum = Integer.toString(randNum);
            if (checkAccountNumAlready(accountNum)) {
                break;
            }
        }
        Account account = new Account(name, password, accountNum);
        ll.add(account);
        System.out.println("계좌 개설이 정상적으로 완료되었습니다.");
        System.out.println(account.toString());
    }

    private int checkHaveAccountNum() {
        System.out.println("계좌번호를 입력하시오: ");
        String accountNum = sc.next();

        ListIterator it = ll.listIterator();
        while (it.hasNext()) {
            Account account = (Account) it.next();
            if (account.getAccountNum().equals(accountNum)) {
                return it.previousIndex();
            }
        }
        return -1;
    }

    private boolean checkPassword(int orderCount) {
        String password = sc.next();
        if (password.equals(ll.get(orderCount).getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkAccountNumAlready(String accountNum){
        ListIterator it = ll.listIterator();
        while (it.hasNext()) {
            Account account = (Account) it.next();
            if (account.getAccountNum().equals(accountNum)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BankingSystem app = new BankingSystem();
        app.processCommand();
    }
}