package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.utils.MiniMessageTemplate;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

public class ItemCommand extends Command {

    private final CytonicBedWars plugin;

    public ItemCommand(CytonicBedWars plugin) {
        super("item", "i");
        this.plugin = plugin;

        var itemArgument = ArgumentType.Word("item");
        var amountArgument = ArgumentType.Integer("amount");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("cytonic.bedwars.commands.item")) {
                    int amount = context.get(amountArgument);
                    String item = context.get(itemArgument);
                    if (Items.get(item) != null) {
                        player.getInventory().addItemStack(Items.get(item));
                        player.sendMessage(MiniMessageTemplate.MM."<GREEN>Gave you 1 " + item);
                    } else {
                        player.sendMessage(MiniMessageTemplate.MM."<RED>Invalid item ID: '\{item}'");
                    }
                    if (Items.get(item) != null) {
                        ItemStack foo = Items.get(item);
                        foo = foo.withAmount(amount);
                        player.getInventory().addItemStack(foo);
                        player.sendMessage(MiniMessageTemplate.MM."<GREEN>Gave you \{amount} \{item}");
                    } else {
                        player.sendMessage(MiniMessageTemplate.MM."<RED>Invalid item ID: '\{item}'");
                    }
                }
            }
        });
    }
}
