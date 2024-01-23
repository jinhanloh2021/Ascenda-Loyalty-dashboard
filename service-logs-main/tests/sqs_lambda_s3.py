# Now import the Lambda function
import sys
import os

# Add the directory containing your Lambda function code to the Python path
lambda_function_dir = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "lambda_function"))
sys.path.append(lambda_function_dir)

import unittest
from unittest.mock import Mock, patch
from lambda_function.lambda_function import lambda_handler, process_log_data  # Import your Lambda function code

class TestLambdaFunction(unittest.TestCase):

    @patch('boto3.client')
    def test_lambda_handler(self, mock_boto3_client):
        # Mock the SQS client and its receive_message method
        mock_sqs = Mock()
        mock_sqs.receive_message.return_value = {
            'Messages': [
                {
                    'Body': '{"log_data": "Test log data"}'
                }
            ]
        }
        mock_boto3_client.return_value = mock_sqs

        # Call your Lambda function handler
        event = {}
        context = Mock()
        response = lambda_handler(event, context)

        # Verify the Lambda function response
        self.assertEqual(response['statusCode'], 200)

        # Verify that process_log_data was called with the expected data
        process_log_data.assert_called_with('Test log data')

        # Add more assertions based on your Lambda function behavior

if __name__ == '__main__':
    unittest.main()