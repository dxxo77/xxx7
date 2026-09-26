package Game1.src.com.xiedongGame.ui;

import Game1.src.com.xiedongGame.domain.User;
import Game1.src.com.xiedongGame.domain.UserStore;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//注册界面
public class RegisterJFrame extends JFrame {

    //注册界面直接复用登录界面的素材
    String loginPath = "Game1\\image\\login\\";

    //输入框
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    public RegisterJFrame(){
        //界面初始化
        initJFrame();

        //添加组件
        initView();

        //显示注册界面
        this.setVisible(true);
    }

    //界面初始化
    private void initJFrame() {
        this.setSize(488,430);
        this.setTitle("用户注册界面");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        this.setResizable(false);
    }

    //添加组件
    private void initView() {

        //---------------- 1.用户名 ----------------
        JLabel usernameText = new JLabel(new ImageIcon(loginPath + "用户名.png"));
        usernameText.setBounds(63, 140, 47, 17);
        this.getContentPane().add(usernameText);

        usernameField.setBounds(120, 134, 230, 30);
        usernameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        this.getContentPane().add(usernameField);

        //---------------- 2.密码 ----------------
        JLabel passwordText = new JLabel(new ImageIcon(loginPath + "密码.png"));
        passwordText.setBounds(78, 202, 32, 16);
        this.getContentPane().add(passwordText);

        passwordField.setBounds(120, 195, 230, 30);
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        this.getContentPane().add(passwordField);

        //---------------- 3.注册按钮 ----------------
        JButton registerBtn = new JButton();
        registerBtn.setBounds(171, 265, 128, 47);
        registerBtn.setIcon(new ImageIcon(loginPath + "注册按钮.png"));        //平时的样子
        registerBtn.setPressedIcon(new ImageIcon(loginPath + "注册按下.png")); //按住时的样子
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        this.getContentPane().add(registerBtn);

        //---------------- 4.背景图（最后添加 → 在最底层）----------------
        JLabel background = new JLabel(new ImageIcon(loginPath + "background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);
    }


    //===========================================================
    //                        业务逻辑
    //===========================================================

    //点击"注册"按钮之后执行
    private void register() {

        //拿到用户输入的东西
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        //---------------- 第 1 关：都不能为空 ----------------
        if (username.length() == 0) {
            showDialog("请输入用户名");
            return;
        }
        if (password.length() == 0) {
            showDialog("请输入密码");
            return;
        }

        //---------------- 第 2 关：用户名长度限制 ----------------
        if (username.length() < 3) {
            showDialog("用户名至少 3 个字符");
            return;
        }

        //---------------- 第 3 关：用户名不能重复 ----------------
        //这一步就是"查数据库"的雏形：先看看有没有重名的
        if (UserStore.exists(username)) {
            showDialog("用户名已存在，换一个吧");
            return;
        }

        //---------------- 全部通过 → 存起来 ----------------
        UserStore.add(new User(username, password));

        showDialog("注册成功！请回到登录界面用新账号登录");

        //清空输入框，关掉注册窗口
        usernameField.setText("");
        passwordField.setText("");
        this.setVisible(false);
    }

    //弹出提示框
    private void showDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
