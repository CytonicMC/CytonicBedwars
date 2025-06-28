package net.cytonic.cytonicbedwars.managers;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    public void createTables() {
        createStatsTable();
    }

    private void createStatsTable() {
        PreparedStatement ps = Cytosis.getDatabaseManager().getMysqlDatabase().prepare("CREATE TABLE IF NOT EXISTS bedwars_stats (uuid VARCHAR(36), kills INT, deaths INT, finalKills INT, bedsBroken INT, damageDealt DOUBLE, damageTaken DOUBLE, PRIMARY KEY (uuid))");
        Cytosis.getDatabaseManager().getMysqlDatabase().update(ps);
    }

    public void saveStats() {
        CytonicBedWars.getGameManager().getStatsManager().getStats().forEach((uuid, kills) -> {
            try {
                PreparedStatement ps = Cytosis.getDatabaseManager().getMysqlDatabase().prepare("INSERT IGNORE INTO bedwars_stats (uuid, kills, deaths, finalKills, bedsBroken, damageDealt, damageTaken) VALUES (?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, kills.getKills());
                ps.setInt(3, kills.getDeaths());
                ps.setInt(4, kills.getFinalKills());
                ps.setInt(5, kills.getBedsBroken());
                ps.setDouble(6, kills.getDamageDealt());
                ps.setDouble(7, kills.getDamageTaken());
                Cytosis.getDatabaseManager().getMysqlDatabase().update(ps);
            } catch (SQLException e) {
                Logger.error("Failed to save stats!", e);
            }
        });
    }
}