package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entiey.Book;
import org.example.entiey.User;



import java.util.List;

@Data
@AllArgsConstructor
public class UserBorrowDetail {
    User user;
    List<Book> bookList;
}
