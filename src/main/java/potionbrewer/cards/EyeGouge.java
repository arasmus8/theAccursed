package potionbrewer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import potionbrewer.PotionbrewerMod;
import potionbrewer.characters.Potionbrewer;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static potionbrewer.PotionbrewerMod.makeCardPath;

public class EyeGouge extends CatalyzeCard {
    // TEXT DECLARATION

    public static final String ID = PotionbrewerMod.makeID(EyeGouge.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    public static CardStrings CARD_STRINGS = languagePack.getCardStrings(ID);
    // Must have an image with the same NAME as the card in your image folder!.

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Potionbrewer.Enums.COLOR_CYAN;

    private static final int COST = 0;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    // /STAT DECLARATION/

    public EyeGouge() {
        super(ID, CARD_STRINGS.NAME, IMG, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    @Override
    public void catalyzeActions(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void useActions(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}