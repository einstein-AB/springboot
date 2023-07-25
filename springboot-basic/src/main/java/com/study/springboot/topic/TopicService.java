package com.study.springboot.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public List<Topic> getTopics() {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findAll().forEach(topic -> topics.add(topic));
        return topics;
    }

    public Optional<Topic> getTopic(Long id) {
        return topicRepository.findById(id);

    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    public void updateTopic(Long id, Topic topic) {
        topicRepository.save(topic);
    }

    public void deleteTopic(Long id) {
        //RawData.topics.removeIf(topic -> topic.getId().equals(id));
        topicRepository.deleteById(id);
    }
}
