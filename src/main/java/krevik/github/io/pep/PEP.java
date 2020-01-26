package krevik.github.io.pep;

import krevik.github.io.pep.item.ProgrammableEnderPearl;
import krevik.github.io.pep.item.ProgrammedEnderPearl;
import krevik.github.io.pep.networking.PacketsHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("pep")
public class PEP {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "pep";
    public static final String NAME = "programmable_ender_pearl";
    public static final String VERSION = "@VERSION@";

    public static final Item PROGRAMMABLE_ENDER_PEARL = new ProgrammableEnderPearl(new Item.Properties().group(ItemGroup.MISC).maxStackSize(16)).setRegistryName("pep","programmable_ender_pearl");
    public static final Item PROGRAMMED_ENDER_PEARL = new ProgrammedEnderPearl(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)).setRegistryName("pep","programmed_ender_pearl");

    public PEP() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        PacketsHandler.register();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }



    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event){
            final IForgeRegistry<Item> registry = event.getRegistry();
            registry.register(PROGRAMMABLE_ENDER_PEARL);
            registry.register(PROGRAMMED_ENDER_PEARL);
        }

    }

}
