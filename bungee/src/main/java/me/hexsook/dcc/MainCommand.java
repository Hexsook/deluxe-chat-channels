package me.hexsook.dcc;

import hexsook.originext.format.presets.PlaceholderFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MainCommand extends Command {

    private final DeluxeChatChannels plugin = DeluxeChatChannels.getInstance();

    public MainCommand() {
        super("deluxechatchannels", "", "dcc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("deluxechatchannels.admin")) {
            plugin.getMessages().send(sender, "command-no-permission");
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            long start = System.currentTimeMillis();
            plugin.getConfig().getConfig().reload();
            plugin.getConfig().getMessages().reload();
            plugin.getConfig().getChannels().reload();
            plugin.getChannelManager().load();
            plugin.getMessages().send(sender, "plugin-reloaded", PlaceholderFormat.builder()
                    .placeholder("<@a>")
                    .append("time", System.currentTimeMillis() - start)
                    .build());
            return;
        }

        plugin.getMessages().send(sender, "command-usage", PlaceholderFormat.builder()
                .placeholder("<@a>")
                .append("usage", "deluxechatchannels reload")
                .build());
    }
}
