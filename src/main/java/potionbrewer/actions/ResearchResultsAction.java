package potionbrewer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ResearchResultsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private boolean upgraded;
    private boolean freeToPlayOnce;
    private int energyOnUse;

    public ResearchResultsAction(final AbstractPlayer p, final AbstractMonster m, final boolean upgraded, final boolean freeToPlayOnce, final int energyOnUse) {
        this.freeToPlayOnce = false;
        this.p = p;
        this.m = m;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (upgraded) {
            effect += 1;
        }
        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                this.addToBot(new PlayPotionCardAction(m));
                this.addToBot(new WaitAction(Settings.ACTION_DUR_XFAST));
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}