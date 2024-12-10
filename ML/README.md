# README: Diabtic - Diabetes Risk Prediction Application (ML)

## C242-PS362_diabetes_risk_prediction
This repository are used for capstone project from grup id C242-PS362 

| Nama          | Student ID | Path              | LinkedIn                                      |
|---------------|------------|-------------------|-----------------------------------------------|
| Irendra Lintang Keksi             | M283B4KX1998 | Machine Learning   | [Irendra Lintang Keksi](https://www.linkedin.com/in/irendra-lintang)          |
| Luthfiyyah Wahyu Nurfarida        | M283B4KX2311 | Machine Learning   | [Luthfiyyah Wahyu Nurfarida](https://www.linkedin.com/in/fiyyahwahyu)      


## Overview
Diabtic is a machine learning-driven application designed to assist users in predicting their risk of developing diabetes. The program analyzes health data such as BMI, blood pressure, cholesterol levels, and physical activity to provide personalized risk predictions and health insights.

## Features
1. Data Cleaning: Automatically handles irrelevant or redundant data.
2. Data Visualization: Provides insights through correlation matrices and feature distribution graphs.
3. Machine Learning Models:
   Random Forest
   XGBoost
   Support Vector Machine (SVM)
   Deep Neural Network (DNN)
4. Ensemble Learning: Combines the strengths of multiple models for improved prediction accuracy.
5. Saved Models: Pre-trained models are saved for reuse without retraining.
6. Interactive Prediction: Allows predictions based on new input data.

## Prerequisites
1. Environment Setup:
   Python 3.8 or higher.
   Required libraries: pandas, numpy, seaborn, matplotlib, sklearn, xgboost, tensorflow, joblib.
2. Install Libraries: Use the following command to install all required libraries:
   ```
   pip install pandas numpy seaborn matplotlib scikit-learn xgboost tensorflow joblib
   ```
3. Dataset:
   [dataset](https://raw.githubusercontent.com/IrendraLintang/Diabtic/refs/heads/main/diabetes_data.csv)

## How to use
1. Run the Program:
   Save the Python file (diabtic_fix.py) to your system.
   Open a terminal and navigate to the file's directory.
   Run the script using
   ```
   python diabtic_fix.py
   ```
2. Outputs:
   Data Visualization: heatmaps and histograms show data relationships and distributions.
   Model Training: accuracy metrics for Random Forest, XGBoost, SVM, and DNN models.
   Prediction Results: ensemble predictions combining all models.
   Confusion Matrix: visualizes the performance of the ensemble model.

## Key Insights for Users
The program automatically scales and processes data for machine learning models.
Outputs are presented in an easy-to-understand format, including charts and metrics.
Ensemble learning improves prediction reliability by combining multiple models.
