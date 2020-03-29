package me.raptor.ccr;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class AntiGravityPotion implements Listener{
	Plugin plugin;
	static NamespacedKey key1, key2;
	HashMap<String, Integer> rtime = new HashMap<String, Integer>();
	ArrayList<String> dr = new ArrayList<String>();
	ArrayList<String> fall = new ArrayList<String>();

	@SuppressWarnings("static-access")
	public AntiGravityPotion(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		NamespacedKey key1 = new NamespacedKey(plugin, "AGP1");
		NamespacedKey key2 = new NamespacedKey(plugin, "AGP2");
		this.key1 = key1;
		this.key2 = key2;
	}

	
	public static ItemStack AGP(List<String> dur) {
	ItemStack potion = new ItemStack(Material.POTION, 1);
	PotionMeta pm = (PotionMeta) potion.getItemMeta();
	pm.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
	pm.setDisplayName("Anti Gravity Potion");
	pm.setLore(dur);
	pm.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	potion.setItemMeta(pm);
	return potion;
}
	public ItemStack SFP1() {
	ItemStack potion = new ItemStack(Material.POTION, 1);
	PotionMeta pm = (PotionMeta) potion.getItemMeta();
	pm.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, false, false));
	potion.setItemMeta(pm);
	return potion;
}
	
	public ItemStack SFP2() {
	ItemStack potion = new ItemStack(Material.POTION, 1);
	PotionMeta pm = (PotionMeta) potion.getItemMeta();
	pm.setBasePotionData(new PotionData(PotionType.SLOW_FALLING, true, false));
	potion.setItemMeta(pm);
	return potion;
}
	public static void AGPRecipe1() {


		ShapelessRecipe agp1 = new ShapelessRecipe(key1, AGP(Arrays.asList(ChatColor.BLUE + "Flying (1:30)")));
		agp1.addIngredient(1, AGP(Arrays.asList(ChatColor.BLUE + "Flying (1:30)")).getType());
		agp1.addIngredient(3, Material.FEATHER);
		agp1.addIngredient(2, Material.PHANTOM_MEMBRANE);
		Bukkit.getServer().addRecipe(agp1);
	}
	public static void AGPRecipe2() {

		ShapelessRecipe agp2 = new ShapelessRecipe(key2, AGP(Arrays.asList(ChatColor.BLUE + "Flying (4:00)")));
		agp2.addIngredient(1, AGP(Arrays.asList(ChatColor.BLUE + "Flying (4:00)")).getType());
		agp2.addIngredient(5, Material.FEATHER);
		agp2.addIngredient(3, Material.PHANTOM_MEMBRANE);
		Bukkit.getServer().addRecipe(agp2);
	}
@EventHandler
public void correctRecipe1(PrepareItemCraftEvent e) {
	CraftingInventory ci = e.getInventory();
	if (e.getRecipe() == null) return;
	if (e.getRecipe().getResult() == null) return;
	if (e.getRecipe().getResult().getItemMeta() == null) return;
		boolean found = false;
		if (e.getRecipe().getResult().getItemMeta().equals(AGP(Arrays.asList(ChatColor.BLUE + "Flying (1:30)")).getItemMeta())) {

			for (ItemStack item : ci.getMatrix()) {
				if (item == null) continue;
				if(item.getType() == Material.POTION && item.getItemMeta().equals(SFP1().getItemMeta())) {
					found = true;
				} 
			}
			if (!found) {
				ci.setResult(null);
			}
		}

	}

@EventHandler
public void correctRecipe2(PrepareItemCraftEvent e) {
	CraftingInventory ci = e.getInventory();
	if (e.getRecipe() == null) return;
	if (e.getRecipe().getResult() == null) return;
	if (e.getRecipe().getResult().getItemMeta() == null) return;
		boolean found = false;
		if (e.getRecipe().getResult().getItemMeta().equals(AGP(Arrays.asList(ChatColor.BLUE + "Flying (4:00)")).getItemMeta())) {

			for (ItemStack item : ci.getMatrix()) {
				if (item == null) continue;
				if(item.getType() == Material.POTION && item.getItemMeta().equals(SFP2().getItemMeta())) {
					found = true;
				} 
			}
			if (!found) {
				ci.setResult(null);
			}
		}

	}

@EventHandler
public void onPlayerDrinkPotion(PlayerItemConsumeEvent e) {
	if (e.getItem().getItemMeta() == null) return;
	if (e.getItem().getItemMeta().equals(AGP(Arrays.asList(ChatColor.BLUE + "Flying (1:30)")).getItemMeta())) {
		Player p = e.getPlayer();
		rtime.put(p.getName(), 90);
		p.setAllowFlight(true);
		p.sendMessage(ChatColor.GREEN + "You have 1 minute 30 seconds of flying");
		if (!dr.contains(p.getName())) {
			dr.add(p.getName());
		new BukkitRunnable() {
			public void run() {
				if (p.isOnline() == false) cancel();
				rtime.replace(p.getName(),rtime.get(p.getName())-1);
				if (rtime.get(p.getName()) == 60) p.sendMessage(ChatColor.GOLD + "1 minute of flying remaining");
				if (rtime.get(p.getName()) == 15) p.sendMessage(ChatColor.RED + "15 seconds of flying remaining");
				if (rtime.get(p.getName()) > 0 && rtime.get(p.getName()) <=10) p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + rtime.get(p.getName()));
				if (rtime.get(p.getName()) <= 0 && rtime.containsKey(p.getName())) {
					p.setAllowFlight(false);
					dr.remove(p.getName());
					if(!fall.contains(p.getName())) fall.add(p.getName()); fall.add(p.getName());
					cancel();
				}		

			}
		}.runTaskTimerAsynchronously(plugin, 0, 20);
	}
} else if (e.getItem().getItemMeta().equals(AGP(Arrays.asList(ChatColor.BLUE + "Flying (4:00)")).getItemMeta())) {
	Player p = e.getPlayer();
	rtime.put(p.getName(), 240);
	p.setAllowFlight(true);
	p.sendMessage(ChatColor.GREEN + "You have 4 minutes of flying");
	if (!dr.contains(p.getName())) {
		dr.add(p.getName());
	new BukkitRunnable() {
		public void run() {
			if (p.isOnline() == false) cancel();
			rtime.replace(p.getName(),rtime.get(p.getName())-1);
			if (rtime.get(p.getName()) == 180) p.sendMessage(ChatColor.GREEN + "3 minute of flying remaining");
			if (rtime.get(p.getName()) == 120) p.sendMessage(ChatColor.GREEN + "2 minute of flying remaining");
			if (rtime.get(p.getName()) == 60) p.sendMessage(ChatColor.GOLD + "1 minute of flying remaining");
			if (rtime.get(p.getName()) == 15) p.sendMessage(ChatColor.RED + "15 seconds of flying remaining");
			if (rtime.get(p.getName()) > 0 && rtime.get(p.getName()) <=10) p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + rtime.get(p.getName()));
			if (rtime.get(p.getName()) <= 0 && rtime.containsKey(p.getName())) {
				p.setAllowFlight(false);
				dr.remove(p.getName());
				if(!fall.contains(p.getName())) fall.add(p.getName());
				cancel();
			}		

		}
	}.runTaskTimerAsynchronously(plugin, 0, 20);
}
}
}
@EventHandler
public void onPlayerDie(PlayerDeathEvent e) {
	Player p = (Player) e.getEntity();
	if (dr.contains(p.getName())) {
		dr.remove(p.getName());
		rtime.replace(p.getName(),0);
		p.setFlying(false);
		p.setAllowFlight(false);
		
	}
}
@EventHandler
public void onPlayerFall(EntityDamageEvent e) {
	if (!(e.getEntity() instanceof Player)) return;
	Player p = (Player) e.getEntity();
	if (e.getCause() != DamageCause.FALL) return;
		if (fall.contains(p.getName())) {
			e.setCancelled(true);
			fall.remove(p.getName());
		}
	
}
@EventHandler 
public void onPlayerQuit(PlayerQuitEvent e) {
	if (dr.contains(e.getPlayer().getName()))
		dr.remove(e.getPlayer().getName());
}
@EventHandler
public void onPlayerRejoin(PlayerJoinEvent e) {

	Player p = e.getPlayer();
	if (dr.contains(p.getName())) return;
	if(rtime.containsKey(p.getName())) {
		rtime.replace(p.getName(),rtime.get(p.getName()));
		p.setAllowFlight(true);
		p.setFlying(true);
		new BukkitRunnable() {
			public void run() {
				if (p.isOnline() == false) cancel();
				rtime.replace(p.getName(),rtime.get(p.getName())-1);
				if (rtime.get(p.getName()) == 180) p.sendMessage(ChatColor.GREEN + "3 minute of flying remaining");
				if (rtime.get(p.getName()) == 120) p.sendMessage(ChatColor.GREEN + "2 minute of flying remaining");
				if (rtime.get(p.getName()) == 60) p.sendMessage(ChatColor.GOLD + "1 minute of flying remaining");
				if (rtime.get(p.getName()) == 15) p.sendMessage(ChatColor.RED + "15 seconds of flying remaining");
				if (rtime.get(p.getName()) > 0 && rtime.get(p.getName()) <=10) p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + rtime.get(p.getName()));
				if (rtime.get(p.getName()) <= 0 && rtime.containsKey(p.getName())) {
					p.setAllowFlight(false);
					dr.remove(p.getName());
					if(!fall.contains(p.getName())) fall.add(p.getName()); fall.add(p.getName());
					dr.remove(p.getName());
					cancel();
				}		

			}
		}.runTaskTimerAsynchronously(plugin, 1, 20);
	}
		
	

}



}
	
