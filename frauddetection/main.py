from flask import Flask, request
from flask_restful import Resource, Api
from flask_cors import CORS
import prediction

app = Flask(__name__)
cors = CORS(app, resources = { r"*": { "origins": "*" } } )
api = Api(app)
model = prediction.FraudDetectionModel()

class GetPredictionOutput(Resource):
    def post(self):
        try:
            data = request.get_json()
            predict = model.is_fraud(data)
            return { 'result': predict }
        except Exception as error:
            return { 'error': error }

api.add_resource(GetPredictionOutput,'/predict')

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5001)