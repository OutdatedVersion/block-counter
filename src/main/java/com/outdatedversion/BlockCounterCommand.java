package com.outdatedversion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.simplexitymc.command.api.Command;
import com.simplexitymc.command.api.CommandHandler;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * In charge of taking in user input
 * then spitting back results
 *
 * @author Ben (OutdatedVersion)
 * @since Sep/19/2016 (6:11 PM)
 */
public class BlockCounterCommand extends Command
{

    /** WorldEdit plugin instance */
    private final WorldEditPlugin worldEdit;

    /** configuration details */
    private final FileConfiguration config;

    /** materials that we don't count whilst summing a chunk */
    private final List<Material> excludedTypes;

    /**
     * @param handler our command center instance
     * @param worldEdit WorldEdit
     * @param config our configuration file
     */
    public BlockCounterCommand(CommandHandler handler, WorldEditPlugin worldEdit, FileConfiguration config)
    {
        super(handler, "blockcounter.command", "blockcount", "countblocks");

        this.worldEdit = worldEdit;
        this.config = config;

        excludedTypes = Lists.newArrayList();

        config.getStringList("results.exclude-of-type")
              .stream()
              .filter(entry -> Util.matchEnum(entry, Material.class) != null)
              .forEach(entry -> excludedTypes.add(Material.valueOf(entry)));
    }

    @Override
    public void execute(Player player, String... args)
    {
        try
        {
            if (args.length >= 1 && matches(args, "info"))
            {
                player.sendMessage(Util.colorize("&c&lBlock Counter &7by OutdatedVersion &6https://twitter.com/OutdatedVersion"));
                return;
            }

            final Selection _selection = worldEdit.getSelection(player);

            if (_selection == null)
            {
                message(player, "messages.no-selection", "player:" + player.getName());
                return;
            }

            if (_selection.getWorld() == null)
            {
                message(player, "messages.no-world", "player:" + player.getName());
                return;
            }

            Map<Material, Integer> _count = Maps.newHashMap();

            _selection.getRegionSelector().getRegion().forEach(blockVector ->
            {
                Block _block = _selection.getWorld().getBlockAt(blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());

                if (!excludedTypes.contains(_block.getType()))
                    _count.put(_block.getType(), _count.getOrDefault(_block.getType(), 0) + 1);
            });

            message(player, "messages.start", "count:" + _count.size(), "player:" + player.getName());

            _count.forEach((material, total) ->
                message(player, "messages.line", "type:" + Util.friendlyEnum(material), "count:" + total));

            message(player, "messages.end", "count:" + _count.size());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message(player, "messages.um-rip", "player:" + player.getName());
        }
    }

    /**
     * Sends the provided {@link Player} a message
     * taking in data from the local {@link #config}
     * file
     *
     * @param player our player
     * @param configLocation path to the configuration data
     * @param args the data requested from the file
     */
    private void message(Player player, String configLocation, String... args)
    {
        player.sendMessage(Util.formatMessage(config.getString(configLocation), args));
    }

}
