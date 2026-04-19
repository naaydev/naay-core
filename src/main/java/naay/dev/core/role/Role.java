package naay.dev.core.role;

import naay.dev.core.Main;

import java.util.ArrayList;
import java.util.List;

public class Role {

    private static final List<Role> roles = new ArrayList<>();
    
    private final String name;
    private final String prefix;
    private final String permission;
    private final boolean alwaysVisible;
    private final boolean broadcast;
    
    public Role(String name, String prefix, String permission, boolean alwaysVisible, boolean broadcast) {
        this.name = name;
        this.prefix = prefix;
        this.permission = permission;
        this.alwaysVisible = alwaysVisible;
        this.broadcast = broadcast;
    }
    
    public static void setupRoles() {
        roles.clear();
        if (Main.instance.getConfig().contains("roles")) {
            for (String key : Main.instance.getConfig().getConfigurationSection("roles").getKeys(false)) {
                String name = Main.instance.getConfig().getString("roles." + key + ".name");
                String prefix = Main.instance.getConfig().getString("roles." + key + ".prefix");
                String permission = Main.instance.getConfig().getString("roles." + key + ".permission", "");
                boolean alwaysVisible = Main.instance.getConfig().getBoolean("roles." + key + ".alwaysvisible", false);
                boolean broadcast = Main.instance.getConfig().getBoolean("roles." + key + ".broadcast", true);
                roles.add(new Role(name, prefix, permission, alwaysVisible, broadcast));
            }
        }
        if (roles.isEmpty()) {
            roles.add(new Role("Membro", "&7", "", false, false));
        }
    }
    
    public static List<Role> getRoles() {
        return roles;
    }
    
    public static Role getRole(String name) {
        for (Role role : roles) {
            if (role.getName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return roles.get(0);
    }
    
    public String getName() {
        return name;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public String getPermission() {
        return permission;
    }
    
    public boolean isAlwaysVisible() {
        return alwaysVisible;
    }
    
    public boolean isBroadcast() {
        return broadcast;
    }
}