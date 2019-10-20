package potionbrewer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import potionbrewer.cards.Prototype;
import potionbrewer.orbs.Reagent;
import potionbrewer.patches.PotionTracker;
import potionbrewer.powers.NoCatalyzePower;

public class PrototypeAction extends AbstractGameAction {
    private Prototype card;
    private Reagent reagent;
    private AbstractPlayer player;
    private AbstractMonster monster;
    private int damageTimes;
    private int blockTimes;
    private boolean aoeDamage;

    public PrototypeAction(
            Prototype card,
            Reagent reagent,
            AbstractPlayer player,
            AbstractMonster monster,
            int damageTimes,
            int blockTimes,
            boolean aoeDamage
    ) {
        duration = Settings.ACTION_DUR_XFAST;
        this.card = card;
        this.reagent = reagent;
        this.player = player;
        this.monster = monster;
        this.damageTimes = damageTimes;
        this.blockTimes = blockTimes;
        this.aoeDamage = aoeDamage;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            this.isDone = true;
            if (reagent.catalyze && PotionTracker.potionsUsedThisTurn.get(player) == 0) {
                return;
            } else if (reagent.catalyze && player.hasPower(NoCatalyzePower.POWER_ID)) {
                return;
            }
            if (reagent.damages) {
                for (int i = 0; i < damageTimes; i++) {
                    if (aoeDamage) {
                        reagent.doAoeDamage(player, reagent.damage);
                    } else {
                        card.applyPowersDynamic(reagent.damage);
                        card.calculateCardDamage(monster);
                        reagent.doDamage(player, monster, new DamageInfo(player, card.damage, card.damageTypeForTurn));
                    }
                }
            }
            if (reagent.blocks) {
                for (int i = 0; i < blockTimes; i++) {
                    card.applyPowersToBlockDynamic(reagent.block);
                    reagent.doBlock(player, card.block);
                }
            }
            if (aoeDamage && reagent.targeted) {
                for (AbstractMonster mm : AbstractDungeon.getMonsters().monsters) {
                    if (mm != null && !mm.isDeadOrEscaped()) {
                        reagent.doEffects(player, mm);
                    }
                }
            } else {
                reagent.doEffects(player, monster);
            }
        }
    }
}
