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


## Test interfaces through AWS
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

## Frontend page Demo
Since I am not a front end developer, I use this frontend demo to build my interfaces. Here are the pages and corresponding test return results. I use postman to test.
