package me.raptor.ccr;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
public void onEnable() {
	Logger.console("&8Custom Crafting Recipe Has Been Enabled");
	new AntiGravityPotion(this);
	AntiGravityPotion.AGPRecipe1();
	AntiGravityPotion.AGPRecipe2();

}
public void onDisable() {
	Logger.console("&7Custom Crafting Recipe Has Been Disabled");
}


}

