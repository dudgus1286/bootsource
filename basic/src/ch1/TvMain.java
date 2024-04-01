package ch1;

public class TvMain {
    public static void main(String[] args) {
        // Tv 객체 생성
        // 1) 각 클래스별로 객체 생성
        LgTv tv = new LgTv();
        // SamsungTv samsungTv = new SamsungTv();
        // 2) 여러 클래스에 상속하는 부모 클래스(인터페이스)로 객체 생성
        // Tv tv = new LgTv();
        // Tv tv = new LgTv(new BritzSpeaker());
        // 부모클래스 객체로 자식 클래스에만 있는 메소드 사용하면 에러
        // 자색클래스 생성자로 생성된 부모클래스 객체로는 자식 클래스의 오버라이딩 된 메소드만 사용가능
        // 자바 객체 간의 관계 1) 상속 2) 포함
        tv.setSpeaker(new BritzSpeaker()); // 의존 관계 (포함)

        tv.powerOn();

        // 멤버변수 초기화 안 하고 그냥 하면 NullPointerException : BritzSpeaker 객체 생성 안 해서
        tv.volumeUp();
        tv.volumeDown();
        tv.powerOff();
    }
}
