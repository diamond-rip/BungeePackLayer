package io.th0rgal.packsmanager.velocity.listeners;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.proxy.protocol.packet.ResourcePackRequest;

import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import io.th0rgal.packsmanager.velocity.BungeePackLayerVelocityPlugin;
import io.th0rgal.packsmanager.velocity.util.Common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ResourcePackRequestListener extends AbstractPacketListener<ResourcePackRequest> {

    private final BungeePackLayerVelocityPlugin plugin;
    private final Map<UUID, String> map = new HashMap<>();

    public ResourcePackRequestListener(BungeePackLayerVelocityPlugin plugin) {
        super(ResourcePackRequest.class, Direction.DOWNSTREAM, 0);
        this.plugin = plugin;
    }

    @Override
    public void packetReceive(PacketReceiveEvent<ResourcePackRequest> event) {
        final ResourcePackRequest resourcePackRequest = event.packet();
        final ProtocolizePlayer pPlayer = event.player();
        final UUID uuid = pPlayer.uniqueId();
        final Optional<Player> optional = plugin.getProxyServer().getPlayer(uuid);
        if (map.containsKey(uuid) && map.get(uuid).equals(resourcePackRequest.getHash())) {
            event.cancelled(true);
            optional.ifPresent(player -> Common.sendMessage(player, "<gray><italic>跳過資源包安裝（您已經載入它）"));
            return;
        }
        map.put(uuid, resourcePackRequest.getHash());
    }

    @Override
    public void packetSend(PacketSendEvent<ResourcePackRequest> event) {

    }

    public void removePlayer(final UUID uuid) {
        map.remove(uuid);
    }
}
