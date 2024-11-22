# ART PILLARS

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Android](https://img.shields.io/badge/platform-Android-blue)
![Java](https://img.shields.io/badge/language-Java-orange)
![License](https://img.shields.io/badge/license-MIT-green)

## 📱 Introduction
**Art Pillars** is a modern Android application built with Java. The project aims to give some insight into famous artworks and their creators. Art Pillars is also integrated with an AI-Generated tool that allows users to prompt and download their creations.

---

## 🚀 Features
- **Feature 1**: Artwork information.
- **Feature 2**: Artist information.
- **Feature 3**: Ai-generated tools powered by Stability AI.

---

## 📸 Screenshots

| **Feature**              | **Screenshot**                        |
|---------------------------|----------------------------------------|
| **Login Page**            | <img src="https://github.com/user-attachments/assets/1ef53c85-e073-4585-ab77-1779bd17bafa" alt="Login Page" height="500"> |
| **Artwork Page**          | <img src="https://github.com/user-attachments/assets/790eb816-e9c9-427a-8e62-fd9e45977be3" alt="Artwork Page" height="500"> |
| **Artwork Detail Page**   | <img src="https://github.com/user-attachments/assets/5ea4d0a8-af9e-47a3-ad49-afc6ca951d11" alt="Artwork Detail Page" height="500"> |
| **Artist Profile**        | <img src="https://github.com/user-attachments/assets/819cf39f-7120-44f7-9afb-ddc6c71bfec9" alt="Artist Profile" height="500"> |
| **User Profile**          | <img src="https://github.com/user-attachments/assets/10cdeb68-8b3a-46d9-99f9-94f0d753a488" alt="User Profile" height="500"> |
| **AI-Generated Tool**     | <img src="https://github.com/user-attachments/assets/59c98fa5-a7db-4619-8f35-e4b8da958aab" alt="AI-Generated Tool" height="500"> |

---

## 📂 Project Structure
### Key Components:
```markdown
Android-Art-Gallery-App/
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/
│ │ │ │ └── com/
│ │ │ │ └── example/
│ │ │ │ └── artgallery/
│ │ │ │ ├── activities/ # Activities (screens) of the app
│ │ │ │ ├── adapters/ # Adapters for managing data in views
│ │ │ │ ├── models/ # Data models
│ │ │ │ ├── network/ # Network operations and API calls
│ │ │ │ └── utils/ # Utility classes and helpers
│ │ │ └── res/
│ │ │ ├── layout/ # XML files for UI layouts
│ │ │ ├── drawable/ # Image assets and drawable resources
│ │ │ ├── values/ # Strings, colors, dimensions, and styles
│ │ │ └── mipmap/ # Launcher icons
│ │ └── test/ # Unit tests
│ └── build.gradle # Gradle build script for the app module
├── gradle/ # Gradle wrapper files
├── .gitignore # Files to be ignored by Git
├── build.gradle # Project-level Gradle configuration
├── gradle.properties # Gradle properties file
├── gradlew # Unix script for Gradle wrapper
├── gradlew.bat # Windows script for Gradle wrapper
└── settings.gradle # Settings file for Gradle
```

## 🛠 Tech Stack
- **Language**: Java
- **Framework**: Android SDK
- **Build Tool**: Gradle
- **Database**: Firestore Database & Authentication
- **Architecture**: MVVM / MVP / MVC

---

## 🏗️ Installation

Follow the steps below to set up and run this project locally:

### **1. Clone the Repository**
Start by cloning the project to your local machine:
```bash
git clone https://github.com/GarrettTran/Android-Art-Gallery-App.git
cd Android-Art-Gallery-App
```
### **2. Set Up Firebase**
The app requires Firebase for backend services. To configure Firebase:

Go to the Firebase Console.
Create a new Firebase project.
Download the google-services.json file from your Firebase project settings.
Place the google-services.json file in the app/ directory of the project.

### **3. Create a Stability AI Account**
The app uses Stability AI to generate art. To set this up:

Sign up for an account on Stability AI.
Obtain your API Key (you’ll start with 25 credits, where each prompt costs 3 credits).
Open the file:
```bash
app/java/rmit/ad/assignment1/activity/GenerateArtActivity.java
```
Replace the value of the API_KEY variable with your Stability AI API key:
```java
private static final String API_KEY = "YOUR_API_KEY_HERE";
```
### **4. Sync and Build the Project**
Open the project in Android Studio.
Sync the Gradle files to download all required dependencies.
Build and run the app on an emulator or physical device.


