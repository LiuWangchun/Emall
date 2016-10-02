package com.lambda.service;

import com.lambda.pojo.Content;

public interface ItemService {
    
    Content getItemById(Integer itemId);
    
    Content addItem(Content item);

    Content updateItem(Content item);

    Boolean deleteItemById(Integer id);
    
}
