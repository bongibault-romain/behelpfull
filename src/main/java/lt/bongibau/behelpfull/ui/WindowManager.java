package lt.bongibau.behelpfull.ui;

public class WindowManager {

    private static WindowManager instance;

    private Window currentWindow;

    public void start(Window currentWindow) {
        this.setCurrentWindow(currentWindow);
        this.currentWindow.show();
    }

    public void start() {
        if (currentWindow != null) {
            currentWindow.show();
        }
    }

    public void stop() {
        if (currentWindow != null) {
            currentWindow.dispose();
        }
    }

    public void setCurrentWindow(Window currentWindow) {
        if (this.currentWindow != null) {
            this.currentWindow.dispose();
        }

        this.currentWindow = currentWindow;
        this.currentWindow.show();
    }

    public Window getCurrentWindow() {
        return currentWindow;
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            instance = new WindowManager();
        }

        return instance;
    }
}
