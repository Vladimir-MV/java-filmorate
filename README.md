# java-filmorate
Template repository for Filmorate project.

QuickDBD-Sprint 10.png

SELECT *
FROM film AS f
INNER JOIN ratingmpa AS r ON f.ratingid = r.ratingid
INNER JOIN genre AS g ON f.genre = g.genre
INNER JOIN like AS l ON f.filmid = l.filmid
GROUP BY f.filmid;

SELECT *
FROM user AS u
INNER JOIN friends AS f ON u.userid = f.userid
INNER JOIN confirmedfriends AS c ON f.confirmedfriendsid = c.confirmedfriendsid
GROUP BY u.userid;