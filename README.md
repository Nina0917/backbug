# backbug
An online bug tracker and issue tracking application that helps the team to manage the project. Users can add, modify projects and assign issues to other users in the same team.
I built the application based on the frontend from this demo: https://www.youtube.com/watch?v=PYxLwn-Kk2U&t=110s

## Configured with
- JDK 1.8
- Spring MVC
- Spring Data JPA
- Spring Security
- Lombok
- Unit test
- Redis
- Postgresql

## Features
- Authentication and access control
- Users can follow, search other users. By following others, the login user make the following user his team member.
- Application will refer people the current user may know.
- User can create projects, and add tickects to each project.
- User can assign tickets to his team members. Each ticket has attributes like priority, time, category, etc.
- By selecting the project, users can see all the tickets under this project.
- User can check the corresponding members under one specific project
- Admin has the access to update, delete users


## AWS Deployment
http://3.99.213.164:5000/

## Postman Collection (API's)
#### What We Have Now in DB

| Email |   Password    |   Firstname    | LastName  |
| :---:   | :---: | :---: | :---: |
| admin@123.com | root   |    |     |
| 123@gmail.com | password   | Nina   |   Ma  |
| 456@gmail.com | password   | Yige   |   Cao |
| 789@gmail.com | password   | Ashley   |  Ward   |
| 125@gmail.com | password   | Eli   |  Jenkins   |
| 285@gmail.com | password   | Luna   |   Bell  |
| 232@gmail.com | password   | Patterson   |   Paul |
| 386@gmail.com | password   | Gemma   |  Cox   |
| 098@gmail.com | password   | Scott   |  Hicks   |
| 593@gmail.com | password   | Hector   |  Evans   |

You can login in these accounts and play with them, or you can create your own account.

All postman API collections is in: https://github.com/Nina0917/backbug/blob/main/backbug.postman_collection.json

Includes the following:

- User
  - SignUp
  - Get User
  - Delete User
  - Update User
- Follow
  - Follow/Unfollow User
  - Get following Users
  - Recommend Following Users 
  - Is user following another user
- Project
  - Add Project
  - Get Project
  - Get Projects
  - Get the corresponding members under the project
- Ticket
  - Add
  - Get Ticket
  - Get Tickets
- Auth
  - Login
  - Logout
In Login Request, it returns the Bearer Token that needs to be used after all request.
```
{
    "code": "0",
    "message": "SUCCESS",
    "data": "Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJyb2wiOiJST0xFX0FETUlOIiwianRpIjoiMSIsImlzcyI6IlNuYWlsQ2xpbWIiLCJpYXQiOjE2NjM3ODY5NTMsInN1YiI6ImFkbWluQDEyMy5jb20iLCJleHAiOjE2NjQzOTE3NTN9.0V6fFXPYK0hXYWjEYMAr1XVDd0tyFqOCE57Cfgl9PcE"
}

```
Copy the token and paste it in Postman, or you will have 401 unauthorized error.
