package net.nerrog.velocitysafe.velocitysafe;

import co.aikar.commands.VelocityCommandManager;
import com.google.inject.Inject;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.nerrog.velocitysafe.velocitysafe.Data.DataLoader;
import net.nerrog.velocitysafe.velocitysafe.Data.data;
import net.nerrog.velocitysafe.velocitysafe.Data.commands.vwhitelist;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "velocitysafe",
        name = "VelocitySafe",
        version = "1.0-SNAPSHOT",
        description = "Whitelist for Velocity",
        authors = {"nerrog"}
)
public class VelocitySafe {

    public static Logger logger;
    private ProxyServer proxyServer;
    public static data whitelist;
    private static VelocityCommandManager manager;

    @Inject
    public VelocitySafe(ProxyServer proxyServer, Logger logger){
        this.proxyServer = proxyServer;
        VelocitySafe.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        //jsonロード
        whitelist = DataLoader.getJson();

        //コマンド登録
        manager = new VelocityCommandManager(proxyServer, this);
        manager.enableUnstableAPI("help");
        manager.registerCommand(new vwhitelist());

        logger.info("VelocitySafe Loaded!");
    }

    public static void reloadWhitelist(){
        //jsonの再ロード
        whitelist = DataLoader.getJson();
    }

    public static void reRegisterCommand(){
        //コマンドの再登録
        manager.unregisterCommand( new vwhitelist());
        manager.registerCommand(new vwhitelist());
    }


    public static String listWhitelist(){
        List<String> wPlayers = new ArrayList<>();
        for (data.playersJSON p : whitelist.players){
            wPlayers.add(p.name);
        }
        return wPlayers.size()+" players are registered on whitelist:\n"+String.join(",", wPlayers);
    }

    @Subscribe
    public void joinPlayer(LoginEvent e) throws IOException {
        //ホワリスのonがtrueなら実行
        if (whitelist.on) {
            boolean isWhitelisted = false;
            String reason = "";

            //検証
            for (data.playersJSON p : whitelist.players) {
                if (p.name.equals(e.getPlayer().getUsername())) {
                    if (p.uuid.equals("")) {
                        //uuidが未登録なら
                        whitelist.players.get(whitelist.players.indexOf(p)).uuid = String.valueOf(e.getPlayer().getUniqueId());
                        DataLoader.writeWhitelist(whitelist);
                        isWhitelisted = true;

                    } else {
                        //uuidの検証
                        if (p.uuid.equals(e.getPlayer().getUniqueId().toString())) {
                            isWhitelisted = true;
                        } else {
                            reason = "uuid is not match";
                            isWhitelisted = false;
                        }
                    }
                }
            }

                if (!isWhitelisted) {
                    if (reason.equals("")) {
                        reason = "Username is not match";
                    }
                    //ホワリスに無かったら蹴る
                    logger.info("Kicked " + e.getPlayer().getUsername() + " from " + e.getPlayer().getRemoteAddress().getAddress().toString() + " reason:" + reason);
                    e.setResult(ResultedEvent.ComponentResult.denied(Component.text(ChatColor.translateAlternateColorCodes('&', whitelist.NotwhitelistedMessage))));
                } else {
                    e.setResult(ResultedEvent.ComponentResult.allowed());
                }
        }
    }
}













