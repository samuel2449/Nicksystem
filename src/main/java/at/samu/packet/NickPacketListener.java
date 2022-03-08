package at.samu.packet;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import at.samu.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;

public class NickPacketListener extends PacketAdapter {

    private static final boolean SHOW_NICKED_COSMETICS = false;

    public NickPacketListener(Plugin plugin) {
        super(new PacketAdapter.AdapterParameteters().plugin(plugin).gamePhase(GamePhase.PLAYING).serverSide()
                .types(Server.NAMED_ENTITY_SPAWN, Server.PLAYER_INFO, Server.SCOREBOARD_TEAM));
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player receiver = event.getPlayer();
        PacketType packetType = event.getPacketType();
        PacketContainer packet = event.getPacket();

        if(packetType.equals(Server.NAMED_ENTITY_SPAWN)) {
            handleNamedEntitySpawn(receiver, packet);
        } else if(packetType.equals(Server.PLAYER_INFO)) {
            handlePlayerInfo(receiver, packet);
        } else if(packetType.equals(Server.SCOREBOARD_TEAM)) {
            handleScoreboardTeam(receiver, packet);
        }
    }

    private void handlePlayerInfo(Player receiver, PacketContainer packet) {
        List<PlayerInfoData> dataList = packet.getPlayerInfoDataLists().read(0);
        List<PlayerInfoData> nickedData = dataList.stream().filter(playerInfoData -> isNicked(playerInfoData.getProfile().getUUID())).collect(Collectors.toList());

        nickedData.forEach(playerInfoData -> {
            UUID uniqueId = playerInfoData.getProfile().getUUID();
            Player player = Bukkit.getPlayer(uniqueId);

            if(receiver.equals(player) || receiver.hasPermission("system.nick.see")) {
                return;
            }

            dataList.remove(playerInfoData);
            dataList.add(new PlayerInfoData(generateProfile(uniqueId), player == null ? 0 : player.getPing(),
                    EnumWrappers.NativeGameMode.fromBukkit(player == null ? GameMode.SURVIVAL : player.getGameMode()),
                    modifyName(player, uniqueId, playerInfoData.getDisplayName())));
        });

        packet.getPlayerInfoDataLists().write(0, dataList);
    }

    private void handleNamedEntitySpawn(Player receiver, PacketContainer packet) {
        UUID uniqueId = packet.getUUIDs().read(0);

        if(isNicked(uniqueId) && !(receiver.getUniqueId().equals(uniqueId) || receiver.hasPermission("system.nick.see"))) {
            packet.getUUIDs().write(0, generateUUID(fetchNickname(uniqueId)));
        }
    }

    @SuppressWarnings("unchecked")
    private void handleScoreboardTeam(Player receiver, PacketContainer packet) {
        Collection<String> entries = new CopyOnWriteArrayList<>();

        packet.getSpecificModifier(Collection.class).read(0).forEach(o -> entries.add(String.valueOf(o)));

        entries.stream().filter(s -> Bukkit.getPlayerExact(s) != null).map(Bukkit::getPlayerExact)
                .filter(player -> isNicked(player.getUniqueId()) && !(receiver.equals(player) || receiver.hasPermission("system.nick.see")))
                .forEach(player -> {
                    entries.remove(player.getName());
                    entries.add(fetchNickname(player.getUniqueId()));
                });

        packet.getSpecificModifier(Collection.class).write(0, entries);
    }

    private WrappedChatComponent modifyName(Player player, UUID uniqueId, WrappedChatComponent current) {
        if(current == null) {
            return null;
        }

        String nickname = fetchNickname(uniqueId);

        if(player == null) {
            return WrappedChatComponent.fromText(nickname);
        }

        current.setJson(current.getJson().replace(player.getName(), nickname));

        return current;
    }

    private WrappedGameProfile generateProfile(UUID uniqueId) {
        String nickname = fetchNickname(uniqueId);
        UUID uuid = generateUUID(nickname);
        GameProfile gameProfile = new GameProfile(uuid, nickname);
        gameProfile.getProperties().put("textures", Main.getInstance().getPropertyfromuuid().get(uniqueId));

        return WrappedGameProfile.fromHandle(gameProfile);
    }

    private UUID generateUUID(String nickname) {
        return SHOW_NICKED_COSMETICS ? uuidFromNickname(nickname) : UUID.nameUUIDFromBytes(("nicked:" + nickname)
                .getBytes(StandardCharsets.UTF_8));
    }

    private boolean isNicked(UUID uniqueId) {
        return Main.getInstance().getNick().contains(uniqueId);
    }

    private String fetchNickname(UUID uniqueId) {
        return Main.getInstance().getNickbyUUID().get(uniqueId);
    }

    private UUID uuidFromNickname(String nickname) {
        return Main.getInstance().getUUIDfromNick().get(nickname);
    }
}

