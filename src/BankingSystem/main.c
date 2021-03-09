#pragma warning (disable:4996)
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

/*
* 과제 01의 뱅킹 시스템을 절차지향 버전으로 개발한다.
*/

#define ACCOUNT_NAME_LENGTH 20
#define ACCOUNT_PASSWORD_LENGTH 5

typedef struct account {
	int  accountNum; // 계좌번호
	char name[ACCOUNT_NAME_LENGTH]; // 고객 이름
	char password[ACCOUNT_PASSWORD_LENGTH]; // 비밀번호
	int balance; // 잔고
} Account;

void processCommand();
void handleCreate(); // 계좌 개설
void handleDeposit(); // 입금
void handleWithdraw(); // 출금
void handleBalanceInquiry(); // 잔액 확인
void handleAccountTransfer(); // 계좌이체
void toString(Account); // 계좌 정보 출력
int changeBalance(Account*, int); // 현재 계좌의 잔액 변경 함수

Account allAccount[100]; // 계좌 배열
int nowAccountCount = 0; // 현재 계좌의 개수

int main() {

	processCommand();

	return 0;
}

void processCommand() {
	while (1) {
		printf("\n1. 계좌 개설\n");
		printf("2. 계좌 입금\n");
		printf("3. 계좌 출금\n");
		printf("4. 계좌 잔액조회\n");
		printf("5. 계좌 이체\n");
		printf("0. 프로그램 종료\n");
		printf("원하는 기능을 선택하시오: ");
		int command;
		scanf("%d", &command);
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

void handleAccountTransfer() { // 계좌이체
	int orderAccount = checkHaveAccountNum();
	if (checkPassword(orderAccount)) {
		printf("상대방의 ");
		int otherAccount = checkHaveAccountNum();
		if (otherAccount != -1) {
			printf("얼마를 이체하시겠습니까? ");
			int money;
			scanf("%d", &money);
			if (changeBalance(&allAccount[orderAccount], money * -1)) {
				changeBalance(&allAccount[otherAccount], money);
				printf("금액이 정상적으로 이체되었습니다.\n");
				toString(allAccount[orderAccount]);
			}
			else {
				printf("계좌의 잔액이 부족합니다.\n");
				return;
			}
		}
		else {
			printf("해당하는 계좌가 없습니다.\n");
			return;
		}
	}
	else {
		printf("틀린 비밀번호입니다.\n");
		return;
	}

}

void handleBalanceInquiry() { // 잔액 확인
	int orderAccount = checkHaveAccountNum();
	if (orderAccount != -1) {
		if (checkPassword(orderAccount)) {
			toString(allAccount[orderAccount]);
		}
		else {
			printf("틀린 비밀번호입니다.\n");
			return;
		}
	}
	else {
		printf("해당하는 계좌가 없습니다.\n");
		return;
	}
}

void handleWithdraw() { // 출금
	int orderAccount = checkHaveAccountNum();
	if (orderAccount != -1) {
		if (checkPassword(orderAccount)) {
			printf("얼마를 출금하시겠습니까? ");
			int money;
			scanf("%d", &money);
			if (changeBalance(&allAccount[orderAccount], money * -1)) {
				printf("금액이 정상적으로 출금되었습니다.\n");
				toString(allAccount[orderAccount]);
			}
			else {
				printf("계좌의 잔액이 부족합니다.\n");
				return;
			}
		}
		else {
			printf("틀린 비밀번호입니다.\n");
			return;
		}
	}
	else {
		printf("해당하는 계좌가 없습니다.\n");
		return;
	}
}

void handleDeposit() { // 입금
	int orderAccount = checkHaveAccountNum();
	if (orderAccount != -1) {
		printf("얼마를 입금하시겠습니까? ");
		int money;
		scanf("%d", &money);
		changeBalance(&allAccount[orderAccount], money);
		printf("금액이 정상적으로 입금되었습니다.\n");
		toString(allAccount[orderAccount]);
	}
	else {
		printf("해당하는 계좌가 없습니다.\n");
		return;
	}
}

void handleCreate() { // 계좌 개설
	printf("고객의 이름을 입력하시오: ");
	char name[ACCOUNT_NAME_LENGTH];
	scanf("%s", name);
	printf("고객의 비밀번호를 설정하세요: ");
	char password[ACCOUNT_PASSWORD_LENGTH];
	scanf("%s", password);
	int accountNum;// 계좌번호
	while (1) {
		srand(time(NULL)); // 매번 다른 시드값 생성
		accountNum = rand() % 1000000;
		if (checkAccountNumAlready(accountNum)) {
			break;
		}
	}

	allAccount[nowAccountCount].accountNum = accountNum;
	strcpy(allAccount[nowAccountCount].name, name);
	strcpy(allAccount[nowAccountCount].password, password);
	allAccount[nowAccountCount].balance = 0;

	nowAccountCount++;
	printf("계좌 개설이 정상적으로 완료되었습니다.\n");
	toString(allAccount[nowAccountCount - 1]);
}

int checkHaveAccountNum() { // 등록된 계좌인지 확인
	printf("계좌번호를 입력하시오: ");
	int accountNum;
	scanf("%d", &accountNum);

	for (int i = 0; i < nowAccountCount; i++) {
		if (allAccount[i].accountNum == accountNum) {
			return i;
		}
	}
	return -1;
}

int checkPassword(int orderCount) { // 비밀번호 확인
	printf("비밀번호를 입력하시오: ");
	char password[5];
	scanf("%s", password);
	if (strcmp(password, allAccount[orderCount].password)) {
		return 0;
	}
	else {
		return 1;
	}
}

int checkAccountNumAlready(int accountNum) {
	// 계좌번호 랜덤 생성 시 이미 존재하는 계좌번호와의 중복을 피하기 위해서
	// 랜덤 생성된 번호가 이미 등록된 계좌번호인지 확인
	for (int i = 0; i < nowAccountCount; i++) {
		if (allAccount[i].accountNum == accountNum) {
			return 0;
		}
	}
	return 1;
}

int changeBalance(Account* account, int money) {
	if ((*account).balance + money >= 0) { // 출금 시 계좌 잔액이 0원 미만이 되는 것을 방지한다
		(*account).balance += money;
		return 1;
	}
	else {
		return 0;
	}
}

void toString(Account account) { // 계좌 정보 출력용 메서드
	printf("\n계좌번호: %d", account.accountNum);
	printf("\n이름: %s", account.name);
	printf("\n잔액: %d\n", account.balance);

}