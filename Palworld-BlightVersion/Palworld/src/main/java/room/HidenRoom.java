package room;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class HidenRoom extends AbstractRoom{
        private MapRoomNode teleDest;

        public HidenRoom(MapRoomNode teleDest) {
            this.teleDest = teleDest;
            this.phase = RoomPhase.EVENT;
            this.mapSymbol = "PTL";

        }

        public void onPlayerEntry() {
            AbstractDungeon.overlayMenu.proceedButton.hide();

            this.event.onEnterRoom();
        }

    @Override
    public AbstractCard.CardRarity getCardRarity(int i) {
        return null;
    }
}
