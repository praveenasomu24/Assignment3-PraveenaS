# Assignment 3 - Location Logger App

## Introduction
The Location Logger application is an Android-based mobile application developed in Java using Android Studio.  
The main objective of this app is to capture the device’s current geographic location, display the latest location information, and maintain a local history of saved location records.

This project demonstrates key Android concepts such as location services, runtime permissions, service lifecycle management, and local data persistence.

With the growing use of location-aware applications in navigation and tracking systems, this project provides practical experience in handling real-time device location efficiently and reliably.


## Application Functionality

The application provides a simple interface with **Start Tracking** and **Stop Tracking** controls.

- When tracking is started, runtime location permission is requested if not already granted.
- The app uses `FusedLocationProviderClient` to fetch the device’s latest location.
- Latitude, longitude, and timestamp are displayed in real time.
- Location tracking is handled using an Android Service for background processing.
- Each location update is stored locally and displayed as history.
- Data persists even after restarting the application.


## Features Implemented

### Location Services
- Real-time GPS location tracking
- Latitude and longitude display
- Timestamp generation for each update
- Safe handling of unavailable location data

### Android Service Integration
- Background service for location updates
- Proper service start and stop handling
- Separation of UI and background logic

### Local Storage
- Uses SharedPreferences for persistence
- Stores latest and historical location data
- Data survives app restart

### User Interface
- Start Tracking button
- Stop Tracking button
- Live location display
- Tracking status indicator
- Location history section

### Robustness
- Runtime permission handling
- Graceful permission denial handling
- Null safety for location data
- Controlled service lifecycle



## System Design

The app uses a single-activity architecture for simplicity and ease of use.

- `FusedLocationProviderClient` → Efficient location retrieval
- `Android Service` → Background location processing
- `SharedPreferences` → Lightweight local storage

This design ensures modularity, efficiency, and battery optimization.



##Testing and Validation

The application was tested for:

- Runtime permission approval and denial scenarios
- Continuous location updates during tracking
- Service start/stop behavior
- Data persistence after app restart
Praveena Somu
