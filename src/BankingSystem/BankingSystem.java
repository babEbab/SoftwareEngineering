package BankingSystem;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class BankingSystem {

    Scanner sc = new Scanner(System.in);
    private LinkedList<Account> ll = new LinkedList(); // 수많은 계좌를 연결 리스트로 관리한다.

    void processCommand() {
        while (true) {
            System.out.println("\n1. 계좌 개설");
            System.out.println("2. 계좌 입금");
            System.out.println("3. 계좌 출금");
            System.out.println("4. 계좌 잔액조회");
            System.out.println("5. 계좌 이체");
            System.out.println("0. 프로그램 종료");
            System.out.print("원하는 기능을 선택하시오: ");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    handleCreate();
                    break;
                case 2:
                    handleDeposit();
                    break;
                case 3:
                    handleWithdraw();
                    break;
                case 4:
                    handleBalanceInquiry();
                    break;
                case 5:
                    handleAccountTransfer();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void handleAccountTransfer() { // 계좌이체
        int orderAccount = checkHaveAccountNum();
        if (checkPassword(orderAccount)) {
            System.out.print("상대방의 ");
            int otherAccount = checkHaveAccountNum();
            if (otherAccount != -1) {
                System.out.print("얼마를 이체하시겠습니까? ");
                int money = sc.nextInt();
                if (ll.get(orderAccount).changeBalance(money * (-1))) {
                    System.out.println("금액이 정상적으로 이체되었습니다.");
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
        else {
            System.out.println("틀린 비밀번호입니다.");
            return;
        }

    }

    private void handleBalanceInquiry() { // 잔액 확인
        int orderAccount = checkHaveAccountNum();
        if (orderAccount != -1) {
            if (checkPassword(orderAccount)) {
                System.out.println(ll.get(orderAccount).toString());
            }
            else {
                System.out.println("틀린 비밀번호입니다.");
                return;
            }
        }
        else {
            System.out.println("해당하는 계좌가 없습니다.");
            return;
        }
    }

    private void handleWithdraw() { // 출금
        int orderAccount = checkHaveAccountNum();
        if (orderAccount != -1) {
            if (checkPassword(orderAccount)) {
                System.out.print("얼마를 출금하시겠습니까? ");
                int money = sc.nextInt();
                if (ll.get(orderAccount).changeBalance(money * (-1))) {
                    System.out.println("금액이 정상적으로 출금되었습니다.");
                    System.out.println(ll.get(orderAccount).toString());
                }
                else {
                    System.out.println("계좌의 잔액이 부족합니다.");
                    return;
                }
            }
            else {
                System.out.println("틀린 비밀번호입니다.");
                return;
            }
        }
        else {
            System.out.println("해당하는 계좌가 없습니다.");
            return;
        }
    }

    private void handleDeposit() { // 입금
        int orderAccount = checkHaveAccountNum();
        if (orderAccount != -1) {
            System.out.print("얼마를 입금하시겠습니까? ");
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

    private void handleCreate() { // 계좌 개설
        System.out.print("고객의 이름을 입력하시오: ");
        String name = sc.next();
        System.out.print("고객의 비밀번호를 설정하세요: ");
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

    private int checkHaveAccountNum() { // 등록된 계좌인지 확인
        System.out.print("계좌번호를 입력하시오: ");
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

    private boolean checkPassword(int orderCount) { // 비밀번호 확인
        System.out.print("비밀번호를 입력하시오: ");
        String password = sc.next();
        if (password.equals(ll.get(orderCount).getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkAccountNumAlready(String accountNum) {
        // 계좌번호 랜덤 생성 시 이미 존재하는 계좌번호와의 중복을 피하기 위해서
        // 랜덤 생성된 번호가 이미 등록된 계좌번호인지 확인
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