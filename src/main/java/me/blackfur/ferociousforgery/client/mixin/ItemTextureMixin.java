package me.blackfur.ferociousforgery.client.mixin;

import me.blackfur.ferociousforgery.FerociousForgery;
import me.blackfur.ferociousforgery.items.ForgingBlankModelGenerator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
