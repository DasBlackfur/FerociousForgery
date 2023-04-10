package me.blackfur.ferociousforgery;

import me.blackfur.ferociousforgery.blocks.entities.FormingAnvilEntity;
import me.blackfur.ferociousforgery.screens.FormingAnvilScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class FerociousForgery implements ModInitializer {
    public static final Block FORMING_ANVIL_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BlockEntityType<FormingAnvilEntity> FORMING_ANVIL_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier("ferocious-forgery", "forming_anvil_entity"),
            FabricBlockEntityTypeBuilder.create(FormingAnvilEntity::new, FORMING_ANVIL_BLOCK).build());

    public static final ScreenHandlerType<FormingAnvilScreenHandler> FORMING_ANVIL_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER, new Identifier("ferocious-forgery", "forming_anvil_screen_handler"),
            new ScreenHandlerType<>(FormingAnvilScreenHandler::new,
                                    FeatureFlags.VANILLA_FEATURES));

    @Override
    public void onInitialize() {

    }
}
