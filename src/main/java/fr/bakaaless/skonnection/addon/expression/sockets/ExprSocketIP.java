package fr.bakaaless.skonnection.addon.expression.sockets;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.bakaaless.skonnection.addon.type.sockets.AdaptSocket;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Socket IP")
@Description("This expression allows you to get a socket's ip.")
@Examples({"on socket receive data:",
        "\tif ip of event-socket is not \"127.0.0.1\":",
        "\t\tbroadcast \"RECEIVE DATA FROM A THIRD PARTY : %event-text%\""})
@Since("1.0.0")
public class ExprSocketIP extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprSocketIP.class, String.class, ExpressionType.SIMPLE, "[get] ip of socket %socket%", "[get] socket %socket%'s ip");
    }

    private Expression<AdaptSocket> socket;

    @Override
    public boolean init(final Expression<?> @NotNull [] exprs, final int matchedPattern, final @NotNull Kleenean isDelayed, final SkriptParser.@NotNull ParseResult parseResult) {
        this.socket = (Expression<AdaptSocket>) exprs[0];
        return true;
    }

    @Override
    protected String[] get(final @NotNull Event e) {
        if (this.socket == null || this.socket.getSingle(e) == null)
            return new String[0];
        if (!this.socket.getSingle(e).isConnected())
            return new String[0];
        return new String[] {socket.getSingle(e).getIp()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(final @Nullable Event e, final boolean debug) {
        return "get socket's ip";
    }

}
