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

All user functions are accessed through an internal load-balancer.

## View all user - GET `/v1/app/users`
GET All Users

Ouput
```json
[
  {
    "userId": "744a2bc4-62ce-4b1b-8847-842bd66fd6e4",
    "name": "abc",
    "email": "abc@abc.com",
    "password": "$2a$10$7p.SHouee8tGR974IJJ3.uls2M1VNjj6eLmSzY.5negzI5.4.TsCm",
    "role": "Default",
    "permissions": null,
    "enabled": true,
    "authorities": [
      {
        "authority": "Default"
      }
    ],
    "username": "abc@abc.com",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true
  },
  {
    "userId": "a27fa870-b55c-45f4-a577-4e0d278062d8",
    "name": "New User",
    "email": "newuser@test.com",
    "password": null,
    "role": "Default",
    "permissions": null,
    "enabled": true,
    "authorities": [
      {
        "authority": "Default"
      }
    ],
    "username": "newuser@test.com",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true
  }
]

```


## View user with id - GET `/v1/app/users/{userId}`
GET User by ID

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/users/6a43f46c-7f71-44b4-9148-1aeb600c33fa
```

Ouput
```json
{
  "userId": "6a43f46c-7f71-44b4-9148-1aeb600c33fa",
  "name": "Jin Han",
  "email": "jinhanloh@gmail.com",
  "password": "$2a$10$nKb2FqnW.6wk6Tz4wSTe/eYFmNgnZNj6woC8rkPCuxEXW35blkYbi",
  "role": "Default",
  "permissions": null,
  "enabled": true,
  "authorities": [
    {
      "authority": "Default"
    }
  ],
  "username": "jinhanloh@gmail.com",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true
}

```

## Create user - POST `/v1/app/users`
Creation of a new user.

Input
```json
{
  "name": "edward",
  "email": "edward@test.com",
  "role": "admin"
}
```

Ouput
```json
{
  "name": "edward",
  "email": "edward@test.com",
  "password": "RZAzLb4O@6ft%JhW"
}

```

## Update user - PUT `/v1/app/users/{userId}`
Update the user details of a user.

Input
```json
{
  "name": "patrick",
  "email": "patrick@gmail.com",
  "role": "Admin"
}
```

Ouput
```json
{
  "userId": "118a591a-6c6d-4d1a-ac7c-86dc42d4debc",
  "name": "patrick",
  "email": "patrick@gmail.com",
  "password": null,
  "role": "Admin",
  "permissions": null,
  "enabled": true,
  "authorities": [
    {
      "authority": "Admin"
    }
  ],
  "username": "patrick@gmail.com",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true
}

```

## Delete user - PUT `/v1/app/users/{userId}`
Deletion of a user.

Example URL
```agsl
https://www.proxy.itsag2t3.com:8001/v1/app/users/f73f3b59-eaab-4598-997b-a2033e88e404
```

Ouput
```json
{
  "userId": "f73f3b59-eaab-4598-997b-a2033e88e404",
  "name": "edward",
  "email": "edward@test.com",
  "password": null,
  "role": "Default",
  "permissions": null,
  "enabled": true,
  "authorities": [
    {
      "authority": "Default"
    }
  ],
  "username": "edward@test.com",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true
}
```


