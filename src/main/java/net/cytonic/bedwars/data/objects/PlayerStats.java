package net.cytonic.bedwars.data.objects;

import java.util.UUID;

import io.ebean.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import net.cytonic.cytosis.utils.Utils;

@Getter
@Setter
@Entity
@Table(name = "bedwars_stats")
public class PlayerStats extends Model {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "wins")
    private int wins;
    @Column(name = "final_kills")
    private int finalKills;
    @Column(name = "beds_broken")
    private int bedsBroken;
    @Column(name = "losses")
    private int losses;
    @Column(name = "beds_lost")
    private int bedsLost;
    @Column(name = "kills")
    private int kills;
    @Column(name = "deaths")
    private int deaths;
    @Column(name = "final_deaths")
    private int finalDeaths;
    @Column(name = "damage_dealt")
    private double damageDealt;
    @Column(name = "damage_taken")
    private double damageTaken;

    public PlayerStats(UUID id) {
        this.id = id;
    }

    public void addWin() {
        wins++;
    }

    public void addFinalKill() {
        finalKills++;
    }

    public void addBedBreak() {
        bedsBroken++;
    }

    public void addLoss() {
        losses++;
    }

    public void addBedLost() {
        bedsLost++;
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addFinalDeath() {
        finalDeaths++;
    }

    public void addDamageDealt(double damageDealt) {
        this.damageDealt += damageDealt;
    }

    public void addDamageTaken(double damageTaken) {
        this.damageTaken += damageTaken;
    }

    public double getDamageDealt() {
        return Double.parseDouble(Utils.TWO_PLACES.format(damageDealt));
    }

    public double getDamageTaken() {
        return Double.parseDouble(Utils.TWO_PLACES.format(damageTaken));
    }
}
