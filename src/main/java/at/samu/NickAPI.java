package at.samu;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import at.samu.packet.ReflectionUtils;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.mojang.authlib.properties.Property;

public class NickAPI {

    public List<String> nicknames;
    final public List<String> values;
    final public List<String> signatures;

    public NickAPI() {
        nicknames = new ArrayList<String>();
        values = new ArrayList<String>();
        signatures = new ArrayList<String>();

        for(String s : Main.getInstance().getNicks().getStringList("Nicknames")) {
            if(!nicknames.contains(s)) {
                nicknames.add(s);
            }
        }

        nicknames.add("Hugo");
        nicknames.add("Boss");

        values.add("eyJ0aW1lc3RhbXAiOjE1ODA0NzcxNjcxNzksInByb2ZpbGVJZCI6ImUzZDMyNGRkY2I2ODRjNDliN2EzNTVjOTllNjYxNTQyIiwicHJvZmlsZU5hbWUiOiJTYW11XyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFiNDEzY2VjZjJmMDVmN2IyNGUxZjlhYzUxMDI3YjJjYjljZGRlZTU2MmQ5YzMxNGFhNDQ3ZWU5ZjczNjM0MyJ9fX0");
        values.add("eyJ0aW1lc3RhbXAiOjE1ODA0NzcxNjcxNzksInByb2ZpbGVJZCI6ImUzZDMyNGRkY2I2ODRjNDliN2EzNTVjOTllNjYxNTQyIiwicHJvZmlsZU5hbWUiOiJTYW11XyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFiNDEzY2VjZjJmMDVmN2IyNGUxZjlhYzUxMDI3YjJjYjljZGRlZTU2MmQ5YzMxNGFhNDQ3ZWU5ZjczNjM0MyJ9fX0");

        signatures.add("f79djdB9Ihvgj3pZ+MLiTI0fP9uQwwFLwUqMKv4qW1mOo/AZNWuaZ7UTutw1c9rJOVatYYGXk494+U0kjJYIETjdlufFCfJ92GZk3meL+2vUGWeLqYc+1LWQjHbWvYRQJpJXuoPVHau9G7vAk2eEl+JLN44ddwuV4l/oXAW0zsy9kuF0DchAIczv8vftZ205tUGimpt2Hye1kKj2/O9AN1GqIpahrsZ623CltZaU3ffTqY0pqqNw+aj/QtocPLdaURUn7MlSQ5aw5R494ASDRi98ej4Pyn/Itd1POENSBq8NmqO0uxf8i7gfoPbGLMND3k6AbjOwXOcZ66LBv0xhv38Sym8dpDXFa+x0yEN886MPe8DEcH0L0Lhh7lnfVLTZPlROSmBe4mvI39WhHN/yc7xGnYEyoKxKBKxn1T0G9RdicRhgm0FyNe6RGbMM289L0rWqbbrG5vCuPim0lNeF/AyxRmBb8RHPuCVp7n8QPGrbPMxGVVhQcXMFNRzB9SG/HV4AdbE8l1aiuxSu/zm5qYcWe9W0LLNjVOl6Hx7b9Hh4ojv0U8bk59vq7LgAGp8GU93TZdg1tHgeYR2tgYxZwX5RbSDtNdhvzOVKnhGT2kOkbCAbQhUIfprnNRxBd/ekk3cQ+IUl0t/ZKHZsgNdE8T0XfQR5Nj2sk5eYVjZ1CRw=");
        signatures.add("f79djdB9Ihvgj3pZ+MLiTI0fP9uQwwFLwUqMKv4qW1mOo/AZNWuaZ7UTutw1c9rJOVatYYGXk494+U0kjJYIETjdlufFCfJ92GZk3meL+2vUGWeLqYc+1LWQjHbWvYRQJpJXuoPVHau9G7vAk2eEl+JLN44ddwuV4l/oXAW0zsy9kuF0DchAIczv8vftZ205tUGimpt2Hye1kKj2/O9AN1GqIpahrsZ623CltZaU3ffTqY0pqqNw+aj/QtocPLdaURUn7MlSQ5aw5R494ASDRi98ej4Pyn/Itd1POENSBq8NmqO0uxf8i7gfoPbGLMND3k6AbjOwXOcZ66LBv0xhv38Sym8dpDXFa+x0yEN886MPe8DEcH0L0Lhh7lnfVLTZPlROSmBe4mvI39WhHN/yc7xGnYEyoKxKBKxn1T0G9RdicRhgm0FyNe6RGbMM289L0rWqbbrG5vCuPim0lNeF/AyxRmBb8RHPuCVp7n8QPGrbPMxGVVhQcXMFNRzB9SG/HV4AdbE8l1aiuxSu/zm5qYcWe9W0LLNjVOl6Hx7b9Hh4ojv0U8bk59vq7LgAGp8GU93TZdg1tHgeYR2tgYxZwX5RbSDtNdhvzOVKnhGT2kOkbCAbQhUIfprnNRxBd/ekk3cQ+IUl0t/ZKHZsgNdE8T0XfQR5Nj2sk5eYVjZ1CRw=");
    }

    public String getRandomNick() {
        String nick = nicknames.get((nicknames.size() == 1 ? 0 : new Random().nextInt(nicknames.size()-1)));
        if(nick != null) {
            if(nick.length() > 16) {
                nick = nick.substring(0, 16);
            }
        }
        return nick;
    }



    public Property getRandomProperty() {
        return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzMxMTk4MjUzNywKICAicHJvZmlsZUlkIiA6ICJlM2QzMjRkZGNiNjg0YzQ5YjdhMzU1Yzk5ZTY2MTU0MiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTYW11XyIsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83NWYwMjA3YzM5YjJlOWMzODE4ODFmYjVmNGQ0MDVhZDRlNzY0ZWRhMTdhMjM2YTNmYmUyNmVlMzI1MTc4NmI0IgogICAgfQogIH0KfQ", "f79djdB9Ihvgj3pZ+MLiTI0fP9uQwwFLwUqMKv4qW1mOo/AZNWuaZ7UTutw1c9rJOVatYYGXk494+U0kjJYIETjdlufFCfJ92GZk3meL+2vUGWeLqYc+1LWQjHbWvYRQJpJXuoPVHau9G7vAk2eEl+JLN44ddwuV4l/oXAW0zsy9kuF0DchAIczv8vftZ205tUGimpt2Hye1kKj2/O9AN1GqIpahrsZ623CltZaU3ffTqY0pqqNw+aj/QtocPLdaURUn7MlSQ5aw5R494ASDRi98ej4Pyn/Itd1POENSBq8NmqO0uxf8i7gfoPbGLMND3k6AbjOwXOcZ66LBv0xhv38Sym8dpDXFa+x0yEN886MPe8DEcH0L0Lhh7lnfVLTZPlROSmBe4mvI39WhHN/yc7xGnYEyoKxKBKxn1T0G9RdicRhgm0FyNe6RGbMM289L0rWqbbrG5vCuPim0lNeF/AyxRmBb8RHPuCVp7n8QPGrbPMxGVVhQcXMFNRzB9SG/HV4AdbE8l1aiuxSu/zm5qYcWe9W0LLNjVOl6Hx7b9Hh4ojv0U8bk59vq7LgAGp8GU93TZdg1tHgeYR2tgYxZwX5RbSDtNdhvzOVKnhGT2kOkbCAbQhUIfprnNRxBd/ekk3cQ+IUl0t/ZKHZsgNdE8T0XfQR5Nj2sk5eYVjZ1CRw=");
    }

    public boolean isNicked(UUID uuid) {
        return Main.getInstance().getNick().contains(uuid);
    }

    public String getNickname(UUID uuid) {
        return Main.getInstance().getNickbyUUID().get(uuid);
    }

    public boolean isNickname(String s) {
        boolean b = false;
        for(String nick : Main.getInstance().getUUIDfromNick().keySet()) {
            if(nick.toLowerCase().equalsIgnoreCase(s.toLowerCase())) {
                b = true;
            }
        }
        return b;
    }

    public UUID getRandomUUID() {
        List<String> list = Main.getInstance().getNicks().getStringList("Nicks");
        return UUID.fromString(list.get(new Random().nextInt(list.size()-1)));
    }

    public void changeTabPrefix(Player p) {
        EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();
        sendPlayerInfo(entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e);

    }

    public void unnickPlayer(Player p) {
        String nick = Main.getInstance().getNickbyUUID().get(p.getUniqueId());
        destroyPlayerEntity(p.getEntityId());

        EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();

        sendPlayerInfo(entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e);

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Main.getInstance().getNick().remove(p.getUniqueId());
            String nicks = Main.getInstance().getNickbyUUID().get(p.getUniqueId());
            Main.getInstance().getNickbyUUID().remove(p.getUniqueId());
            Main.getInstance().getUUIDfromNick().remove(nicks);

            sendPlayerInfo(entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a);
            spawnPlayerEntity(p);
        });
    }

    public void unnickPlayerForPlayer(Player p, Player t) {
        destroyPlayerEntity(p.getEntityId(), t);

        EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();

        sendPlayerInfo(t, entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e);

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            sendPlayerInfo(t, entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a);
            spawnPlayerEntity(p, t);
        });
    }

    private void sendPlayerInfo(EntityPlayer entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction playerInfoAction) {
        PacketPlayOutPlayerInfo playerInfoPacket = new PacketPlayOutPlayerInfo(playerInfoAction, entityPlayer);

        Bukkit.getOnlinePlayers().stream().filter(player -> !player.getUniqueId().equals(entityPlayer.getBukkitEntity().getUniqueId())).map(player -> {
            ReflectionUtils.sendPacket(player, playerInfoPacket);
            return player;
        });
    }

    private void sendPlayerInfo(Player t, EntityPlayer entityPlayer, PacketPlayOutPlayerInfo.EnumPlayerInfoAction playerInfoAction) {
        PacketPlayOutPlayerInfo playerInfoPacket = new PacketPlayOutPlayerInfo(playerInfoAction, entityPlayer);

        if(t.getUniqueId() == entityPlayer.getBukkitEntity().getUniqueId()) {
            return;
        }

        ReflectionUtils.sendPacket(t, playerInfoPacket);
    }

    private void spawnPlayerEntity(Player player) {
        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager()
                .createPacketConstructor(PacketType.Play.Server.NAMED_ENTITY_SPAWN, player).createPacket(player);

        Bukkit.getOnlinePlayers().forEach(other -> {
            if(other.equals(player)) {
                return;
            }

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(other, spawnPacket);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void spawnPlayerEntity(Player player, Player target) {
        PacketContainer spawnPacket = ProtocolLibrary.getProtocolManager()
                .createPacketConstructor(PacketType.Play.Server.NAMED_ENTITY_SPAWN, player).createPacket(player);

        if(target.equals(player)) {
            return;
        }

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(target, spawnPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private void destroyPlayerEntity(int entityId) {
        PacketContainer destroyPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

        destroyPacket.getIntegerArrays().write(0, new int[] {entityId});

        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.getEntityId() == entityId) {
                return;
            }

            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, destroyPacket);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private void destroyPlayerEntity(int entityId, Player target) {
        PacketContainer destroyPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);

        destroyPacket.getIntegerArrays().write(0, new int[] {entityId});

        if(target.getEntityId() == entityId) {
            return;
        }

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(target, destroyPacket);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
