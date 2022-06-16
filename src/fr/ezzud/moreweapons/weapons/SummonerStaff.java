package fr.ezzud.moreweapons.weapons;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import fr.ezzud.moreweapons.MoreWeapons;
import fr.ezzud.moreweapons.others.SummonerEntity;
import fr.ezzud.moreweapons.utils.InventoryUtil;
import fr.ezzud.moreweapons.utils.ItemCooldown;


public class SummonerStaff {
	static MoreWeapons plugin = MoreWeapons.getInstance();
	private Player player;
	public SummonerStaff(Player player) {
		this.player = player;
	}
	
	public void give() {
		ItemStack item = new ItemStack(Material.GOLDEN_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(1232401);
		meta.setDisplayName("Summoner's Staff");
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		
		if(InventoryUtil.isInventoryFull(this.player)) {
			this.player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
		} else {
			this.player.getInventory().addItem(item);
		}
	}
	
	public static void action(Player player) {
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(InventoryUtil.isDamaged(meta)) {
			return;
		}
		ItemCooldown.cooldownItem(15, item, player, new Runnable() {
			@Override
			public void run() {
				((Damageable)meta).setDamage((item.getType().getMaxDurability() - 1));
	        	item.setItemMeta(meta);
	        	SummonerEntity.summonGolems(2, player, plugin);
			}
        	
		});
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
		
	}
	
	
	public static void secondAction(Player player) {
		ItemStack item = player.getInventory().getItemInMainHand();
		ItemMeta meta = item.getItemMeta();
		if(InventoryUtil.isDamaged(meta)) {
			return;
		}
		ItemCooldown.cooldownItem(15, item, player, new Runnable() {
			@Override
			public void run() {
				((Damageable)meta).setDamage((item.getType().getMaxDurability() - 1));
	        	item.setItemMeta(meta);
	        	SummonerEntity.summonZombies(1, player, plugin);
			}
        	
		});
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
		
	}


}
