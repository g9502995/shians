package main.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.getServer;


public class eventlistener implements Listener {

    public static eventlistener instance;

    @EventHandler(priority= EventPriority.HIGHEST)

   public void onPlayerLogin(PlayerLoginEvent e) {
        //新增玩家名字

        eventlistener pl = eventlistener.instance;

        LoginData.addPlayerName(e.getPlayer().getName());

        if (LoginData.hasPlayerName(e.getPlayer().getName())) {
            e.getPlayer().setGameMode(GameMode.CREATIVE);
            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

                int i = 30;
                @Override

                public void run() {


                    i--;
                    String I = "目前數字:"+i;
                    e.getPlayer().sendMessage(I);
                    if(i<0){
                        scheduler.cancelTasks(Main.getPlugin());
                    }
                }

            },0L,  1*20L);

        }
    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
    //離線移除玩家名字
        LoginData.removePlayerName(e.getPlayer().getName());
    }

    public static void cancelIfNotLoggedIn(Cancellable e) {
        // 这里写着 Cancellable，和上面的 List 是一个原理，说到底我们只需要「可以取消」这个功能就可以了，至于到底是哪个类，不重要


        if (e instanceof PlayerEvent) {
            // instanceof 关键字指示 Java 重新判断左边对象的类型是不是右边的类或者右边类的子类，也就是判断能否进行强制类型转换
            if (LoginData.hasPlayerName(((PlayerEvent) e).getPlayer().getName())) {

                // if 语句用于看看玩家是不是在限制列表中
                // (PlayerEvent) e 进行类型转换
                e.setCancelled(true);
            }
        } else if (e instanceof InventoryOpenEvent) {
            // else if 表示「上一条 if 的条件为假」并且「当前括号中的条件为真」时才执行大括号里面的内容，相当于「如果不是那样，而是这样，就做……」

            // 限制玩家打开物品栏，需要 InventoryOpenEvent
            if (LoginData.hasPlayerName(((InventoryOpenEvent) e).getPlayer().getName())) {

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void restrictMove(PlayerMoveEvent e) {
        // 移动
        cancelIfNotLoggedIn(e);
        // 你看这多方便
    }

    @EventHandler
    public void restrictInteract(PlayerInteractEvent e) {
        // 交互
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictInteractAtEntity(PlayerInteractAtEntityEvent e) {
        // 实体交互
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictPortal(PlayerPortalEvent e) {
        // 传送门
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictTeleport(PlayerTeleportEvent e) {
        // 传送
        cancelIfNotLoggedIn(e);
    }

    @EventHandler
    public void restrictOpenInventory(InventoryOpenEvent e) {
        // 打开物品栏
        cancelIfNotLoggedIn(e);
    }




}
