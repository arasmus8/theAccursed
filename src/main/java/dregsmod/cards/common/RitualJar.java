package dregsmod.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dregsmod.DregsMod;
import dregsmod.actions.RitualJarAction;
import dregsmod.cards.DregsCardTags;
import dregsmod.characters.Dregs;
import dregsmod.powers.CursedPower;

import static dregsmod.DregsMod.makeCardPath;

public class RitualJar extends CustomCard {

// TEXT DECLARATION

    public static final String ID = DregsMod.makeID(RitualJar.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("RitualJar.png");
// Must have an image with the same NAME as the card in your image folder!

// /TEXT DECLARATION/

// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Dregs.Enums.COLOR_BLACK;

    private static final int COST = 1;

    private static final int MAGIC = 5;
    private static final int UPGRADED_MAGIC = 5;

// /STAT DECLARATION/

    public RitualJar() {
        super(ID, CARD_STRINGS.NAME, IMG, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
        magicNumber = baseMagicNumber;
        tags.add(DregsCardTags.AWAKEN_SKILL);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new CursedPower(m, magicNumber), magicNumber));
        addToBot(new RitualJarAction(this));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
