package at.samu.commands;

import at.samu.Main;
import at.samu.listener.events.PlayerUnnickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd_Nick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {

        if(s instanceof Player) {
            Player p = (Player)s;
            if(p.hasPermission("system.nick")) {
                if(Main.getInstance().getNick().contains(p.getUniqueId())) {
                    Main.getInstance().getNickAPI().unnickPlayer(p);
                    p.sendMessage(Main.getInstance().getPrefix() + "Dein Nickname wurde entfernt");
                    Bukkit.getPluginManager().callEvent(new PlayerUnnickEvent(p));
                    return true;
                } else {
                    p.sendMessage(Main.getInstance().getPrefix() + "Dein Nickname wurde bereits entfernt");
                    return true;
                }
            } else {
                p.sendMessage(Main.getInstance().getNoperm());
                return true;
            }
        }

        return false;
    }

}
