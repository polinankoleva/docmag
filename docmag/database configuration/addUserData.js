var database = db.getSiblingDB("docmag");

var student = database.users.findOne({type: "Student"});
student.thesisDefenceMark = "Отличен 6";
student.graduationDate = new Date();
database.users.update({_id: student._id}, student);

addThesisProposalForStudentId(student._id, database);

var masterProfile = {
     firstName: "Теодора", 
     surname: "Любомирова",
     lastName: "Тончева", 
     email: "tton4ewa@abv.bg", 
     faculty: "ФМИ",
     studentIdentifier: "61388",
     educationForm: "Regular",
     educationDegree: "Master",
     educationSubject: "Софтуерно инженерство",
     educationYear: 1,
     gpa: 6.00
}

var userId = ObjectId();
database.getCollection("users").insert({
    _id: userId, 
     username: "testuUser", 
     password: "test", 
     type : "Student", 
     profile: masterProfile,
     thesisDefenceMark: "Отличен 6++",
     graduationDate: new Date("January 02, 2014")
  });

addThesisProposalForStudentId(userId, database);

function addThesisProposalForStudentId(studentId, database) {

  var teachers = database.users.find({"profile.department": "SoftwareTechnologies"}, {_id: 1}).toArray();
  var thesisProposal = {
    type: "ThesisProposal",
    status: "Submitted",
    userId: studentId,
    scientificLeaderIds: userIds(teachers).slice(1, 3),
    consultantIds: userIds(teachers).slice(0, 1),
    subject: "Система за магистри и докторанти - DocMag",
    annotation: "Разработка на система за преподаватели, магистри и докторанти към катедра Софтуерни Технологии във факултета по математика и информатика",
    purpose: "Да се улесни комуникацията между преподаватели и студенти като се предостави уеб-приложение за управление на документи и съхраняване на информация.",
    tasks: "Осъществяване на връзка със СУСИ за аутентикация на потребителите и звличане на профилна информация за всеки потребител" + 
    "\nРазработване на уеб-форми за попълване на информация" +
    "\nОсигуряване на функционалност за съхранение и изтеглене на информация под формата на pdf-документ. Поддръжка на различни роли на потребителите",
    restrictions: "Гъвкато решение за базата данн",
    executionDeadline: new Date("September 15, 2014"),
    lastModified: new Date()
  }

  var documentCollection = database.getCollection("documents");
  documentCollection.insert(thesisProposal);

}

function userIds(users) {
  var userIds = [];
  for (var userIndex in users) {
    userIds.push(users[userIndex]._id);
  }
  return userIds;
}