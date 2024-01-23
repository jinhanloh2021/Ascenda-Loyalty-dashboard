# Now import the Lambda function
import sys
import os

# Add the directory containing your Lambda function code to the Python path
lambda_function_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "lambda_function"))
sys.path.append(lambda_function_dir)

from lambda_function import lambda_handler
import unittest
from unittest.mock import Mock
import json 


class TestLambdaFunction(unittest.TestCase):
    def test_lambda_handler(self):
        event = {
            "Records": [
                {
                    "body": json.dumps({"log_data": "Test log data"}),
                }
            ]
        }
        context = Mock()
        response = lambda_handler(event, context)
        self.assertEqual(response['statusCode'], 200)

if __name__ == '__main__':
    unittest.main()