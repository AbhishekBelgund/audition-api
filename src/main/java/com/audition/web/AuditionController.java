package com.audition.web;

import com.audition.common.logging.AuditionLogger;
import com.audition.model.AuditionPost;
import com.audition.model.Comment;
import com.audition.service.AuditionService;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuditionController {

    @Autowired
    AuditionService auditionService;
    @Autowired
    AuditionLogger logger;

    // TODO Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    /* Here I have added a paginationFilter query param which will filter out first 2 (minimum) to 10 (maximum)
        AuditionPost records. The intent of adding this filter is, we can add pagination to the response
        returned from /posts API thus returning small subsets of response. I have kept this filter as a simple
        implementation, but can be extended to add offset to pagination so that anhy range of records can be
        returned. For example: records from 35 to 50 can also be returned
    */
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts(@RequestParam (value = "paginationFilter") int paginationFilter) {
        // TODO Add logic that filters response data based on the query param
        logger.info(log,"Beginning of method getPosts");
        String pattern = "\\b([2-9]|10)\\b";
        String pagination = String.valueOf(paginationFilter);
        if(pagination.matches(pattern)){
            List<AuditionPost> auditionPostList = auditionService.getPosts();
            logger.info(log,"Valid paginationFilter in method getPosts");
            return auditionPostList.subList(0,paginationFilter);
        }
        logger.info(log,"Invalid paginationFilter. End of method getPosts");
        return auditionService.getPosts();
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPosts(@PathVariable("id") final String postId) {
        // TODO Add input validation
        logger.info(log,"Beginning of method getPosts");
        String pattern = "\\b([1-9][0-9]{0,1}|100)\\b";
        if(postId.matches(pattern)) {
            logger.info(log,"Valid postId in method getPosts");
            return auditionService.getPostById(postId);
        } else {
            logger.info(log,"Invalid postId in method getPosts");
            return new AuditionPost();
        }
    }

    // TODO Add additional methods to return comments for each post. Hint: Check https://jsonplaceholder.typicode.com/

    @RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getCommentsForPosts(@PathVariable("id") final String postId) {
        // TODO Add input validation
        logger.info(log,"Beginning of method getCommentsForPosts");
        String pattern = "\\b([1-9][0-9]{0,1}|100)\\b";
        if(postId.matches(pattern)) {
            logger.info(log,"Valid postId in method getCommentsForPosts");
            return auditionService.addCommentsByPostId(postId);
        } else {
            logger.info(log,"Invalid postId in method getCommentsForPosts");
            return new AuditionPost();
        }
    }

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Comment> getCommentsByPostId(@RequestParam("postId") final String postId) {
        // TODO Add input validation
        logger.info(log,"Beginning of method getCommentsByPostId");
        String pattern = "\\b([1-9][0-9]{0,1}|100)\\b";
        if(postId.matches(pattern)) {
            logger.info(log,"Valid postId in  method getCommentsByPostId");
            return auditionService.getCommentsByPostId(postId);
        } else {
            logger.info(log,"Invalid postId in method getCommentsByPostId");
            return new ArrayList<>();
        }
    }
}
