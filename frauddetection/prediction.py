import pickle
import pandas as pd
import sklearn as sk


class FraudDetectionModel:
    def __init__(self):
        pkl_filename = "model.pkl"
        with open(pkl_filename, 'rb') as f_in:
            model = pickle.load(f_in)
        self.model = model

    def is_fraud(self, transaction):
        if type(transaction) == dict:
            df = pd.DataFrame(transaction)
        else:
            df = transaction
        type_value =' type_' + df['type'].iloc[0]
        new_columns = df.columns.drop('type')
        df = df[new_columns]
        self.safe_add_column(df, 'type_CASH_IN', type_value == 'type_CASH_IN')
        self.safe_add_column(df, 'type_CASH_OUT', type_value == 'type_CASH_OUT')
        self.safe_add_column(df, 'type_CREDIT', type_value == 'type_CREDIT')
        self.safe_add_column(df, 'type_DEPOSIT', type_value == 'type_DEPOSIT')
        self.safe_add_column(df, 'type_PAYMENT', type_value == 'type_PAYMENT')
        self.safe_add_column(df, 'type_TRANSFER', type_value == 'type_TRANSFER')
        print(df)
        y_pred = self.model.predict(df)
        print(y_pred)

        if y_pred == 0:
            return 'Not Fraud'
        return 'Fraud'

    @staticmethod
    def safe_add_column(df, column_name, flag=False):
        if column_name not in df.columns:
            if flag:
                df[column_name] = 1
            else:
                df[column_name] = 0
        return df