package me.hexsook.dcc.channel;

import hexsook.originext.config.Configuration;
import hexsook.originext.format.presets.PlaceholderFormat;
import hexsook.originext.object.Strings;
import me.hexsook.dcc.DeluxeChatChannels;
import me.hexsook.dcc.utils.DCCConfiguration;
import me.hexsook.dcc.utils.Messages;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelManager {

    private final DeluxeChatChannels plugin = DeluxeChatChannels.getInstance();
    private final Messages messages = plugin.getMessages();

    private final List<FixedChannel> fixedChannels = new ArrayList<>();
    private final Map<ProxiedPlayer, ChatChannel> playerChannels = new HashMap<>();

    private final DCCConfiguration config;

    public ChannelManager(DCCConfiguration config) {
        this.config = config;
    }

    public void load() {
        playerChannels.keySet().forEach(player -> messages.send(player, "channel-reload-disband"));

        fixedChannels.clear();
        playerChannels.clear();

        for (String key : config.getChannels().getKeys()) {
            Configuration sec = config.getChannels().getSection(key);
            fixedChannels.add(new FixedChannel(
                    key,
                    sec.getString("permission"),
                    messages.color(sec.getString("display-name")),
                    sec.getStringList("commands"),
                    sec.getInteger("connection-limit")
            ));
        }
    }
    
    public void messageChannel(ProxiedPlayer player, ChatChannel channel, String message) {
        for (ProxiedPlayer connection : channel.getConnections()) {
            connection.sendMessage(messages.color(plugin.getConfig().getConfig().getString("chat.chat-format")
                    .replace("<channel>", channel.getDisplayName())
                    .replace("<player>", player.getDisplayName())
                    .replace("<message>", message)));
        }
    }
    
    public void joinFixedChannel(ProxiedPlayer player, FixedChannel channel) {
        if (!Strings.isNullOrWhite(channel.getPermission()) && !player.hasPermission(channel.getPermission())) {
            messages.send(player, "channel-no-permission");
            return;
        }
        joinChannel(player, channel);
    }

    public void joinChannel(ProxiedPlayer player, ChatChannel channel) {
        if (channel.getConnections().size() >= channel.getConnectionLimit()) {
            messages.send(player, "connection-limit-reached");
            return;
        }
        if (playerChannels.containsKey(player)) {
            leaveChannel(player);
            messages.send(player, "join-other-channel-message");
        }
        playerChannels.put(player, channel);
        messages.send(playerChannels.get(player), "player-join-channel",
                PlaceholderFormat.builder()
                        .placeholder("<@a>")
                        .append("player", player.getDisplayName())
                        .build()
        );
        channel.addConnection(player);
        messages.send(player, "join-channel-message",
                PlaceholderFormat.builder()
                        .placeholder("<@a>")
                        .append("channel", channel.getDisplayName())
                        .build());
    }

    public void leaveChannel(ProxiedPlayer player) {
        if (!playerChannels.containsKey(player)) {
            return;
        }
        
        ChatChannel channel = playerChannels.get(player);
        channel.removeConnection(player);
        messages.send(playerChannels.get(player), "player-leave-channel",
                PlaceholderFormat.builder()
                        .placeholder("<@a>")
                        .append("player", player.getDisplayName())
                        .build()
        );
        messages.send(player, "leave-channel-message",
                PlaceholderFormat.builder()
                        .placeholder("<@a>")
                        .append("channel", channel.getDisplayName())
                        .build());
        playerChannels.remove(player);
    }

    public boolean isInChannel(ProxiedPlayer player, ChatChannel channel) {
        return playerChannels.containsKey(player) && playerChannels.get(player).equals(channel);
    }
    
    public List<FixedChannel> getFixedChannels() {
        return fixedChannels;
    }

    public Map<ProxiedPlayer, ChatChannel> getPlayerChannels() {
        return playerChannels;
    }
}
