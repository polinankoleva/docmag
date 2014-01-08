var database = db.getSiblingDB("docmag");
database.dropDatabase();
addTestUsers(db.getSiblingDB("docmag"));

function addTestUsers(database) {

// reset data base
  
  var userCollection = database.getCollection("users");
  
  var teacherProfile = {
    firstName: "Polina", 
    surname: "Nikolaeva",
    lastName: "Koleva", 
    email: "pkoleva@abv.bg", 
    faculty: "FMI",
    department: "Software technologies",
    degree: "doc. d-r"
  }
  var teacherId = ObjectId();
  userCollection.insert({_id: teacherId, username: "pkoleva", password: "pissme", type : "Teacher", profile: teacherProfile});

  var phdProfile = {
    firstName: "Teodora", 
    surname: "Lubomirova",
    lastName: "Toncheva", 
    email: "ttoncheva@abv.bg", 
    department: "FMI",
    studentIdentifier: "61387",
    educationForm: "Regular",
    educationDegree: "PHD",
    educationSubject: "artificial intelligence",
    educationYear: 1,
    gpa: 6.00,
    recordIdentifier: "phd007"
  }
  userCollection.insert({username: "ttoncheva", password: "pissme", type : "PHD", profile: phdProfile, scientificLeaderIds: [teacherId]});

  var masterProfile = {
    firstName: "Adriyana", 
    surname: "Dyankova",
    lastName: "Dyankova", 
    email: "adyankova@abv.bg", 
    department: "FMI",
    studentIdentifier: "61388",
    educationForm: "Regular",
    educationDegree: "Master",
    educationSubject: "software technologies",
    educationYear: 1,
    gpa: 6.00,
  }

  var userId = ObjectId();
  userCollection.insert({_id: userId, username: "adyankova", password: "pissme", type : "Student", profile: masterProfile});

  
  var documentCollection = database.getCollection("documents");

  var thesisProposal = {
    type: "ThesisProposal",
    status: "Unapproved",
    userId: userId,
    scientificLeaderIds: [],
    consultantIds: [],
    subject: "My thesis subject",
    annotation: "WTF",
    purpose: "No purpose",
    tasks: "No tasks yet",
    restrictions: "oh dear..",
    executionDeadline: new Date("October 15, 2015"),
    lastModified: new Date()
  }

  documentCollection.insert(thesisProposal);

}