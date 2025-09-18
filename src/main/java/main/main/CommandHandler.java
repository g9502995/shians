package main.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

public class CommandHandler implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用此指令");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();

        if (!LoginData.hasPlayerName(playerName)) {
            player.sendMessage(ChatColor.GREEN + "你已經登入過了");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "請輸入密碼");
            return false;
        }

        String password = args[0];

        if (!ConfigReader.isPlayerRegistered(playerName)) {
            player.sendMessage(ChatColor.RED + "你尚未註冊，請使用 /register <密碼> <確認密碼> 來註冊");
            return true;
        }

        if (ConfigReader.verifyPassword(playerName, password)) {
            LoginData.removePlayerName(playerName);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.GREEN + "登入成功，歡迎回來！");
        } else {
            player.sendMessage(ChatColor.RED + "密碼錯誤！");
        }
        return true;
    }
}
