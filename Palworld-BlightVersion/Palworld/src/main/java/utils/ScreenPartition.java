package utils;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.HashSet;
import java.util.Set;

public class ScreenPartition {
    private static final int GRID_SIZE = 5;
    public static final Set<Integer> assignedPositions = new HashSet<>();
    public static int currentRow = 3;
    public static int currentCol = GRID_SIZE - 1;

    public static void assignSequentialPosition(AbstractMonster am1, AbstractMonster am2) {
        int gridSizeX = Settings.WIDTH / 2 / GRID_SIZE;
        int gridSizeY = (int) (Settings.HEIGHT * 0.9) / GRID_SIZE;

        // Check if all positions are assigned
        if (assignedPositions.size() >= GRID_SIZE * GRID_SIZE) {
            // Reset assigned positions
            assignedPositions.clear();
            currentRow = 0;
            currentCol = GRID_SIZE - 1;
        }

        // Get the next unassigned position
        int position = getNextPosition();
        assignedPositions.add(position);

        // Calculate x and y coordinates for the position
        int x = currentCol * gridSizeX + gridSizeX / 2;
        int y = currentRow * gridSizeY + gridSizeY / 2;

        // Assign positions for am1 and am2
        if (am1 != null) {
            am1.drawX = (float) (x);
            am1.drawY = y;
        }
        if (am2 != null) {
            am2.drawX = Settings.WIDTH - x;
            am2.drawY = y;
        }
    }

    private static int getNextPosition() {
        int position;
        if (currentCol == 3) {

            currentCol --;
        }
        if (currentRow == 3 && currentCol % 2 == 0) {
            position = currentRow * GRID_SIZE + currentCol;
        } else if (currentRow == 1 && currentCol % 2 == 0) {
            position = currentRow * GRID_SIZE + currentCol;
        } else if (currentRow == 3 && currentCol % 2 != 0) {
            position = currentRow * GRID_SIZE + currentCol + GRID_SIZE;
        } else {
            position = currentRow * GRID_SIZE + currentCol + GRID_SIZE;
        }

        if (currentCol == 0) {
            if (currentRow == 3) {
                currentRow = 1;
            } else {
                currentRow = 3;
            }
            currentCol = GRID_SIZE - 1;
        } else {
            currentCol--;
        }

        return position;
    }
}