
// ─────────────────────────────────────────────────────────────
// ▼ Messages - Stores all messages in one place
// ─────────────────────────────────────────────────────────────
public class Messages {

    // ─────────────────────────────────────────────────────────────
    // ▼ Game Logo
    // ─────────────────────────────────────────────────────────────
    static final String ASCII_GAME_LOGO = "                ██████╗ █████╗ ██╗      █████╗ \n"
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
    static final String PROMPT_PRESS_ENTER          = "Press Enter to continue...";

    // ─────────────────────────────────────────────────────────────
    // ▼ Menu Input Validation Messages
    // ─────────────────────────────────────────────────────────────

    static final String ERROR_INVALID_MENU_CHOICE   = "⚠️  SYSTEM: Please choose between 1 and %d!%n";
    static final String ERROR_NON_NUMERIC_INPUT     = "🔢 SYSTEM: Numbers only, hunter!%n";
    static final String ERROR_EMPTY_MENU_INPUT      = "❓ SYSTEM: Please enter a choice!%n";
    static final String SUCCESS_MENU_CHOICE         = "✅ SYSTEM: You chose: %s%n";

    // ─────────────────────────────────────────────────────────────
    // ▼ Main Menu Options & Actions
    // ─────────────────────────────────────────────────────────────

    static final String MENU_OPTIONS                = "1. Start New Game\n"
                                                    + "2. Load Game\n"
                                                    + "3. Options\n"
                                                    + "4. Login\n"
                                                    + "5. Register\n"
                                                    + "6. Exit";

    static final String ACTION_START_GAME           = "▶️  Starting your adventure";
    static final String ACTION_LOAD_GAME            = "📂 Loading your saved journey";
    static final String ACTION_OPTIONS              = "⚙️  Adjusting your experience";
    static final String ACTION_LOGIN                = "🔐 Entering the gate";
    static final String ACTION_REGISTER             = "📝 Joining the hunter's guild";
    static final String ACTION_EXIT                 = "👋 Until we meet again, hunter. Arise!";

    // ─────────────────────────────────────────────────────────────
    // ▼ Player Input Messages
    // ─────────────────────────────────────────────────────────────

    static final String PROMPT_ENTER_NAME           = "👤 What should we call you, hunter?%n";
    static final String SUCCESS_NAME_ENTERED        = "✨ Welcome, %s! The dungeon awaits!%n";
    static final String ERROR_NAME_EMPTY            = "❌ A hunter must have a name! Try again.%n";
    static final String ERROR_NAME_FORMAT           = "🚫 Only letters, hunter! Try again.%n";

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
    // ▼ Player Name Prompts & Greetings
    // ─────────────────────────────────────────────────────────────

    static final String PROMPT_HUNTER_NAME          = "👤 What is your hunter name? ";
    static final String NOTICE_NO_NAME_PROVIDED     = "🤔 No name entered, so you are %s!";
    static final String GREETING_PLAYER             = "👋 Hello, %s!";

    // ─────────────────────────────────────────────────────────────
    // ▼ Player Progress Messages
    // ─────────────────────────────────────────────────────────────

    static final String NOTICE_PLAYER_WEAKEST       = "\nYou are now the weakest hunter!";
    static final String NOTICE_PLAYER_GOAL          = "Fight monsters and become super strong!";

    // ─────────────────────────────────────────────────────────────
    // ▼ In-Game Menu Options
    // ─────────────────────────────────────────────────────────────

    static final String GAME_MENU_OPTIONS           = "1. Fight monsters in dungeon\n"
                                                    + "2. Rest to get health back\n"
                                                    + "3. Look at my stats\n"
                                                    + "4. Stop playing";

    // ─────────────────────────────────────────────────────────────
    // ▼ Battle Messages
    // ─────────────────────────────────────────────────────────────

    static final String NOTICE_ENTER_DUNGEON        = "\n🏰 Entering dungeon...";
    static final String NOTICE_BATTLE_TIME          = "\n⚔️ BATTLE TIME! ⚔️";

    static final String BATTLE_HP_STATUS            = "Your HP: %d | %s HP: %d";

    // ─────────────────────────────────────────────────────────────
    // ▼ Battle Menu Options
    // ─────────────────────────────────────────────────────────────

    static final String BATTLE_MENU_OPTIONS         = "1. Attack the monster\n"
                                                    + "2. Run away";

    static final String NOTICE_BATTLE_VICTORY       = "\n🎉 You killed the %s!";
    static final String NOTICE_BATTLE_FLEE          = "🏃 You run away from the %s!";

    // ─────────────────────────────────────────────────────────────
    // ▼ Stats & Game Over Messages
    // ─────────────────────────────────────────────────────────────

    static final String STATS_DISPLAY               = "📊 Your stats are shown above!";

    static final String GAME_OVER_QUIT_MESSAGE      = "👋 Thanks for playing, %s!";
    static final String GAME_OVER_DEATH_MESSAGE     = "\n💀 %s died! Game Over!";

    // ─────────────────────────────────────────────────────────────
    // ▼ Developer Note
    // ─────────────────────────────────────────────────────────────
    /*
     * Trivia: Spent 2 hours crafting these messages
     * Only to realize players will probably skip them anyway...
     * But hey, at least we had fun! 😄
     */
}
