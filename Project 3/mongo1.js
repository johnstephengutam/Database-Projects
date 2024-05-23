db=db.getSiblingDB("school");
db.students.drop();
db.courses.drop();
db.students.insertOne ( {"sid" : "G11111", "lastName" : "Molloy", "firstName" : "Penny", "birthday": "06/15/2013", "courses" : [ { "courseID" : "CS112", "grade" : "A" } , { "courseID" : "CS201", "grade" : "B" } ] });
db.students.insertOne ({ "sid" : "G22222", "lastName" : "Owens", "firstName" : "Arthur", "birthday": "01/15/2004", "courses" :[ { "courseID" : "MATH113", "grade" : "c" }, {"courseID" : "CS201", "grade" : "a"}, { "courseID" : "MATH114", "grade" : "B" } ]});
db.students.insertOne ( {"sid" : "G33333", "lastName" : "Webserver", "firstName" : "Apache", "birthday" : "03/31/1992","courses" : [ { "courseID" : "MATH113", "grade" : "c" } , { "courseID" : "CS201", "grade" : "B" } , { "courseID" : "ENGL301", "grade" : "c" } ] });
db.students.insertOne ( {"sid": "G44444", "lastName" : "Ellison", "firstName" : "Larry", "birthday" : "07/05/1965", "courses" : [ { "courseID" : "ECON103", "grade" : "c" }, { "courseID" : "ECON104", "grade" : "F" }, { "courseID" : "PARK101", "grade" : "F" }, { "courseID" : "CS483", "grade": "F" } ] } );

db.courses.insertOne({"courseID" : "MATH113" ,
"professor" : "Dr. Calc",
"availableSeats" : 5});
db.courses.insertOne(
{"courseID" : "ECON103" ,
"professor" : "Dr. Einstein" ,
"availableSeats" : 0 });
db.courses.insertOne({"courseID" : "CS112" ,
"professor" : "Dr. Dijstria" ,
"availableSeats" : 22 }
);
db.courses.insertOne({"courseID" : "CS483" ,
"professor" : "Dr. Dantzig" ,
"availableSeats" : 22});
db.courses.insertOne({"courseID" : "CS201" ,
"professor" : "Dr. Dijstria",
"availableSeats" : 17});
db.courses.insertOne({
"courseID" : "MATH114" ,
"professor" : "Dr. Markov",
"availableSeats" : 11 });


