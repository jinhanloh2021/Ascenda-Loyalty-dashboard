# Logs Service

## Project Overview

This project contains a set of AWS Lambda functions written in Python to be deployed in AWS lambda.

### AWS Lambda Functions
This Lambda functions simply writes logs into s3 by calling the AWS SQS function as a trigger.

## Getting Started

#### Setup for local testing
### Dependencies
- AWS CLI: Used for managing AWS services.
- Python 3.10: Required for running and testing the Lambda functions.
- Required Python libraries as listed in `requirements.txt`.

1. **run sqs_lambda_s3.py unit testing***

#### Setup for production

1. Create a new queue in AWS.
2. Create a new lambda triggers
   - Copy the code from "lambda_function.py" in the root directory 
3. Add a new layer with working boto3 version
    - boto3==1.17.119 (any newer version of boto3 works)
4. Configure the 4 environment variable with the respective values
   - **Key** : **Value**
   - ACCESS_KEY_ID : **Value**
   - REGION : **Value**
   - S3_BUCKET_NAME : **Value**
   - SECRET_ACCESS_KEY : **Value**

### To test this service, go to SQS and send a message body to trigger this service

****

