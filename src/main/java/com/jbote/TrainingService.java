package com.jbote;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbote.dto.InfoDto;

/**
 * @author jobert
 */
@Service
public class TrainingService {
	private static final Logger log = LoggerFactory.getLogger(TrainingService.class);
	@Autowired
	VectorStore vectorStore;

	public void train(InfoDto info) {
		if (info != null) {
			String text = info.text();
			Map<String, Object> metadata = info.metadata();

			if (StringUtils.isEmpty(text)) {
				return;
			}
			Document.Builder builder = Document.builder().text(text);
			if (metadata != null) {
				for (String key : metadata.keySet()) {
					Object value = metadata.get(key);
					if (value == null) {
						continue;
					}
					builder.metadata(key, value);
				}
			}

			Document doc = builder.build();
			TokenTextSplitter splitter = TokenTextSplitter.builder().withChunkSize(300).withMinChunkLengthToEmbed(50)
					.build();
			List<Document> chunks = splitter.split(doc);

			log.info("Adding {} chunk(s).", chunks.size());
			vectorStore.add(chunks);
			log.info("Done adding in vector store.");
		}
	}
}
