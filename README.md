Getting Started with TodoList App

### Installation and Running Guide

* install Java 21 (for backend)
* install Maven (for build backend)

*Frontend*
cd > frontend

### `npm install` 
Installs all the necessary dependencies for the frontend project (such as React, MUI, Axios, Tailwind CSS, etc.).

### `npm start` 
Starts the React development server and opens the app at [http://localhost:3000/home] to view it in your browser.
The page will automatically reload when you make changes to the source code.

*Backend*
cd > backend

### `mvn clean install` 
Cleans previous builds, downloads dependencies, and compiles the Java source code into a .jar file.

### `mvn spring-boot:run` 
Runs the Spring Boot application, which will expose the REST API at [http://localhost:8088] by default.

# Additional Notes
- Please make sure to configure your database connection properly in the application.properties file or via environment variables before running the backend.

- Once both frontend and backend are running, open your browser to http://localhost:3000 to use the Todo List app.

-If the frontend calls the backend API on a different port (e.g., localhost:8088), ensure CORS or proxy settings are correctly configured.

### Project Structure

*Frontend*

frontend/
│
├── public/                     
│   └── index.html              # Static HTML entry point
│
├── src/
│   ├── assets/                 # Images, icons, and styles
│   |
│   ├── components/             # Reusable UI components
│   │   ├── AlertDialog.tsx
│   │   ├── Modal.tsx
│   │   ├── Slidebar.tsx
│   │   ├── TodoList.tsx
│   │   └── Topbar.tsx
│   |
│   ├── pages/                  # Main pages
│   │   └── Home.tsx            
│   |
│   ├── services/               # API call logic
│   │   └── todoInstance.ts    
│   |
│   ├── todo.ts                 # TypeScript interfaces (Todo)
│   |
│   ├── App.tsx                 # Main app component
│
├── .env                        # Environment variables
├── package.json                # Dependencies and scripts
├── postcss.config.js           # Tailwind CSS config
├── tailwind.config.js          # Tailwind CSS config
└── tsconfig.json               # TypeScript compiler config

*Backend*

backend/
│
├── src/
│   └── main/
│       ├── java/com/example/backend/
|       |   |
│       │   ├── api/                                # REST API layer
│       │   │   ├── controller/                     # Controllers for handling requests
│       │   │   │   └── TodoListController.java
│       │   │   ├── request/                        # Request 
│       │   │   │   ├── InsertItemRequest.java
│       │   │   │   ├── SearchTodoRequest.java
│       │   │   │   └── UpdateItemRequest.java
│       │   │   ├── response/                       # Response 
│       │   │       ├── Response.java
│       │   │       ├── SearchResult.java
│       │   │       └── TodoListResponse.java
│       │   │
│       │   ├── config/                             # Configuration classes
│       │   │   └── WebConfig.java
│       │   │
│       │   ├── data/                               # Entities 
│       │   │   └── TodoList.java
│       │   │
│       │   ├── service/                            # Business logic
│       │      ├── TodoListService.java
│       │      └── TodoListServiceImpl.java
│       │   
│       └── resources/
│           └── application.properties              # Configuration file (DB, etc.)
│
├── pom.xml                                         # Maven dependencies and build config


### System Overview

 *Frontend:* Built with React and TypeScript, responsible for user interface and interacting with the backend via API.  
 *Backend:*  Built with Spring Boot (Java), handles API requests, processes data, and manages database operations.  

1. User open the web app and the frontend loads the Todo list page.  
2. Frontend sends a GET request to the backend API to fetch all todo items.  
3. Backend receives the request, retrieves data from the database, and sends it back as JSON.  
4. Frontend displays the todo items to the user.  
5. When the user adds a new todo item, frontend sends a POST request to the backend.  
6. Backend validates and saves the new item, then responds with a success status.  
7. Frontend updates the UI.  


### Additional tools

*Frontend*
- TypeScript – Adds type safety and helps catch errors during development
- MUI (Material UI) – Provides ready-made UI components and styling solutions
- Axios or fetch – Used to make HTTP requests to the backend APIs
- tailwindcss - Utility-first CSS framework for fast and flexible styling
- postcss - A tool to transform CSS with plugins for enhanced features

*Backend*
- Spring Boot – Framework to build RESTful APIs quickly and efficiently (use v 3.5.0)
- Spring Data JPA -  Simplifies database access using ORM (Object-Relational Mapping)
- lombok -  Reduces boilerplate code by auto-generating getters, setters, constructors, etc.
