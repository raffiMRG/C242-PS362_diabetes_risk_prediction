from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
from google.cloud import storage
import numpy as np
import os

app = Flask(__name__)

# Fungsi untuk mendownload model dari GCS
def download_model_from_gcs(bucket_name, model_path, local_path):
    client = storage.Client()
    bucket = client.bucket(bucket_name)
    blob = bucket.blob(model_path)
    blob.download_to_filename(local_path)
    print(f"Model downloaded from {bucket_name}/{model_path} to {local_path}")

BUCKET_NAME = "diabtic-capstone-project.appspot.com" 
MODEL_PATH = "diabetes_risk_model.h5"
LOCAL_MODEL_PATH = "diabetes_risk_model.h5"

# Download model saat container dimulai
download_model_from_gcs(BUCKET_NAME, MODEL_PATH, LOCAL_MODEL_PATH)
model = load_model(LOCAL_MODEL_PATH)

@app.route('/')
def home():
    return jsonify({"message": "Welcome to the Flask App!"})

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        features = np.array(data['features']).reshape(1, -1)
        prediction = model.predict(features)
        result = "yes" if prediction[0][0] > 0.5 else "no"
        return jsonify({"prediction": result})
    except Exception as e:
        return jsonify({"error": str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
