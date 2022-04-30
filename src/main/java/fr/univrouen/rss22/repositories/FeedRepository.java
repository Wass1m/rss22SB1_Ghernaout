package fr.univrouen.rss22.repositories;

import fr.univrouen.rss22.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface FeedRepository  extends MongoRepository<Item, String> {

    List<Item> findAll();
    Item findByTitleAndPublished(String title, String published);
    Item findByTitleAndUpdated(String title, String updated);

}
