# Documentation string for the module
"""
This module provides functionality for deleting a specific role from the
AWS DynamoDB 'Role' table.
"""

import json
import boto3
import botocore.exceptions

# Initialize DynamoDB resource
dynamodb = boto3.resource('dynamodb')
role_table = dynamodb.Table('Role')


def lambda_handler(event, context):
    """
    AWS Lambda handler for deleting a role based on the provided role ID.

    Args:
    event (dict): The event dictionary containing request parameters.
    context: The context in which the lambda function is executed.

    Returns:
    dict: The response object with status code and body.
    """
    try:
        if 'pathParameters' in event and 'role_id' in event['pathParameters']:
            role_id = event['pathParameters']['role_id']
        else:
            return {
                'statusCode': 400,
                'body': json.dumps({'error':
                                    "Missing role_id in path parameters"})
            }

        response = role_table.get_item(Key={'RoleID': role_id})
        if 'Item' in response:
            role_name = response['Item']['RoleName']
            role_table.delete_item(Key={'RoleID': role_id})
            return {
                'statusCode': 200,
                'body': json.dumps(
                    {'message':
                        f'Role {role_name} with RoleID {role_id} deleted'})
            }
        return {
            'statusCode': 404,
            'body': json.dumps({'error': 'Role not found'})
        }

    except botocore.exceptions.ClientError as error:
        error_code = error.response['Error']['Code']
        return {
            'statusCode': 500,
            'body': json.dumps({'error':
                                f'DynamoDB ClientError: {error_code}'})
        }

    except Exception as error:
        return {
            'statusCode': 500,
            'body': json.dumps({'error': f'Unexpected error: {str(error)}'})
        }
