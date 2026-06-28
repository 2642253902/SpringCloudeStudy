package org.example.service;

import org.example.entiey.Book;

public interface BookService {
    Book getBookById(int bid);

    public boolean setRemain(int bid, int count);

    public int getRemain(int bid);
}
