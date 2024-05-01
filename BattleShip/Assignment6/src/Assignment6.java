/**
 * I, Jacob Robbins, student number 000371194, certify that this material is my original work.
 * No other person's work has been used without due acknowledgment and I have not made my work
 * available to anyone else.
 */

import battleship.BattleShip2;

/**
 * Starting code for COMP10205 - Assignment#6 - Version 2 of BattleShip
 * @author mark.yendt@mohawkcollege.ca (Dec 2021)
 * @adapted jacob.robbins@mohawkcollege.ca (April 2024)
 */

/**
 * Class containing the Entry/Exit point of the application.
 */
public class Assignment6 {

    /**
     * The Entry/Exit point of the application.
     * @param args - never used
     */
    public static void main(String[] args) {

        // DO NOT add any logic to this code
        // All logic must be added to your Bot implementation
        // see fireShot in the ExampleBot class

        final int NUMBEROFGAMES = 10000;
        System.out.println(BattleShip2.getVersion());
        BattleShip2 battleShip = new BattleShip2(NUMBEROFGAMES, new JacobBot());
        int [] gameResults = battleShip.run();

        // You may add some analysis code to look at all the game scores that are returned in gameResults
        // This can be useful for debugging purposes.

        battleShip.reportResults();
    }
}
