package tn.esprit.arctic.lawappbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.arctic.lawappbackend.entities.ArticleStatistic;
import tn.esprit.arctic.lawappbackend.repositories.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleStatisticService {

    private final PostRepository postRepository;

    @Autowired
    public ArticleStatisticService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<ArticleStatistic> getArticleStatistics() {
        return postRepository.findAll().stream()
                .map(post -> new ArticleStatistic(post.getName(), (long) post.getViewCount()))
                .collect(Collectors.toList());
    }

}
