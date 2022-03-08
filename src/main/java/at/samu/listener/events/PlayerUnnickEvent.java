package at.samu.listener.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUnnickEvent extends Event {

    public static HandlerList handlers = new HandlerList();

    Player player;

    public PlayerUnnickEvent(Player p) {
        this.player = p;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }

}
