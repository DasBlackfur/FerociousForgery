package me.blackfur.ferociousforgery.screens;

import me.blackfur.ferociousforgery.FerociousForgery;
import me.blackfur.ferociousforgery.utility.AnvilInputSlot;
import me.blackfur.ferociousforgery.utility.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

import java.awt.*;

public class FormingAnvilScreenHandler extends ScreenHandler implements ScreenHandlerListener {
    private final Inventory inventory;
    PropertyDelegate propertyDelegate;


    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public FormingAnvilScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(258));
    }

    public FormingAnvilScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(FerociousForgery.FORMING_ANVIL_SCREEN_HANDLER, syncId);
        this.propertyDelegate = propertyDelegate;
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);
        addListener(this);
        this.addProperties(propertyDelegate);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        this.addSlot(new AnvilInputSlot(this.inventory, 0, 10, 30));
        this.addSlot(new OutputSlot(this.inventory, 1, 150, 30));

        int m;
        int l;
        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        propertyDelegate.set(id + 2, 1);
        return super.onButtonClick(player, id);
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 0) {
            if (stack.isOf(FerociousForgery.FORGING_BLANK)) {
                propertyDelegate.set(0, stack.getNbt().getInt("materialID"));
                propertyDelegate.set(1, stack.getNbt().getInt("heat"));
                for (int i = 0; i < 256; i++) {
                    propertyDelegate.set(i+2, stack.getNbt().getIntArray("Model")[i]);
                }
            } else if (stack.isEmpty()) {
                handler.getSlot(1).setStack(Items.AIR.getDefaultStack());
            }
        }
        if (slotId == 1 && stack.isEmpty()) {
            handler.getSlot(0).setStack(Items.AIR.getDefaultStack());
            for (int i = 0; i < 258; i++) {
                propertyDelegate.set(i, 0);
            }
        }
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
        if (!this.inventory.getStack(0).isEmpty()) {
            ItemStack tmpStack = new ItemStack(FerociousForgery.FORGING_BLANK);
            NbtCompound tmpCompound = tmpStack.getOrCreateNbt();
            int[] tmpArray = new int[256];
            for (int i = 0; i < 256; i++) {
                tmpArray[i] = propertyDelegate.get(i+2);
            }
            tmpCompound.putIntArray("Model", tmpArray);
            tmpCompound.putInt("materialID", propertyDelegate.get(0));
            tmpCompound.putInt("heat", propertyDelegate.get(1));
            tmpStack.setNbt(tmpCompound);
            handler.slots.get(1).setStack(tmpStack);
        }
    }
}
