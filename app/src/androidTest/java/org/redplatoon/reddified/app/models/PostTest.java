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

    public void testType() throws Exception {
        Post post = new Post();
        post.setUrl("http://i.imgur.com/aa6qVxc.jpg");
        post.setTypeFlag();
        assertEquals(true, post.isImage());
        assertEquals(false, post.isGif());
        assertEquals(false, post.isVideo());
        assertEquals(false, post.isText());
        assertEquals(false, post.isWebPage());

        Post post1 = new Post();
        post1.setUrl("http://i.imgur.com/aa6qVxc.png");
        post1.setTypeFlag();
        assertEquals(true, post1.isImage());
        assertEquals(false, post1.isGif());
        assertEquals(false, post1.isVideo());
        assertEquals(false, post1.isText());
        assertEquals(false, post1.isWebPage());

        Post post2 = new Post();
        post2.setUrl("http://i.imgur.com/aa6qVxc.gif");
        post2.setTypeFlag();
        assertEquals(false, post2.isImage());
        assertEquals(true, post2.isGif());
        assertEquals(false, post2.isVideo());
        assertEquals(false, post2.isText());
        assertEquals(false, post2.isWebPage());

        Post post3 = new Post();

        post3.setUrl("https://www.youtube.com/watch?v=I9gT_Nc41s4");
        post3.setTypeFlag();
        assertEquals(false, post3.isImage());
        assertEquals(false, post3.isGif());
        assertEquals(true, post3.isVideo());
        assertEquals(false, post3.isText());
        assertEquals(false, post3.isWebPage());

        Post post4 = new Post();
        post4.setPermalink("/r/subredditoftheday/comments/283zqg/june_14th_2014_rcoffee_because_without_it_there/");
        post4.setUrl("http://www.reddit.com/r/subredditoftheday/comments/283zqg/june_14th_2014_rcoffee_because_without_it_there/");
        post4.setTypeFlag();
        assertEquals(false, post4.isImage());
        assertEquals(false, post4.isGif());
        assertEquals(false, post4.isVideo());
        assertEquals(true, post4.isText());
        assertEquals(false, post4.isWebPage());

        Post post5 = new Post();
        post5.setUrl("http://www.slate.com/blogs/lexicon_valley/2014/06/09/ish_how_a_suffix_became_an_independent_word_even_though_it_s_not_in_all.html\n");
        post5.setTypeFlag();
        assertEquals(false, post5.isImage());
        assertEquals(false, post5.isGif());
        assertEquals(false, post5.isVideo());
        assertEquals(false, post5.isText());
        assertEquals(true, post5.isWebPage());

    }
}
