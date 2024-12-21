package me.hexsook.dcc;

import me.hexsook.dcc.channel.ChannelManager;
import me.hexsook.dcc.utils.DCCConfiguration;
import me.hexsook.dcc.utils.InterruptStartupSignal;
import me.hexsook.dcc.utils.Messages;
import net.md_5.bungee.api.plugin.Plugin;

public class DeluxeChatChannels extends Plugin {

    private static DeluxeChatChannels instance;

    private DCCConfiguration config;
    private Messages messages;
    private ChannelManager channelManager;

    @Override
    public void onEnable() {
        instance = this;

        try {
            config = new DCCConfiguration(getDataFolder());;
            try {
                config.load();
            } catch (Exception e) {
                throw new InterruptStartupSignal("Failed to load config");
            }

            messages = new Messages(config);
            channelManager = new ChannelManager(config);

            channelManager.load();

            getProxy().getPluginManager().registerCommand(this, new MainCommand());
            getProxy().getPluginManager().registerListener(this, new Listeners());
        } catch (InterruptStartupSignal e) {
            getLogger().severe("Error while starting up: " + e.getMessage());
            getLogger().severe("Disabling plugin...");
            onDisable();
        }
    }

    @Override
    public void onDisable() {

    }

    public static DeluxeChatChannels getInstance() {
        return instance;
    }

    public DCCConfiguration getConfig() {
        return config;
    }

    public Messages getMessages() {
        return messages;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }
}
