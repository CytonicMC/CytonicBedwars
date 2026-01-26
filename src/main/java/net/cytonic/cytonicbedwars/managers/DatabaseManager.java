package net.cytonic.cytonicbedwars.managers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.cytonic.cytosis.Bootstrappable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.bootstrap.annotations.CytosisComponent;
import net.cytonic.cytosis.data.MysqlDatabase;
import net.cytonic.cytosis.logging.Logger;

@CytosisComponent
public class DatabaseManager implements Bootstrappable {

    @Override
    public void init() {
        createStatsTable();
    }

    private void createStatsTable() {
        MysqlDatabase database = Cytosis.CONTEXT.getComponent(MysqlDatabase.class);
        PreparedStatement ps = database.prepare(
            "CREATE TABLE IF NOT EXISTS bedwars_stats (uuid VARCHAR(36), kills INT, deaths INT, finalKills INT, bedsBroken INT, damageDealt DOUBLE, damageTaken DOUBLE, PRIMARY KEY (uuid))");
        database.update(ps);
    }

    public void saveStats() {
        Cytosis.CONTEXT.getComponent(StatsManager.class).getStats().forEach((uuid, kills) -> {
            try {
                MysqlDatabase database = Cytosis.CONTEXT.getComponent(MysqlDatabase.class);
                PreparedStatement ps = database.prepare(
                    "INSERT IGNORE INTO bedwars_stats (uuid, kills, deaths, finalKills, bedsBroken, damageDealt, damageTaken) VALUES (?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, kills.getKills());
                ps.setInt(3, kills.getDeaths());
                ps.setInt(4, kills.getFinalKills());
                ps.setInt(5, kills.getBedsBroken());
                ps.setDouble(6, kills.getDamageDealt());
                ps.setDouble(7, kills.getDamageTaken());
                database.update(ps);
            } catch (SQLException e) {
                Logger.error("Failed to save stats!", e);
            }
        });
    }
}