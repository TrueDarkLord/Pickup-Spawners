package me.truedarklord.pickupspawners.events;

import me.truedarklord.pickupspawners.PickupSpawners;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class BlockBreak implements Listener {

    public BlockBreak(PickupSpawners plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        BlockState block = event.getBlock().getState();

        if (block.getType() != Material.SPAWNER) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!tool.getType().toString().endsWith("PICKAXE")) return;
        if (!(tool.hasItemMeta() && tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) return;

        block.getWorld().dropItemNaturally(block.getLocation(), getSpawnerItem(block));

    }

    private ItemStack getSpawnerItem(BlockState block) {

        ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
        BlockStateMeta spawnerMeta = (BlockStateMeta) spawnerItem.getItemMeta();
        CreatureSpawner spawnerState = (CreatureSpawner) spawnerMeta.getBlockState();

        spawnerState.setSpawnedType(((CreatureSpawner) block).getSpawnedType());

        spawnerMeta.setBlockState(spawnerState);
        spawnerItem.setItemMeta(spawnerMeta);

        return spawnerItem;

    }


}
