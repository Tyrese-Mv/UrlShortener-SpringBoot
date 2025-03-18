# TinyURL Clone

TinyURL Clone is a URL shortening service that allows users to shorten long URLs into more manageable links. The application is built with Spring Boot for the backend and HTML,CSS and JavaScript for the frontend.

You can access this project here [urlfusion.live](http://urlfusion.live/)
## Features

- Shorten long URLs into concise, shareable links
- Redirect users to the original URL when they visit a shortened link
- Track the number of times a shortened URL is accessed
- Custom domain support (e.g., `urlfusion.live`)
- User authentication with OAuth (Google and Facebook)

## Tech Stack

- **Backend:** Spring Boot, Java, JPA, MySQL
- **Frontend:** Thymeleaf, HTML,CSS and JavaScript
- **Authentication:** OAuth 2.0 (Google)
- **Hosting:** Heroku, Name.com (for custom domain setup)

## Getting Started


### Prerequisites

Ensure you have the following installed:
- Java 17+
- Node.js & npm
- MySQL (or another supported database)
- Heroku CLI (if deploying to Heroku)

### Installation

#### Backend (Spring Boot)
```sh
# Clone the repository
git clone https://github.com/yourusername/tinyurl-clone.git
cd tinyurl-clone/backend

# Configure database connection in application.properties

# Build and run the application
mvn clean install
mvn spring-boot:run
```


## Deployment

### Backend Deployment (Heroku)
```sh
# Login to Heroku
heroku login

# Create a new Heroku app
heroku create

# Deploy the backend
mvn clean package
heroku deploy:jar target/*.jar
```

### Frontend Deployment (Vercel/Netlify)
```sh
# Install Vercel CLI (or use Netlify CLI)
npm install -g vercel

# Deploy
vercel
```

### Custom Domain Setup
To set up a custom domain (e.g., `urlfusion.live`), configure DNS records on your domain registrar (Name.com) and link them to your hosting provider.


## Contributing

Feel free to submit pull requests or open issues to improve the project!


## Contact

For any inquiries, reach out via [musawenkosi106@gmail.com](musawenkosi106@gmail.com) or open an issue on GitHub.

