#pragma warning (disable:4996)
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

/*
* ���� 01�� ��ŷ �ý����� �������� �������� �����Ѵ�.
*/

#define ACCOUNT_NAME_LENGTH 20
#define ACCOUNT_PASSWORD_LENGTH 5

typedef struct account {
	int  accountNum; // ���¹�ȣ
	char name[ACCOUNT_NAME_LENGTH]; // �� �̸�
	char password[ACCOUNT_PASSWORD_LENGTH]; // ��й�ȣ
	int balance; // �ܰ�
} Account;

void processCommand();
void handleCreate(); // ���� ����
void handleDeposit(); // �Ա�
void handleWithdraw(); // ���
void handleBalanceInquiry(); // �ܾ� Ȯ��
void handleAccountTransfer(); // ������ü
void printAccountInfo(Account); // ���� ���� ���
int changeBalance(Account*, int); // ���� ������ �ܾ� ���� �Լ�
int checkHaveAccountNum();
int checkPassword(int);
int changeBalance(Account*, int);
void printAccountInfo(Account);

Account allAccount[100]; // ���� �迭
int curAccountCount = 0; // ���� ������ ����

int main() {

	processCommand();

	return 0;
}

void processCommand() {
	while (1) {
		printf("\n1. ���� ����\n");
		printf("2. ���� �Ա�\n");
		printf("3. ���� ���\n");
		printf("4. ���� �ܾ���ȸ\n");
		printf("5. ���� ��ü\n");
		printf("0. ���α׷� ����\n");
		printf("���ϴ� ����� �����Ͻÿ�: ");
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

void handleAccountTransfer() { // ������ü
	int orderAccount = checkHaveAccountNum();
	if (checkPassword(orderAccount)) {
		printf("������ ");
		int otherAccount = checkHaveAccountNum();
		if (otherAccount != -1) {
			printf("�󸶸� ��ü�Ͻðڽ��ϱ�? ");
			int money;
			scanf("%d", &money);
			if (changeBalance(&allAccount[orderAccount], money * -1)) {
				changeBalance(&allAccount[otherAccount], money);
				printf("�ݾ��� ���������� ��ü�Ǿ����ϴ�.\n");
				printAccountInfo(allAccount[orderAccount]);
			}
			else {
				printf("������ �ܾ��� �����մϴ�.\n");
				return;
			}
		}
		else {
			printf("�ش��ϴ� ���°� �����ϴ�.\n");
			return;
		}
	}
	else {
		printf("Ʋ�� ��й�ȣ�Դϴ�.\n");
		return;
	}

}

void handleBalanceInquiry() { // �ܾ� Ȯ��
	int orderAccount = checkHaveAccountNum();
	if (orderAccount != -1) {
		if (checkPassword(orderAccount)) {
			printAccountInfo(allAccount[orderAccount]);
		}
		else {
			printf("Ʋ�� ��й�ȣ�Դϴ�.\n");
			return;
		}
	}
	else {
		printf("�ش��ϴ� ���°� �����ϴ�.\n");
		return;
	}
}

void handleWithdraw() { // ���
	int orderAccount = checkHaveAccountNum();
	if (orderAccount != -1) {
		if (checkPassword(orderAccount)) {
			printf("�󸶸� ����Ͻðڽ��ϱ�? ");
			int money;
			scanf("%d", &money);
			if (changeBalance(&allAccount[orderAccount], money * -1)) {
				printf("�ݾ��� ���������� ��ݵǾ����ϴ�.\n");
				printAccountInfo(allAccount[orderAccount]);
			}
			else {
				printf("������ �ܾ��� �����մϴ�.\n");
				return;
			}
		}
		else {
			printf("Ʋ�� ��й�ȣ�Դϴ�.\n");
			return;
		}
	}
	else {
		printf("�ش��ϴ� ���°� �����ϴ�.\n");
		return;
	}
}

void handleDeposit() { // �Ա�
	int orderAccount = checkHaveAccountNum();
	if (orderAccount != -1) {
		printf("�󸶸� �Ա��Ͻðڽ��ϱ�? ");
		int money;
		scanf("%d", &money);
		changeBalance(&allAccount[orderAccount], money);
		printf("�ݾ��� ���������� �ԱݵǾ����ϴ�.\n");
		printAccountInfo(allAccount[orderAccount]);
	}
	else {
		printf("�ش��ϴ� ���°� �����ϴ�.\n");
		return;
	}
}

void handleCreate() { // ���� ����
	const static int accountSeed = 1000000;

	printf("���� �̸��� �Է��Ͻÿ�: ");
	char name[ACCOUNT_NAME_LENGTH];
	scanf("%s", name);
	printf("���� ��й�ȣ�� �����ϼ���: ");
	char password[ACCOUNT_PASSWORD_LENGTH];
	scanf("%s", password);
	int accountNum = accountSeed + curAccountCount;// ���¹�ȣ

	allAccount[curAccountCount] = { accountNum, name, password, 0 };

	//allAccount[curAccountCount].accountNum = accountNum;
	//strcpy(allAccount[curAccountCount].name, name);
	//strcpy(allAccount[curAccountCount].password, password);
	//allAccount[curAccountCount].balance = 0;

	curAccountCount++;
	printf("���� ������ ���������� �Ϸ�Ǿ����ϴ�.\n");
	printAccountInfo(allAccount[curAccountCount - 1]);
}

int checkHaveAccountNum() { // ��ϵ� �������� Ȯ��
	printf("���¹�ȣ�� �Է��Ͻÿ�: ");
	int accountNum;
	scanf("%d", &accountNum);

	for (int i = 0; i < curAccountCount; i++) {
		if (allAccount[i].accountNum == accountNum) {
			return i;
		}
	}
	return -1;
}

int checkPassword(int orderCount) { // ��й�ȣ Ȯ��
	printf("��й�ȣ�� �Է��Ͻÿ�: ");
	char password[5];
	scanf("%s", password);
	if (strcmp(password, allAccount[orderCount].password)) {
		return 0;
	}
	else {
		return 1;
	}
}

int changeBalance(Account* account, int money) {
	if (account->balance + money >= 0) { // ��� �� ���� �ܾ��� 0�� �̸��� �Ǵ� ���� �����Ѵ�
		account->balance += money;
		return 1;
	}
	else {
		return 0;
	}
}

void printAccountInfo(Account account) { // ���� ���� ��¿� �޼���
	printf("\n���¹�ȣ: %d", account.accountNum);
	printf("\n�̸�: %s", account.name);
	printf("\n�ܾ�: %d\n", account.balance);
}