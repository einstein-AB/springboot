package com.study.springboot.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping ("/topics")
    public List<Topic> getTopics() {
        return topicService.getTopics();
    }

    @GetMapping ("/topics/{id}")
    public Optional<Topic> getTopic(@PathVariable Long id) {
        return topicService.getTopic(id);
    }

    @PostMapping ("/topics")
    public void addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
    }

    @PutMapping ("/topics/{id}")
    public void updateTopic(@PathVariable Long id,
                            @RequestBody Topic topic) {
        topicService.updateTopic(id, topic);
    }

    @DeleteMapping ("/topics/{id}")
    public void deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
    }
}
