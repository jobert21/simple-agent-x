package com.jbote;

import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jobert
 *
 */
@Service
public class RagService {
	@Autowired
	private VectorStore vectorStore;
	@Autowired
	private ChatClient chatClient;
	
	public String ask(String question) {
        var searchRequest = SearchRequest.builder()
                .query(question)
                .topK(5)
                .build();

        var docs = vectorStore.similaritySearch(searchRequest);

        String context = docs.stream()
                .map(d -> {
                	var text = d.getText();
                	var md = d.getMetadata();
                	var link = String.valueOf(md.getOrDefault("link", ""));
                	return """
                		TEXT:
                		%s
                		
                		LINK: %s
                	""".formatted(text, link);
                })
                .collect(Collectors.joining("\n---\n"));

        return chatClient
                .prompt()
                .system("""
                        You are a helpful assistant.
                        
                        Rules:
						- Answer naturally and directly. Do NOT mention “context”, “provided context”, “documents”, “sources”, or similar phrases.
						- Use ONLY the information in the reference material.
						- For every factual claim, include the supporting LINK(s) from the reference material.
						- If the answer is not in the reference material, say: "I don't know. Please contact your administrator."
						
						Output format:
						- Start with the answer (no preface).
						- Then a "Sources:" section listing the LINK(s) used.
                        """)
                .user(u -> u
                        .text("""
                        	REFERENCE MATERIAL (use internally):
                        	{context}
                        	
                        	QUESTION:
                        	{question}
                        	
                        	Answer directly. Do not mention the reference material.
                        """)
                        .param("context", context)
                        .param("question", question))
                .call()
                .content();
    }
}
