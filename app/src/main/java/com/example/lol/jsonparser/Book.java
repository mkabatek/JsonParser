package com.example.lol.jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lol on 7/18/17.
 * Model for book: contains title, author, and image(url)
 * Uses static method Book.fromJson to return ArrayList of Book
 */

class Book {

    String title;
    String author;
    String image;

    private Book(JSONObject book){

        try {

            title = "";
            author = "";
            image = "";

            /* Validate each field in case
             * one of three is missing we can still display
             * the other data.
             */
            if(book.has("title")){
                title = book.getString("title");
            }

            if(book.has("author")){
                author = book.getString("author");
            }

            if(book.has("imageURL")){
                image = book.getString("imageURL");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Factory method to convert an array of JSON objects into a list of book objects
    // Book.fromJson(jsonArray);
    public static ArrayList<Book> fromJson(JSONArray jsonObjects) {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                books.add(new Book(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return books;
    }

}
