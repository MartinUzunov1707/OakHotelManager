package bg.oakhotelmanager.service.impl;

import bg.oakhotelmanager.config.CommentsApiConfig;
import bg.oakhotelmanager.model.dto.AddCommentDTO;
import bg.oakhotelmanager.model.dto.CommentDTO;
import bg.oakhotelmanager.model.dto.EditCommentDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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

    public List<CommentDTO> getAllComments() {
        return commentsRestClient
                .get()
                .uri(apiConfig.getBaseUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<CommentDTO> getUserComments(Long id) {
        return commentsRestClient
                .get()
                .uri(apiConfig.getBaseUrl() + "user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
    public void deleteComment(Long id){
        commentsRestClient
                .delete()
                .uri(apiConfig.getBaseUrl() + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }
    public void editComment(Long id, EditCommentDTO data){
        commentsRestClient
                .post()
                .uri(apiConfig.getBaseUrl() + id)
                .accept(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve();
    }
}
