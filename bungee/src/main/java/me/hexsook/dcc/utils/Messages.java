package me.hexsook.dcc.utils;

import hexsook.originext.format.Format;
import hexsook.originext.format.Formatter;
import hexsook.originext.object.Strings;
import me.hexsook.dcc.channel.ChatChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Messages {

    private final DCCConfiguration config;

    public Messages(DCCConfiguration config) {
        this.config = config;
    }

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private String prefix() {
        String prefix = config.getMessages().getString("prefix");
        if(Strings.isNullOrWhite(prefix)) {
            return "";
        }
        return color(prefix);
    }

    public String get(String key, Format... formats) {
        return color(Formatter.format(config.getMessages().getString(key), formats));
    }

    public void send(CommandSender sender, String key, Format... formats) {
        sender.sendMessage(prefix() + get(key, formats));
    }

    public void send(ChatChannel channel, String key, Format... formats) {
        for (ProxiedPlayer connection : channel.getConnections()) {
            connection.sendMessage(prefix() + get(key, formats));
        }
    }
}
