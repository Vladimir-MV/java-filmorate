# java-filmorate
Template repository for Filmorate project.

![Database schema](/QuickDBD-Sprint%2010.png)

SELECT *
FROM film AS f
JOIN ratingmpa AS r ON f.ratingid = r.ratingid
JOIN genre AS g ON f.filmid = g.filmid
JOIN likesusersfilms AS l ON f.filmid = l.filmid
GROUP BY f.filmid;

SELECT *
FROM userfilmorate AS u
JOIN friends AS f ON u.userid = f.userid
JOIN confirmedfriends AS c ON f.friendsid = c.friendsid
GROUP BY u.userid;
