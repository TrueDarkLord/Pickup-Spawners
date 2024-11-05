package me.truedarklord.pickupspawners;

import me.truedarklord.pickupspawners.events.BlockBreak;
import me.truedarklord.pickupspawners.events.BlockPlace;
import me.truedarklord.pickupspawners.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class PickupSpawners extends JavaPlugin {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 23819);

        new BlockBreak(this);
        new BlockPlace(this);
    }

}
