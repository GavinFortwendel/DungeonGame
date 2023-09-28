import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TextBasedGame {

    private static int numPotionsUsed = 0;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int enemiesDefeated = Game();
        long end = System.currentTimeMillis();
        long milliseconds = end-start;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = (TimeUnit.MILLISECONDS.toSeconds(milliseconds)) % 60;

        System.out.println("\n#######################");
        System.out.println("Final Statistics:");
        System.out.println("\tEnemies slain: " + enemiesDefeated);
        System.out.println("\tNumber of Health Potions Used: " + numPotionsUsed);
        System.out.println("\tTime spent in dungeon: " + minutes + " minute(s) and " + seconds + " second(s)\n");
        System.out.println("Thanks for playing!");
        System.out.println("#######################");
    }

    public static int Game() {

        //System Objects
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        //Game variables
        String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin", "Dragon", "Goblin", "Ogre"};
        int maxEnemyHealth = 75;
        int enemyAttackDamage = 25;
        int enemiesDefeated = 0;

        //Player variables
        int playerHealth = 100;
        int attackDamage = 50;
        int numHealthPotions = 3;
        int healthPotionHealAmount = 30;
        int healthPotionDropChance = 50; //Percentage

        boolean running = true;

        System.out.println("Welcome to the Dungeon!");

        GAME:
        while(running) {
            System.out.println("---------------------------------------------");
            int enemyHealth = random.nextInt(maxEnemyHealth);
            String enemy = enemies[random.nextInt(enemies.length)];
            System.out.println("\t# " + enemy + " has appeared! #\n");

            while(enemyHealth > 0) {
                System.out.println("\tYour HP: " + playerHealth);
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
                System.out.println("\tNumber of health potions: " + numHealthPotions);
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Drink health potion");
                System.out.println("\t3. Run!");

                String input = scanner.nextLine();
                if (input.equals("1")) {
                    int damageDelt = random.nextInt(attackDamage);
                    int damageTaken = random.nextInt(enemyAttackDamage);

                    enemyHealth -= damageDelt;
                    playerHealth -=damageTaken;

                    System.out.println("\t> You strike the " + enemy + ", dealing " + damageDelt + " damage!");
                    System.out.println("\t> You receive " + damageTaken + " damage from the enemy in retaliation!");

                    if (playerHealth < 1) {
                        System.out.println("\t> You have taken too much damage and are too weak to go on!\n");
                        break;
                    }
                } else if (input.equals("2")) {
                    if(numHealthPotions > 0) {
                        numPotionsUsed++;
                        playerHealth += healthPotionHealAmount;
                        numHealthPotions--;
                        if (playerHealth >= 100) {
                            playerHealth = 100;
                            System.out.println("\t> You drank a health potion, which has brought you back to full health."
                                            + "\n\t> You now have " + playerHealth + " HP!"
                                            + "\n\t> You have " + numHealthPotions + " health potions left.");
                        } else {
                            System.out.println("\t> You drank a health potion, healing yourself for " + healthPotionHealAmount + " HP."
                                    + "\n\t> You now have " + playerHealth + " HP!"
                                    + "\n\t> You have " + numHealthPotions + " health potions left.");
                        }
                    } else {
                        System.out.println("\t> You have no health potions left. Defeat enemies for a chance to get one!");
                    }
                } else if (input.equals("3")) {
                    System.out.println("\t> You run away from the " + enemy + "!");
                    continue GAME;
                } else {
                    System.out.println("\tInvalid command, try again!");
                }
            }

            if (playerHealth < 1) {
                System.out.println("You slowly limp out of the dungeon, weak from battle.");
                break;
            }

            System.out.println("---------------------------------------------");
            System.out.println(" # " + enemy + " was defeated! #");
            System.out.println(" # You have " + playerHealth + " HP left. #");
            enemiesDefeated++;
            if (random.nextInt(100) < healthPotionDropChance) {
                if (numHealthPotions == 3) {
                    System.out.println(" # The " + enemy + " dropped a health potion, but you can't carry anymore!");
                } else {
                    numHealthPotions++;
                    System.out.println(" # The " + enemy + " dropped a health potion! # ");
                    System.out.println(" # You now have " + numHealthPotions + " health potion(s). #");
                }
            }
            System.out.println("---------------------------------------------");
            System.out.println("What would you like to do?");
            System.out.println("1. Continue fighting");
            System.out.println("2. Exit the dungeon");

            String input = scanner.nextLine();

            while (!input.equals("1") && !input.equals("2")) {
                System.out.println("Invalid command, try again!");
                input = scanner.nextLine();
            }

            if (input.equals("1")) {
                System.out.println("You continue on your adventure!");
            }
            else if (input.equals("2")) {
                System.out.println("You exit the dungeon, successful from your adventures!");
                break;
            }
        }
        return enemiesDefeated;
    }
}
