# Documentation string for the module
"""
This module provides functionality to retrieve a specific role from
AWS DynamoDB and convert its permissions from an integer representation
to a dict format.
"""

import json
import decimal
import boto3
import botocore.exceptions

# Initialize DynamoDB resource
dynamodb = boto3.resource('dynamodb')
role_table = dynamodb.Table('Role')


def int_to_permission(permission_int):
    """
    Converts an integer representation of permissions back to a dict format.

    Args:
    permission_int (int or decimal.Decimal): int representation of permissions.

    Returns:
    dict: Dictionary of permissions.
    """
    # Convert to int if it's a Decimal
    permission_int = int(permission_int) if isinstance(
        permission_int, decimal.Decimal) else permission_int
    return {
        'create': bool(permission_int & 1),
        'read': bool(permission_int & 2),
        'update': bool(permission_int & 4),
        'delete': bool(permission_int & 8),
    }


def lambda_handler(event, context):
    """
    AWS Lambda handler for retrieving a role based on the provided role ID
    and converting its permissions.

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
            item = response['Item']
            item['UserStorage'] = int_to_permission(
                item.pop('UserStoragePermission', None))
            item['PointLedger'] = int_to_permission(
                item.pop('PointLedgerPermission', None))
            item['Logs'] = int_to_permission(item.pop('LogsPermission', None))
            item['Role'] = int_to_permission(item.pop('RolePermission', None))

            return {
                'statusCode': 200,
                'body': json.dumps(item)
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
