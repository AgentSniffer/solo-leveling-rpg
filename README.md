# Solo Leveling RPG Menu System

A structured console-based RPG menu system written in Java, inspired by the Solo Leveling universe. This project demonstrates modular design principles, clean class separation, and intuitive menu-driven navigation for RPG game development.

## 🎮 Features

- **Modular Architecture**: Clean separation of concerns across multiple Java classes
- **Console-Based Interface**: Terminal-friendly RPG menu system
- **Extensible Design**: Built to accommodate future RPG game logic implementation
- **Database Ready**: Optional MySQL integration for data persistence
- **Cross-Platform**: Runs on Windows, Linux, macOS, and Termux

## 📁 Project Structure

```
src/
├── Main.java              # Application entry point
├── MainMenu.java          # Menu selection and input logic
├── Messages.java          # Centralized game and system messages
├── Display.java           # Terminal output management
├── UI.java                # User interface and visual elements
├── SoloLevelingRPG.java   # Main RPG logic (placeholder)
├── Database.java          # Database connectivity (optional)
└── test.java              # Development testing scratchpad
```

### File Descriptions

| File | Purpose |
|------|---------|
| `Main.java` | Entry point of the application |
| `MainMenu.java` | Handles menu selection and input logic |
| `Messages.java` | Centralized game and system messages |
| `Display.java` | Clears terminal output and manages display |
| `UI.java` | User interface display and visual elements |
| `SoloLevelingRPG.java` | Placeholder for main RPG game logic |
| `Database.java` | Database connection and setup (optional) |
| `test.java` | Temporary test and development scratchpad |

## 📋 Prerequisites

- **Java JDK 17** or higher
- **Git** (optional, for version control)
- **MySQL JDBC Connector** (only if using database features)

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/solo-leveling-rpg.git
cd solo-leveling-rpg
```

### 2. Create Build Directory
```bash
mkdir bin
```

### 3. Compile the Project
```bash
javac -d bin src/*.java
```

## 🚀 Running the Application

### Standard Mode (No Database)
```bash
java -cp bin Main
```

### With MySQL Database Support

**Windows:**
```bash
java -cp "bin;lib/mysql-connector-j-9.3.0.jar" Database
```

**Linux / macOS / Termux:**
```bash
java -cp "bin:lib/mysql-connector-j-9.3.0.jar" Database
```

## 🎯 Usage

1. **Launch the application** using one of the run commands above
2. **Navigate through menus** using the console interface
3. **Follow on-screen prompts** for menu selection and input
4. **Exit gracefully** through the main menu options

## 🏗️ Development Status

- ✅ **Menu System**: Complete and functional
- ✅ **Modular Architecture**: Implemented with clean separation
- ✅ **Console Interface**: Working terminal-based UI
- 🔄 **Game Logic**: In development (placeholder structure ready)
- 🔄 **Database Integration**: Optional feature available
- 📋 **Future Plans**: RPG mechanics, character progression, combat system

## 🔄 Updating

Keep your local repository up to date:
```bash
git pull origin main
```

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/new-feature`)
3. **Make your changes** following the existing code structure
4. **Test thoroughly** using the console interface
5. **Commit your changes** (`git commit -m 'Add new feature'`)
6. **Push to your branch** (`git push origin feature/new-feature`)
7. **Open a Pull Request**

### Code Style Guidelines
- Follow Java naming conventions
- Maintain modular class structure
- Add comments for complex logic
- Test all menu navigation paths

## 🛠️ Technical Details

### Architecture
- **MVC Pattern**: Separation of menu logic, display, and data
- **Console I/O**: Scanner-based input handling
- **Modular Design**: Each class has a specific responsibility
- **Database Layer**: Optional MySQL integration for persistence

### Dependencies
- Java Standard Library
- MySQL Connector/J (optional)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🎮 Inspiration

Inspired by the Solo Leveling manhwa series and classic console RPG games.

## 📊 Project Status

![Development Status](https://img.shields.io/badge/status-in%20development-yellow)
![Java](https://img.shields.io/badge/java-17%2B-orange)
![Platform](https://img.shields.io/badge/platform-cross%20platform-blue)

---

⭐ **Interested in RPG development? Give this project a star!** ⭐