package org.redplatoon.reddified.app.factories;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.redplatoon.reddified.app.models.Comment;

import java.util.ArrayList;

/**
 * Created by nmiano on 6/27/14 12:49 AM for Reddified
 */
public class CommentsFactory {

    /**
     * creates arraylist of comments from json
     * @param json
     * @return
     */
    public static ArrayList<Comment> newCommentsList(JsonObject json) {

        JsonArray children = json.get("data")
                                 .getAsJsonObject()
                                 .get("children").getAsJsonArray();

        return buildComments(children, new ArrayList<Comment>());
    }

    /**
     * Recursively creates comments tree
     * @param children
     * @return
     */
    private static ArrayList<Comment> buildComments(JsonArray children, ArrayList<Comment> comments) {
        if (children == null) {
            return comments;
        } else {
            for (JsonElement child : children) {
                Gson gson = new Gson();

                if(!child.getAsJsonObject().get("kind").getAsString().equals("more")) {
                    boolean repliesIsJsonObject = child.getAsJsonObject()
                                                       .get("data")
                                                       .getAsJsonObject()
                                                       .get("replies")
                                                       .isJsonObject();
                    JsonArray subChildren;
                    if (repliesIsJsonObject) {
                        subChildren = child.getAsJsonObject()
                                           .get("data")
                                           .getAsJsonObject()
                                           .get("replies")
                                           .getAsJsonObject()
                                           .get("data")
                                           .getAsJsonObject()
                                           .get("children")
                                           .getAsJsonArray();
                    } else {
                        subChildren = new JsonArray();
                    }

                    Comment comment = gson.fromJson(child.getAsJsonObject().get("data"), Comment.class);
                    System.out.println("COMMENT BOD: " + comment.getBody());

                    comment.setChildren(buildComments(subChildren, new ArrayList<Comment>()));
                    comments.add(comment);
                }
            }

            return comments;
        }
    }
}
