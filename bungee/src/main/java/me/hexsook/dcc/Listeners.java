package me.hexsook.dcc;

import hexsook.originext.object.Strings;
import me.hexsook.dcc.channel.ChannelManager;
import me.hexsook.dcc.channel.FixedChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Listeners implements Listener {

    private final DeluxeChatChannels plugin = DeluxeChatChannels.getInstance();

    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
        ChannelManager manager = plugin.getChannelManager();

        if (!e.isCommand()) {
            if (manager.getPlayerChannels().containsKey(player)) {
                e.setCancelled(true);
                manager.messageChannel(player, manager.getPlayerChannels().get(player), e.getMessage());
            }
            return;
        }

        if (!plugin.getConfig().getConfig().getBoolean("fixed-channel-settings.enabled")) {
            return;
        }

        String mainCommand = new StringBuilder(e.getMessage()).delete(0, 1).toString();
        List<String> args = new ArrayList<>();

        if (mainCommand.contains(" ")) {
            List<String> blocks = new ArrayList<>(Arrays.asList(mainCommand.split(" ")));
            mainCommand = blocks.get(0);
            blocks.remove(0);
            for (String block : blocks) {
                if (Strings.isNullOrWhite(block)) {
                    continue;
                }
                args.add(block);
            }
        }

        for (FixedChannel channel : manager.getFixedChannels()) {
            if (!channel.getCommands().stream().map(String::toUpperCase)
                    .collect(Collectors.toList()).contains(mainCommand.toUpperCase())) {
                continue;
            }

            e.setCancelled(true);

            if (args.isEmpty()) {
                if (manager.isInChannel(player, channel)) {
                    manager.leaveChannel(player);
                } else {
                    manager.joinFixedChannel(player, channel);
                }
            } else {
                if (manager.isInChannel(player, channel)) {
                    manager.messageChannel(player, channel, Strings.join(args, 0, " "));
                } else {
                    plugin.getMessages().send(player, "send-message-not-in-channel");
                }
            }
        }
    }
}
