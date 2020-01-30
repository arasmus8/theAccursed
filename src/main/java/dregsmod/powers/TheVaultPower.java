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
import java.util.stream.Collectors;

public class TheVaultPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = DregsMod.makeID(TheVaultPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TheVaultPower(AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;

        loadRegion("flight");
        updateDescription();
    }

    @Override
    public void onDrawOrDiscard() {
        ArrayList<AbstractCard> discardedCurses = AbstractDungeon.player.discardPile.group.stream()
                .filter(card -> card.type == AbstractCard.CardType.CURSE && !DregsMod.postSealedCards.contains(card))
                .collect(Collectors.toCollection(ArrayList::new));
        discardedCurses.forEach(card -> CardSealed.isSealed.set(card, true));
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