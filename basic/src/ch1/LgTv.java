package ch1;

public class LgTv implements Tv {

    // 멤버 변수 초기화
    // 기본형 : int, long ... 등
    // 1) 정수 타입 : 0
    // 2) boolean : false
    // 3) 실수 타입 : 0.0
    // 참조형(대문자로 시작하는 타입, 배열 등) : null 로 초기화
    private Speaker speaker; // == null

    // 멤버 변수 초기화 방법
    // 1) setter 메소드 이용
    // 2) 생성자 이용
    public LgTv(Speaker speaker) {
        // 다형성(부모클래스 = 자식클래스)
        this.speaker = speaker;
    }

    public LgTv() {
    }

    // 메소드
    @Override
    public void powerOn() {
        System.out.println("LgTv - 전원 On");
    }

    @Override
    public void powerOff() {
        System.out.println("LgTv - 전원 Off");
    }

    @Override
    public void volumeUp() {
        // System.out.println("LgTv - volume up");
        speaker.volumeUp();
    }

    @Override
    public void volumeDown() {
        // System.out.println("LgTv - volume down");
        speaker.volumeDown();
    }

    // Setter
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
