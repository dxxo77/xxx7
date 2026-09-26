package Game1.src.com.xiedongGame.ui;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
//主页面

public class GameJFrame extends JFrame implements KeyListener,ActionListener {
    //记录空白方块位置
    int x=0;
    int y=0;

    String imagePath = "Game1\\image\\girl2\\";

    int [][] win = new int[][]{
        {1,2,3,4},
        {5,6,7,8},
        {9,10,11,12},
        {13,14,15,0}
    };
    //二维数组：记录每个格子该显示第几张图片
    //data[行][列]，值为 0 表示该格子是空白位（没有图片）
    private int[][] data = new int[4][4];
    //统计步数 
    int count=0;

            //选项下面的条目对象
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("开发者");

    public GameJFrame(){
         //界面初始化
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据（打乱图片顺序）
        initData();

        //初始化图片
        initImage();

        //显示游戏主界面
        this.setVisible(true);

        

    }



    
    //初始化数据：决定 16 个格子分别显示第几张图
    private void initData() {
        //第一步：准备 0~15 这 16 个数
        //其中 0 代表空白位，1~15 分别对应 1.jpg~15.jpg
        int[] tempArr = new int[16];
        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = i;
        }

        //第二步：打乱这个一维数组
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            //随机挑一个下标
            int index = r.nextInt(tempArr.length);
            //把当前位置的数 和 随机位置的数 对调
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        //第三步：把打乱后的一维数组，按"每行4个"填进二维数组
        for (int i = 0; i < tempArr.length; i++) {
            if(tempArr[i]==0){
                //如果是空白格子，记录空白格子的位置
                x=i/4;
                y=i%4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }


    //初始化图片
    private void initImage() {
        this.getContentPane().removeAll(); //清空原有图片


        if(victory()){
            JLabel winJLabel = new JLabel(new ImageIcon("Game1\\image\\win.png"));
            winJLabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJLabel);
        }

        JLabel Count = new JLabel("步数："+count+"步");
        Count.setBounds(50,30,100,20);
        this.getContentPane().add(Count);
        //先加载的图片在上方，后加载的图片在下方
        // //创建图片icon的对象
        // ImageIcon icon = new ImageIcon("Game1\\image\\girl2\\1.jpg");
        // //创建一个JLabel对象（管理容器）
        // JLabel label1 = new JLabel(icon); 
        // //设置label的大小和位置
        // label1.setBounds(0,0,105,105);
        // //将标签添加到界面上
        // //this.add(label);
        // this.getContentPane().add(label1);
        //外层循环控制"行"，i 依次是 0、1、2、3
        for (int i = 0; i < 4; i++) {
            //内层循环控制"列"，j 依次是 0、1、2、3
            for (int j = 0; j < 4; j++) {
                //编号直接从二维数组里取，不再靠计算
                int number = data[i][j];

                //创建一个JLabel对象（管理容器）
                JLabel label1;
                if (number == 0) {
                    //编号为 0：这个格子是空白位，放一个不带图片的空标签
                    label1 = new JLabel();
                } else {
                    //创建图片icon的对象
                    ImageIcon icon = new ImageIcon(imagePath + number + ".jpg");
                    label1 = new JLabel(icon);
                }

                //设置label的大小和位置
                label1.setBounds(105 * j+83, 105 * i+134, 105, 105);
                label1.setBorder(new BevelBorder(1));
                //将标签添加到界面上
                //this.add(label);
                this.getContentPane().add(label1);
            }
        }

        ImageIcon bg = new ImageIcon("Game1\\image\\background.png");
        JLabel bgLabel = new JLabel(bg);
        bgLabel.setBounds(40, 40, 508, 560);
        this.getContentPane().add(bgLabel);


        this.getContentPane().repaint(); //刷新界面  
    }




    private void initJMenuBar() {
        //初始化菜单
        JMenuBar menuBar = new JMenuBar();
        //菜单上的两个选项
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");


        //将菜单条目添加到菜单上
        menuBar.add(functionJMenu);
        menuBar.add(aboutJMenu);

        //将菜单添加到界面上
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        aboutJMenu.add(accountItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        this.setJMenuBar(menuBar);
    }

    private void initJFrame() {
        //界面初始化，设置宽高
        this.setSize(603,680);
        //设置界面标题
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);
        //取消默认的居中放置
        this.setLayout(null);
        //添加键盘监听事件
        this.addKeyListener(this);
    }




    @Override
    public void keyTyped(KeyEvent e) {
        
    }



    //按下不松调用此方法
    @Override
    public void keyPressed(KeyEvent e) {
        if(victory()){
            return;
        }
       int code = e.getKeyCode();
       if(code==65){
         //把界面上所有的图片都移除
            this.getContentPane().removeAll();
            //加载一张完整图片
            JLabel allImage = new JLabel(new ImageIcon(imagePath + "all.jpg"));
            allImage.setBounds(83,134,420,420);
            this.getContentPane().add(allImage);

        ImageIcon bg = new ImageIcon("Game1\\image\\background.png");
        JLabel bgLabel = new JLabel(bg);
        bgLabel.setBounds(40, 40, 508, 560);
        this.getContentPane().add(bgLabel);
        this.getContentPane().repaint(); //刷新界面

       }
    }

 


    @Override
    public void keyReleased(KeyEvent e) {
        //判断是否胜利，若胜利就结束游戏
        if(victory()){
            return;
        }
        //对上下左右判断
        //左37右38上39下40
        int code = e.getKeyCode();
        if(code==37){
            if(y==3){
                return;
            }
            data[x][y] = data[x][y+1];
            data[x][y+1] = 0;
            y++;
            count++;
            initImage();
        }else if(code==38){
           if(x==3){
               return;
           }
           data[x][y] = data[x+1][y];
           data[x+1][y] = 0;
           x++;
            count++;
           initImage();
        }else if(code==39){
            if(y==0){
                return;
            }
            data[x][y] = data[x][y-1];
            data[x][y-1] = 0;
            y--;
             count++;
            initImage();
        }else if(code==40){
            if(x==0){
                return;
            }
            data[x][y] = data[x-1][y];
            data[x-1][y] = 0;
            x--;
             count++;
            initImage();
        }else if(code==65){
            initImage();
        }else if(code==87){
            data = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}};
                x=3;
                y=3;
            initImage();
        }
    }



    //判断胜利
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
       Object obj=e.getSource();
       if(obj== replayItem){//重开
        initData();
        count=0;
        initImage();
        
       }else if(obj==reLoginItem){//重新登录
        this.setVisible(false);//关闭游戏界面
        new LoginJFrame();

       }else if(obj==closeItem){//关闭游戏
        System.exit(0);

       }else if(obj==accountItem){//图片
            JDialog jj= new JDialog();
            JLabel JJ1= new JLabel(new ImageIcon());
            JJ1.setBounds(0,0,258,258);
            jj.getContentPane().add(JJ1);
            jj.setSize(344,344);//弹框大小
            jj.setAlwaysOnTop(true);
            jj.setLocationRelativeTo(null);//居中
            jj.setModal(true);//不关闭不能操作
            jj.setVisible(true);
       }
    }
}