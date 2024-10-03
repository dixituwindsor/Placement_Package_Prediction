# 📊 Placement Package Prediction System

Welcome to the **Placement Package Prediction System** repository! This project integrates **Machine Learning** with an **Android Application** to predict the package a student is likely to receive during campus placements based on their academic and extracurricular records.

## 📁 Project Overview

The **Placement Package Prediction System** is designed to help students predict their likely placement package based on historical data from previous batches. By entering details such as academic performance, projects, and extracurricular activities, students can receive an estimate of their campus placement package.

This prediction system is built using **Python** for the machine learning model and deployed within an **Android Application** for ease of use.

## 🛠️ Features

- Predicts the campus placement package for students based on their academic record, project work, and achievements.
- Built using **Random Forest Classifier** and **Ensemble Learning** techniques for reliable predictions.
- Integrated with an Android app for easy access by students.
- The prediction is based on historical placement data from the past two years.

## 📱 Android Application

The Android app allows students to:
- **Login/Register**: Access the application with user accounts.
- **Enter Details**: Input details such as academic scores, project work, and extracurricular achievements.
- **Receive Prediction**: View the predicted package based on the machine learning model.
- **Company Listings**: View available companies for campus recruitment.
- **Log Out**: Exit the application and return to the login page.

## ⚙️ Technologies Used

The project integrates both **Machine Learning** and Android development. Below are the key technologies used:

- **Python** for building the machine learning model
- **Android Studio** for creating the mobile application
- **NumPy** and **Pandas** for data preprocessing
- **Scikit-learn** for machine learning algorithms
- **Keras** for deep learning integration (optional)
- **Tkinter** for GUI (in the Python script for testing)
- **Matplotlib** for data visualization in Python

## 🧠 Algorithms Used

1. **Random Forest Classifier**: Used for classification of students based on historical placement data. The Random Forest algorithm creates multiple decision trees and combines them to improve accuracy.
2. **Ensemble Learning**: Combines several models to make predictions. Both **boosting** and **bagging** techniques are applied to improve prediction performance.

## 📈 Data Preprocessing

The following steps were taken to prepare the data for machine learning:

- **Data Cleaning**: Handled missing values by replacing them with mean values.
- **Feature Scaling**: Standardized the range of input data to improve model accuracy.
- **Train/Test Split**: Divided the data into 80% training and 20% testing sets.

## 📚 Python Libraries

The following Python libraries were used to build the machine learning model:

- **NumPy**: For numerical data manipulation.
- **Pandas**: For data analysis and preprocessing.
- **Scikit-learn**: For machine learning algorithms.
- **Matplotlib**: For creating visualizations.
- **Keras**: For potential deep learning extensions.
- **Tkinter**: For creating a simple GUI (if needed for desktop testing).

## 🚀 How It Works

1. **Data Input**: The student inputs their academic data into the Android app.
2. **Model Prediction**: The app sends the data to the Python model, which uses the trained Random Forest algorithm to predict the likely placement package.
3. **Results Displayed**: The predicted package is then displayed on the app along with a list of available companies for recruitment.
