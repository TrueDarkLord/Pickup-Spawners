package me.truedarklord.pickupspawners;

import me.truedarklord.pickupspawners.events.BlockBreak;
import me.truedarklord.pickupspawners.events.BlockPlace;
import org.bukkit.plugin.java.JavaPlugin;

public final class PickupSpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        new BlockBreak(this);
        new BlockPlace(this);
    }

}
