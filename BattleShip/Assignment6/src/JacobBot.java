/**
 * I, Jacob Robbins, student number 000371194, certify that this material is my original work.
 * No other person's work has been used without due acknowledgment and I have not made my work
 * available to anyone else.
 */

/**
 * A Sample random shooter - Takes no precaution on double shooting and has no strategy once
 * a ship is hit - This is not a good solution to the problem!
 *
 * @author mark.yendt@mohawkcollege.ca (Dec 2021)
 * @adapted jacob.robbins@mohawkcollege.ca (April 2024)
 */

import battleship.*;
import java.awt.Point;
import java.util.*;

/**
 * Class containing properties and methods pertaining to the JacobBot Class, implementing the
 * BattleShipBot class as it's shell structure.
 */
public class JacobBot implements BattleShipBot {
    /**
     *The size of the gameboard.
     */
    private int gameSize;

    /**
     *The battleship bot.
     */
    private BattleShip2 battleShip;

    /**
     *Random variable helps to choose next shots.
     */
    private Random random;

    /**
     * Map of the Cells of the gameboard, helps determine ship location.
     */
    private Map<Point, CellState> cellMap;

    /**
     * Sizes of the remaining ships, helps determine what is left to sink.
     */
    private int[] remainingShipSizes;

    /**
     * Set containing all points already fired upon to ensure no duplicate shots are fired.
     */
    private Set<Point> firedShots;

    /**
     * Priority queue containing preferred points to target for next shots fired.
     */
    private PriorityQueue<Point> targetQueue;

    /**
     * Method used to initialize the gameboard and variables required to complete games.
     * @param b - the battleship bot.
     */
    @Override
    public void initialize(BattleShip2 b) {
        battleShip = b;
        gameSize = b.BOARD_SIZE;
        random = new Random();
        initializeCellMap();
        remainingShipSizes = b.getShipSizes(); // Initialize remaining ships sizes
        firedShots = new HashSet<>();
        targetQueue = new PriorityQueue<>(Comparator.comparingInt(this::getShotPriority));
    }

    /**
     * Method used to initialize and map the cells of the gameboard to Point values.
     */
    private void initializeCellMap() {
        cellMap = new HashMap<>();
        for (int x = 0; x < gameSize; x++) {
            for (int y = 0; y < gameSize; y++) {
                Point point = new Point(x, y);
                cellMap.put(point, CellState.Empty);
            }
        }
    }

    /**
     * Method used to fire the next shot dependent on helper method generateRandomShot(),
     * Method updates the target queue dependent on whether the shot is a hit or miss.
     */
    @Override
    public void fireShot() {
        Point shot;
        if (!targetQueue.isEmpty()) {
            shot = targetQueue.poll();
        } else {
            do {
                shot = generateRandomShot();
            } while (firedShots.contains(shot));
        }

        firedShots.add(shot);
        cellMap.put(shot, CellState.Miss); // Assume miss by default
        boolean hit = battleShip.shoot(shot);

        if (hit) {
            cellMap.put(shot, CellState.Hit);
            updateRemainingShips();
            updateTargetQueue(shot);
        }
    }

    /**
     * Method used to generate random point for next shot fired.
     * @return Point - random point to be chosen for next shot.
     */
    private Point generateRandomShot() {
        int x = random.nextInt(gameSize);
        int y = random.nextInt(gameSize);
        return new Point(x, y);
    }

    /**
     * Method used to update the remaining amount of ships once ships have been sunk.
     */
    private void updateRemainingShips() {
        int[] updatedShipSizes = battleShip.getShipSizes();
        for (int i = 0; i < remainingShipSizes.length; i++) {
            if (updatedShipSizes[i] < remainingShipSizes[i]) {
                System.out.println("Ship of size " + remainingShipSizes[i] + " sunk!");
                remainingShipSizes[i] = updatedShipSizes[i];
                break; // Assuming only one ship of each size, exit loop once found
            }
        }
    }

    /**
     *
     * @param point - Point pertaining to random shot, prioritized by aiming in the center of the board.
     * @return int - The cell prioritized for the next shot.
     */
    private int getShotPriority(Point point) {
        // Example heuristic: prioritize shots closer to the center
        int centerX = gameSize / 2;
        int centerY = gameSize / 2;
        return Math.abs(point.x - centerX) + Math.abs(point.y - centerY);
    }

    /**
     * Method used to update the target queue for preferred next shots
     * dependent on success of previous shots.
     * @param hit - The point values, x,y of the last shot that hit.
     */
    private void updateTargetQueue(Point hit) {
        int x = hit.x;
        int y = hit.y;

        if (x > 0 && cellMap.get(new Point(x - 1, y)) == CellState.Empty) {
            targetQueue.add(new Point(x - 1, y)); // Left
        }
        if (x < gameSize - 1 && cellMap.get(new Point(x + 1, y)) == CellState.Empty) {
            targetQueue.add(new Point(x + 1, y)); // Right
        }
        if (y > 0 && cellMap.get(new Point(x, y - 1)) == CellState.Empty) {
            targetQueue.add(new Point(x, y - 1)); // Up
        }
        if (y < gameSize - 1 && cellMap.get(new Point(x, y + 1)) == CellState.Empty) {
            targetQueue.add(new Point(x, y + 1)); // Down
        }
    }

    /**
     * Method to stand in place of ToString().
     * @return string containing student name.
     */
    @Override
    public String getAuthors() {
        return "Jacob Robbins (CSAIT Student 2024)";
    }
}