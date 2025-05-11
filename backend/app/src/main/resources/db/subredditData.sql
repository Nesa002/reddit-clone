-- Ensure the uuid-ossp extension is available for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Insert 20 unique subreddits if they do not already exist

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'JavaProgramming', 'A community for Java enthusiasts.', 12500,
       'https://example.com/images/java-banner.png', 'https://example.com/images/java-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'JavaProgramming');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'PythonCommunity', 'A hub for Python programmers.', 18000,
       'https://example.com/images/python-banner.png', 'https://example.com/images/python-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'PythonCommunity');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'WebDevelopment', 'Tips and tricks for web developers.', 9500,
       'https://example.com/images/webdev-banner.png', 'https://example.com/images/webdev-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'WebDevelopment');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'DataScience', 'Discussion and resources on data science.', 20000,
       'https://example.com/images/datasci-banner.png', 'https://example.com/images/datasci-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'DataScience');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'MachineLearning', 'All things ML and AI.', 22000,
       'https://example.com/images/ml-banner.png', 'https://example.com/images/ml-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'MachineLearning');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Gaming', 'Talk about your favorite games.', 30000,
       'https://example.com/images/gaming-banner.png', 'https://example.com/images/gaming-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Gaming');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'MusicLovers', 'Share and discover new music.', 15000,
       'https://example.com/images/music-banner.png', 'https://example.com/images/music-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'MusicLovers');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'MoviesAndTV', 'Discuss your favorite movies and series.', 20000,
       'https://example.com/images/movies-banner.png', 'https://example.com/images/movies-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'MoviesAndTV');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Fitness', 'Stay fit and healthy.', 10000,
       'https://example.com/images/fitness-banner.png', 'https://example.com/images/fitness-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Fitness');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Travel', 'Share your travel experiences.', 8000,
       'https://example.com/images/travel-banner.png', 'https://example.com/images/travel-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Travel');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'TechNews', 'Latest technology news and trends.', 17000,
       'https://example.com/images/tech-banner.png', 'https://example.com/images/tech-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'TechNews');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Foodies', 'Share recipes and food experiences.', 9000,
       'https://example.com/images/food-banner.png', 'https://example.com/images/food-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Foodies');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Photography', 'Capture and share beautiful moments.', 11000,
       'https://example.com/images/photo-banner.png', 'https://example.com/images/photo-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Photography');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'BooksAndLiterature', 'Discuss books and literature.', 6000,
       'https://example.com/images/books-banner.png', 'https://example.com/images/books-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'BooksAndLiterature');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Science', 'Explore scientific discussions and news.', 14000,
       'https://example.com/images/science-banner.png', 'https://example.com/images/science-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Science');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'DIYProjects', 'Showcase your DIY projects.', 7000,
       'https://example.com/images/diy-banner.png', 'https://example.com/images/diy-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'DIYProjects');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'History', 'Discuss historical events and facts.', 5000,
       'https://example.com/images/history-banner.png', 'https://example.com/images/history-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'History');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'Cars', 'Talk about cars and automotive trends.', 8000,
       'https://example.com/images/cars-banner.png', 'https://example.com/images/cars-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'Cars');

INSERT INTO subreddit (id, name, description, member_count, banner_image_url, icon_image_url)
SELECT uuid_generate_v4(), 'CodingChallenges', 'Challenge yourself with coding problems.', 9500,
       'https://example.com/images/coding-banner.png', 'https://example.com/images/coding-icon.png'
    WHERE NOT EXISTS (SELECT 1 FROM subreddit WHERE name = 'CodingChallenges');
