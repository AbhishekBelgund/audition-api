package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import java.util.ArrayList;
import java.util.List;

import com.audition.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuditionIntegrationClient {


    @Autowired
    private RestTemplate restTemplate;

    private final String postsUrl="https://jsonplaceholder.typicode.com/posts/";
    private final String commentsUrl="https://jsonplaceholder.typicode.com/comments";

    public List<AuditionPost> getPosts() {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        ResponseEntity<ArrayList> response = restTemplate.getForEntity(postsUrl,ArrayList.class);
        List<AuditionPost> responseBody = response.getBody();
        if(responseBody != null && !responseBody.isEmpty())
            return responseBody;
        return new ArrayList<>();
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            ResponseEntity<AuditionPost> response = restTemplate.getForEntity(postsUrl+id,AuditionPost.class);
            if(response != null)
                return response.getBody();
            return new AuditionPost();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                //Added e.getMessage() method so that the original exception message is not lost
                throw new SystemException(e.getMessage());
            }
        }
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.

    public List<Comment> getCommentsByPostIdIntoAuditionPost(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            ResponseEntity<ArrayList> response = restTemplate.getForEntity(postsUrl+id+"/comments", ArrayList.class);
            if(response != null)
                return response.getBody();
            return new ArrayList<>();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                        404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                //Added e.getMessage() method so that the original exception message is not lost
                throw new SystemException(e.getMessage());
            }
        }
    }

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
    public List<Comment> getCommentsByPostId(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            ResponseEntity<ArrayList> response = restTemplate.getForEntity(commentsUrl+"?postId="+id, ArrayList.class);
            if(response != null)
                return response.getBody();
            return new ArrayList<>();
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                        404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                //Added e.getMessage() method so that the original exception message is not lost
                throw new SystemException(e.getMessage());
            }
        }
    }
}
