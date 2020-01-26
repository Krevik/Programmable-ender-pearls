package krevik.github.io.pep.gui;

import com.google.common.collect.Lists;
import krevik.github.io.pep.networking.PacketsHandler;
import krevik.github.io.pep.networking.messages.PacketServerGivePlayerEditedPearl;
import net.minecraft.block.StructureBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.EditBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ProgrammableEnderPearlGui extends Screen {
    private final ItemStack ender_Pearl_To_Programm;
    private PlayerEntity editor;
    private Button button_Finish;
    private Button button_Cancel;
    private Button button_Clear;
    private int updateCount;
    private TextFieldWidget posX;
    private TextFieldWidget posY;
    private TextFieldWidget posZ;
    private TextFieldWidget name;

    public ProgrammableEnderPearlGui(PlayerEntity player, ItemStack stack) {
        super(NarratorChatListener.EMPTY);
        this.editor = player;
        this.ender_Pearl_To_Programm = stack;
    }


    @Override
    public void tick() {
        super.tick();
        ++this.updateCount;
        this.posX.tick();
        this.posY.tick();
        this.posZ.tick();
        this.name.tick();

    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.button_Finish = (Button)this.addButton(new Button(this.width / 2 - 49, 220, 98, 20, I18n.format("pep.gui.finish", new Object[0]), (p_214204_1_) -> {
                BlockPos targetPos = null;
                try{
                    int targetPosX = Integer.parseInt(posX.getText());
                    int targetPosY = Integer.parseInt(posY.getText());
                    int targetPosZ = Integer.parseInt(posZ.getText());
                    targetPos = new BlockPos(targetPosX,targetPosY,targetPosZ);
                }catch(RuntimeException exception){

                }
                if(targetPos!=null) {
                    PacketsHandler.sendToServer(new PacketServerGivePlayerEditedPearl(targetPos, name.getText()));
                }
            this.minecraft.displayGuiScreen((Screen)null);
        }));
        this.button_Clear = (Button)this.addButton(new Button(this.width / 2 - 100, 196, 98, 20, I18n.format("pep.programmable_ender_pearl.gui.clear", new Object[0]), (p_214195_1_) -> {
            this.posX.setText("");
            this.posY.setText("");
            this.posZ.setText("");
            this.name.setText("");
        }));
        this.button_Cancel = (Button)this.addButton(new Button(this.width / 2 + 2, 196, 98, 20, I18n.format("pep.gui.cancel", new Object[0]), (p_214212_1_) -> {
            this.minecraft.displayGuiScreen((Screen)null);
        }));

        BlockPos blockpos = editor.getPosition();
        this.posX = new TextFieldWidget(this.font, this.width / 2 - 120, 80, 80, 20, I18n.format("pep.programmable_ender_pearl.gui.position.x"));
        this.posX.setMaxStringLength(15);
        this.posX.setText(Integer.toString(blockpos.getX()));
        this.children.add(this.posX);
        this.posY = new TextFieldWidget(this.font, this.width / 2 - 40, 80, 80, 20, I18n.format("pep.programmable_ender_pearl.gui.position.y"));
        this.posY.setMaxStringLength(15);
        this.posY.setText(Integer.toString(blockpos.getY()));
        this.children.add(this.posY);
        this.posZ = new TextFieldWidget(this.font, this.width / 2 + 40, 80, 80, 20, I18n.format("pep.programmable_ender_pearl.gui.position.z"));
        this.posZ.setMaxStringLength(15);
        this.posZ.setText(Integer.toString(blockpos.getZ()));
        this.children.add(this.posZ);

        this.name = new TextFieldWidget(this.font, this.width / 2 - 40, 20, 80, 20, I18n.format("pep.programmable_ender_pearl.gui.name"));
        this.name.setMaxStringLength(15);
        this.name.setText("Point Name");
        this.children.add(this.name);
    }

    public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
        String s1 = this.posX.getText();
        String s2 = this.posY.getText();
        String s3 = this.posZ.getText();
        String s4 = this.name.getText();
        this.init(p_resize_1_, p_resize_2_, p_resize_3_);
        this.posX.setText(s1);
        this.posY.setText(s2);
        this.posZ.setText(s3);
        this.name.setText(s4);
    }

    @Override
    public void removed() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
    }


    @Override
    public void onClose() {
        this.minecraft.displayGuiScreen((Screen)null);;
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
            return true;
        } else if (p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
            return false;
        } else {
            this.minecraft.displayGuiScreen((Screen)null);
            return true;
        }
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 10, 16777215);

            //this.drawString(this.font, I18n.format("structure_block.position"), this.width / 2 - 153, 70, 10526880);
            this.drawString(this.font, I18n.format("pep.programmable_ender_pearl.gui.position.x"), this.width / 2 -120 + 40 - 10, 60, 10526880);
            this.drawString(this.font, I18n.format("pep.programmable_ender_pearl.gui.position.y"), this.width / 2 - 40 + 40 - 10, 60, 10526880);
            this.drawString(this.font, I18n.format("pep.programmable_ender_pearl.gui.position.z"), this.width / 2 + 40 + 40 - 10, 60, 10526880);
            this.drawString(this.font, I18n.format("pep.programmable_ender_pearl.gui.name"), this.width / 2 - 40 + 40 - 10, 10, 10526880);

            this.posX.render(p_render_1_, p_render_2_, p_render_3_);
            this.posY.render(p_render_1_, p_render_2_, p_render_3_);
            this.posZ.render(p_render_1_, p_render_2_, p_render_3_);
            this.name.render(p_render_1_, p_render_2_, p_render_3_);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


}
