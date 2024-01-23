# Role Service

## Project Overview

This project contains a set of AWS Lambda functions written in Python, designed for various roles management tasks. It utilizes GitHub Actions for continuous integration and deployment, ensuring code quality through linting and conditional deployment based on changes in specific files.

## Getting Started

### Dependencies

- AWS CLI: Used for managing AWS services.
- Python 3.9: Required for running and testing the Lambda functions.
- Required Python libraries as listed in `requirements.txt`.

### Setup

1. **Install Python Dependencies**.
2. **AWS CLI Configuration**:
   - Configure AWS CLI with your credentials.

### AWS Lambda Functions

This project includes the following Lambda functions:

- **createRole**: Manages the creation of roles.
- **deleteRole**: Handles the deletion of roles.
- **getAllRole**: Retrieves all roles.
- **getRole**: Fetches a specific role.
- **updateRole**: Updates an existing role.

### Continuous Integration and Deployment

The project employs GitHub Actions for CI/CD. The workflows perform the following tasks:

- **Linting**: Code is linted using Flake8 and Pylint to ensure adherence to Python best practices.
- **Conditional Deployment**: Lambda functions are deployed only when changes are made to their respective Python files in the `src` directory.

#### GitHub Actions Workflows

- **Flake8 and Pylint**: Ensures code quality and style consistency.
- **Lambda Deployment**: Deploys the Lambda functions to AWS. This is triggered on changes to the respective Python script in the `src` folder.

****

## API Endpoints

All Lambda functions are accessed through an API Gateway

## Create Role - POST `/role`
Creation of a new role.

Input
```json
{
  "body": "{\"RoleName\": \"Owner\", \"UserStorage\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": true}, \"PointLedger\": {\"create\": false, \"read\": true, \"update\": true, \"delete\": false}, \"Logs\": {\"create\": false, \"read\": true, \"update\": false, \"delete\": false}, \"Role\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": true}}"
}
```

Ouput
```json
{
  "statusCode": 201,
  "body": "{\"RoleID\": \"9fd80af0-d937-4efa-b9ed-37ffa2f8a543\", \"RoleName\": \"Owner\", \"UserStoragePermission\": 7, \"PointLedgerPermission\": 6, \"LogsPermission\": 2, \"RolePermission\": 15}"
}

```
## Get All Role - Get `/role`
Retrieve all roles.


Ouput
```json
{
  "statusCode": 200,
  "body": "[{\"RoleID\": \"071a6eb6-dd7c-40de-af1d-04c6391b109c\", \"RoleName\": \"Mock Admin 1\", \"UserStorage\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": false}, \"PointLedger\": {\"create\": false, \"read\": true, \"update\": true, \"delete\": false}, \"Logs\": {\"create\": false, \"read\": true, \"update\": false, \"delete\": false}, \"Role\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": true}}, {\"RoleID\": \"cef078eb-b89b-4c97-993b-73384a9e26ba\", \"RoleName\": \"Mock Admin 2\", \"UserStorage\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": false}, \"PointLedger\": {\"create\": false, \"read\": true, \"update\": true, \"delete\": false}, \"Logs\": {\"create\": false, \"read\": true, \"update\": false, \"delete\": false}, \"Role\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": true}}]"
}

```
## Update Role - PUT `/role{role_id}`
Update an existing role.

Input
```json
{
  "body": "{\"RoleName\": \"Mock Admin\", \"UserStorage\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": false}, \"PointLedger\": {\"create\": false, \"read\": true, \"update\": true, \"delete\": false}, \"Logs\": {\"create\": false, \"read\": true, \"update\": false, \"delete\": false}, \"Role\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": true}}",
  "pathParameters": {
    "role_id": "0d8e5982-ed83-481a-8a89-c196401a91dc"
  }
}

```

Ouput
```json
{
  "statusCode": 200,
  "body": "{\"RoleID\": \"0d8e5982-ed83-481a-8a89-c196401a91dc\", \"RoleName\": \"Mock Admin\", \"UserStorage\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": false}, \"PointLedger\": {\"create\": false, \"read\": true, \"update\": true, \"delete\": false}, \"Logs\": {\"create\": false, \"read\": true, \"update\": false, \"delete\": false}, \"Role\": {\"create\": true, \"read\": true, \"update\": true, \"delete\": true}}"
}


```
## Delete Role - DELETE `/role{role_id}`
Delete an existing role.

Input
```json
{
"pathParameters": {
    "role_id": "9fd80af0-d937-4efa-b9ed-37ffa2f8a543"
  }
}
```

Ouput
```json
{
  "statusCode": 200,
  "body": "{\"message\": \"RoleName: Mock Administrator, RoleID: 9fd80af0-d937-4efa-b9ed-37ffa2f8a543 deleted\"}"
}

```

## GET Role - GET `/role{role_id}`
Retrieve an existing role.

Input
```json
{
"pathParameters": {
    "role_id": "9fd80af0-d937-4efa-b9ed-37ffa2f8a543"
  }
}
```

Ouput
```json
{
  "statusCode": 200,
  "body": "{\"message\": \"RoleName: Mock Administrator, RoleID: 9fd80af0-d937-4efa-b9ed-37ffa2f8a543 deleted\"}"
}

