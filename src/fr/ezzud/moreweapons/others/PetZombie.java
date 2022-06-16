package fr.ezzud.moreweapons.others;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityMonster;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.World;

public class PetZombie extends EntityZombie {
	
	
	public PetZombie(World world, Location location) {
        super(world);
        
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setCustomNameVisible(true);

        this.goalSelector.a(1, new PathfinderGoalPet(this, 1.0, 25));
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2,new PathfinderGoalLookAtPlayer(this,EntityHuman.class,8.0F));
        this.goalSelector.a(4,new PathfinderGoalAvoidTarget<PetZombie>(this,PetZombie.class,8.0F,1F,1F));
        
        this.targetSelector.a(0,new PathfinderGoalNearestAttackableTarget<EntityMonster>(this,EntityMonster.class,false));
        this.targetSelector.a(1,new PathfinderGoalLeapAtTarget(this,0.1F));


        this.getWorld().addEntity(this);
    }

	public void setOwner(Player player) {
		this.setGoalTarget((EntityLiving)((CraftPlayer)player).getHandle(), TargetReason.CUSTOM, false);
	}
	
	public void setDisplayName(String name) {
		this.setCustomName(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', name)));
	}
	
	public void killEntity(Player player) {
		this.die();
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
	}
	
	
	
}
