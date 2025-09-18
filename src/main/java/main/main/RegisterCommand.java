package main.main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.security.NoSuchAlgorithmException;

public class RegisterCommand implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用此指令");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();

        if (ConfigReader.isPlayerRegistered(playerName)) {
            player.sendMessage(ChatColor.RED + "你已經註冊過了");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "請輸入密碼");
            return false;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "用法: /register <密碼> <確認密碼>");
            return false;
        }

        if (!args[0].equals(args[1])) {
            sender.sendMessage(ChatColor.RED + "密碼不匹配");
            return true;
        }

        String password = args[0];

        try {
            ConfigReader.addPlayer(playerName, password, player.getUniqueId().toString());
            player.sendMessage(ChatColor.GREEN + "註冊成功，請使用 /login <密碼> 來登入");
            // 註冊成功後不需要解鎖玩家或更改遊戲模式，因為他們在登入之前無法進行任何操作
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "註冊時發生錯誤");
        }

        return true;
    }
}
