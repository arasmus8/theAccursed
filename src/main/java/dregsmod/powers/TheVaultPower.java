package dregsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import dregsmod.DregsMod;
import dregsmod.patches.variables.CardSealed;

import java.util.ArrayList;

public class TheVaultPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = DregsMod.makeID(TheVaultPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static ArrayList<AbstractCard> ignoredCards;

    public TheVaultPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;

        loadRegion("flight");
        updateDescription();
        if (!AbstractDungeon.player.hasPower(POWER_ID)) {
            ignoredCards = new ArrayList<>(AbstractDungeon.player.discardPile.group);
        }
    }

    @Override
    public void onDrawOrDiscard() {
        ignoredCards.removeAll(AbstractDungeon.player.hand.group);
        ArrayList<AbstractCard> discardedCurses = new ArrayList<>(AbstractDungeon.player.discardPile.getCardsOfType(AbstractCard.CardType.CURSE).group);
        discardedCurses.removeAll(DregsMod.postSealedCards);
        discardedCurses.removeAll(ignoredCards);
        discardedCurses.forEach(card -> {
            CardSealed.isSealed.set(card, true);
            AbstractDungeon.player.powers.forEach(power -> {
                if (power instanceof TriggerOnSealedPower) {
                    ((TriggerOnSealedPower) power).triggerOnSealed(card);
                }
            });
        });
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new TheVaultPower(owner);
    }
}
