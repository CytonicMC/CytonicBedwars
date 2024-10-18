package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.utils.Items;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

import static net.cytonic.utils.MiniMessageTemplate.MM;

public class ItemCommand extends Command {

    public ItemCommand() {
        super("item", "i");
        var itemArgument = ArgumentType.Word("item");
        itemArgument.setSuggestionCallback(((commandSender, commandContext, suggestion) ->
                Items.getItemIDs().forEach(a -> suggestion.addEntry(new SuggestionEntry(a)))));
        var amountArgument = ArgumentType.Integer("amount");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("cytonic.bedwars.commands.item")) {
                    String item = context.get(itemArgument);
                    if (Items.get(item) != null) {
                        player.getInventory().addItemStack(Items.get(item));
                        player.sendMessage(MM."<GREEN>Gave you 1 \{item}");
                    } else {
                        player.sendMessage(MM."<RED>Invalid item ID: '\{item}'");
                    }
                }
            }
        }, itemArgument);
        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("cytonic.bedwars.commands.item")) {
                    int amount = context.get(amountArgument);
                    String item = context.get(itemArgument);
                    if (Items.get(item) != null) {
                        ItemStack foo = Items.get(item);
                        foo = foo.withAmount(amount);
                        player.getInventory().addItemStack(foo);
                        player.sendMessage(MM."<GREEN>Gave you \{amount} \{item}");
                    } else {
                        player.sendMessage(MM."<RED>Invalid item ID: '\{item}'");
                    }
                }
            }
        }, itemArgument, amountArgument);
    }
}
