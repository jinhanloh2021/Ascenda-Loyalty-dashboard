# User Service

## Project Overview

This project utilizes GitHub Actions for continuous integration and deployment. It writes into an existing AWS cluster's container and spins up a new sets of task with the new deployed code when every there is any git commit.


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

All points functions are accessed through an internal load-balancer.

## View all points account - GET `/v1/app/points`
GET All Points Account

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
  }
]

```


## View user with id - GET `/v1/app/points/users/{userid}`
GET All Points Account belonging to a specific user

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/points/users/b9985c41-3969-4342-8ccb-e8ed570b5b5c
```

Ouput
```json
[
  {
    "pointsId": "8befcf2e-6bcf-4809-99cc-37d4c3076f5f",
    "balance": 100,
    "appName": "app1",
    "userId": "b9985c41-3969-4342-8ccb-e8ed570b5b5c"
  },
  {
    "pointsId": "b21a8283-7503-465e-b3fe-c9922b5f5882",
    "balance": 8292,
    "appName": "app2",
    "userId": "b9985c41-3969-4342-8ccb-e8ed570b5b5c"
  }
]
```

## View account with id - GET `/v1/app/points/{pointsId}`
GET points account by pointsId

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/points/94fa8ded-3905-4a68-9af2-54df37282715
```

Ouput
```json
{
  "pointsId": "94fa8ded-3905-4a68-9af2-54df37282715",
  "balance": 3500,
  "appName": "app1",
  "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}
```

## View points account by userId and appName - GET `/v1/app/points/users/{userid}/{appName}`
GET points account by userId and appName

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/points/users/b674c785-6a0f-4900-84fd-b7d3fa9e7bdc/app1
```

Ouput
```json
{
  "pointsId": "94fa8ded-3905-4a68-9af2-54df37282715",
  "balance": 3500,
  "appName": "app1",
  "userId": "b674c785-6a0f-4900-84fd-b7d3fa9e7bdc"
}
```



## Create a points account - POST `/v1/app/points`
Creation of a points account

Input
```json
{
  "balance": 200,
  "appName": "gong cha",
  "userId": "2de8acd5-89bc-472c-9e73-47ebe1029364"
}
```

Ouput
```json
{
  "pointsId": "bdw8a8283-7503-465e-b3fe-c9922b5f5882",
  "balance": 200,
  "appName": "gong cha",
  "userId": "2de8acd5-89bc-472c-9e73-47ebe1029364"
}
```

## Update points - PUT `/v1/app/points/{pointsId}`
Update the points of a user.

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/points/bdw8a8283-7503-465e-b3fe-c9922b5f5882
```


Input
```json
{
  "pointsId": "bdw8a8283-7503-465e-b3fe-c9922b5f5882",
  "balance": 300,
  "appName": "gong cha",
  "userId": "2de8acd5-89bc-472c-9e73-47ebe1029364"
}
```

Ouput
```json
{
  "pointsId": "bdw8a8283-7503-465e-b3fe-c9922b5f5882",
  "balance": 300,
  "appName": "gong cha",
  "userId": "2de8acd5-89bc-472c-9e73-47ebe1029364"
}
```

## Delete points account by ID - PUT `/v1/app/points/{pointsId}`
Deletion of a points account with a particular ID

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/points/bdw8a8283-7503-465e-b3fe-c9922b5f5882
```

Ouput
```json
{
  "pointsId": "bdw8a8283-7503-465e-b3fe-c9922b5f5882",
  "balance": 300,
  "appName": "gong cha",
  "userId": "2de8acd5-89bc-472c-9e73-47ebe1029364"
}
```


