package krevik.github.io.pep.item;

import krevik.github.io.pep.gui.ProgrammableEnderPearlGui;
import krevik.github.io.pep.networking.PacketsHandler;
import krevik.github.io.pep.networking.messages.PacketClientOpenGuiProgrammableEnderPearl;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CommandBlockScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ProgrammableEnderPearl extends EnderPearlItem {
    public ProgrammableEnderPearl(Properties builder) {
        super(builder);
    }

    public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack lvt_4_1_ = p_77659_2_.getHeldItem(p_77659_3_);
        if(!p_77659_1_.isRemote()){
            if(p_77659_2_ instanceof ServerPlayerEntity) {
                PacketsHandler.sendTo(new PacketClientOpenGuiProgrammableEnderPearl(lvt_4_1_, p_77659_2_.getPosition()), (ServerPlayerEntity)p_77659_2_);
            }
        }

        return ActionResult.func_226248_a_(lvt_4_1_);
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {
        super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
        list.add(new StringTextComponent("Use it to programm it!"));
    }
}
