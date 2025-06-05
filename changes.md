# Solo Leveling RPG - Changelog

## Project Overview

A text-based RPG inspired by the Solo Leveling series.

## Current Project Structure (as of 2025-06-04 01:25)

```
src/
├── Main.java          # Main entry point
└── cli/
    ├── cliUtils.java  # CLI utilities and menu handling
    └── cliLogin.java   # Login interface
```

## Implementation Timeline

### [2025-06-04 01:20] Initial Setup

- Created project structure
- Set up basic Java project
- Initialized version control

### [2025-06-04 01:22] cliUtils Implementation

- Added base CLI functionality
- Implemented menu display system
- Added input handling utilities

### [2025-06-04 01:23] cliLogin Implementation

- Extended cliUtils
- Set up basic login interface
- Integrated with main menu system

### [2025-06-04 01:25] Main Class Update

- Created application entry point
- Integrated login interface
- Added basic menu flow

## Example Usage

```java
// In Main.java
public static void main(String[] args) {
    cliLogin login = new cliLogin();
    login.displayMenu("Welcome to Solo Leveling RPG");
}
```
