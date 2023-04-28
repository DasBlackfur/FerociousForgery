package me.blackfur.ferociousforgery.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ForgingBlank extends MiningToolItem {

    public ForgingBlank(float attackDamage, float attackSpeed, ToolMaterial material, Settings settings) {
        super(attackDamage, attackSpeed, material, TagKey.of(RegistryKeys.BLOCK, new Identifier("none")), settings);
    }
}
