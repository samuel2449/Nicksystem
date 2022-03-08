package at.samu.listener.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mojang.authlib.properties.Property;

public class PlayerNickEvent extends Event{

    public static HandlerList handlers = new HandlerList();

    Player player;
    String nick;
    Property pr;

    public PlayerNickEvent(Player p, String nick, Property pr) {
        this.player = p;
        this.nick = nick;
        this.pr = pr;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getNickname() {
        return nick;
    }

    public Property getProperty() {
        return pr;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
