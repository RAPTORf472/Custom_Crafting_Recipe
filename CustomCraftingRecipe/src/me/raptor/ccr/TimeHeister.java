package me.raptor.ccr;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class TimeHeister implements Listener{
Plugin plugin;
static NamespacedKey thk;
@SuppressWarnings("static-access")
public TimeHeister(Plugin plugin) {
	this.plugin = plugin;
	NamespacedKey th = new NamespacedKey(plugin, "Time_Heister");
	this.thk = th;
	Bukkit.getPluginManager().registerEvents(this, plugin);
}
public static ItemStack th() {
	ItemStack th = new ItemStack(Material.CLOCK);
	ItemMeta thm = th.getItemMeta();
	thm.setDisplayName(ChatColor.DARK_PURPLE + "Time Heister");
	thm.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE + "Use this to skip the night"));
	thm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	th.setItemMeta(thm);
	th.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
	return th;
	
}
public static void TimeHeisterRecipe() {
	ShapedRecipe thr = new ShapedRecipe(thk, th());	
	RecipeChoice rc = new MaterialChoice(Arrays.asList(Material.WHITE_BED, 
													   Material.BLUE_BED,
													   Material.MAGENTA_BED,
													   Material.GREEN_BED,
													   Material.PURPLE_BED,
													   Material.RED_BED,
													   Material.BLACK_BED,
													   Material.PURPLE_BED,
													   Material.YELLOW_BED,
													   Material.LIGHT_BLUE_BED,
													   Material.LIGHT_GRAY_BED,
													   Material.BROWN_BED,
													   Material.CYAN_BED,
													   Material.GRAY_BED,
													   Material.LIME_BED,
													   Material.PINK_BED));
	thr.shape("***",
			  "*#*",
			  "***");
	thr.setIngredient('*', rc);
	thr.setIngredient('#', Material.CLOCK);
	Bukkit.getServer().addRecipe(thr);
}
@EventHandler
public void onPlayerClick(PlayerInteractEvent e) {
	if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction() == Action.RIGHT_CLICK_AIR) {
		EquipmentSlot hand = e.getHand();
		if (!hand.equals(EquipmentSlot.HAND)) return;
		Player p = e.getPlayer();
		if (p.getInventory().getItemInMainHand() == null) return;
		if (p.getInventory().getItemInMainHand().getType() != Material.CLOCK) return;

		ItemStack is = p.getInventory().getItemInMainHand();
		if (is.getItemMeta().getDisplayName() == null) return;
		if (is.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Time Heister")) {
			if (!(p.getWorld().getTime() >= 12541 && p.getWorld().getTime() <= 23458)) {
				p.sendMessage(ChatColor.RED + "Can't heist the time right now");
				return;
			}
			p.getWorld().setTime(1000);
			Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Time Heisted!");
				ItemStack hi = p.getInventory().getItemInMainHand();
				hi.setAmount(hi.getAmount()-1);
				p.getInventory().setItemInMainHand(hi);
				return;
			
		}
	}
}
}
