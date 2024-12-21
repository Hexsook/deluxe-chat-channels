package me.hexsook.dcc.channel;

public class TempChannel extends ChatChannel {

    private final boolean pub;

    public TempChannel(String name, boolean pub, String displayName, int connectionLimit) {
        super(name, displayName, connectionLimit);
        this.pub = pub;
    }

    public boolean isPublic() {
        return pub;
    }
}
