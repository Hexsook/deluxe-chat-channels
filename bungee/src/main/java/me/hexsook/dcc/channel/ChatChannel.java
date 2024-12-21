package me.hexsook.dcc.channel;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class ChatChannel {

    private final String name;
    private final String displayName;
    private final List<ProxiedPlayer> connections = new ArrayList<>();
    private final int connectionLimit;

    public ChatChannel(String name, String displayName, int connectionLimit) {
        this.name = name;
        this.displayName = displayName;
        this.connectionLimit = connectionLimit;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public List<ProxiedPlayer> getConnections() {
        return connections;
    }

    public void addConnection(ProxiedPlayer player) {
        connections.add(player);
    }

    public void removeConnection(ProxiedPlayer player) {
        connections.remove(player);
    }

    public int getConnectionLimit() {
        return connectionLimit;
    }
}
