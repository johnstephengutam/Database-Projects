select * from Authors;
select * from PUBLICATIONS;


SELECT P.PUBLICATIONID, AUTHOR, TITLE, YEAR, TYPE, SUMMARY  
FROM PUBLICATIONS P 
JOIN AUTHORS A ON P.PUBLICATIONID = A.PUBLICATIONID 
WHERE UPPER(A.AUTHOR) LIKE UPPER('%Choi%') AND 
    UPPER(P.TITLE) LIKE UPPER('%Data-Driven%') AND 
    P.YEAR = '2016' AND 
    UPPER(P.TYPE) LIKE UPPER('long') 
ORDER BY PUBLICATIONID;