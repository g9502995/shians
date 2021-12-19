package main.main;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConfigReader {
    public static FileConfiguration config = Main.instance.getConfig();
    // 由于三个方法都要使用，我们将这个变量抽取出来到最外层
    public static boolean isPlayerRegistered(String playerName) {

        return config.contains(playerName.toLowerCase());
    }

    public static boolean verifyPassword(String playerName, String password) {
        try {
            String A = sha1.password(password);
            return A.equals(config.getString(playerName.toLowerCase()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
     return false;
        // 三步合成一行：转换小写，读取字符串，返回是否相等
    }

    public static void addPlayer(String playerName, String password) throws NoSuchAlgorithmException {
       String A = sha1.password(password);
        Main.instance.getConfig().set(playerName.toLowerCase(), A);
        Main.instance.saveConfig();
    }




}
