package krevik.github.io.pep.networking.messages;

import krevik.github.io.pep.PEP;
import krevik.github.io.pep.item.ProgrammableEnderPearl;
import krevik.github.io.pep.item.ProgrammedEnderPearl;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketServerGivePlayerEditedPearl  {

    private BlockPos targetPos;
    private String name;
    public PacketServerGivePlayerEditedPearl(BlockPos pos, String title){
        targetPos=pos;
        name=title;
    }

    public static void encode(PacketServerGivePlayerEditedPearl msg, PacketBuffer buf)
    {
        buf.writeBlockPos(msg.targetPos);
        buf.writeString(msg.name);
    }

    public static PacketServerGivePlayerEditedPearl decode(PacketBuffer buf)
    {
        return new PacketServerGivePlayerEditedPearl(buf.readBlockPos(), buf.readString());
    }

    public static class Handler
    {
        public static void handle(final PacketServerGivePlayerEditedPearl message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() -> {
                EntityPlayerMP player = ctx.get().getSender();
                ItemStack stack = new ItemStack(PEP.PROGRAMMED_ENDER_PEARL,1);
                ItemStack stackToGive = new ItemStack(((ProgrammedEnderPearl)stack.getItem()),1);
                ((ProgrammedEnderPearl)stackToGive.getItem()).setTargetPos(stackToGive,message.targetPos);
                ((ProgrammedEnderPearl)stackToGive.getItem()).setName(stackToGive,message.name);
                ItemStack heldStack = player.getHeldItem(EnumHand.MAIN_HAND);
                if(heldStack.getItem() instanceof ProgrammableEnderPearl){
                    heldStack.shrink(1);
                    player.addItemStackToInventory(stackToGive);
                }
                return;
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
