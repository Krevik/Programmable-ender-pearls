package krevik.github.io.pep.networking.messages;

import krevik.github.io.pep.gui.ProgrammableEnderPearlGui;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClientOpenGuiProgrammableEnderPearl  {

    private ItemStack usedStack;
    private BlockPos calledPos;
    public PacketClientOpenGuiProgrammableEnderPearl(ItemStack stack, BlockPos pos){
        usedStack=stack;
        calledPos = pos;
    }

    public static void encode(PacketClientOpenGuiProgrammableEnderPearl msg, PacketBuffer buf)
    {
        buf.writeItemStack(msg.usedStack);
        buf.writeBlockPos(msg.calledPos);
    }

    public static PacketClientOpenGuiProgrammableEnderPearl decode(PacketBuffer buf)
    {
        ItemStack stack = buf.readItemStack();
        BlockPos pos = buf.readBlockPos();
        return new PacketClientOpenGuiProgrammableEnderPearl(stack,pos);
    }

    public static class Handler
    {
        public static void handle(final PacketClientOpenGuiProgrammableEnderPearl message, Supplier<NetworkEvent.Context> ctx)
        {
            if(ctx.get().getDirection()== NetworkDirection.PLAY_TO_CLIENT){
                DistExecutor.runWhenOn(Dist.CLIENT,()->new Runnable() {
                    @Override
                    public void run() {
                        Minecraft.getInstance().displayGuiScreen(new ProgrammableEnderPearlGui(Minecraft.getInstance().player,message.usedStack));
                    }
                });
            }
            ctx.get().setPacketHandled(true);
        }
    }
}

