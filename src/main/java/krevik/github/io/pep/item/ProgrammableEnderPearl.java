package krevik.github.io.pep.item;

import krevik.github.io.pep.gui.ProgrammableEnderPearlGui;
import krevik.github.io.pep.networking.PacketsHandler;
import krevik.github.io.pep.networking.messages.PacketClientOpenGuiProgrammableEnderPearl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ProgrammableEnderPearl extends ItemEnderPearl {
    public ProgrammableEnderPearl(Properties builder) {
        super(builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entity, EnumHand p_77659_3_) {
        ItemStack lvt_4_1_ = entity.getHeldItem(p_77659_3_);
        if(!world.isRemote()){
            if(entity instanceof EntityPlayerMP) {
                PacketsHandler.sendTo(new PacketClientOpenGuiProgrammableEnderPearl(lvt_4_1_, entity.getPosition()), (EntityPlayerMP)entity);
            }
        }

        return new ActionResult(EnumActionResult.SUCCESS, lvt_4_1_);
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
        list.add(new TextComponentString("Use it to programm it!"));
    }
}
