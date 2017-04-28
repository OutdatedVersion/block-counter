package com.outdatedversion;

import com.simplexitymc.command.api.CommandHandler;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Ben (OutdatedVersion)
 * @since Sep/19/2016 (5:59 PM)
 */
public class BlockCounter extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        final WorldEditPlugin _worldEdit = checkNotNull(JavaPlugin.getPlugin(WorldEditPlugin.class),
                                            "We couldn't seem to find a running instance of WorldEdit on your server.");

        final CommandHandler _commands = new CommandHandler(this);

        _commands.addCommand(new BlockCounterCommand(_commands, _worldEdit, getConfig()));
    }

}
