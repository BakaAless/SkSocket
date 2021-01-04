package fr.bakaaless.sksocket.addon.effect;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import fr.bakaaless.sksocket.plugin.SkSocket;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffUnregisterChannel extends Effect {

    static {
        Skript.registerEffect(EffSendPluginMessage.class, "register channel %string%");
    }

    private Expression<String> channel;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        this.channel = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    protected void execute(final Event e) {
        final String channel = this.channel.getSingle(e);
        if (channel == null)
            return;
        if (SkSocket.getMessenger().isOutgoingChannelRegistered(SkSocket.get(), channel) || SkSocket.getMessenger().isIncomingChannelRegistered(SkSocket.get(), channel))
            return;
        SkSocket.getMessenger().unregisterOutgoingPluginChannel(SkSocket.get(), channel);
        SkSocket.getMessenger().unregisterIncomingPluginChannel(SkSocket.get(), channel);
    }

    @Override
    public String toString(final @Nullable Event e, final boolean debug) {
        return "unregister a channel";
    }

}