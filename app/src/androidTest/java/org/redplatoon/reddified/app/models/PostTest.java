package org.redplatoon.reddified.app.models;

import junit.framework.TestCase;

/**
 * Created by nmiano on 6/5/14 2:30 PM for Reddified
 */
public class PostTest extends TestCase {
    public void testThumbnail() throws Exception {
        Post post = new Post();
        post.setThumbnail("nsfw");
        assertEquals("http://www.reddit.com/static/nsfw2.png", post.getThumbnail());

        post.setThumbnail("self");
        assertEquals("http://www.reddit.com/static/self_default2.png", post.getThumbnail());

        post.setThumbnail("default");
        assertEquals("http://www.reddit.com/static/noimage.png", post.getThumbnail());

        post.setThumbnail("");
        assertEquals("http://www.reddit.com/static/noimage.png", post.getThumbnail());

        post.setThumbnail("http://www.imgur.com/poopic.png");
        assertEquals("http://www.imgur.com/poopic.png", post.getThumbnail());
    }
}
