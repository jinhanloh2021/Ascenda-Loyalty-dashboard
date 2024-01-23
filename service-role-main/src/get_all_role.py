# Documentation string for the module
"""
This module provides functionality for listing all roles stored in the
AWS DynamoDB 'Role' table.
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
    permission_int = int(permission_int) if isinstance(
        permission_int, decimal.Decimal) else permission_int
    permission = {
        'create': bool(permission_int & 1),
        'read': bool(permission_int & 2),
        'update': bool(permission_int & 4),
        'delete': bool(permission_int & 8),
    }
    return permission


def lambda_handler(event, context):
    """
    AWS Lambda handler for retrieving and returning a list of all roles.

    Args:
    event (dict): The event dictionary containing the request parameters.
    context: The context in which the lambda function is executed.

    Returns:
    dict: The response object with status code and body.
    """
    try:
        response = role_table.scan()

        if 'Items' in response:
            items = response['Items']
            for item in items:
                item['UserStorage'] = int_to_permission(
                    item.pop('UserStoragePermission', None))
                item['PointLedger'] = int_to_permission(
                    item.pop('PointLedgerPermission', None))
                item['Logs'] = int_to_permission(
                    item.pop('LogsPermission', None))
                item['Role'] = int_to_permission(
                    item.pop('RolePermission', None))

            return {
                'statusCode': 200,
                'body': json.dumps(items)
            }

        return {
            'statusCode': 404,
            'body': json.dumps({'error': 'No roles found'})
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
