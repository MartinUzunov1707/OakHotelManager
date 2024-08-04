package bg.oakhotelmanager.service.impl;

import bg.oakhotelmanager.config.CommentsApiConfig;
import bg.oakhotelmanager.model.dto.AddCommentDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CommentService {
    private final RestClient commentsRestClient;
    private final CommentsApiConfig apiConfig;

    public CommentService(RestClient commentRestClient, CommentsApiConfig apiConfig) {
        this.commentsRestClient = commentRestClient;
        this.apiConfig = apiConfig;
    }
    public void createComment(AddCommentDTO data){
        commentsRestClient
                .post()
                .uri(apiConfig.getBaseUrl())
                .accept(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve();
    }
}
