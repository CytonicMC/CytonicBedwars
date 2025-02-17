package net.cytonic.cytonicbedwars.menu;

import eu.koboo.minestom.invue.api.PlayerView;
import eu.koboo.minestom.invue.api.item.PrebuiltItem;
import eu.koboo.minestom.invue.api.item.ViewItem;
import eu.koboo.minestom.invue.api.slots.ViewPattern;
import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.menu.itemShop.*;
import net.cytonic.cytonicbedwars.utils.Items;

@NoArgsConstructor
public class MenuUtils {
    public static final PrebuiltItem filler = PrebuiltItem.of(Items.MENU_FILLER).cancelClicking();

    public static final PrebuiltItem blocksMenu = PrebuiltItem.of(Items.MENU_BLOCKS, action -> {
        action.getEvent().setCancelled(true);
        new BlocksShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem armorMenu = PrebuiltItem.of(Items.MENU_ARMOR, action -> {
        action.getEvent().setCancelled(true);
        new ArmorShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem combatMenu = PrebuiltItem.of(Items.MENU_COMBAT, action -> {
        action.getEvent().setCancelled(true);
        new CombatShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem toolMenu = PrebuiltItem.of(Items.MENU_TOOLS, action -> {
        action.getEvent().setCancelled(true);
        new ToolShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem potionMenu = PrebuiltItem.of(Items.MENU_POTIONS, action -> {
        action.getEvent().setCancelled(true);
        new PotionShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem utilsMenu = PrebuiltItem.of(Items.MENU_UTILS, action -> {
        action.getEvent().setCancelled(true);
        new UtilsShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem rotatingMenu = PrebuiltItem.of(Items.MENU_CUSTOM_ITEMS, action -> {
        action.getEvent().setCancelled(true);
        new RotatingShopMenu().open(action.getPlayer());
    });

    public static final PrebuiltItem selectedPage = PrebuiltItem.of(Items.MENU_SELECTED_PAGE).cancelClicking();

    public static void setItemShopItems(PlayerView view, ViewPattern pattern) {
        for (int slot : pattern.getAllSlots()) {
            ViewItem.bySlot(view, slot).applyPrebuilt(MenuUtils.filler);
        }

        ViewItem.bySlot(view, pattern.getSlot('b')).applyPrebuilt(MenuUtils.blocksMenu);
        ViewItem.bySlot(view, pattern.getSlot('c')).applyPrebuilt(MenuUtils.combatMenu);
        ViewItem.bySlot(view, pattern.getSlot('a')).applyPrebuilt(MenuUtils.armorMenu);
        ViewItem.bySlot(view, pattern.getSlot('t')).applyPrebuilt(MenuUtils.toolMenu);
        ViewItem.bySlot(view, pattern.getSlot('p')).applyPrebuilt(MenuUtils.potionMenu);
        ViewItem.bySlot(view, pattern.getSlot('u')).applyPrebuilt(MenuUtils.utilsMenu);
        ViewItem.bySlot(view, pattern.getSlot('r')).applyPrebuilt(MenuUtils.rotatingMenu);
        ViewItem.bySlot(view, pattern.getSlot('s')).applyPrebuilt(MenuUtils.selectedPage);
    }
}
