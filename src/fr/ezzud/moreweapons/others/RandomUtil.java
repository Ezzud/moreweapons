package fr.ezzud.moreweapons.others;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RandomUtil {
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

		public static Block getRandomBlockAround(Player player, int radius) {
		           
		        Location playerLoc = player.getLocation();
		        double pX = playerLoc.getX();
		        double pY = playerLoc.getY();
		        double pZ = playerLoc.getZ();
		     
		        for (int x = -(radius); x <= radius; x ++)
		        {
		            for (int y = -(radius); y <= radius; y ++)
		            {
		                for (int z = -(radius); z <= radius;)
		                {
		                    Block b = player.getWorld().getBlockAt((int)pX+x, (int)pY+y, (int)pZ+z);
		                    return(b);
		                }
		            }
		        }
				return null;
		    }
	
}
