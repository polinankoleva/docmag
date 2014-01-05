var database = db.getSiblingDB("docmag");
database.dropDatabase();
addTestUsers(db.getSiblingDB("docmag"));

function addTestUsers(database) {

// reset data base
  
  var userCollection = database.getCollection("users");
  
  var teacherProfile = {
    firstName: "Polina", 
    lastName: "Koleva", 
    email: "pkoleva@abv.bg", 
    department: "FMI",
    degree: "doc. d-r"
  }
  var teacherId = ObjectId();
  userCollection.insert({_id: teacherId, userName: "pkoleva", password: "pissme", type : "UserTypeTeacher", profile: teacherProfile});

  var phdProfile = {
    firstName: "Teodora", 
    lastName: "Toncheva", 
    email: "ttoncheva@abv.bg", 
    department: "FMI",
    studentIdentifier: "61387",
    educationForm: "EducationFormRegular",
    educationDegree: "EducationDegreePHD",
    educationSubject: "artificial intelligence",
    educationYear: 1,
    gpa: 6.00,
    recordIdentifier: "phd007",
    scientificLeaderIds: [teacherId]
  }
  userCollection.insert({userName: "ttoncheva", password: "pissme", type : "UserTypePHDStudent", profile: phdProfile});

  var masterProfile = {
    firstName: "Adriyana", 
    lastName: "Dyankova", 
    email: "adyankova@abv.bg", 
    department: "FMI",
    studentIdentifier: "61388",
    educationForm: "EducationFormRegular",
    educationDegree: "EducationDegreeMaster",
    educationSubject: "software technologies",
    educationYear: 1,
    gpa: 6.00,
  }

  var userId = ObjectId();
  userCollection.insert({_id: userId, userName: "adyankova", password: "pissme", type : "UserTypeStudent", profile: masterProfile});

  
  var documentCollection = database.getCollection("documents");

  var thesisProposal = {
    type: "DocumentTypeThesisProposal",
    user_id: userId,
    scientificLeaderIds: [],
    conultantIds: [],
    subject: "My thesis subject",
    anotation: "WTF",
    purpose: "No purpose",
    tasks: "No tasks yet",
    restrictions: "oh dear..",
    executionDeadline: new Date("October 15, 2015")
  }

  documentCollection.insert(thesisProposal);

}