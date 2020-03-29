package me.raptor.ccr;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
 
public class Logger {
   
    public static void broadcast(String s) {
        Bukkit.getServer().broadcastMessage(prefix() + chat(s));
    }
   
    public static void chatP(Player p, String s) {
        p.sendMessage(prefix() + chat(s));
    }
   
    public static void console(String s) {
        Bukkit.getConsoleSender().sendMessage(prefix() + chat(s));
    }
   
    public static String prefix() {
        return chat("&8&l[&4&lCustom Crafting Recipe&8&l] &e");
    }
   
    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
   
   
   
}