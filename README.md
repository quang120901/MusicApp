# Music Application
A full-featured music player Android application built with modern Android development practices and local storage.

## 🚀 Features

### Music Management
- Browse music library from device storage
- Music search and filtering
- Detailed song information display
- Music categories and playlists

### Player Features
- Music playback functionality
- Playlist creation and management
- Queue management
- Playback history
- Shuffle and repeat modes
- Background playback support

### Storage Features
- Local storage for music files
- Playlist persistence
- Playback history tracking
- Favorites management

## 🛠️ Technology Stack

### Platform:
- Android SDK
- Material Design components
- Java

### Storage:
- Local file system

### Build Tool:
- Gradle

## 📋 Prerequisites

Before running this application, make sure you have:
- Android Studio (Arctic Fox or newer recommended)
- Android SDK API Level 21 (Android 5.0) or higher
- JDK 11 or higher

## ⚙️ Installation & Setup

1. **Clone the repository**
```bash
git clone https://github.com/quang120901/MusicApplication.git
cd MusicApplication
```

2. **Open in Android Studio**
- Launch Android Studio
- Select "Open an Existing Project"
- Navigate to `MusicApp/Source` folder
- Click "OK"

3. **Sync Gradle**
- Android Studio will automatically sync Gradle dependencies
- Wait for the sync to complete

4. **Run the Application**
- Connect an Android device via USB (with USB debugging enabled) or start an emulator
- Click the "Run" button (green play icon) in Android Studio
- Select your target device
- The app will build and install automatically

## 📁 Project Structure

```
MusicApp/
├── Source/                     # Application source code
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/      # Java source files
│   │   │   │   ├── res/       # Resources
│   │   │   │   │   ├── layout/      # XML layouts
│   │   │   │   │   ├── drawable/    # Images and icons
│   │   │   │   │   ├── values/      # Strings, colors, styles
│   │   │   │   │   └── ...
│   │   │   │   └── AndroidManifest.xml
│   │   │   ├── androidTest/   # Instrumented tests
│   │   │   └── test/          # Unit tests
│   │   ├── build.gradle.kts   # App-level build config
│   │   └── proguard-rules.pro # ProGuard rules
│   ├── gradle/                # Gradle wrapper
│   ├── build.gradle.kts       # Project-level build config
│   ├── settings.gradle.kts    # Settings configuration
│   └── gradle.properties      # Gradle properties
└── README.md
```

## 🎯 Usage

1. **First Launch:**
   - Grant storage permissions when prompted
   - The app will scan your device for music files

2. **Playing Music:**
   - Browse your music library
   - Tap on a song to start playback
   - Use player controls to manage playback

3. **Creating Playlists:**
   - Navigate to the playlist section
   - Create new playlists
   - Add songs from your library

4. **Managing Queue:**
   - View current playback queue
   - Reorder songs
   - Add or remove tracks

## 🔒 Permissions

This application requires the following permissions:
- **READ_EXTERNAL_STORAGE**: To access music files on your device
- **WRITE_EXTERNAL_STORAGE**: To manage playlists and metadata (Android 9 and below)
- **FOREGROUND_SERVICE**: For background music playback
- **WAKE_LOCK**: To keep playback active when screen is off

## Screenshots

### Main Interface
![Main Interface](https://github.com/user-attachments/assets/c68f1ee4-58a1-4067-9695-96829dcec59f)

### Music Player Interface
![Music Player](https://github.com/user-attachments/assets/96242905-ce92-4864-8f97-c368e121a3c5)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is for educational purposes. Please check local laws regarding software licensing.
