package ch1;

public class IriverSpeaker implements Speaker {
    public IriverSpeaker() {
        System.out.println("IriverSpeaker 생성");
    }

    public void volumeUp() {
        System.out.println("IriverSpeaker volume up");
    }

    public void volumeDown() {
        System.out.println("IriverSpeaker volume down");
    }
}
