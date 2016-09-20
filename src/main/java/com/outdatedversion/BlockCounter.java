package com.outdatedversion;

import com.simplexitymc.command.api.CommandHandler;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * OutdatedVersion
 * Sep/19/2016 (5:59 PM)
 */

public class BlockCounter extends JavaPlugin
{

    @Override
    public void onEnable()
    {
        if (!(new File(getDataFolder(), "config.yml").exists()))
            saveDefaultConfig();

        WorldEditPlugin _worldEdit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        Validate.notNull(_worldEdit, "We couldn't seem to find a running instance of WorldEdit on your server.");

        CommandHandler _commands = new CommandHandler(this);

        _commands.addCommand(new BlockCounterCmd(_commands, _worldEdit, getConfig()));
    }

}
