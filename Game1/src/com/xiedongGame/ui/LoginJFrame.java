package Game1.src.com.xiedongGame.ui;

import Game1.src.com.xiedongGame.domain.User;
import Game1.src.com.xiedongGame.domain.UserStore;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

//登录界面
public class LoginJFrame extends JFrame {

    //登录界面素材所在的文件夹
    String loginPath = "Game1\\image\\login\\";

    //账号密码不再写死在代码里了，而是保存在 users.txt 中，由 UserStore 负责读写

    //当前这张验证码图片上写的正确答案
    String code = "";

    //三个输入框（写成成员变量，因为点击"登录"时还要用到它们）
    JTextField usernameField = new JTextField();         //用户名
    JPasswordField passwordField = new JPasswordField(); //密码
    JTextField codeField = new JTextField();             //验证码

    //用来显示验证码图片的标签
    JLabel codeImage = new JLabel();

    public LoginJFrame(){
        //界面初始化
        initJFrame();

        //添加组件
        initView();

        //显示登录界面
        this.setVisible(true);
    }

    //界面初始化
    private void initJFrame() {
        this.setSize(488,430);
        this.setTitle("用户登录界面");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        //取消默认的居中放置，不写这行下面的 setBounds 全部失效
        this.setLayout(null);
        //固定窗口大小，避免用户拉伸后布局错乱
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

        //---------------- 3.验证码 ----------------
        JLabel codeText = new JLabel(new ImageIcon(loginPath + "验证码.png"));
        codeText.setBounds(54, 257, 56, 21);
        this.getContentPane().add(codeText);

        codeField.setBounds(120, 253, 120, 30);
        codeField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        this.getContentPane().add(codeField);

        //验证码图片（这张图是用代码画出来的，不是素材）
        codeImage.setBounds(255, 253, 100, 30);
        codeImage.setToolTipText("看不清楚？点我换一张");
        //鼠标点一下验证码，就换一张
        codeImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshCode();
            }
        });
        this.getContentPane().add(codeImage);

        //---------------- 4.登录按钮 ----------------
        JButton loginBtn = new JButton();
        loginBtn.setBounds(97, 315, 128, 47);
        loginBtn.setIcon(new ImageIcon(loginPath + "登录按钮.png"));        //平时的样子
        loginBtn.setPressedIcon(new ImageIcon(loginPath + "登录按下.png")); //按住时的样子
        loginBtn.setBorderPainted(false);      //不要按钮自带的边框
        loginBtn.setContentAreaFilled(false);  //不要按钮自带的背景
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        this.getContentPane().add(loginBtn);

        //---------------- 5.注册按钮（业务逻辑先不做）----------------
        JButton registerBtn = new JButton();
        registerBtn.setBounds(245, 315, 128, 47);
        registerBtn.setIcon(new ImageIcon(loginPath + "注册按钮.png"));
        registerBtn.setPressedIcon(new ImageIcon(loginPath + "注册按下.png"));
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开注册界面（注册窗口会盖在登录界面上面，关掉就回来了）
                new RegisterJFrame();
            }
        });
        this.getContentPane().add(registerBtn);

        //---------------- 6.背景图 ----------------
        //★ 必须最后添加！Swing 里"先添加的在上层，后添加的在最底层"
        //  所以背景图放最后，它才会乖乖待在底下当背景，不会盖住上面的控件
        JLabel background = new JLabel(new ImageIcon(loginPath + "background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

        //最后：生成第一张验证码
        refreshCode();
    }


    //===========================================================
    //                        业务逻辑
    //===========================================================

    //点击"登录"按钮之后执行
    private void login() {

        //---------------- 第 1 关：验证码 ----------------
        String inputCode = codeField.getText();
        if (inputCode.length() == 0) {
            showDialog("请输入验证码");
            return;
        }
        //equalsIgnoreCase：忽略大小写比较，用户不用纠结大小写
        if (!inputCode.equalsIgnoreCase(code)) {
            showDialog("验证码错误");
            refreshCode();          //换一张新的
            codeField.setText("");  //把输入框清空
            return;
        }

        //---------------- 第 2 关：用户名 ----------------
        String inputUsername = usernameField.getText();
        if (inputUsername.length() == 0) {
            showDialog("请输入用户名");
            return;
        }
        //去 users.txt 里查一查，这个用户名注册过没有
        if (!UserStore.exists(inputUsername)) {
            showDialog("用户名不存在，请先注册");
            return;
        }

        //---------------- 第 3 关：密码 ----------------
        String inputPassword = new String(passwordField.getPassword());
        if (inputPassword.length() == 0) {
            showDialog("请输入密码");
            return;
        }
        //把用户名和密码一起拿去匹配，匹配上才算登录成功
        if (UserStore.find(inputUsername, inputPassword) == null) {
            showDialog("密码错误");
            return;
        }

        //---------------- 全部通过 → 进入游戏 ----------------
        this.setVisible(false);   //先把登录界面藏起来
        new GameJFrame();         //再打开游戏主界面
    }

    //弹出提示框
    private void showDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    //===========================================================
    //                      验证码相关
    //===========================================================

    //换一张验证码：先随机出一个答案，再把它画成图片
    private void refreshCode() {
        code = randomCode();
        codeImage.setIcon(drawCodeImage(code));
    }

    //随机生成 4 位验证码
    private String randomCode() {
        //故意去掉了容易看错的 0 O 1 l I，免得用户明明输对了却通不过
        String pool = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(pool.charAt(r.nextInt(pool.length())));
        }
        return sb.toString();
    }

    //把验证码文字画成一张图片
    private ImageIcon drawCodeImage(String text) {
        int w = 100;
        int h = 30;

        //先创建一张空白的图片
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        //铺一层浅黄色底，跟背景图色调搭配
        g.setColor(new Color(252, 245, 205));
        g.fillRect(0, 0, w, h);

        //画 4 个字符，每个字符的颜色和高低都随机，看起来更像真验证码
        Random r = new Random();
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 0; i < text.length(); i++) {
            g.setColor(new Color(r.nextInt(120), r.nextInt(120), r.nextInt(120)));
            int y = 22 + r.nextInt(4) - 2;      //上下轻微抖动
            g.drawString(String.valueOf(text.charAt(i)), 14 + i * 21, y);
        }

        //再画几条干扰线
        for (int i = 0; i < 5; i++) {
            g.setColor(new Color(r.nextInt(180), r.nextInt(180), r.nextInt(180)));
            g.drawLine(r.nextInt(w), r.nextInt(h), r.nextInt(w), r.nextInt(h));
        }

        g.dispose();   //画完了，释放画笔
        return new ImageIcon(img);
    }
}
