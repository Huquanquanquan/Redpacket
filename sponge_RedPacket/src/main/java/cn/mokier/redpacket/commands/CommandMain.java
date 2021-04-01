package cn.mokier.redpacket.commands;

import cn.mokier.redpacket.config.Chat;
import cn.mokier.redpacket.config.ConfigLang;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import java.util.List;

public class CommandMain implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        List<String> help = ConfigLang.getValue("help");
        for(String s : help) {
            Chat.sendMessage(src, s, false);
        }
        return CommandResult.success();
    }
}
