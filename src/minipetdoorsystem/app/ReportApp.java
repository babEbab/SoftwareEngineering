package minipetdoorsystem.app;

import minipetdoorsystem.accessrecord.AccessRecord;

public class ReportApp implements App {

    private static final ReportApp instance = new ReportApp();

    public static ReportApp getInstance() {
        return instance;
    }

    private ReportApp() {
    }

    public void showReport() {
        AccessRecord[] report = processor.makeReport();
        if (report == null) {
            System.out.println("보고서 생성 실패. 원인: 데이터가 없거나 프로세서의 전원이 꺼져있다.");
        }
        else {

            for (AccessRecord accessRecord : report) {
                System.out.print(" │ ");
                System.out.print(accessRecord.getId() + " │ ");
                System.out.print(accessRecord.getSucceed() + " │ ");
                System.out.print(accessRecord.getDate() + " ");
                System.out.print(accessRecord.getTime().getHour() + ":" + accessRecord.getTime().getMinute() + " │ ");
                System.out.println(accessRecord.getPetName() + " │ ");
            }
        }
    }
}
