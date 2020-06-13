package chess.modules.gameObjects;

public class Timer {

    private boolean isOn;
    private int time;

    public Timer(boolean isOn, int time) {
        this.isOn = isOn;
        this.time = time;
    }

    public void SetIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean getIsOn() {
        return isOn;
    }

    public int getTime() {
        return time;
    }

}
