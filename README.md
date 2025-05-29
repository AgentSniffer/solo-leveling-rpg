# 🎮 My Java Game Project

> A simple console-based game menu in Java.

---

<details>
  <summary>📁 Files for Main Program:</summary>

* **`Main.java`**
* **`MainMenu.java`**
* **`Display.java`**
* **`Messages.java`**

</details>

<details>
  <summary>🧪 Extra / Test Files (if any):</summary>

* _(Optional test files can go here, if you add more later)_

</details>

---

<div align="center">

## ⬇️ Download & Setup
</div>

<details>
  <summary>💻 WINDOWS</summary>

* Download Git:  
  [Git for Windows](https://github.com/git-for-windows/git/releases)

* Download OpenJDK (e.g. JDK 17 or 21):  
  [JDK Download](https://jdk.java.net/)

* Open your code editor (like VS Code or IntelliJ)

* Clone the repo:
    ```bash
    git clone https://github.com/YOUR_USERNAME/YOUR_PROJECT.git
    cd YOUR_PROJECT/
    ```

</details>

<details>
  <summary>📱 ANDROID (Termux)</summary>

* Download Termux:  
  [F-Droid Link](https://f-droid.org/repo/com.termux_1021.apk)

* Setup and install Java:
    ```bash
    pkg update && pkg upgrade -y
    pkg install git openjdk-17 -y
    termux-setup-storage
    cd ~/storage/downloads
    git clone https://github.com/YOUR_USERNAME/YOUR_PROJECT.git
    cd YOUR_PROJECT/
    ```

</details>

---

<div align="center">

## ▶️ How to Run
</div>

```bash
javac -d bin src/*.java
java -cp bin Main
