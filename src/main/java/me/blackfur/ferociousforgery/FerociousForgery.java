package me.blackfur.ferociousforgery;

import me.blackfur.ferociousforgery.blocks.FormingAnvil;
import me.blackfur.ferociousforgery.blocks.entities.FormingAnvilEntity;
import me.blackfur.ferociousforgery.items.ForgingBlank;
import me.blackfur.ferociousforgery.items.ForgingBlankRenderer;
import me.blackfur.ferociousforgery.screens.FormingAnvilScreenHandler;
import me.blackfur.ferociousforgery.utility.MeltableToolMaterialRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.awt.*;

public class FerociousForgery implements ModInitializer {
    public static final Block FORMING_ANVIL_BLOCK = new FormingAnvil(
            FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BlockEntityType<FormingAnvilEntity> FORMING_ANVIL_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier("ferocious-forgery", "forming_anvil_entity"),
            FabricBlockEntityTypeBuilder.create(FormingAnvilEntity::new, FORMING_ANVIL_BLOCK).build());

    public static final ScreenHandlerType<FormingAnvilScreenHandler> FORMING_ANVIL_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER, new Identifier("ferocious-forgery", "forming_anvil_screen_handler"),
            new ScreenHandlerType<>(FormingAnvilScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static ToolItem FORGING_BLANK = new ForgingBlank(1, -2.8F, ToolMaterials.WOOD, new Item.Settings());

    public static final ItemGroup FEROCIOUS_FORGERY_GROUP = FabricItemGroup.builder(new Identifier("ferocious-forgery", "main"))
            .icon(() -> new ItemStack(FORMING_ANVIL_BLOCK))
            .build();

    @Override
    public void onInitialize() {
        MeltableToolMaterialRegistry.init();
        Registry.register(Registries.BLOCK, new Identifier("ferocious-forgery", "forming_anvil"), FORMING_ANVIL_BLOCK);
        Registry.register(Registries.ITEM, new Identifier("ferocious-forgery", "forming_anvil"),
                          new BlockItem(FORMING_ANVIL_BLOCK, new FabricItemSettings()));
        Registry.register(Registries.ITEM, new Identifier("ferocious-forgery", "forging_blank"), FORGING_BLANK);

        BuiltinItemRendererRegistry.INSTANCE.register(FORGING_BLANK, new ForgingBlankRenderer());

        ItemStack tmpStack = new ItemStack(FORGING_BLANK);
        NbtCompound tmpCompound = tmpStack.getOrCreateNbt();
        tmpCompound.putIntArray("Model", new int[256]);
        tmpCompound.putInt("materialID", Color.RED.getRGB());
        tmpCompound.putInt("heat", 1);
        tmpStack.setNbt(tmpCompound);
        ItemGroupEvents.modifyEntriesEvent(FEROCIOUS_FORGERY_GROUP).register(content -> {
            content.add(FORMING_ANVIL_BLOCK);
            content.add(tmpStack);
        });
    }
}
