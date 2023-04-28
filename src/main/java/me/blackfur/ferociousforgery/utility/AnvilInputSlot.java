package me.blackfur.ferociousforgery.utility;

import me.blackfur.ferociousforgery.FerociousForgery;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class AnvilInputSlot extends Slot {
    public AnvilInputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack itemStack) {
        return itemStack.isOf(FerociousForgery.FORGING_BLANK);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }
}
