package naay.dev.core.server;

import naay.dev.core.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerManager {

    private static final List<Server> servers = new ArrayList<>();

    public static void setup() {
    }

    public static Server createServer(String name, String ip, int port) {
        Server server = new Server(name, ip, port);
        servers.add(server);
        return server;
    }

    public static List<Server> getServers() {
        return servers;
    }

    public static class Server {
        private final String name;
        private final String ip;
        private final int port;
        private int players;
        private int maxPlayers;

        public Server(String name, String ip, int port) {
            this.name = name;
            this.ip = ip;
            this.port = port;
        }

        public String getName() { return name; }
        public String getIp() { return ip; }
        public int getPort() { return port; }
        public int getPlayers() { return players; }
        public int getMaxPlayers() { return maxPlayers; }
    }
}