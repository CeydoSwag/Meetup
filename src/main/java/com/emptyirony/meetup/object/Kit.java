package com.emptyirony.meetup.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/1/16 21:27
 * 4
 */
@Data
@AllArgsConstructor
public class Kit {
    private List<ItemStack> armor;
    private List<ItemStack> contents;


}
