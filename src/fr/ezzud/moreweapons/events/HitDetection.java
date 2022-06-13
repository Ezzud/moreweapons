package fr.ezzud.moreweapons.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import fr.ezzud.moreweapons.MoreWeapons;

@SuppressWarnings("deprecation")
public class HitDetection implements Listener {
	
    MoreWeapons plugin;
    public HitDetection(MoreWeapons instance) {
        plugin = instance;
    }
    

	   @EventHandler
	   public void hit(ProjectileHitEvent event) {
		   if(event.getEntity() instanceof Snowball) {
			   if(event.getEntity().hasMetadata("bullet")) {
				   World world = event.getEntity().getWorld();
				   if(event.getHitEntity() == null) { 
					   Block x = event.getHitBlock();
					   if(x.getType().equals(Material.BEDROCK)) return;
			           x.setType(Material.AIR);
			           world.playSound(x.getLocation(), Sound.BLOCK_STONE_BREAK, 3, 1); 
				   } else {
					   if(!(event.getHitEntity() instanceof LivingEntity)) {
						   return;
					   }
					   if(!(event.getHitEntity() instanceof Damageable)) {
						   return;
					   }
					   Entity entity = event.getHitEntity();
					   ((LivingEntity) entity).setNoDamageTicks(0);
					   ((Damageable) entity).damage(3);
					   world.playSound(entity.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 2, 1); 
				   }
			   } else if(event.getEntity().hasMetadata("tridentBullet")) {
				   Entity entity = event.getEntity();
				   World world = entity.getWorld();
				   world.strikeLightningEffect(entity.getLocation());
				   world.playSound(entity.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 5, 1); 
				   world.createExplosion(entity.getLocation(), 1, false);
				   entity.setMetadata("bulletTouched", new FixedMetadataValue(plugin, 1));
			   }
			   
			   
	           
		   }
		   
		   if(event.getEntity() instanceof Arrow && event.getEntity().hasMetadata("bouncing")) {
			   Entity entity = event.getEntity();
			   if(event.getHitEntity() == null) { 
				      
		           	Vector arrowVector = entity.getVelocity();	
					Location hitLoc = entity.getLocation();
					BlockIterator b = new BlockIterator(hitLoc.getWorld(), hitLoc.toVector(), arrowVector, 0, 3);
				
					Block hitBlock = event.getEntity().getLocation().getBlock();
					Block blockBefore = hitBlock;
					Block nextBlock = b.next();
					
					while (b.hasNext() && nextBlock.getType() == Material.AIR)
					{
						blockBefore = nextBlock;
						nextBlock = b.next();
					}
					
					BlockFace blockFace = nextBlock.getFace(blockBefore);

					if (blockFace != null) {
						
						if(blockFace == BlockFace.UP || blockFace == BlockFace.SELF) {
							entity.remove();
							return;
						}
						if (blockFace == BlockFace.SELF) {
							blockFace = BlockFace.UP;
						}
						
						Vector hitPlain = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
						double dotProduct = arrowVector.dot(hitPlain);
						Vector u = hitPlain.multiply(dotProduct).multiply(2.0);
						float speed = (float) Math.sqrt(Math.pow(arrowVector.getX(), 2) +Math.pow(arrowVector.getY(), 2) + Math.pow(arrowVector.getZ(), 2)); 	;
						
						Arrow newArrow = entity.getWorld().spawnArrow(entity.getLocation(), arrowVector.subtract(u), speed, 12.0F);
						newArrow.setMetadata("bouncing", new FixedMetadataValue(plugin, 1));
						entity.remove();
						
					}
			   } else {
				   if(!(event.getHitEntity() instanceof LivingEntity)) {
					   return;
				   }
				   if(!(event.getHitEntity() instanceof Damageable)) {
					   return;
				   }
				   if(event.getHitEntity() instanceof Arrow || event.getHitEntity() instanceof TippedArrow) {
					   return;
				   }
				   Entity shooted = event.getHitEntity();
				   shooted.setFireTicks(2 * 20);
				   ((Damageable) shooted).damage(2.0);
				   entity.remove();
			   }
			   
		   }
	   }
	   
	   @EventHandler
	   public void antiBreak(EntityDamageByEntityEvent event) {
		   if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();  
			if(player.getInventory().getItemInMainHand() == null) return;
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item.getItemMeta() == null) return;
			ItemMeta meta = item.getItemMeta();
			if(!meta.hasCustomModelData()) {
				return;
			}
			int modelData = meta.getCustomModelData();
			if(modelData == 1232401) {
				event.setCancelled(true);
			}
		   }
		   Entity entity = event.getEntity();
		   if(entity.hasMetadata("fishBomb")) {
				  event.setCancelled(true); 
			  }
	   }
}
