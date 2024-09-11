from flask import Flask, request, jsonify
import pickle
import numpy as np

model_classification = pickle.load(open('RFC.pkl', 'rb'))
model_regression = pickle.load(open('RFR.pkl', 'rb'))

app = Flask(__name__)


@app.route('/')
def home():
    return "Hello world"


@app.route('/predict', methods=['POST', 'GET'])
def predict():
    ssc_p = request.form.get('ssc_p')
    hsc_p = request.form.get('hsc_p')
    degree_p = request.form.get('degree_p')
    etest_p = request.form.get('etest_p')
    mba_p = request.form.get('mba_p')
    gender_encoded = request.form.get('gender_encoded')
    ssc_b_encoded = request.form.get('ssc_b_encoded')
    hsc_b_encoded = request.form.get('hsc_b_encoded')
    workex_encoded = request.form.get('workex_encoded')
    specialisation_encoded = request.form.get('specialisation_encoded')
    degree_t_commmgmt = request.form.get('degree_t_commmgmt')
    degree_t_others = request.form.get('degree_t_others')
    degree_t_scitech = request.form.get('degree_t_scitech')
    hsc_s_arts = request.form.get('hsc_s_arts')
    hsc_s_commerce = request.form.get('hsc_s_commerce')
    hsc_s_science = request.form.get('hsc_s_science')

    # sc = StandardScaler()
    # data = np.array([[ssc_p, hsc_p, degree_p, etest_p, mba_p, gender_encoded, ssc_b_encoded, hsc_b_encoded, workex_encoded, specialisation_encoded, degree_t_commmgmt, degree_t_others, degree_t_scitech, hsc_s_arts, hsc_s_commerce, hsc_s_science]]).astype(float)

    # data = sc.fit_transform(data)

    # print(data)

    input_query1 = np.array([[ssc_p, hsc_p, degree_p, etest_p, mba_p, gender_encoded, ssc_b_encoded, hsc_b_encoded, workex_encoded, specialisation_encoded, degree_t_commmgmt, degree_t_others, degree_t_scitech, hsc_s_arts, hsc_s_commerce, hsc_s_science]])
    result = str(model_classification.predict(input_query1)[0])

    if result == "1":
        input_query2 = np.array([[ssc_p, hsc_p, degree_p, etest_p, mba_p, gender_encoded, ssc_b_encoded, hsc_b_encoded, workex_encoded, specialisation_encoded, degree_t_commmgmt, degree_t_scitech, hsc_s_commerce, hsc_s_science]])
        final = str(model_regression.predict(input_query2)[0])

    else:
        final = "Can not be placed"

    return jsonify({'placement': final})


if __name__ == '__main__':
    app.run(debug=True)
