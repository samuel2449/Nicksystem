package at.samu.commands;

import at.samu.Main;
import de.gambogamesmc.api.util.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd_Nicks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
        if(s instanceof Player) {
            if(s.hasPermission("system.nick.see")) {
                Player p = (Player)s;
                if(Main.getInstance().getNick().size() == 0) {
                    p.sendMessage(Main.getInstance().getPrefix() + "Derzeit ist keiner in dieser Runde genickt");
                    return true;
                }
                p.sendMessage(Main.getInstance().getPrefix() + "Genickte Spieler (" +  Main.getInstance().getNick().size() + "):");
                Main.getInstance().getNick().forEach(nicked_player -> {
                    p.sendMessage(" §8▶ " + User.getUser(nicked_player).getDisplayName() + " §7(§5" + Main.getInstance().getNickAPI().getNickname(nicked_player) + "§7)");
                });

                return true;
            } else {
                s.sendMessage(Main.getInstance().getNoperm());
                return true;
            }
        }
        return false;
    }

}
