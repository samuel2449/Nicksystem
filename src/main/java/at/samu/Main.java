package at.samu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import at.samu.commands.Cmd_Nick;
import at.samu.commands.Cmd_Nicks;
import at.samu.listener.events.Events;
import com.comphenix.protocol.ProtocolLibrary;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public ArrayList<UUID> nick;
    public NickAPI nickapi;
    public HashMap<UUID, String> nickbyuuid;
    public HashMap<String, UUID> uuidfromnick;
    public HashMap<UUID, Property> propertyfromuuid;
    public HashMap<String, UUID> realudfromnick;
    public HashMap<UUID, String> tabprefix;
    public String prefix = "§8[§5Nick§8] §7";
    public String noperm = prefix + "Du hast keine Rechte dafür";
    public File file = new File("settings/Nicks.yml");
    public FileConfiguration nicks = YamlConfiguration.loadConfiguration(file);

    @Override
    public void onEnable() {
        insert();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Events(), this);
        getCommand("nick").setExecutor(new Cmd_Nick());
        getCommand("nicks").setExecutor(new Cmd_Nicks());
        Bukkit.getConsoleSender().sendMessage(prefix + "Das Plugin wurde geladen");
    }

    public void insert() {
        instance = this;
        setNick(new ArrayList<>());
        new Load(this);
        setNickapi(new NickAPI());
        setNickbyuuid(new HashMap<>());
        setUUIDfromNick(new HashMap<>());
        setPropertyfromuuid(new HashMap<>());
        setRealudfromnick(new HashMap<>());
        setTabprefix(new HashMap<>());
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    public static Main getInstance() {
        return instance;
    }

    public ArrayList<UUID> getNick() {
        return nick;
    }

    public void setNick(ArrayList<UUID> nick) {
        this.nick = nick;
    }

    public NickAPI getNickAPI() {
        return nickapi;
    }

    public HashMap<UUID, String> getNickbyUUID() {
        return nickbyuuid;
    }

    public void setNickapi(NickAPI nickapi) {
        this.nickapi = nickapi;
    }

    public void setNickbyuuid(HashMap<UUID, String> nickbyuuid) {
        this.nickbyuuid = nickbyuuid;
    }

    public HashMap<String, UUID> getUUIDfromNick() {
        return uuidfromnick;
    }

    public void setUUIDfromNick(HashMap<String, UUID> uuidfromnick) {
        this.uuidfromnick = uuidfromnick;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNoperm() {
        return noperm;
    }

    public HashMap<UUID, Property> getPropertyfromuuid() {
        return propertyfromuuid;
    }

    public void setPropertyfromuuid(HashMap<UUID, Property> propertyfromuuid) {
        this.propertyfromuuid = propertyfromuuid;
    }

    public FileConfiguration getNicks() {
        return nicks;
    }

    public HashMap<String, UUID> getRealudfromnick() {
        return realudfromnick;
    }

    public void setRealudfromnick(HashMap<String, UUID> realudfromnick) {
        this.realudfromnick = realudfromnick;
    }

    public HashMap<UUID, String> getTabprefix() {
        return tabprefix;
    }

    public void setTabprefix(HashMap<UUID, String> tabprefix) {
        this.tabprefix = tabprefix;
    }
}
