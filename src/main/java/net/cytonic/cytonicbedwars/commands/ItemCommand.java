package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.commands.utils.CommandUtils;
import net.cytonic.cytosis.commands.utils.CytosisCommand;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

public class ItemCommand extends CytosisCommand {

    public ItemCommand() {
        super("item", "i");
        setCondition(CommandUtils.IS_ADMIN);
        var itemArgument = ArgumentType.Word("item");
        itemArgument.setSuggestionCallback(((commandSender, commandContext, suggestion) ->
                Items.getItemIDs().forEach(a -> suggestion.addEntry(new SuggestionEntry(a)))));
        var amountArgument = ArgumentType.Integer("amount");

        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                String item = context.get(itemArgument);
                if (Items.get(item) != null) {
                    player.getInventory().addItemStack(Items.get(item));
                    player.sendMessage(Msg.green("Gave you 1 %s", item));
                } else {
                    player.sendMessage(Msg.red("Invalid item ID: '%s'", item));
                }
            }
        }, itemArgument);
        addSyntax((sender, context) -> {
            if (sender instanceof Player player) {
                int amount = context.get(amountArgument);
                String item = context.get(itemArgument);
                if (Items.get(item) != null) {
                    ItemStack foo = Items.get(item);
                    foo = foo.withAmount(amount);
                    player.getInventory().addItemStack(foo);
                    player.sendMessage(Msg.green("Gave you %d %s", amount, item));
                } else {
                    player.sendMessage(Msg.red("Invalid item ID: '%s'", item));
                }
            }
        }, itemArgument, amountArgument);
    }
}
