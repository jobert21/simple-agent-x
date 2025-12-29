### Description

A simple Spring AI project that uses Retrieval-Augmented Generation (RAG) to answer questions by combining an AI model with relevant data retrieved from a knowledge base.

### Details

This will run with the following services

- ollama - llama3.2:1b
- pgvector - pgvector/pgvector:pg16
- agent-x (current project)

### Running the project

```
docker compose up -d
```

### Endpoints

Endpoint to chat with the model

**GET**<br/>
http://localhost:8080/api/chat?q=your question

Endpoint to train

**POST**<br/>
http://localhost:8080/api/chat

```
{
    "text": "I am the strongest superhero. I am superman!",
    "metadata": {
        "link": "https://super.man",
        "title": "Heroes"
    }
}
```