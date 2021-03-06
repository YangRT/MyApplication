// IMyAidlInterface.aidl
package com.example.administrator.myapplication;
import com.example.administrator.myapplication.aidl.Book;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();
    void addBook(in Book book);
}
