package Game1.src;
import Game1.src.com.xiedongGame.ui.LoginJFrame;
public class App {
    public static void main(String[] args) {
        //启动时先打开登录界面
        //登录成功后，由 LoginJFrame 自己打开游戏主界面
        new LoginJFrame();
    }
}
