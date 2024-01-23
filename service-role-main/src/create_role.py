# Documentation string for the module
"""
This module contains functionality to handle AWS DynamoDB operations for
creating roles.
"""

import uuid
import json
import boto3
import botocore.exceptions

dynamodb = boto3.resource('dynamodb')
role_table = dynamodb.Table('Role')


def permission_to_int(permission):
    """
    Converts permission dictionary to an integer representation.

    Args:
    permission (dict): Dictionary with permission settings.

    Returns:
    int: Integer representation of permissions.
    """
    int_representation = 0
    if permission.get("create"):
        int_representation |= 1
    if permission.get("read"):
        int_representation |= 2
    if permission.get("update"):
        int_representation |= 4
    if permission.get("delete"):
        int_representation |= 8
    return int_representation


def lambda_handler(event, context):
    """
    AWS Lambda handler for processing the incoming request to manage role data.

    Args:
    event (dict): The event dictionary containing the request parameters.
    context: The context in which the lambda function is executed.

    Returns:
    dict: The response object with status code and body.
    """
    required_fields = ['RoleName', 'UserStorage',
                       'PointLedger', 'Logs', 'Role']
    try:
        data = json.loads(event['body'])

        # Validate required fields
        if not all(field in data for field in required_fields):
            missing_fields = [
                field for field in required_fields if field not in data]
            return {
                'statusCode': 400,
                'body': json.dumps(
                    {'error':
                        f'Missing required fields: {missing_fields}'})
            }

        # Generate a unique Role ID
        role_id = str(uuid.uuid4())

        # Create a new role record in DynamoDB
        role_data = {
            'RoleID': role_id,
            'RoleName': data['RoleName'],
            'UserStoragePermission': permission_to_int(data['UserStorage']),
            'PointLedgerPermission': permission_to_int(data['PointLedger']),
            'LogsPermission': permission_to_int(data['Logs']),
            'RolePermission': permission_to_int(data['Role'])
        }
        role_table.put_item(Item=role_data)

        return_role = {'RoleID': role_id, **data}

        response = {
            'statusCode': 201,
            'body': json.dumps(return_role),
        }

    except json.JSONDecodeError:
        response = {
            'statusCode': 400,
            'body': json.dumps({'error':
                                'Invalid JSON format in request body'})
        }
    except botocore.exceptions.ClientError as error:
        response = {
            'statusCode': 500,
            'body': json.dumps({'error': f'DynamoDB error: {str(error)}'})
        }
    except Exception as error:
        response = {
            'statusCode': 400,
            'body': json.dumps({'error': f'Unexpected error: {str(error)}'})
        }

    return response
