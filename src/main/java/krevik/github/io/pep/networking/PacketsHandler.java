package krevik.github.io.pep.networking;

import krevik.github.io.pep.networking.messages.PacketClientOpenGuiProgrammableEnderPearl;
import krevik.github.io.pep.networking.messages.PacketServerGivePlayerEditedPearl;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

/**
 * thank you @williewillus
 */
public final class PacketsHandler
{
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation("pep", "channel_pep_"+PROTOCOL_VERSION))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register()
    {
        int id = 0;
        HANDLER.registerMessage(id++, PacketClientOpenGuiProgrammableEnderPearl.class, PacketClientOpenGuiProgrammableEnderPearl::encode, PacketClientOpenGuiProgrammableEnderPearl::decode, PacketClientOpenGuiProgrammableEnderPearl.Handler::handle);
        HANDLER.registerMessage(id++, PacketServerGivePlayerEditedPearl.class, PacketServerGivePlayerEditedPearl::encode, PacketServerGivePlayerEditedPearl::decode, PacketServerGivePlayerEditedPearl.Handler::handle);
    }

    public static void sendToServer(Object msg)
    {
        HANDLER.sendToServer(msg);
    }

    public static void sendTo(Object msg, ServerPlayerEntity player)
    {
        if (!(player instanceof FakePlayer))
        {
            HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void sendToAll(Object msg){
        for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
        {
            sendTo(msg,player);
        }
    }
}