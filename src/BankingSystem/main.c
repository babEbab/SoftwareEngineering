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
void toString(Account); // ���� ���� ���
int changeBalance(Account*, int); // ���� ������ �ܾ� ���� �Լ�

Account allAccount[100]; // ���� �迭
int nowAccountCount = 0; // ���� ������ ����

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
				toString(allAccount[orderAccount]);
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
			toString(allAccount[orderAccount]);
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
				toString(allAccount[orderAccount]);
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
		toString(allAccount[orderAccount]);
	}
	else {
		printf("�ش��ϴ� ���°� �����ϴ�.\n");
		return;
	}
}

void handleCreate() { // ���� ����
	printf("���� �̸��� �Է��Ͻÿ�: ");
	char name[ACCOUNT_NAME_LENGTH];
	scanf("%s", name);
	printf("���� ��й�ȣ�� �����ϼ���: ");
	char password[ACCOUNT_PASSWORD_LENGTH];
	scanf("%s", password);
	int accountNum;// ���¹�ȣ
	while (1) {
		srand(time(NULL)); // �Ź� �ٸ� �õ尪 ����
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
	printf("���� ������ ���������� �Ϸ�Ǿ����ϴ�.\n");
	toString(allAccount[nowAccountCount - 1]);
}

int checkHaveAccountNum() { // ��ϵ� �������� Ȯ��
	printf("���¹�ȣ�� �Է��Ͻÿ�: ");
	int accountNum;
	scanf("%d", &accountNum);

	for (int i = 0; i < nowAccountCount; i++) {
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

int checkAccountNumAlready(int accountNum) {
	// ���¹�ȣ ���� ���� �� �̹� �����ϴ� ���¹�ȣ���� �ߺ��� ���ϱ� ���ؼ�
	// ���� ������ ��ȣ�� �̹� ��ϵ� ���¹�ȣ���� Ȯ��
	for (int i = 0; i < nowAccountCount; i++) {
		if (allAccount[i].accountNum == accountNum) {
			return 0;
		}
	}
	return 1;
}

int changeBalance(Account* account, int money) {
	if ((*account).balance + money >= 0) { // ��� �� ���� �ܾ��� 0�� �̸��� �Ǵ� ���� �����Ѵ�
		(*account).balance += money;
		return 1;
	}
	else {
		return 0;
	}
}

void toString(Account account) { // ���� ���� ��¿� �޼���
	printf("\n���¹�ȣ: %d", account.accountNum);
	printf("\n�̸�: %s", account.name);
	printf("\n�ܾ�: %d\n", account.balance);

}