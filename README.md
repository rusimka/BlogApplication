# BlogApplication
###### Welcome to the Blog Application project! This application provides a simple backend API for managing blog posts and users. It aims to fulfill various features and requirements related to creating, updating, and managing blog posts.
###### The Blog Application project aims to develop a full-fledged blog management system. The project is divided into different versions, each introducing new features and improvements. The versions are outlined below:
### Version 0.1.0
- Users can create simple blog posts with titles and text.
-  Users can retrieve a simplified list of all blog posts, including titles and short summaries. 
- Users can update the title and text of any blog post.
- Users can add or remove tags from any blog post.
- Users can retrieve all blog posts with specific tags. 
- Unit tests provided for the application.
- A setup guide is included in this README.
-  The application uses an H2 database with initial bootstrap data.

### Version 0.2.0
-  User management features are introduced.
- Users can be created with username, password, and display name.
- Users can log in and log out using JWT authentication.
- Users can view other users' posts.
- Users can delete their posts.
- Documentation for the REST APIs is provided using Swagger.
- Logging is implemented using Logback.
- The database is migrated to MySQL.
-  Dockerization of the application is implemented.
- Integration testing is conducted using test containers.

### Version 0.3.0

- Users can add images and videos to posts, with support for multiple display resolutions and sizes.
- Search functionality is added to search for blog posts by any input (words, sentences, tags).
-  Pagination is introduced to the simplified list of blog posts.

### Version 0.4.0
- The application is split into smaller services: Blog Management and User Management.

- Communication between the separate services is established using Kafka or RabbitMQ.
-  Dockerization of separate services is implemented.
- CI-CD pipelines are set up for automated builds and test runs.
- Deployment to cloud platforms such as Google Cloud Platform and Amazon Web Services is achieved.

## Technical Requirements
- Java 17 or newer is used for development.
- Spring or Spring Boot framework is employed.
-  Maven or Gradle is used as the build tool.
- Libraries and dependencies are utilized as needed.
-  Only stable and official library versions are used.
- Design patterns, SOLID principles, and Clean architecture are followed.
- Code is hosted on a preferred version control platform (GitHub, GitLab, BitBucket).
- Standard Git flow is followed for version control.

## Getting started
###### To get started with Blog Application follow these steps:
-  Clone the repository to your local machine.
- Ensure you have Java 17 or newer installed.
-  Choose your preferred build tool (Maven or Gradle) and ensure it's installed.
- Set up the necessary database (H2, MySQL) as indicated in the application's documentation.
- Build and run the application according to the provided setup guide.

## Documentation
###### For detailed information about the REST APIs, please refer to the Swagger documentation included in the application.

## Contributing
###### Contributions to the Blog Application project are welcome! If you find any bugs, issues, or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## Contact
###### If you have any questions or need assistance with the project, you can reach out to the project maintainers.
- Maintainer Name: Rusimka Dineva
- Maintainer Email: rusimka.dineva@scalefocus.com
######  We hope you enjoy using and contributing to the Blog Application! Happy coding!











 





