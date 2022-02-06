package net.nerrog.velocitysafe.velocitysafe.Data.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nerrog.velocitysafe.velocitysafe.Data.DataLoader;
import net.nerrog.velocitysafe.velocitysafe.VelocitySafe;

@CommandAlias("vwhitelist|velocitysafe")
@CommandPermission("velocitysafe")
public class vwhitelist extends BaseCommand {

    @Default
    @HelpCommand
    public static void onHelp(CommandHelp help){
        //Helpコマンド
        help.showHelp();
    }

    @Subcommand("list")
    @Description("List of whitelist")
    public static void onList(CommandSource source){
        //List表示
        source.sendMessage(Component.text(VelocitySafe.listWhitelist()));
    }

    @Subcommand("add")
    @Description("Add player of whitelist")
    @CommandCompletion("PlayerName")
    public static void onAdd(CommandSource source, String player){
        //追加
        DataLoader.modifyWhitelist(DataLoader.modifyWList.ADD, player);
        source.sendMessage(Component.text(player+" added!").color(NamedTextColor.GREEN));
    }

    @Subcommand("remove")
    @Description("Add player of whitelist")
    @CommandCompletion("PlayerName")
    public static void onRemove(CommandSource source, String player){
        //削除
        if(DataLoader.modifyWhitelist(DataLoader.modifyWList.REMOVE, player)){
            source.sendMessage(Component.text(player+" removed!").color(NamedTextColor.GREEN));
        }else {
            source.sendMessage(Component.text("Not found "+player+" in the whitelist.").color(NamedTextColor.RED));
        }

    }

    @Subcommand("reload")
    @Description("Reload whitelist from json file")
    public static void onReload(CommandSource source){
        //リロード
        VelocitySafe.reloadWhitelist();
        source.sendMessage(Component.text("Whitelist reloaded!").color(NamedTextColor.GREEN));
    }

    @Subcommand("on")
    @Description("Toggle whitelist to ON")
    public static void onON(CommandSource source){
        if(DataLoader.switchWhitelist(DataLoader.OnOff.ON)){
            source.sendMessage(Component.text("Whitelist ON!").color(NamedTextColor.GREEN));
        }else {
            source.sendMessage(Component.text("Whitelist is already ON!").color(NamedTextColor.RED));
        }
    }

    @Subcommand("off")
    @Description("Toggle whitelist to OFF")
    public static void onOFF(CommandSource source){
        if(DataLoader.switchWhitelist(DataLoader.OnOff.OFF)){
            source.sendMessage(Component.text("Whitelist OFF!").color(NamedTextColor.GREEN));
        }else {
            source.sendMessage(Component.text("Whitelist is already OFF!").color(NamedTextColor.RED));
        }
    }

}
