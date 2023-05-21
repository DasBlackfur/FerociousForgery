package me.blackfur.ferociousforgery.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.blackfur.ferociousforgery.FerociousForgery;
import me.blackfur.ferociousforgery.items.ForgingBlankModelGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
public class ItemTextureMixin {
    @Inject(method = "getItemLayer", at = @At(value = "HEAD"), cancellable = true)
    private static void add_forgingBlankLayer(ItemStack stack, boolean direct, CallbackInfoReturnable<RenderLayer> cir) {
        if (stack.isOf(FerociousForgery.FORGING_BLANK)) {
            cir.setReturnValue(RenderLayer.getEntityTranslucentCull(ForgingBlankModelGenerator.TEXTURE_CACHE.get(stack.getOrCreateNbt())));
        }
    }
}
