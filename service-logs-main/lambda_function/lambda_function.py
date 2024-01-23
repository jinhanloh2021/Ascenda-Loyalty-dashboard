import boto3
import json
import datetime
import os  # Import the 'os' module to access environment variables

s3 = boto3.client('s3')

def lambda_handler(event, context):
    # Retrieve AWS credentials and region from environment variables
    # aws_access_key_id = os.environ['AWS_ACCESS_KEY_ID']
    # aws_secret_access_key = os.environ['AWS_SECRET_ACCESS_KEY']
    # aws_region = os.environ['AWS_REGION']
    
    ########################## for testing only # to remove after working########
    aws_access_key_id = 'AKIA2FRH44W3CXLIQBE6'
    aws_secret_access_key = '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'
    aws_region = 'ap-southeast-1'
    #############################################################################

    # Configure the AWS session with the credentials and region
    session = boto3.Session(
        aws_access_key_id=aws_access_key_id,
        aws_secret_access_key=aws_secret_access_key,
        region_name=aws_region
    )

    # Create an S3 client using the configured session
    s3 = session.client('s3')

    timestamp = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S")  # Format the current time as a timestamp
    log_filename = f'logs/log_{timestamp}.txt'  # Use the timestamp in the log filename

    for record in event['Records']:
        body = json.loads(record['body'])
        data = body['log_data']
        bucket_name = 'project-g2t3b-logs'  # Replace with your S3 bucket name

        # Upload the log data with the timestamp in the filename
        s3.put_object(Bucket=bucket_name, Key=log_filename, Body=data)

    return {
        'statusCode': 200,
        'body': json.dumps('Log data processed and uploaded to S3')
    }
