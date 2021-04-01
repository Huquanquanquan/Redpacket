package cn.mokier.redpacket;

import cn.mokier.redpacket.commands.CommandManager;
import cn.mokier.redpacket.config.Chat;
import cn.mokier.redpacket.config.Config;
import cn.mokier.redpacket.config.ConfigLang;
import cn.mokier.redpacket.packet.PacketManager;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.ServiceManager;
import org.spongepowered.api.service.economy.EconomyService;

import java.nio.file.Path;
import java.util.Optional;

@Plugin(
        id = "redpacket",
        name = "RedPacket",
        description = "on RedPacket plugin"
)
public class RedPacket {

    public Path configDir;
    public static Logger logger;
    public static PluginContainer pluginContainer;
    public static RedPacket instance;
    public static EconomyService economyService;

    @Inject
    public RedPacket(@ConfigDir(sharedRoot = false) Path configDir, Logger logger, ServiceManager serviceManager, PluginContainer pluginContainer) {
        this.configDir = configDir;
        RedPacket.logger = logger;
        RedPacket.pluginContainer = pluginContainer;
        instance = this;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Config.initialization(configDir);
        ConfigLang.initialization(configDir);
        CommandManager.initialization();
        PacketManager.initialization();

        loadEconomy();

        logger.info("§aRedPacket enabled success, Author: Mokier");
    }

    @Listener
    public void onServerReload(GameReloadEvent event) {
        Config.initialization(configDir);
        ConfigLang.initialization(configDir);
        CommandManager.initialization();
        PacketManager.initialization();

        logger.info("§aRedPacket reload success");
    }

    /**
     * 加载经济API
     */
    public void loadEconomy() {
        Optional<EconomyService> serviceOpt = Sponge.getServiceManager().provide(EconomyService.class);
        if (!serviceOpt.isPresent()) {
            Chat.sendMessage(Sponge.getServer().getConsole(), "§c未检测到EconomyAPI", false);
            return;
        }
        economyService = serviceOpt.get();
    }

}
