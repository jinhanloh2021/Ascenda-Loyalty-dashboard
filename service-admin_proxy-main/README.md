# Admin Proxy Service

## Project Overview

This project utilizes GitHub Actions for continuous integration and deployment. It writes into an existing AWS cluster's container and spins up a new sets of task with the new deployed code when every there is any git commit.

The Admin Proxy application is an orchestrator like service that serves the various business logic of the application. It connects to the User and Points backend services, as well as the Role lambda service through an internal API gateway, as well as a logs lambda service through Amazon SQS


## Getting Started

### Continuous Integration and Deployment

#### GitHub Actions Workflows

- Build Image
- Configure AWS credentials
- Login to Amazon ECR
- Build with Maven
- Build, tag, and push image to Amazon ECR
- Fill in the new image ID in the Amazon ECS task definition
- Deploy Amazon ECS task definition


## API Endpoints

All Admin Proxy functions are accessed through an internet facing elastic load-balancer.


## Sign up - POST `/v1/app/auth/signup`
User sign up

Input
```json
{
  "name": "bobby123",
  "email": "bobby1234@gmail.com",
  "password": "bobby123"
}

```


Ouput
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbInVzZXIuY3JlYXRlIiwidXNlci5yZWFkIiwidXNlci51cGRhdGUiLCJ1c2VyLmRlbGV0ZSIsInBvaW50cy5yZWFkIiwicG9pbnRzLnVwZGF0ZSIsImxvZ3MucmVhZCIsInJvbGUuY3JlYXRlIiwicm9sZS5yZWFkIiwicm9sZS51cGRhdGUiLCJyb2xlLmRlbGV0ZSIsIk93bmVyIiwiT3duZXIiXSwibmFtZSI6ImJvYmJ5IiwidXNlcklkIjoiYWM0OTBmZTktMWJkNC00NDBjLTgxYzUtOTBhNTQ0MWEzYTZiIiwic3ViIjoiYm9iYnlAZ21haWwuY29tIiwiaWF0IjoxNjk5OTcwMDE1LCJleHAiOjE2OTk5NzE0NTV9.itsxwmkR5fQm33Y7-IEOGRm6XxQG8JvsUflAPlhNPNk"
}

```

## Sign in - POST `/v1/app/auth/signin`
User sign in

Input
```json
{
  "email": "bobby@gmail.com",
  "password": "bobby123"
}

```


Ouput
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbInVzZXIuY3JlYXRlIiwidXNlci5yZWFkIiwidXNlci51cGRhdGUiLCJ1c2VyLmRlbGV0ZSIsInBvaW50cy5yZWFkIiwicG9pbnRzLnVwZGF0ZSIsImxvZ3MucmVhZCIsInJvbGUuY3JlYXRlIiwicm9sZS5yZWFkIiwicm9sZS51cGRhdGUiLCJyb2xlLmRlbGV0ZSIsIk93bmVyIiwiT3duZXIiXSwibmFtZSI6ImJvYmJ5IiwidXNlcklkIjoiYWM0OTBmZTktMWJkNC00NDBjLTgxYzUtOTBhNTQ0MWEzYTZiIiwic3ViIjoiYm9iYnlAZ21haWwuY29tIiwiaWF0IjoxNjk5OTcwMDE1LCJleHAiOjE2OTk5NzE0NTV9.itsxwmkR5fQm33Y7-IEOGRm6XxQG8JvsUflAPlhNPNk"
}

```


## View all user - GET `/v1/app/users`
Getting all Users

Input
```json
{

}

```


Ouput
```json
[
  {
    "userId": "c9e87adf-0e3f-49cc-a43b-6df7ec299a70",
    "name": "test12345",
    "email": "test12345@gmail.com",
    "password": "$2a$10$J4ThdsFEnPWyoMws0Jdccu0kS0AeSWB6Z9ooIP4ltIRSnw6Skshmu",
    "role": "Default",
    "permissions": null,
    "enabled": true,
    "authorities": [
      {
        "authority": "Default"
      }
    ],
    "username": "test12345@gmail.com",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true
  },
  ...
]

```

## GET user by userId - GET `/v1/app/users/{userId}`
Getting a user with userId

Input
```json
{

}

```

Ouput
```json

{
"userId": "c9e87adf-0e3f-49cc-a43b-6df7ec299a70",
"name": "test12345",
"email": "test12345@gmail.com",
"password": "$2a$10$J4ThdsFEnPWyoMws0Jdccu0kS0AeSWB6Z9ooIP4ltIRSnw6Skshmu",
"role": "Default",
"permissions": null,
"enabled": true,
"authorities": [
  {
    "authority": "Default"
  }
],
"username": "test12345@gmail.com",
"accountNonExpired": true,
"accountNonLocked": true,
"credentialsNonExpired": true
}

```

## Create new user - POST `/v1/app/users`
POST a new user

Input
```json
{
  "name": "wei han",
  "email": "weihangoh2002@email.com"
}

```

Output
```json
{
  "name": "wei han",
  "email": "weihangoh2002@email.com",
  "password": "Vs)g_*aLB@MUqRuY"
}

```

## Update a user - PUT `/v1/app/users/{userId}`
Update an existing user

Input
```json
{
  "name": "guest user 999",
  "email": "12345@kgoomail.com",
  "role": "IT specialist"
}

```

Output
```json
{
  "userId": "06427d68-d1ea-459b-a512-f42bcc616eb8",
  "name": "guest user 999",
  "email": "12345@kgoomail.com",
  "role": "IT specialist"
}

```

## Delete a user - DELETE `/v1/app/users/{userId}`
Delete an existing user

Input
```json
{
  
}

```

Output
```json
{
  "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc",
  "name": "guestUser5",
  "email": "email@email.com",
  "role": "admin"
}

```

## Get all logs - GET `/v1/app/logs
Delete an existing user

Input
```json
{
  
}

```

Output
```json
[
  {
    "logsId": "ac0bfeda-e5ba-4f14-b639-4a0910544987",
    "dateTime": "1699763358220",
    "device": "PostmanRuntime/7.35.0202.161.35.27",
    "description": "bobby retrieved all users information"
  },
  ...
]

```

## Get logs based on offset and limit - GET `/v1/app/logs?offset=x&limit=y
Delete an existing user

Input
```json
{
  
}

```

Output
```json
[
  {
    "logsId": "ac0bfeda-e5ba-4f14-b639-4a0910544987",
    "dateTime": "1699763358220",
    "device": "PostmanRuntime/7.35.0202.161.35.27",
    "description": "bobby retrieved all users information"
  },
  ...
]

```

## Get all roles - GET `/v1/app/role
Getting all roles

Input
```json
{
  
}

```

Output
```json
[
  {
    "RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
    "RoleName": "Owner",
    "UserStorage": {
      "create": true,
      "read": true,
      "update": true,
      "delete": true
    },
    "PointLedger": {
      "create": false,
      "read": true,
      "update": true,
      "delete": false
    },
    "Logs": {
      "create": false,
      "read": true,
      "update": false,
      "delete": false
    },
    "Role": {
      "create": true,
      "read": true,
      "update": true,
      "delete": true
    }
  },
  ...
]

```


## Get role by id - GET `/v1/app/role/{roleId}
Getting Role by Id

Input
```json
{
  
}

```

Output
```json

{
"RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
"RoleName": "Owner",
"UserStorage": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
},
"PointLedger": {
  "create": false,
  "read": true,
  "update": true,
  "delete": false
},
"Logs": {
  "create": false,
  "read": true,
  "update": false,
  "delete": false
},
"Role": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
}
}
```


## Create Role - POST `/v1/app/role/
Create new Role

Input
```json
{
  "RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
  "RoleName": "Owner",
  "UserStorage": {
    "create": true,
    "read": true,
    "update": true,
    "delete": true
  },
  "PointLedger": {
    "create": false,
    "read": true,
    "update": true,
    "delete": false
  },
  "Logs": {
    "create": false,
    "read": true,
    "update": false,
    "delete": false
  },
  "Role": {
    "create": true,
    "read": true,
    "update": true,
    "delete": true
  }
}

```

Output
```json

{
"RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
"RoleName": "Owner",
"UserStorage": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
},
"PointLedger": {
  "create": false,
  "read": true,
  "update": true,
  "delete": false
},
"Logs": {
  "create": false,
  "read": true,
  "update": false,
  "delete": false
},
"Role": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
}
}
```


## Update Role - PUT `/v1/app/role/{roleId}
Update existing Role

Input
```json
{
  "RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
  "RoleName": "Owner",
  "UserStorage": {
    "create": true,
    "read": true,
    "update": true,
    "delete": true
  },
  "PointLedger": {
    "create": false,
    "read": true,
    "update": true,
    "delete": false
  },
  "Logs": {
    "create": false,
    "read": true,
    "update": false,
    "delete": false
  },
  "Role": {
    "create": true,
    "read": true,
    "update": true,
    "delete": true
  }
}

```

Output
```json

{
"RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
"RoleName": "Owner",
"UserStorage": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
},
"PointLedger": {
  "create": false,
  "read": true,
  "update": true,
  "delete": false
},
"Logs": {
  "create": false,
  "read": true,
  "update": false,
  "delete": false
},
"Role": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
}
}
```

## Delete Role - DELETE `/v1/app/role/{roleId}
Delete existing Role

Input
```json
{

}

```

Output
```json

{
"RoleID": "700c0dff-61bb-4148-a009-8fbf6d21fc07",
"RoleName": "Owner",
"UserStorage": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
},
"PointLedger": {
  "create": false,
  "read": true,
  "update": true,
  "delete": false
},
"Logs": {
  "create": false,
  "read": true,
  "update": false,
  "delete": false
},
"Role": {
  "create": true,
  "read": true,
  "update": true,
  "delete": true
}
}
```

## Create Points - POST `/v1/app/points`
Create new Points

Input
```json
{
  "balance": 888,
  "appName": "app1",
  "userId": "b9985c41-3969-4342-8ccb-e8ed570b5b5c"
}

```


Ouput
```json
{
  "balance": 888,
  "appName": "app1",
  "userId": "b9985c41-3969-4342-8ccb-e8ed570b5b5c"
}

```

## Get all Points - GET `/v1/app/points`
Get all points

Input
```json
{

}

```


Ouput
```json
[
  {
    "pointsId": "0d5e58a8-78e4-40ec-a1e2-03e82f19b377",
    "balance": 50,
    "appName": "SQ",
    "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
  },
  ...
]

```

## Get Points by pointsId - GET `/v1/app/points/{pointsId}`
Get points by ID

Input
```json
{

}

```


Ouput
```json

{
"pointsId": "0d5e58a8-78e4-40ec-a1e2-03e82f19b377",
"balance": 50,
"appName": "SQ",
"userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}

```

## Update Points by pointsId - GET `/v1/app/points/{pointsId}`
Update Points

Input
```json
{
  "pointsId": "0d5e58a8-78e4-40ec-a1e2-03e82f19b377",
  "balance": 50,
  "appName": "SQ",
  "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}

```


Ouput
```json

{
"pointsId": "0d5e58a8-78e4-40ec-a1e2-03e82f19b377",
"balance": 50,
"appName": "SQ",
"userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}

```

## Delete Points by pointsId - GET `/v1/app/points/{pointsId}`
Delete points by ID

Input
```json
{

}

```


Ouput
```json

{
"pointsId": "0d5e58a8-78e4-40ec-a1e2-03e82f19b377",
"balance": 50,
"appName": "SQ",
"userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}

```

## Get all points of a User - GET `/v1/app/users/{userId}/points`
Delete points by ID

Input
```json
{

}

```


Ouput
```json

[
  {
    "pointsId": "94fa8ded-3905-4a68-9af2-54df37282715",
    "balance": 3500,
    "appName": "app1",
    "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
  },
  {
    "pointsId": "0d5e58a8-78e4-40ec-a1e2-03e82f19b377",
    "balance": 50,
    "appName": "SQ",
    "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
  },
  {
    "pointsId": "ba30bb33-a6a2-4afb-949f-4ae507bf5ea4",
    "balance": 50,
    "appName": "frasersProperty",
    "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
  }
]

```

## Get all points of a User - GET `/v1/app/users/{userId}/points/{appName}`
Delete points by ID

Input
```json
{

}

```


Ouput
```json

{
"pointsId": "ba30bb33-a6a2-4afb-949f-4ae507bf5ea4",
"balance": 50,
"appName": "frasersProperty",
"userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}

```

## Get maker-checker request - GET `/v1/app/maker-checker/{requestId}`
Get maker-checker request

Input
```json
{

}

```


Ouput
```json

{
  "RequestId": "085662d6-3de8-49a1-a9b7-c40616f27a71",
  "Checker": "Sean Tan",
  "Maker": "Bryan",
  "RequestData": "For your approval.",
  "RequestType": "points",
  "Status": "Rejected"
}

```


## Delete maker-checker request - DELETE `/v1/app/maker-checker/{requestId}`
Delete maker-checker request

Input
```json
{

}

```


Ouput
```json

{

}

```

## Update maker-checker request - PUT `/v1/app/maker-checker/{requestId}`
Update maker-checker request

Input
```json
{

}

```


Ouput
```json

{

}

```

## Update maker-checker request - PUT `/v1/app/maker-checker`
Update maker-checker request

Input
```json
{
  "checker_user": "Wei Han111",
  "maker_user": "Sean Tan",
  "request_data": "For your approval.",
  "action_type": "points",
  "checker_email": "weihan.goh.2021@scis.smu.edu.sg",
  "maker_role": "product manager",
  "checker_role": "owner"
}
```


Ouput
```json

{

}

```
