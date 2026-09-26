# 拼图小游戏（Java Swing）

一个用 Java 基础语法 + Swing 实现的 4×4 拼图小游戏，跟着网课做的学习项目。

## 功能

- **用户系统**：注册、登录（含随机验证码校验），用户数据保存在本地文件
- **拼图游戏**：图片随机打乱、方向键移动、步数统计、胜利判定（拼对后显示胜利标志）
- **辅助功能**：按住 `A` 键查看完整原图

## 技术点

- Java SE + Swing 界面编程（`JFrame` / `JLabel` / `JButton` / `JDialog` / 绝对布局）
- 面向对象三大特性：封装、继承、多态
- 接口与匿名内部类：`ActionListener` / `KeyListener` / `MouseAdapter`
- 二维数组存储状态 + 一维数组随机交换打乱算法
- 文件 IO 模拟数据库：`BufferedReader` / `BufferedWriter` 读写用户信息
- `BufferedImage` + `Graphics2D` 动态绘制验证码

## 运行方式

**环境要求**：JDK 8 及以上（本项目在 JDK 21 下开发）

```bash
# 1. 克隆到本地
git clone https://github.com/dxxo77/xxx7.git
cd xxx7

# 2. 编译
javac -encoding UTF-8 -d out Game1/src/App.java

# 3. 运行
java -cp out Game1.src.App
```

> ⚠️ **必须在仓库根目录（xxx7）下编译运行。**
> 因为包名里包含 `Game1` 这一层，图片也是 `Game1/image/...` 的相对路径，换个目录就会找不到图片。

## 内置账号

| 用户名 | 密码 |
| --- | --- |
| XIEDONGHONG | 123456789 |

也可以直接点登录界面的「注册」按钮新建账号。

> 用户数据存在 `Game1/users.txt`，程序首次运行时会自动创建。该文件已加入 `.gitignore`，不会提交到仓库。

## 操作说明

| 操作 | 效果 |
| --- | --- |
| 方向键 ↑ ↓ ← → | 移动拼图 |
| 按住 `A` | 查看完整原图 |
| 菜单「功能」 | 重新游戏 / 重新登录 / 关闭游戏 |

## 项目结构

```
Game1/
├── image/                        # 图片素材
│   ├── girl2/                    # 4×4 拼图碎片（1~15.jpg）+ 完整图 all.jpg
│   ├── login/                    # 登录 / 注册界面素材
│   ├── background.png            # 游戏主界面背景
│   └── win.png                   # 胜利标志
└── src/
    ├── App.java                  # 程序入口
    └── com/xiedongGame/
        ├── domain/               # 数据层
        │   ├── User.java         # 用户实体类
        │   └── UserStore.java    # 用户数据读写（本地文件）
        └── ui/                   # 界面层
            ├── LoginJFrame.java      # 登录界面
            ├── RegisterJFrame.java   # 注册界面
            └── GameJFrame.java       # 游戏主界面
```
