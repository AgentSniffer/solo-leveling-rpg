
// ──────────────────────────────────────────────────────────────
// ▼ Messages - Stores reusable text content (ASCII art, menus)
//   Only for content that's used in multiple places or too long
//   to put inline without cluttering the main game logic
//   or use methods like printBox() to generate box text
// ────────────────────────────────────────────────────────────── 
public class Messages {

    // ─────────────────────────────────────────────────────────────
    // ▼ Game Logo
    // ─────────────────────────────────────────────────────────────
    static final String ASCII_GAME_LOGO             = "                ██████╗ █████╗ ██╗      █████╗ \n"
                                                    + "               ██╔════╝██╔══██╗██║     ██╔══██╗\n"
                                                    + "               ╚█████╗ ██║  ██║██║     ██║  ██║\n"
                                                    + "                ╚═══██╗██║  ██║██║     ██║  ██║\n"
                                                    + "               ██████╔╝╚█████╔╝███████╗╚█████╔╝\n"
                                                    + "               ╚═════╝  ╚════╝ ╚══════╝ ╚════╝ \n"
                                                    + "██╗     ███████╗██╗   ██╗███████╗██╗     ██╗███╗  ██╗ ██████╗ \n"
                                                    + "██║     ██╔════╝██║   ██║██╔════╝██║     ██║████╗ ██║██╔════╝ \n"
                                                    + "██║     █████╗  ╚██╗ ██╔╝█████╗  ██║     ██║██╔██╗██║██║  ██╗ \n"
                                                    + "██║     ██╔══╝   ╚████╔╝ ██╔══╝  ██║     ██║██║╚████║██║  ╚██╗\n"
                                                    + "███████╗███████╗  ╚██╔╝  ███████╗███████╗██║██║ ╚███║╚██████╔╝\n"
                                                    + "╚══════╝╚══════╝   ╚═╝   ╚══════╝╚══════╝╚═╝╚═╝  ╚══╝ ╚═════╝ \n";

    // ─────────────────────────────────────────────────────────────
    // ▼ System & General Messages
    // ─────────────────────────────────────────────────────────────
    static final String SYSTEM_ERROR_UNKNOWN        = "💥 SYSTEM: Something went wrong! The System is confused! Try again!%n";
 
    // ─────────────────────────────────────────────────────────────
    // ▼ Menu Input Validation Messages
    // ─────────────────────────────────────────────────────────────
    static final String ERROR_INVALID_MENU_CHOICE   = "⚠️ SYSTEM: Please choose between 1 and %d!%n";
    static final String ERROR_NON_NUMERIC_INPUT     = "🔢 SYSTEM: Numbers only, hunter!%n";
    static final String ERROR_EMPTY_MENU_INPUT      = "❓ SYSTEM: Please enter a choice!%n";

    static final String ERROR_NAME_EMPTY            = "❌ A hunter must have a name! Try again.%n";
    static final String ERROR_NAME_FORMAT           = "🚫 Only letters, hunter! Try again.%n";

    // ─────────────────────────────────────────────────────────────
    // ▼ Main Menu Options & Actions
    // ──────────-──────────────────────────────────────────────────
    static final String MENU_OPTIONS                = "1. Start New Game\n"
                                                    + "2. Load Game\n"
                                                    + "3. Options\n"
                                                    + "4. Login\n"
                                                    + "5. Register\n"
                                                    + "6. Exit";

    // ─────────────────────────────────────────────────────────────
    // ▼ Game State Messages
    // ─────────────────────────────────────────────────────────────
    static final String SUCCESS_GAME_SAVE           = "💾 Progress saved successfully!";
    static final String ERROR_GAME_LOAD_FAIL        = "⚠️ No saved game found. Starting a new adventure!";

    // ─────────────────────────────────────────────────────────────
    // ▼ Welcome & Intro Screens
    // ─────────────────────────────────────────────────────────────
    static final String WELCOME_SCREEN              = "                      High Score: %d\n"
                                                    + "                 Welcome to Solo Leveling\n\n";

    static final String GAME_INTRO_WELCOME          = "🎮 SOLO LEVELING RPG 🎮\n"
                                                    + "Welcome to the world of hunters!\n"
                                                    + "You are about to become a hunter...";

    // ─────────────────────────────────────────────────────────────
    // ▼ In-Game Menu Options
    // ─────────────────────────────────────────────────────────────
    static final String GAME_MENU_OPTIONS           = "1. Fight monsters in dungeon\n"
                                                    + "2. Rest to get health back\n"
                                                    + "3. Look at my stats\n"
                                                    + "4. Stop playing";

    // ─────────────────────────────────────────────────────────────
    // ▼ Battle Menu Options
    // ─────────────────────────────────────────────────────────────
    static final String BATTLE_MENU_OPTIONS         = "1. Attack the monster\n"
                                                    + "2. Run away";

    // ─────────────────────────────────────────────────────────────
    // ▼ Developer Note
    // ─────────────────────────────────────────────────────────────
    /*
     * Trivia: Spent 2 hours crafting these messages
     * Only to realize players will probably skip them anyway...
     * But hey, at least we had fun! 😄
     */
}
