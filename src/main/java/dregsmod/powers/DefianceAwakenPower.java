package dregsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import dregsmod.DregsMod;
import dregsmod.actions.CardAwokenAction;
import dregsmod.util.TextureLoader;

public class DefianceAwakenPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = DregsMod.makeID(DefianceAwakenPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture texture = TextureLoader.getTexture(DregsMod.makePowerPath("defiance.png"));

    public DefianceAwakenPower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;

        region48 = new TextureAtlas.AtlasRegion(texture, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(texture, 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == owner) {
            if (power.ID.equals(WeakPower.POWER_ID) || power.ID.equals(FrailPower.POWER_ID) || power.ID.equals(VulnerablePower.POWER_ID)) {
                addToBot(new CardAwokenAction());
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new DefianceAwakenPower(owner, amount);
    }
}
