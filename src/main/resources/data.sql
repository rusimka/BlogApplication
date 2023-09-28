-- Insert users
INSERT INTO users (username, password, display_name)
VALUES ('user1', 'password1', 'User One'),
       ('user2', 'password2', 'User Two');

-- Insert blog posts
INSERT INTO blog_post (blog_post_title, blog_post_text, user_id)
VALUES ('First Blog Post', 'This is the first blog post.', 1),
       ('Second Blog Post', 'Another blog post here.', 1),
       ('Third Blog Post', 'A blog post from another user.', 2);

-- Insert tags
INSERT INTO tags (tag_name)
VALUES ('Technology'),
       ('Travel'),
       ('Food');

-- Insert blog post tags (many-to-many relationship)
INSERT INTO blog_post_tags (blog_post_id, tag_id)
VALUES (1, 1), -- First Blog Post is tagged with Technology
       (1, 2), -- First Blog Post is tagged with Travel
       (2, 1), -- Second Blog Post is tagged with Technology
       (3, 3); -- Third Blog Post is tagged with Food





