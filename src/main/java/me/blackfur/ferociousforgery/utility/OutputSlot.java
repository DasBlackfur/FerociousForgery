package me.blackfur.ferociousforgery.utility;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class OutputSlot extends Slot {

    public OutputSlot(Inventory itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack itemStack) {
        return false;
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }
}