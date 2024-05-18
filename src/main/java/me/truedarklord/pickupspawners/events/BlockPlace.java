package me.truedarklord.pickupspawners.events;

import me.truedarklord.pickupspawners.PickupSpawners;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;


public class BlockPlace implements Listener {

    public BlockPlace(PickupSpawners plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void placeSpawner(BlockPlaceEvent event) {
        BlockState block = event.getBlock().getState();

        if (block.getType() != Material.SPAWNER) return;

        setSpawnerItem(event.getItemInHand(), block);

    }

    private void setSpawnerItem(ItemStack spawnerItem, BlockState blockState) {

        BlockStateMeta spawnerMeta = (BlockStateMeta) spawnerItem.getItemMeta();
        CreatureSpawner spawnerState = (CreatureSpawner) spawnerMeta.getBlockState();
        EntityType spawnerType = spawnerState.getSpawnedType();

        CreatureSpawner spawner = (CreatureSpawner) blockState;
        spawner.setSpawnedType(spawnerType);
        spawner.update();
    }

}
