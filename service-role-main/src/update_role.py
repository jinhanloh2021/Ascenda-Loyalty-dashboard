# Documentation string for the module
"""
This module provides functionality to update the details of a specific role in
AWS DynamoDB, including its permissions, based on the provided role ID.
"""

import json
import boto3
import botocore.exceptions

# Initialize DynamoDB resource
dynamodb = boto3.resource('dynamodb')
role_table = dynamodb.Table('Role')


def permission_to_int(permission):
    """
    Converts a dictionary of permissions to an integer representation.

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
    AWS Lambda handler for updating the details of a specific role.

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

        data = json.loads(event['body'])

        update_expr = (
            "SET RoleName = :r, UserStoragePermission = :us, "
            "PointLedgerPermission = :pl, LogsPermission = :lp, "
            "RolePermission = :rp"
        )

        expr_attr_vals = {
            ':r': data['RoleName'],
            ':us': permission_to_int(data['UserStorage']),
            ':pl': permission_to_int(data['PointLedger']),
            ':lp': permission_to_int(data['Logs']),
            ':rp': permission_to_int(data['Role'])
        }

        role_table.update_item(
            Key={'RoleID': role_id},
            UpdateExpression=update_expr,
            ExpressionAttributeValues=expr_attr_vals,
            ConditionExpression="attribute_exists(RoleID)"
        )

        updated_role = {'RoleID': role_id, **data}

        return {
            'statusCode': 200,
            'body': json.dumps(updated_role)
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
            'body': json.dumps({'error': str(error)})
        }
