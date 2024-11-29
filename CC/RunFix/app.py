from flask import Flask, request, jsonify
import tensorflow as tf
import numpy as np

app = Flask(__name__)

model = tf.keras.models.load_model('diabetes_risk_model.h5')

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get the data from the request
        data = request.get_json()

        if not all(key in data for key in ["age", "gender", "bmi", "smoking", "alcohol", "activity"]):
            return jsonify({"error": "Missing required data"}), 400

        input_data = np.array([[data["age"], data["gender"], data["bmi"], data["smoking"], data["alcohol"], data["activity"]]])

        # Make prediction
        prediction = model.predict(input_data)
        result = "Yes" if prediction[0] > 0.5 else "No"

        return jsonify({"prediction": result}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=8080)
