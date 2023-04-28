package me.blackfur.ferociousforgery.utility;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.awt.*;

public class MeltableToolMaterialRegistry {
    public static final RegistryKey<Registry<Pair<ToolMaterial, Color>>> MELTABLE_TOOL_MATERIAL_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier("ferocious-forgery", "meltable_tool_material_registry"));
    public static final FabricRegistryBuilder<Pair<ToolMaterial, Color>, SimpleRegistry<Pair<ToolMaterial, Color>>> MELTABLE_TOOL_MATERIAL_REGISTRY_BUILDER = FabricRegistryBuilder.createSimple(MELTABLE_TOOL_MATERIAL_REGISTRY_KEY);

    public static final SimpleRegistry<Pair<ToolMaterial, Color>> MELTABLE_TOOL_MATERIAL_REGISTRY = MELTABLE_TOOL_MATERIAL_REGISTRY_BUILDER.buildAndRegister();

    public static void init() {
        Registry.register(MELTABLE_TOOL_MATERIAL_REGISTRY, new Identifier("iron"), new Pair<>(ToolMaterials.IRON, Color.getColor("#FFAAAAAA")));
        Registry.register(MELTABLE_TOOL_MATERIAL_REGISTRY, new Identifier("gold"), new Pair<>(ToolMaterials.GOLD, Color.getColor("#FFFFFF00")));
        Registry.register(MELTABLE_TOOL_MATERIAL_REGISTRY, new Identifier("netherite"), new Pair<>(ToolMaterials.NETHERITE, Color.getColor("#FF000000")));
    }
}
