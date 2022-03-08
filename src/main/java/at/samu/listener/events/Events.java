package at.samu.listener.events;

import java.util.UUID;

import at.samu.Main;
import de.gambogamesmc.api.util.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.JSONObject;


public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().getNick().contains(p.getUniqueId())) {
            Main.getInstance().getNick().remove(p);
            String nick = Main.getInstance().getNickbyUUID().get(p.getUniqueId());
            Main.getInstance().getUUIDfromNick().remove(nick);
            Main.getInstance().getNickbyUUID().remove(p.getUniqueId());
        }
        if(p.hasPermission("system.nick")) {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                User user = User.getUser(p.getUniqueId());
                if(user != null) {
                    JSONObject object = user.getProperty("lobby");
                    if(object != null) {
                        if(!object.isNull("nick")) {
                            if(object.getBoolean("nick")) {
                                if(User.getUser(p.getUniqueId()).getProperty("lobby").getBoolean("nick")) {
                                    Main.getInstance().getNick().add(p.getUniqueId());
//					                Backend.getInstance().getNicked().add(p.getUniqueId());
                                    UUID uuid = Main.getInstance().getNickAPI().getRandomUUID();
                                    String nick = Main.getInstance().getNickAPI().getRandomNick();
                                    Main.getInstance().getRealudfromnick().put(nick, p.getUniqueId());
//					                NettyAPI.getInstance().writeJSON("Nick", "addnickname", p.getUniqueId().toString() + ";"+ nick);
                                    Main.getInstance().getNickbyUUID().put(p.getUniqueId(), nick);
                                    Main.getInstance().getUUIDfromNick().put(nick, uuid);
                                    p.sendMessage(Main.getInstance().getPrefix() + "Dein aktueller Nickname ist nun: §6" + nick);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission("system.nick")) {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                if(1 == 1) {
                    Main.getInstance().getPropertyfromuuid().put(p.getUniqueId(), Main.getInstance().getNickAPI().getRandomProperty());
                    Main.getInstance().getTabprefix().put(p.getUniqueId(), "§7");
                }
            });
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().getNick().contains(p.getUniqueId())) {
            String nick = Main.getInstance().getNickbyUUID().get(p.getUniqueId());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();
        if(Main.getInstance().getNick().contains(p.getUniqueId())) {
            String nick = Main.getInstance().getNickbyUUID().get(p.getUniqueId());
        }
    }
}
