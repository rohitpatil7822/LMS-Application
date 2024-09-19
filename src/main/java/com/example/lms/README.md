# LMS Exam Enrollment API

This project is a RESTful API service using Spring Boot to manage the exam enrollment process for a Learning Management System (LMS). It uses MySQL to persist the data.

## Features

- CRUD operations for Students, Subjects, and Exams
- Student enrollment in subjects
- Student registration for exams
- Error handling with appropriate HTTP status codes
- Unit tests using MockMvc and Mockito
- Easter Egg feature using the Numbers API

## Prerequisites

- Java 11 or higher
- gradle
- MySQL

## Setup

1. Clone the repository
2. Configure the database connection in `src/main/resources/application.properties`
3. Run the application using `./gradlew bootRun`

## API Endpoints

- Students:
  - POST /api/students
  - GET /api/students
  - GET /api/students/{id}
  - PUT /api/students/{id}
  - DELETE /api/students/{id}
  - POST /api/students/{studentId}/enroll/{subjectId}
  - POST /api/students/{studentId}/register/{examId}

- Subjects:
  - POST /api/subjects
  - GET /api/subjects
  - GET /api/subjects/{id}
  - PUT /api/subjects/{id}
  - DELETE /api/subjects/{id}

- Exams:
  - POST /api/exams
  - GET /api/exams
  - GET /api/exams/{id}
  - PUT /api/exams/{id}
  - DELETE /api/exams/{id}

- Easter Egg:
  - GET /hidden-feature/{number}

## Running Tests

Run the tests using `./gradlew test`

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details