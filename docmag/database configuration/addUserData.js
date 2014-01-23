var database = db.getSiblingDB("docmag");
var student = database.users.findOne({type: "Student"});
student.thesisDefenceMark = "Отличен 6";
database.users.update({_id: student._id}, student);

addTestDataForStudent(student, database);
addThesisDefences(database);

function addTestDataForStudent(student, database) {

  var teachers = database.users.find({"profile.department": "SoftwareTechnologies"}, {_id: 1}).toArray();
  var thesisProposal = {
    type: "ThesisProposal",
    status: "Submitted",
    userId: student._id,
    scientificLeaderIds: userIds(teachers).slice(1, 4),
    consultantIds: userIds(teachers).slice(0, 1),
    subject: "Система за магистри и докторанти - DocMag",
    annotation: "Разработка на система за преподаватели, магистри и докторанти към катедра Софтуерни Технологии във факултета по математика и информатика",
    purpose: "Да се улесни комуникацията между преподаватели и студенти като се предостави уеб-приложение за управление на документи и съхраняване на информация.",
    tasks: "Осъществяване на връзка със СУСИ за аутентикация на потребителите" +
    "\nИзвличане на профилна информация за всеки потребител" + 
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