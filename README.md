# WeatherTrack App

WeatherTrack is a simple Android app designed for users who want to track daily weather statistics in their city. The app fetches current weather data from a mock API, stores it locally every 6 hours, and presents a daily and weekly summary with temperature trends.

---

## Screenshots

### Main Screen  
![Main Screen](screenshots/main_screen.png)

### Weekly Summary  
![Weekly Summary](screenshots/weekly_summary.png)

### Detailed Day View  
![Detailed Day View](screenshots/detailed_day_view.png)

## Features

### 1. Fetch Weather Data
- Retrieves weather information including temperature, humidity, and condition from a mock API or static JSON.
- Each fetch saves a record in a local Room database with an associated timestamp.

### 2. Auto Background Sync
- Uses WorkManager to schedule automatic weather data fetch and storage every 6 hours.
- Provides a manual refresh button in the UI for on-demand updates.

### 3. Weekly Summary Screen
- Displays a temperature trend graph or list summarizing data over the last 7 days.
- Allows users to tap on any day to view detailed weather information for that day.

### 4. App Architecture
- Developed in Java following MVVM (Model-View-ViewModel) or Clean Architecture principles.
- Clean separation of concerns with layers for ViewModel, Repository, and Data sources.

### 5. Error Handling
- Shows user-friendly messages for:
  - No internet connectivity
  - API call failures or errors
  - Database read/write issues

---

## Tech Stack
- Language: Java
- Architecture: MVVM / Clean Architecture
- Local Storage: Room Database
- Background Tasks: WorkManager
- UI: Android Views with MPAndroidChart for graphs

---

## Getting Started

1. Clone the repository.
2. Open in Android Studio.
3. Build and run on an emulator or physical device.
4. Use the manual refresh button or wait for automatic sync.
5. Navigate to the Weekly Summary screen to see temperature trends.

---

## Notes
- The app uses a mock API or static JSON as the weather data source.
- Designed to demonstrate clean architecture and background syncing techniques.
- Ideal starter project for weather tracking and Android architecture learning.

---

Feel free to contribute or raise issues!
