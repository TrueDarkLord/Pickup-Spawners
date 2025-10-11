package me.truedarklord.pickupspawners.events;

import me.truedarklord.pickupspawners.PickupSpawners;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;
import java.util.List;

public class BlockBreak implements Listener {

    public BlockBreak(PickupSpawners plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.isDropItems()) return; // Other plugin is handling it.
        BlockState block = event.getBlock().getState();

        if (block.getType() != Material.SPAWNER) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!player.hasPermission("pickupspawners.break")) return;

        if (!tool.getType().toString().endsWith("PICKAXE")) return;
        if (!(tool.hasItemMeta() && tool.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) return;

        event.setExpToDrop(0); // Prevent infinite xp.
        event.setDropItems(false); // Stop other plugins from handling it.

        dropItems(event.getBlock(), player, getSpawnerItem(block));

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
    
    private void dropItems(Block block, Player player, ItemStack item) {

        List<Item> drops = new ArrayList<>();

        drops.add(block.getWorld().dropItemNaturally(block.getLocation(), item));

        BlockDropItemEvent event = new BlockDropItemEvent(block, block.getState(), player, drops);

        if (event.callEvent()) return;

        event.getItems().forEach(Entity::remove);

    }


}
