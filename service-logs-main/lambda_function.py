import boto3
import json
import datetime
import os

# Retrieve AWS credentials and region from environment variables
access_key_id = os.environ.get('ACCESS_KEY_ID')
secret_access_key = os.environ.get('SECRET_ACCESS_KEY')
region = os.environ.get('REGION')

s3 = boto3.client('s3', aws_access_key_id=access_key_id, aws_secret_access_key=secret_access_key, region_name=region)

def lambda_handler(event, context):
    try:
        timestamp = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S")
        log_filename = f'logs/log_{timestamp}.txt'

        bucket_name = os.environ.get('S3_BUCKET_NAME')  

        # Serialize the event data to bytes before uploading to S3
        data = json.dumps(event).encode('utf-8')

        # Upload the log data with the timestamp in the filename
        s3.put_object(Bucket=bucket_name, Key=log_filename, Body=data)

        return {
            'statusCode': 200,
            'body': json.dumps('Log data processed and uploaded to S3')
        }
    except Exception as e:
        print(f"Error: {str(e)}")
        return {
            'statusCode': 500,
            'body': json.dumps(f'Error: {str(e)}')
        }
