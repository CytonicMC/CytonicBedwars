package net.cytonic.cytonicbedwars.data.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@AllArgsConstructor
@Getter
@Setter
public class Stats {
    private int kills;
    private int deaths;
    private int finalKills;
    private int bedsBroken;
    private double damageDealt;
    private double damageTaken;

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addFinalKill() {
        finalKills++;
    }

    public void addBedBreak() {
        bedsBroken++;
    }

    public void addDamageDealt(double damage) {
        damageDealt += damage;
    }

    public void addDamageTaken(double damage) {
        damageTaken += damage;
    }

    public double getDamageDealt() {
        return Double.parseDouble(new DecimalFormat("#.##").format(damageDealt));
    }

    public double getDamageTaken() {
        return Double.parseDouble(new DecimalFormat("#.##").format(damageTaken));

    }
}
