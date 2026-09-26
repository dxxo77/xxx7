package Game1.src.com.xiedongGame.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * 用户数据的"仓库"。
 * 它负责把用户存到本地文件 users.txt 里，以及从文件里读出来。
 * 这就是一个最简易的"数据库"，只不过存储介质是文本文件。
 *
 * users.txt 里每行的格式是：  用户名,密码
 */
public class UserStore {

    //用户数据文件的位置（相对 D:\java_demo）
    public static final String FILE_PATH = "Game1\\users.txt";

    //=========================== 读 ===========================

    //读出所有已注册的用户
    public static ArrayList<User> loadAll() {
        ArrayList<User> list = new ArrayList<User>();
        File file = new File(FILE_PATH);

        //文件还不存在（第一次运行）→ 自动建一个内置账号
        if (!file.exists()) {
            list.add(new User("XIEDONGHONG", "123456789"));
            saveAll(list);
            return list;
        }

        //逐行读取，每行按逗号拆成 用户名 和 密码
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;   //跳过空行
                }
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    list.add(new User(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //不管有没有出错，都要把文件关掉
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //=========================== 写 ===========================

    //把所有用户覆盖写回文件
    public static void saveAll(ArrayList<User> users) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH), "UTF-8"));
            for (User u : users) {
                bw.write(u.getUsername() + "," + u.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //=========================== 增 ===========================

    //追加一个新用户（只往文件末尾加，不碰已有的数据）
    public static void add(User user) {
        BufferedWriter bw = null;
        try {
            //第二个参数 true 表示"追加模式"，不是覆盖
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), "UTF-8"));
            bw.write(user.getUsername() + "," + user.getPassword());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //=========================== 查 ===========================

    //这个用户名是否已经被注册过了
    public static boolean exists(String username) {
        ArrayList<User> users = loadAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //用 用户名 + 密码 找出一个用户，找不到就返回 null
    public static User find(String username, String password) {
        ArrayList<User> users = loadAll();
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
