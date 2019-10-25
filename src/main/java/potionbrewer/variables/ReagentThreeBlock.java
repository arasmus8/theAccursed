package potionbrewer.variables;

import com.megacrit.cardcrawl.cards.AbstractCard;
import potionbrewer.orbs.Reagent;
import potionbrewer.orbs.ReagentList;

import static potionbrewer.PotionbrewerMod.makeID;

public class ReagentThreeBlock extends ReagentBlock {
    @Override
    public String key() {
        return makeID("R3B");
    }

    @Override
    protected Reagent getReagent(AbstractCard card) {
        return ReagentList.thirdReagent(card.misc);
    }
}